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

import com.beejeem.grammar.bjmParser;
import com.beejeem.grammar.bjmParser.BlockContext;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.StackFrame;
import com.beejeem.parser.listeners.BlockListener;
import com.beejeem.parser.listeners.functionlisteners.ParametersListener.Parameter;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Value;
import com.beejeem.parser.value.Variable;

import java.util.List;

public class UserDefinedFunction implements Function{
    private final String name;
    private final List<Parameter> parameters;
    private final Type resultType;
    private final bjmParser.BlockContext blockContext;

    public UserDefinedFunction(String name, List<Parameter> parameters, BlockContext blockContext, Type resultTypeName) {
        this.name = name;
        this.parameters = parameters;
        this.resultType = resultTypeName;
        this.blockContext = blockContext;
    }

    @Override
    public Variable execute(ExecutionContext executionContext, List<Variable> args) {
        /*
         * new stack frame
         */
        final StackFrame stackFrame = executionContext.pushStackframe();
        /*
         * put the variables into scope
         * TODO check type
         */
        int i = 0;
        for (final Parameter parameter : parameters) {
            stackFrame.declareVariable(parameter.getName(), args.get(i++));
        }

        // put the return value on the stack with the name of the function
        stackFrame.declareVariable(getName(), resultType.createValue());

        /*
         * run the block
         */
        final BlockListener blockListener = new BlockListener(executionContext);
        blockListener.enterBlock(blockContext);
        Variable ret = executionContext.resolveVariable(getName());

        if (ret instanceof Value) {
            ((Value<?>)ret).setValue(blockListener.getVariable().asValue());
        }
        executionContext.popStackframe();

        return ret;
    }

    public BlockContext getBlockContext() {
        return blockContext;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public Type getResultType() {
        return resultType;
    }

}
