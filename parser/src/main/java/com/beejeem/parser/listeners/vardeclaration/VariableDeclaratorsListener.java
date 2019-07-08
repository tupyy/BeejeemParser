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
import com.beejeem.parser.type.ListType;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableDeclaratorsListener extends AbstractListener {

    private final Type valueType;
    private Map<String, Value> values = new HashMap<>();
    private Map<String, List<Value>> lists = new HashMap<>();

    public VariableDeclaratorsListener(ExecutionContext executionContext, Type valueType) {
        super(executionContext);
        this.valueType = valueType;
    }

    public void enterVariableDeclarators(bjmParser.VariableDeclaratorsContext ctx) {
        for (bjmParser.VariableDeclaratorContext variableDeclaratorContext: ctx.variableDeclarator()) {
            VariableDeclaratorListener variableDeclaratorListener = new VariableDeclaratorListener(this.getExecutionContext());
            variableDeclaratorListener.enterVariableDeclarator(variableDeclaratorContext);

            if ( !(this.getValueType() instanceof ListType) ) {
                Value newValue = this.getValueType().createValue();
                if (variableDeclaratorListener.getValue() != null) {
                    newValue.set(variableDeclaratorListener.getValue());
                    this.getValues().put(variableDeclaratorListener.getVariableName(), newValue);
                } else {
                    this.getValues().put(variableDeclaratorListener.getVariableName(), this.getValueType().createValue());
                }
            } else {
                if (variableDeclaratorListener.getValueList() != null) {
                    this.getLists().put(variableDeclaratorListener.getVariableName(), variableDeclaratorListener.getValueList());
                } else {
                    this.getLists().put(variableDeclaratorListener.getVariableName(), ((ListType) this.getValueType()).createList());
                }
            }
        }
    }

    public Map<String, Value> getValues() {
        return values;
    }

    public Map<String, List<Value>> getLists() {
        return lists;
    }

    public void setLists(Map<String, List<Value>> lists) {
        this.lists = lists;
    }

    public Type getValueType() {
        return valueType;
    }
}
