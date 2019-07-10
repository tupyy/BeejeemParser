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

package com.beejeem.parser.listeners.expression;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.ValueOperations;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.listeners.collections.CollectionCallListener;
import com.beejeem.parser.listeners.functionlisteners.FunctionCallListener;
import com.beejeem.parser.value.*;

public class ExpressionListener extends AbstractListener {

    public ExpressionListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override
    public void enterMultExpression(bjmParser.MultExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext)ctx.children.get(0)).enterRule(lhs);

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        if ( !(lhs.getVariable() instanceof Value) || !(rhs.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }
        this.setVariable(ValueOperations.getBiOperations().get(ctx.op.getType()).apply((Value) lhs.getVariable(), (Value)rhs.getVariable()));
    }

    public void enterAddExpression(bjmParser.AddExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext)ctx.children.get(0)).enterRule(lhs);

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        if ( !(lhs.getVariable() instanceof Value) || !(rhs.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }
        this.setVariable(ValueOperations.getBiOperations().get(ctx.op.getType()).apply((Value) lhs.getVariable(), (Value)rhs.getVariable()));
    }

    @Override
    public void enterIntegerExpression(bjmParser.IntegerExpressionContext ctx) {
        this.setVariable(new IntegerValue(Integer.valueOf(ctx.getText())));
    }

    @Override
    public void enterFloatExpression(bjmParser.FloatExpressionContext ctx) {
        this.setVariable(new FloatValue(Float.valueOf(ctx.getText())));
    }

    public void enterBoolExpression(bjmParser.BoolExpressionContext ctx) {
        this.setVariable(new BooleanValue(Boolean.valueOf(ctx.getText())));
    }

    public void enterStringExpression(bjmParser.StringExpressionContext ctx) {
        this.setVariable(new StringValue(ctx.getText()));
    }

    @Override
    public void enterPowerExpression(bjmParser.PowerExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(0)).enterRule(lhs);

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        if ( !(lhs.getVariable() instanceof Value) || !(rhs.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }
        this.setVariable(ValueOperations.getBiOperations().get(ctx.Pow().getSymbol().getType())
                .apply((Value) lhs.getVariable(), (Value)rhs.getVariable()));
    }

    public void enterCompExpression(bjmParser.CompExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(0)).enterRule(lhs);

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        if ( !(lhs.getVariable() instanceof Value) || !(rhs.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }

        this.setVariable(ValueOperations.getBiOperations().get(ctx.op.getType()).apply(lhs.getVariable().asValue(), rhs.getVariable().asValue()));
    }

    @Override
    public void enterEqExpression(bjmParser.EqExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(0)).enterRule(lhs);

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        if ( !(lhs.getVariable() instanceof Value) || !(rhs.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }
        this.setVariable(ValueOperations.getBiOperations().get(ctx.op.getType()).apply((Value)lhs.getVariable(), (Value)rhs.getVariable()));
    }

    @Override
    public void enterLogicExpression(bjmParser.LogicExpressionContext ctx) {
        ExpressionListener lhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(0)).enterRule(lhs);

        ExpressionListener rhs = new ExpressionListener(this.getExecutionContext());
        ((bjmParser.ExpressionContext) ctx.children.get(2)).enterRule(rhs);

        if ( !(lhs.getVariable() instanceof Value) || !(rhs.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }
        this.setVariable(ValueOperations.getBiOperations().get(ctx.op.getType()).apply((Value)lhs.getVariable(), (Value)rhs.getVariable()));
    }

    public void enterNotExpression(bjmParser.NotExpressionContext ctx) {
        ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
        ctx.expression().enterRule(expressionListener);

        if ( !(expressionListener.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }
        Value<?> v = expressionListener.getVariable().asValue();
        this.setVariable(ValueOperations.getUniOperations().get(ctx.Excl().getSymbol().getType()).apply(v));
    }

    @Override
    public void enterUnaryMinusExpression(bjmParser.UnaryMinusExpressionContext ctx) {
        ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
        ctx.expression().enterRule(expressionListener);
        if ( !(expressionListener.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }
        Value<?> v = expressionListener.getVariable().asValue();
        this.setVariable(ValueOperations.getUniOperations().get(ctx.Subtract().getSymbol().getType()).apply(v));
    }

    @Override
    public void enterIncrementExpression(bjmParser.IncrementExpressionContext ctx) {
        ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
        ctx.expression().enterRule(expressionListener);
        if ( !(expressionListener.getVariable() instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Only values are allowed here.", ctx.start.getLine()));
        }
        Variable variable = expressionListener.getVariable();
        Value<?> v = variable.asValue(); // clone !!!

        //The value has to be set to listener in case we have an assignment.
        ((Value<?> )variable).setValue(ValueOperations.getUniOperations().get(ctx.op.getType()).apply(v));
        this.setVariable(variable);
    }

    @Override
    public void enterIdentifierExpression(bjmParser.IdentifierExpressionContext ctx) {
        String variableName = ctx.getText();
        Variable variable = this.getExecutionContext().getCurrentStackframe().getVariable(variableName);
        if (variable == null) {
            throw new InvalidOperationException(
                    String.format("Line %d: Variable not found: %s",ctx.start.getLine(),variableName));
        }
        if ( !(variable instanceof Value)) {
            throw new InvalidOperationException(String.format("Line %d: Cannot use list in this context",ctx.start.getLine()));
        }

        this.setVariable(variable);
    }

    @Override
    public void enterExpressionExpression(bjmParser.ExpressionExpressionContext ctx) {
        ExpressionListener expressionListener =
                new ExpressionListener(this.getExecutionContext());
        ctx.expression().enterRule(expressionListener);
        this.setVariable(expressionListener.getVariable());
    }

    @Override
    public void enterFunctionCallExpression(bjmParser.FunctionCallExpressionContext ctx) {
        FunctionCallListener functionCallListener =
                new FunctionCallListener(this.getExecutionContext());
        ctx.enterRule(functionCallListener);
        this.setVariable(functionCallListener.getVariable());
    }

    public void enterCollectionCallExpression(bjmParser.CollectionCallExpressionContext collectionCallContext) {
        CollectionCallListener collectionCallListener =
                new CollectionCallListener(this.getExecutionContext());
        collectionCallContext.enterRule(collectionCallListener);
        this.setVariable(collectionCallListener.getVariable());
    }
}
