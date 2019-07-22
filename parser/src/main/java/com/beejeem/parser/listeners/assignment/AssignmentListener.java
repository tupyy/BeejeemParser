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

package com.beejeem.parser.listeners.assignment;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.listeners.expression.ExpressionListener;
import com.beejeem.parser.value.Value;
import com.beejeem.parser.value.Variable;

public class AssignmentListener extends AbstractListener {
    public AssignmentListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterAssignment(bjmParser.AssignmentContext assignmentContext) {
        String identifier = assignmentContext.Identifier().getText();
        Variable variable = this.getExecutionContext().getCurrentStackframe().getVariable(identifier);
        if (variable == null) {
            throw new InvalidOperationException(
                    String.format("Line %d: Variable not defined: %s", assignmentContext.start.getLine(), identifier));
        }
        ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
        assignmentContext.expression().enterRule(expressionListener);
        if (variable.isValue()) {
            ((Value<?>)variable).setValue((Value)expressionListener.getVariable());
            this.setVariable(variable);
        } else if (variable.isList()) {

        }

    }
}
