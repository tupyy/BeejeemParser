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

package com.beejeem.parser.listeners.vardeclaration;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.listeners.expression.ExpressionListener;
import com.beejeem.parser.value.Variable;

import java.util.ArrayList;
import java.util.List;

public class ListInitializerListener extends AbstractListener {
    private List<Variable> variables = new ArrayList<>();

    public ListInitializerListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterListInitializer(bjmParser.ListInitializerContext ctx) {
        for(bjmParser.ExpressionContext context: ctx.expression()) {
            ExpressionListener expressionListener =
                    new ExpressionListener(this.getExecutionContext());
            context.enterRule(expressionListener);
            this.getVariables().add(expressionListener.getVariable());
        }
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> list) {
        this.variables = list;
    }
}
