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
import com.beejeem.parser.value.Variable;

import java.util.List;

public class VariableDeclaratorListener extends AbstractListener {
    private String variableName;
    private List<Variable> listInitValues;

    public VariableDeclaratorListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override
    public void enterVariableDeclarator(bjmParser.VariableDeclaratorContext variableDeclaratorContext) {
        this.variableName = variableDeclaratorContext.variableDeclaratorId().getText();

        if (variableDeclaratorContext.variableInitializer() != null) {
            VariableInitializerListener variableInitializerListener = new VariableInitializerListener(this.getExecutionContext());
            variableDeclaratorContext.variableInitializer().enterRule(variableInitializerListener);

            if (variableInitializerListener.getVariable() != null) {
                this.setVariable(variableInitializerListener.getVariable());
            } else {
                this.setListInitValues(variableInitializerListener.getList());
            }
        }
    }

    public String getVariableName() {
        return variableName;
    }

    public List<Variable> getListInitValues() {
        return listInitValues;
    }

    public void setListInitValues(List<Variable> listInitValues) {
        this.listInitValues = listInitValues;
    }
}
