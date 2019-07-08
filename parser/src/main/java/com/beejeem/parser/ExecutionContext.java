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

import com.beejeem.parser.exception.InterpreterException;
import com.beejeem.parser.function.Function;
import com.beejeem.parser.function.UserDefinedFunction;
import com.beejeem.parser.function.RuntimeFunction;
import com.beejeem.parser.function.RuntimeFunctionFactory;
import com.beejeem.parser.listeners.functionlisteners.ParametersListener;
import com.beejeem.parser.type.*;
import com.beejeem.parser.value.Value;
import com.beejeem.parser.value.Variable;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * the execution context.
 */
public class ExecutionContext {
    /**
     * output
     */
    private final OutputStream consoleOut;
    /**
     * stack
     */
    private final Stack<StackFrame> stack = new Stack<StackFrame>();
    /**
     * function factory
     */
    private final RuntimeFunctionFactory runtimeFunctionFactory;

    public ExecutionContext() {
        runtimeFunctionFactory = new RuntimeFunctionFactory();
        consoleOut = System.out;
    }

    public ExecutionContext(OutputStream consoleOut) {
        runtimeFunctionFactory = new RuntimeFunctionFactory();
        this.consoleOut = consoleOut;
    }

    public OutputStream getConsoleOut() {
        return consoleOut;
    }

    public StackFrame getCurrentStackframe() {
        return stack.peek();
    }

    public Stack<StackFrame> getStack() {
        return stack;
    }

    public Variable invokeFunction(String name, List<Variable> args) {
        final RuntimeFunction runtimeFunction = runtimeFunctionFactory.getRuntimeFunction(name);
        if (null != runtimeFunction) {
            return runtimeFunction.execute(this, args);
        } else {
            final UserDefinedFunction userDefinedFunction = resolveFunction(name);
            if (userDefinedFunction != null) {
                return userDefinedFunction.execute(this, args);
            } else {
                throw new InterpreterException("Unknown function '" + name + "'");
            }
        }
    }

    public StackFrame popStackframe() {
        return stack.pop();
    }

    public StackFrame pushStackframe() {
        final StackFrame stackFrame = new StackFrame();
        stack.push(stackFrame);
        return stackFrame;
    }

    /**
     * walk the stack, top to bottom trying to find the variable
     */
    public Variable resolveVariable(String name) {
        for (final StackFrame stackFrame : stack) {
            Variable variable = stackFrame.getVariable(name);
            if (variable != null) {
                return variable;
            }
        }
        throw new InterpreterException("Variable not found: "+name);
    }

    /**
     * walk the stack, top to bottom trying to find the type
     */
    public Type resolveType(String name) {
        final Type t = resolveBuiltinType(name);
        if (t != null) {
            return t;
        }

        throw new InterpreterException("Unable to resolve '" + name + "'");
    }

    private Type resolveBuiltinType(String name) {
        if (name.toLowerCase().compareTo("int") == 0) {
            return new IntegerType();
        } else if (name.toLowerCase().compareTo("float") == 0) {
            return new FloatType();
        } else if (name.toLowerCase().compareTo("string") == 0) {
            return new StringType();
        } else if (name.toLowerCase().compareTo("bool") == 0) {
            return new BooleanType();
        } else if (name.toLowerCase().compareTo("list") == 0) {
            return new ListType();
        }
        return null;
    }

    /**
     * walk the stack, top to bottom trying to find the function or procedure
     */
    private UserDefinedFunction resolveFunction(String name) {
        for (final StackFrame stackFrame : stack) {
            final UserDefinedFunction userDefinedFunction = stackFrame.getFunctionDefinition(name);
            if (userDefinedFunction != null) {
                return userDefinedFunction;
            }
        }
        return null;
    }

}
