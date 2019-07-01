/*
 *
 *    * Beejeem2 Copyright 2019, Cosmin Tupangiu
 *    *
 *    *   This program is free software: you can redistribute it and/or modify
 *    *   it under the terms of the GNU General Public License as published by
 *    *   the Free Software Foundation, either version 3 of the License, or
 *    *   (at your option) any later version.
 *    *
 *    *    This program is distributed in the hope that it will be useful,
 *    *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    *    GNU General Public License for more details.
 *    *
 *    *    You should have received a copy of the GNU General Public License
 *    *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.beejeem.parser;

import com.beejeem.grammar.bjmParser;
import com.beejeem.grammar.bjmParser.BlockContext;
import com.beejeem.parser.exception.NotImplementedException;
import com.beejeem.parser.listeners.BlockListener;
import com.beejeem.parser.listeners.ParametersListener.Parameter;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Value;

import java.util.List;

public class FunctionDefintion {
    private final String name;
    private final List<Parameter> parameters;
    private final String resultTypeName;
    private final bjmParser.BlockContext blockContext;

    /**
     * function
     */
    public FunctionDefintion(String name, List<Parameter> parameters, BlockContext blockContext, String resultTypeName) {
        this.name = name;
        this.parameters = parameters;
        this.resultTypeName = resultTypeName;
        this.blockContext = blockContext;
    }

    public Value execute(ExecutionContext executionContext, List<Value> args) {
        /*
         * new stack frame
         */
        final StackFrame stackFrame = executionContext.pushStackframe();
        /*
         * put the variables into scope
         */
        int i = 0;
        for (final Parameter parameter : parameters) {
            stackFrame.declareVariable(parameter.getName(), args.get(i++));
        }
        /*
         * its a function, and we need to put a variable into scope, with the name of the function and type of the function return type
         */

        final Type resultType = executionContext.resolveType(resultTypeName);
        stackFrame.declareVariable(getName(), resultType.createValue());

        /*
         * run the block
         */
        final BlockListener blockListener = new BlockListener(executionContext);
        blockListener.enterBlock(blockContext);
        Value ret = executionContext.resolveVariable(getName());
        executionContext.popStackframe();

        return ret;
    }

    public BlockContext getBlockContext() {
        return blockContext;
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public String getResultTypeName() {
        return resultTypeName;
    }

    public boolean isFunction() {
        return null != getResultTypeName();
    }
}
