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
import com.beejeem.parser.listener.ExpressionListener;
import com.beejeem.parser.function.RuntimeFunction;
import com.beejeem.parser.function.RuntimeFunctionFactory;
import com.beejeem.parser.value.BooleanValue;
import com.beejeem.parser.pascalParser.ExpressionContext;
import com.beejeem.parser.value.Value;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Stack;

/**
 * the execution context.
 *
 * @author tom
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
      consoleInput = System.in;
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

   public Value invokeFunction(String name, List<Value> values) {
      final RuntimeFunction runtimeFunction = runtimeFunctionFactory.getRuntimeFunction(name);
      if (null != runtimeFunction) {
         return runtimeFunction.execute(this, values);
      } else {
         final FunctionOrProcedureDefinition functionOrProcedureDefinition = resolveFunctionOrProcedure(name);
         if (null != functionOrProcedureDefinition) {
            return functionOrProcedureDefinition.execute(this, values);
         } else {
            throw new InterpreterException("Unknown procedure '" + name + "'");
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
    * walk the stack, top to bottom trying to find the function or procedure
    */
   private FunctionOrProcedureDefinition resolveFunctionOrProcedure(String name) {
      for (final StackFrame stackFrame : stack) {
         final FunctionOrProcedureDefinition functionOrProcedureDefinition = stackFrame.getFunctionOrProcedureDefinition(name);
         if (null != functionOrProcedureDefinition) {
            return functionOrProcedureDefinition;
         }
      }
      return null;
   }

   /**
    * walk the stack, top to bottom trying to find the variable or constant
    */
   public Value resolveVariable(String name) {
      for (int i = 0; i < stack.size(); i++) {
         final StackFrame stackFrame = stack.get(i);
         Value value = stackFrame.getVariable(name);
         if (null == value) {
            value = stackFrame.getConstant(name);
         }
         if (null != value) {
            return value;
         }
      }
      /*
       * it could be a function which takes no parameters, such as "readkey".
       */
      final RuntimeFunction runtimeFunction = runtimeFunctionFactory.getRuntimeFunction(name);
      if (null != runtimeFunction) {
         /*
          * no parameters
          */
         return runtimeFunction.execute(this, null);
      } else {
         final FunctionOrProcedureDefinition functionOrProcedureDefinition = resolveFunctionOrProcedure(name);
         if (null != functionOrProcedureDefinition) {
            /*
             * no parameters
             */
            return functionOrProcedureDefinition.execute(this, null);
         }
      }
      throw new InterpreterException("Unable to resolve '" + name + "'");
   }

   /**
    * test an expression. used in while loop, etc
    */
   public boolean testExpression(ExpressionContext expressionContext) {
      final ExpressionListener expressionListener = new ExpressionListener(this);
      expressionListener.enterExpression(expressionContext);
      final Value cond = expressionListener.getValue();
      if (cond instanceof BooleanValue) {
         return ((BooleanValue) cond).isValue();
      } else {
         throw new InterpreterException("Expected Boolean");
      }
   }
}
