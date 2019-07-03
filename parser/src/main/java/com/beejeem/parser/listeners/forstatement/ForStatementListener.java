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

package com.beejeem.parser.listeners.forstatement;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.StackFrame;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.listeners.BlockListener;
import com.beejeem.parser.listeners.assignment.AssignmentListener;
import com.beejeem.parser.listeners.expression.ExpressionListener;
import com.beejeem.parser.value.BooleanValue;
import com.beejeem.parser.value.Value;

import java.util.Map;

public class ForStatementListener extends AbstractListener {
    private Value value;

    public ForStatementListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override
    public void enterForStatement(bjmParser.ForStatementContext ctx) {
        ForControlListener forControlListener = new ForControlListener(this.getExecutionContext());
        ctx.forControl().enterRule(forControlListener);

        //loop
        while(true) {
            //test for condition
            ExpressionListener endValueExpressionListener = new ExpressionListener(this.getExecutionContext());
            ctx.forControl().expression().enterRule(endValueExpressionListener);
            Value endValue = endValueExpressionListener.getValue();
            if ( !((BooleanValue) endValue).getValue() ) {
                break;
            }
            BlockListener blockListener = new BlockListener(this.getExecutionContext());
            ctx.block().enterRule(blockListener);

            //increment the index
            AssignmentListener incrementAssignmentListener = new AssignmentListener(this.getExecutionContext());
            ctx.forControl().assignment().enterRule(incrementAssignmentListener);
        }
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}