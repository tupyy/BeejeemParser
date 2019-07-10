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
import com.beejeem.parser.CollectionMethods;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.listeners.expression.ExpressionListener;
import com.beejeem.parser.value.ListValue;
import com.beejeem.parser.value.Value;
import com.beejeem.parser.value.Variable;

public class CollectionCallListener extends AbstractListener {

    public CollectionCallListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterCollectionCallExpression(bjmParser.CollectionCallExpressionContext ctx) {
        CollectionCallListener collectionCallListener =
                new CollectionCallListener(this.getExecutionContext());
        ctx.collectionCall().enterRule(collectionCallListener);
        this.setVariable(collectionCallListener.getVariable());
    }

    public void enterCollectionCall(bjmParser.CollectionCallContext ctx) {
        String variableName = ctx.Identifier().getText();
        Integer methodID = ctx.method.getType();

        Value<?> argument = null;
        if (ctx.expression() != null) {
            ExpressionListener expressionListener =
                    new ExpressionListener(this.getExecutionContext());
            ctx.expression().enterRule(expressionListener);
            argument = expressionListener.getVariable().asValue();
        }

        Variable variable = this.getExecutionContext().getCurrentStackframe().getVariable(variableName);
        if ( !(variable.isList())) {
            throw new InvalidOperationException(
                    String.format("Line %d: Variable %s is not a list.",ctx.start.getLine(),variableName));
        }

        ListValue<?> valueList = (ListValue<?>) variable;
        this.setVariable(valueList.invokeMethod(CollectionMethods.getListMethodID().get(methodID), argument));
     }
}
