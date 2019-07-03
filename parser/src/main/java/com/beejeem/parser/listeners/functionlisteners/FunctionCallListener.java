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
import com.beejeem.parser.listeners.expression.ExpressionListener;
import com.beejeem.parser.value.Value;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallListener extends AbstractListener {

    private Value value;

    public FunctionCallListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterFunctionCall(bjmParser.FunctionCallContext ctx) {
        List<Value> args = new ArrayList<>();
        if (ctx.exprList() != null) {
            for (bjmParser.ExpressionContext expressionContext : ctx.exprList().expression()) {
                ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
                expressionContext.enterRule(expressionListener);
                args.add(expressionListener.getValue());
            }
        }
        this.setValue(this.getExecutionContext().invokeFunction(ctx.Identifier().getText(),args));
    }

    @Override
    public void enterFunctionCallExpression(bjmParser.FunctionCallExpressionContext ctx) {
        FunctionCallListener functionCallListener =
                new FunctionCallListener(this.getExecutionContext());
        ctx.functionCall().enterRule(functionCallListener);
        this.setValue(functionCallListener.getValue());
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
