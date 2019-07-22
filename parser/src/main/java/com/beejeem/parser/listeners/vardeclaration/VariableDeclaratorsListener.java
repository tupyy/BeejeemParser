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
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Value;
import com.beejeem.parser.value.Variable;

import java.util.HashMap;
import java.util.Map;

public class VariableDeclaratorsListener extends AbstractListener {

    private Map<String, Variable> variables = new HashMap<>();

    public VariableDeclaratorsListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterVariableDeclarators(bjmParser.VariableDeclaratorsContext ctx) {
        Type valueType = this.getExecutionContext().resolveType(ctx.typeType().getText());

        for (bjmParser.VariableDeclaratorContext variableDeclaratorContext: ctx.variableDeclarator()) {
            VariableDeclaratorListener variableDeclaratorListener = new VariableDeclaratorListener(this.getExecutionContext());
            variableDeclaratorListener.enterVariableDeclarator(variableDeclaratorContext);

            Value<?> newValue = valueType.createValue();
            if (variableDeclaratorListener.getVariable() != null) {
                newValue.setValue(variableDeclaratorListener.getVariable().asValue());
                this.getVariables().put(variableDeclaratorListener.getVariableName(), newValue);
            } else {
                this.getVariables().put(variableDeclaratorListener.getVariableName(), valueType.createValue());
            }
        }
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }
}
