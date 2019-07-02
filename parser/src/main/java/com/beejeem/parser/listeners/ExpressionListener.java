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

package com.beejeem.parser.listeners;

import com.beejeem.grammar.bjmLexer;
import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.value.*;

public class ExpressionListener extends AbstractListener {
    private Value value;
    public ExpressionListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override
    public void enterMultExpression(bjmParser.MultExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext)ctx.children.get(0)).enterRule(lhs);
        Value leftValue = lhs.getValue();

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        switch (ctx.op.getType()) {
            case bjmLexer.Multiply:
                this.setValue(leftValue.mult(rhs.getValue()));
                break;
            case bjmLexer.Divide:
                this.setValue(leftValue.div(rhs.getValue()));
                break;
            case bjmLexer.Modulus:
                this.setValue(leftValue.mod(rhs.getValue()));
                break;
            default:
                throw new InvalidOperationException( String.format("Unknown operator type: %s", ctx.getText()));
        }
    }

    public void enterAddExpression(bjmParser.AddExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext)ctx.children.get(0)).enterRule(lhs);
        Value leftValue = lhs.getValue();

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        switch (ctx.op.getType()) {
            case bjmLexer.Add:
                this.setValue(leftValue.add(rhs.getValue()));
                break;
            case bjmLexer.Subtract:
                this.setValue(leftValue.subtract(rhs.getValue()));
                break;
            default:
                throw new InvalidOperationException( String.format("Unknown operator type: %s", ctx.getText()));
        }
    }

    @Override
    public void enterIntegerExpression(bjmParser.IntegerExpressionContext ctx) {
        this.setValue(new IntegerValue(Integer.valueOf(ctx.getText())));
    }

    @Override
    public void enterFloatExpression(bjmParser.FloatExpressionContext ctx) {
        this.setValue(new FloatValue(Float.valueOf(ctx.getText())));
    }

    public void enterBoolExpression(bjmParser.BoolExpressionContext ctx) {
        this.setValue(new BooleanValue(Boolean.valueOf(ctx.getText())));
    }

    public void enterStringExpression(bjmParser.StringExpressionContext ctx) {
        this.setValue(new StringValue(ctx.getText()));
    }

    @Override
    public void enterPowerExpression(bjmParser.PowerExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(0)).enterRule(lhs);
        Value lhsValue = lhs.getValue();

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        this.setValue(lhsValue.power(rhs.getValue()));
    }

    public void enterCompExpression(bjmParser.CompExpressionContext ctx) {
        ExpressionListener leftExprListener = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(0)).enterRule(leftExprListener);
        Value leftValue = leftExprListener.getValue();

        ExpressionListener rightExprListener = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rightExprListener);

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

    @Override
    public void enterEqExpression(bjmParser.EqExpressionContext ctx) {
        ExpressionListener leftExprListener = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(0)).enterRule(leftExprListener);
        Value leftValue = leftExprListener.getValue();

        ExpressionListener rightExprListener = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rightExprListener);

        switch (ctx.op.getType()) {
            case bjmLexer.Equals:
                this.setValue(leftValue.eq(rightExprListener.getValue()));
                break;
            case bjmLexer.NEquals:
                this.setValue(leftValue.neq(rightExprListener.getValue()));
                break;
            default:
                throw new InvalidOperationException( String.format("Unknown operator type: %s", ctx.getText()));
        }
    }

    @Override
    public void enterLogicExpression(bjmParser.LogicExpressionContext ctx) {
        ExpressionListener leftExprListener = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(0)).enterRule(leftExprListener);
        Value leftValue = leftExprListener.getValue();

        ExpressionListener rightExprListener = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rightExprListener);

        switch (ctx.op.getType()) {
            case bjmLexer.And:
                this.setValue(leftValue.and(rightExprListener.getValue()));
                break;
            case bjmLexer.Or:
                this.setValue(leftValue.or(rightExprListener.getValue()));
                break;
            default:
                throw new InvalidOperationException( String.format("Unknown operator type: %s", ctx.getText()));
        }
    }

    public void enterNotExpression(bjmParser.NotExpressionContext ctx) {
        ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
        ctx.expression().enterRule(expressionListener);
        Value v = expressionListener.getValue();
        this.setValue(v.not());
    }

    @Override
    public void enterUnaryMinusExpression(bjmParser.UnaryMinusExpressionContext ctx) {
        ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
        ctx.expression().enterRule(expressionListener);
        Value v = expressionListener.getValue();
        this.setValue(v.mult(new IntegerValue(-1)));
    }

    @Override
    public void enterIdentifierExpression(bjmParser.IdentifierExpressionContext ctx) {
        String variableName = ctx.getText();
        Value value = this.getExecutionContext().getCurrentStackframe().getVariable(variableName);
        if (value == null) {
            throw new InvalidOperationException(String.format("Variable not found: %s",variableName));
        }
        this.setValue(value);
    }

    @Override
    public void enterExpressionExpression(bjmParser.ExpressionExpressionContext ctx) {
        ExpressionListener expressionListener =
                new ExpressionListener(this.getExecutionContext());
        ctx.expression().enterRule(expressionListener);
        this.setValue(expressionListener.getValue());
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
