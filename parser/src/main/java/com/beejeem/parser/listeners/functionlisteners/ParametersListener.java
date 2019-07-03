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

package com.beejeem.parser.listeners.functionlisteners;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Value;

import java.util.ArrayList;
import java.util.List;

public class ParametersListener extends AbstractListener {

    /**
     * Holds the value and the type of a function parameter
     */
    public class Parameter {

        private final String name;
        private final Value value;
        private final Type parameterType;

        public Parameter(String name, Value value, Type parameterType) {
            this.name = name;
            this.value = value;
            this.parameterType = parameterType;
        }

        public Value getValue() {
            return value;
        }

        public Type getParameterType() {
            return parameterType;
        }

        public String getName() {
            return name;
        }
    }

    private List<Parameter> parameters = new ArrayList<>();

    public ParametersListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterParameters(bjmParser.ParametersContext parametersContext) {
        for (bjmParser.ParameterContext parameterContext: parametersContext.parameter()) {
            Type parameterType = this.getExecutionContext().resolveType(parameterContext.typeType().getText());
            Parameter parameter =
                    new Parameter(parameterContext.Identifier().getText(), parameterType.createValue(),parameterType);
            parameters.add(parameter);
        }
    }

    public List<Parameter> getParameters() {
        return parameters;
    }


}
