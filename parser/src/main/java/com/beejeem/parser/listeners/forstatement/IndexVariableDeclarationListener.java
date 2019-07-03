/*
 * Beejeem2 Copyright 2019, Cosmin Tupangiu
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.beejeem.parser.listeners.forstatement;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.listeners.assignment.AssignmentListener;
import com.beejeem.parser.listeners.vardeclaration.VariableDeclaratorListener;
import com.beejeem.parser.value.IntegerValue;
import com.beejeem.parser.value.Value;

public class IndexVariableDeclarationListener extends AbstractListener {

    private Value value;
    public IndexVariableDeclarationListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterIndexVariableDeclaration(bjmParser.IndexVariableDeclarationContext ctx) {
        if (ctx.variableDeclarator() != null) {
            VariableDeclaratorListener variableDeclaratorListener =
                    new VariableDeclaratorListener(this.getExecutionContext());
            ctx.variableDeclarator().enterRule(variableDeclaratorListener);

            //check if the variable is already defined
            if (this.getExecutionContext().getCurrentStackframe()
                    .hasVariable(variableDeclaratorListener.getVariableName())) {
                throw new InvalidOperationException(
                        String.format("Line %d: Variable %s already defined in this scope.",
                                ctx.start.getLine(),variableDeclaratorListener.getVariableName()));
            }

            ctx.variableDeclarator().enterRule(variableDeclaratorListener);
            Value indexValue = this.getExecutionContext().resolveType(ctx.typeType().getText()).createValue();
            indexValue.set(variableDeclaratorListener.getValue());
            if ( !(indexValue instanceof IntegerValue) ) {
                throw new InvalidOperationException(
                        String.format("Line %d: For variables must be integers.", ctx.start.getLine()));
            }
            //push the value on the stack
            this.getExecutionContext().getCurrentStackframe()
                    .declareVariable(variableDeclaratorListener.getVariableName(), indexValue);
        } else {
            AssignmentListener assignmentListener = new AssignmentListener(this.getExecutionContext());
            ctx.assignment().enterRule(assignmentListener);
            this.setValue(assignmentListener.getValue());
        }
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
