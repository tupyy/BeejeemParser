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
import com.beejeem.parser.exception.InterpreterException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.type.FloatType;
import com.beejeem.parser.type.IntegerType;
import com.beejeem.parser.type.StringType;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.ListValue;
import com.beejeem.parser.value.Variable;

import java.util.ArrayList;
import java.util.List;

public class CollectionVariableDeclarator extends AbstractListener {

    private String variableName;

    public CollectionVariableDeclarator(ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override
    public void enterCollectionVariableDeclarator(bjmParser.CollectionVariableDeclaratorContext ctx) {
        if (ctx.List() != null) {
            this.enterListDeclaration(ctx);
        } // TODO else enter map context
    }

    private void enterListDeclaration(bjmParser.CollectionVariableDeclaratorContext listContext) {
        Type listType = this.getExecutionContext().resolveType(listContext.typeType().getText());

        // Get initial list values if any
        VariableDeclaratorListener variableDeclaratorListener =
                new VariableDeclaratorListener(this.getExecutionContext());
        listContext.variableDeclarator().enterRule(variableDeclaratorListener);
        try {
            List<Variable> variables = variableDeclaratorListener.getListInitValues();
            if (listType instanceof FloatType) {
                ListValue<Float> list = new ListValue<>(listType);
                if (variableDeclaratorListener.getListInitValues() != null) {
                    list.fromList(this.toFloatList(variables));
                }
                this.setVariable(list);
            } else if (listType instanceof IntegerType) {
                ListValue<Integer> list = new ListValue<>(listType);
                if (variableDeclaratorListener.getListInitValues() != null) {
                    list.fromList(this.toIntegerList(variables));
                }
                this.setVariable(list);
            } else if (listType instanceof StringType) {
                ListValue<String> list = new ListValue<>(listType);
                if (variableDeclaratorListener.getListInitValues() != null) {
                    list.fromList(this.toStringList(variables));
                }
                this.setVariable(list);
            } else {
                ListValue<Boolean> list = new ListValue<>(listType);
                if (variableDeclaratorListener.getListInitValues() != null) {
                    list.fromList(this.toBooleanList(variables));
                }
                this.setVariable(list);
            }
        } catch (ClassCastException ex) {
            throw new InterpreterException(String.format("Line %d: Type mismatch.",listContext.start.getLine()));
        }
        this.setVariableName(variableDeclaratorListener.getVariableName());
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    /**
     * Return a list of strings from the list of variables
     * @param variables
     * @return list of strings
     */
    private List<String> toStringList(List<Variable> variables) throws ClassCastException {
        List<String> values = new ArrayList<>();
        for (Variable variable: variables) {
            values.add((String) variable.asValue().get());
        }

        return values;
    }

    /**
     * Return a list of Integer from the list of variables
     * @param variables
     * @return list of Integer
     */
    private List<Integer> toIntegerList(List<Variable> variables) throws ClassCastException {
        List<Integer> values = new ArrayList<>();
        for (Variable variable: variables) {
            values.add((Integer) variable.asValue().get());
        }
        return values;
    }

    /**
     * Return a list of Float from the list of variables
     * @param variables
     * @return list of Float
     */
    private List<Float> toFloatList(List<Variable> variables) throws ClassCastException {
        List<Float> values = new ArrayList<>();
        for (Variable variable: variables) {
            values.add((Float) variable.asValue().get());
        }
        return values;
    }

    /**
     * Return a list of Boolean from the list of variables
     * @param variables
     * @return list of Boolean
     */
    private List<Boolean> toBooleanList(List<Variable> variables) throws ClassCastException {
        List<Boolean> values = new ArrayList<>();
        for (Variable variable: variables) {
            values.add((Boolean) variable.asValue().get());
        }
        return values;
    }
}
