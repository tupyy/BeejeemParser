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

package com.beejeem.parser.listeners.collections;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InterpreterException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.listeners.expression.ExpressionListener;
import com.beejeem.parser.value.IntegerValue;
import com.beejeem.parser.value.Value;

import java.util.ArrayList;
import java.util.List;

public class CollectionCallListener extends AbstractListener {

    public CollectionCallListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterCollectionCallExpression(bjmParser.CollectionCallExpressionContext ctx) {
        CollectionCallListener collectionCallListener =
                new CollectionCallListener(this.getExecutionContext());
        ctx.collectionCall().enterRule(collectionCallListener);
        this.setValue(collectionCallListener.getValue());
    }

    public void enterCollectionCall(bjmParser.CollectionCallContext ctx) {
        String variableName = ctx.Identifier().get(0).getText();
        String methodName = ctx.Identifier().get(1).getText();

        List<Value> args = new ArrayList<>();
        if (ctx.exprList() != null) {
            for(bjmParser.ExpressionContext expressionContext: ctx.exprList().expression()) {
                ExpressionListener expressionListener =
                        new ExpressionListener(this.getExecutionContext());
                expressionContext.enterRule(expressionListener);
                args.add(expressionListener.getValue());
            }
        }

        List<Value> valueList = this.getExecutionContext().getCurrentStackframe().getList(variableName);
        try {
            if (valueList != null) {
                this.setValue(invokeListGetMethod(valueList, methodName, args));
            }
        }
        catch (InterpreterException ex) {
            throw new InterpreterException(String.format("Line %d: Error: %s", ctx.start.getLine(),ex.getMessage()));
        }
     }

    /**
     * Invoke list method like: get, add and size
     * @param list list to be invoked
     * @param methodName method name. It has to be "get", "add", "size"
     * @param args list of arguments
     * @return Value from list
     * @throws InterpreterException if the method is unknown or the number(type) of args is not correct.
     */
    private Value invokeListGetMethod(List<Value> list, String methodName, List<Value> args) throws InterpreterException{

        if (args.size() != 1 && !methodName.equals("size")) {
            throw new InterpreterException("Invalid number of arguments");
        }

        if (methodName.equals("get")) {
            if ( ! (args.get(0) instanceof IntegerValue)) {
                throw new InterpreterException("Argument type mismatch. Integer value is required");
            }
            return list.get((Integer) args.get(0).get());
        } else if (methodName.equals("add")) {
            list.add(args.get(0));
            return args.get(0);
        } else if (methodName.equals("size")) {
            return new IntegerValue(list.size());
        }

        throw new InterpreterException("Method unknown");
    }
}
