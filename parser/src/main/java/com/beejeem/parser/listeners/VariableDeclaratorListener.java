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

package com.beejeem.parser.listeners;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.value.Value;
import com.beejeem.parser.value.VoidValue;

public class VariableDeclaratorListener extends AbstractListener {
    private Value value;
    private String variableName;

    public VariableDeclaratorListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override
    public void enterVariableDeclarator(bjmParser.VariableDeclaratorContext variableDeclaratorContext) {
        this.variableName = variableDeclaratorContext.variableDeclaratorId().getText();

        this.setValue(new VoidValue());
        if (variableDeclaratorContext.variableInitializer() != null) {
            VariableInitializerListener variableInitializerListener = new VariableInitializerListener(this.getExecutionContext());
            variableDeclaratorContext.variableInitializer().enterRule(variableInitializerListener);
            this.setValue(variableInitializerListener.getValue());
        }
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }
}
