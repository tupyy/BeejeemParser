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

package com.beejeem.parser.listeners.ifstatement;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.listeners.BlockListener;
import com.beejeem.parser.listeners.expression.ExpressionListener;
import com.beejeem.parser.value.BooleanValue;
import com.beejeem.parser.value.Value;

public class IfStatementListener extends AbstractListener {
    private Value value;

    public IfStatementListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override
    public void enterIfStatement(bjmParser.IfStatementContext ctx) {
        if (this.evaluateCondionalExpression(ctx.ifStat().expression()).getValue()) {
            BlockListener blockListener = new BlockListener(this.getExecutionContext());
            ctx.ifStat().block().enterRule(blockListener);
        } else {
            Value ifElseConditionalValue = new BooleanValue(false);
            if (ctx.elseIfStat() != null) {
                for (bjmParser.ElseIfStatContext elseIfStatContext : ctx.elseIfStat()) {
                    IfStatementListener ifElseStatListener = new IfStatementListener(this.getExecutionContext());
                    elseIfStatContext.enterRule(ifElseStatListener);
                    ifElseConditionalValue.set(ifElseStatListener.getValue());
                }
            }
            if (!((BooleanValue) ifElseConditionalValue).getValue() && ctx.elseStat() != null) {
                IfStatementListener elseStatListener = new IfStatementListener(this.getExecutionContext());
                ctx.elseStat().enterRule(elseStatListener);
            }
        }
    }

    @Override
    public void enterElseIfStat(bjmParser.ElseIfStatContext ctx) {
        BooleanValue conditionalValue = this.evaluateCondionalExpression(ctx.expression());
        if (conditionalValue.getValue()) {
            BlockListener blockListener = new BlockListener(this.getExecutionContext());
            ctx.block().enterRule(blockListener);
        }
        this.setValue(conditionalValue);
    }

    @Override
    public void enterElseStat(bjmParser.ElseStatContext ctx) {
        BlockListener blockListener = new BlockListener(this.getExecutionContext());
        ctx.block().enterRule(blockListener);
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * Evaluate the conditional expression
     * @param expressionContext expression context
     * @throws InvalidOperationException if the expression is not boolean
     * @return true or false
     */
    private BooleanValue evaluateCondionalExpression(bjmParser.ExpressionContext expressionContext) {
        ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
        expressionContext.enterRule(expressionListener);
        Value ifStatExpressionValue = expressionListener.getValue();
        if (! (ifStatExpressionValue instanceof BooleanValue)) {
            throw new InvalidOperationException("If statement expects a boolean value.");
        }
        return ((BooleanValue) ifStatExpressionValue);
    }
}
