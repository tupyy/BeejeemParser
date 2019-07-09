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

package com.beejeem.parser.listeners.increment;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.ValueOperations;
import com.beejeem.parser.exception.InterpreterException;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.value.Value;
import com.beejeem.parser.value.Variable;

public class IncrementStatementListener extends AbstractListener {

    public IncrementStatementListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterIncrementStatement(bjmParser.IncrementStatementContext ctx) {
        Variable variable = this.getExecutionContext().getCurrentStackframe().getVariable(ctx.Identifier().getText());
        if (variable == null) {
            throw new InterpreterException(
                    String.format("Line %d: Unknown variable %s", ctx.start.getLine(),ctx.Identifier().getText()));
        }
        if (variable instanceof Value) {
            ((Value)variable).set(ValueOperations.getUniOperations().get(ctx.op.getType()).apply((Value)variable));
        } else {
            throw new InvalidOperationException(String.format("Line %d: A list cannot be incremented.", ctx.start.getLine()));
        }
    }
}
