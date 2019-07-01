/*
 *  Beejeem2 Copyright 2019, Cosmin Tupangiu
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.beejeem.parser.listeners.expression;

import com.beejeem.grammar.bjmLexer;
import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.value.Value;

public class CompExpressionListener extends AbstractListener {
    private Value value;

    public CompExpressionListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterCompExpression(bjmParser.CompExpressionContext ctx) {
        ExpressionListener leftExprListener = new ExpressionListener(this.getExecutionContext());
        leftExprListener.enterRule((bjmParser.ExpressionContext) ctx.children.get(0));
        Value leftValue = leftExprListener.getValue();

        ExpressionListener rightExprListener = new ExpressionListener(this.getExecutionContext());
        rightExprListener.enterRule((bjmParser.ExpressionContext) ctx.children.get(2));

        switch (ctx.op.getType()) {
            case bjmLexer.GT:
                this.setValue(leftValue.gt(rightExprListener.getValue()));
                break;
            case bjmLexer.GTEquals:
                this.setValue(leftValue.gte(rightExprListener.getValue()));
                break;
            case bjmLexer.LT:
                this.setValue(leftValue.lt(rightExprListener.getValue()));
                break;
            case bjmLexer.LTEquals:
                this.setValue(leftValue.lte(rightExprListener.getValue()));
                break;
            default:
                throw new InvalidOperationException( String.format("Unknown operator type: %s", ctx.getText()));
        }
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
