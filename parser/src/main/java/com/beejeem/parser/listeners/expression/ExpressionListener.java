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
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.value.Value;

public class ExpressionListener extends AbstractListener {
    private Value value;
    public ExpressionListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterRule(bjmParser.ExpressionContext expressionContext) {
        if (expressionContext instanceof bjmParser.IdentifierExpressionContext) {
            String variableName = expressionContext.getText();
            this.setValue(this.getExecutionContext().getCurrentStackframe().getVariable(variableName));
        } else if (expressionContext instanceof bjmParser.IntegerExpressionContext) {
            IntegerExpressionListener integerExpressionListener
                    = new IntegerExpressionListener(this.getExecutionContext());
            integerExpressionListener.enterNumberExpression((bjmParser.IntegerExpressionContext) expressionContext);
            this.setValue(integerExpressionListener.getValue());
        } else if (expressionContext instanceof bjmParser.FloatExpressionContext) {
            FloatExpressionListener floatExpressionListener =
                    new FloatExpressionListener(this.getExecutionContext());
            floatExpressionListener.enterFloatExpression((bjmParser.FloatExpressionContext) expressionContext);
            this.setValue(floatExpressionListener.getValue());
        } else if (expressionContext instanceof bjmParser.MultExpressionContext) {
            MultExpressionListener multExpressionListener =
                    new MultExpressionListener(this.getExecutionContext());
            multExpressionListener.enterMultExpression((bjmParser.MultExpressionContext) expressionContext);
            this.setValue(multExpressionListener.getValue());
        } else if (expressionContext instanceof bjmParser.CompExpressionContext) {
            CompExpressionListener compExpressionListener =
                    new CompExpressionListener(this.getExecutionContext());
            compExpressionListener.enterCompExpression((bjmParser.CompExpressionContext) expressionContext);
            this.setValue(compExpressionListener.getValue());
        } else if (expressionContext instanceof bjmParser.LogicExpressionContext) {
            LogicExpressionListener logicExpressionListener =
                    new LogicExpressionListener(this.getExecutionContext());
            logicExpressionListener.enterLogicExpression((bjmParser.LogicExpressionContext) expressionContext);
            this.setValue(logicExpressionListener.getValue());
        } else if (expressionContext instanceof bjmParser.EqExpressionContext) {
            EqualExpressionListener equalExpressionListener =
                    new EqualExpressionListener(this.getExecutionContext());
            equalExpressionListener.enterEqExpression((bjmParser.EqExpressionContext) expressionContext);
            this.setValue(equalExpressionListener.getValue());
        } else if (expressionContext instanceof bjmParser.AddExpressionContext) {
            AddExpressionListener addExpressionListener =
                    new AddExpressionListener(this.getExecutionContext());
            addExpressionListener.enterAddExpression((bjmParser.AddExpressionContext) expressionContext);
            this.setValue(addExpressionListener.getValue());
        } else if (expressionContext instanceof bjmParser.ExpressionExpressionContext) {
            ExpressionListener expressionListener =
                    new ExpressionListener(this.getExecutionContext());
            expressionListener.enterRule(((bjmParser.ExpressionExpressionContext) expressionContext).expression());
            this.setValue(expressionListener.getValue());
        }
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
