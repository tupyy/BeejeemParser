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

package com.beejeem.parser.function;

import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.listeners.functionlisteners.ParametersListener;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Value;

import java.util.List;

public interface Function {

    /**
     * Get the name of the function
     * @return function name
     */
    public String getName();

    /**
     * Get the parameter list
     * @return parameter list
     */
    public List<ParametersListener.Parameter> getParameters();

    /**
     * Get the result type
     * @return result type
     */
    public Type getResultType();

    /**
     * Execute the function
     * @param executionContext execution contexts
     * @param args arguments
     * @return Value
     */
    Value execute(ExecutionContext executionContext, List<Value> args);
}
