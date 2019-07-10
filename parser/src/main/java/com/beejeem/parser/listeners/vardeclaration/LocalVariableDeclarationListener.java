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

package com.beejeem.parser.listeners.vardeclaration;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Variable;

import java.util.Map;

public class LocalVariableDeclarationListener extends AbstractListener {

    public LocalVariableDeclarationListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterLocalVariableDeclaration(bjmParser.LocalVariableDeclarationContext ctx) {
        Type variableType = this.getExecutionContext().resolveType(ctx.typeType().getText());
        VariableDeclaratorsListener variableDeclaratorsListener =
                new VariableDeclaratorsListener(this.getExecutionContext(), variableType);
        ctx.variableDeclarators().enterRule(variableDeclaratorsListener);

        // push variables to current stack
        for (Map.Entry<String, Variable> entry: variableDeclaratorsListener.getVariables().entrySet()) {
            if (!this.getExecutionContext().getCurrentStackframe().hasVariable(entry.getKey())) {
                if (variableType.isEqual(entry.getValue().getType())) {
                    this.getExecutionContext().getCurrentStackframe().declareVariable(entry.getKey(), entry.getValue());
                } else {
                    throw new InvalidOperationException(
                            String.format("Line %d: Type mistmatch.",ctx.start.getLine(),entry.getKey()));
                }
            } else {
                throw new InvalidOperationException(
                        String.format("Line %d: Variable %s already defined.",ctx.start.getLine(),entry.getKey()));
            }
        }
    }

}
