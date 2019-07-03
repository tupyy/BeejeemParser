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

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Value;
import com.beejeem.parser.value.VoidValue;

import java.util.Map;

public class LocalVariableDeclarationListener extends AbstractListener {

    private Value value;
    public LocalVariableDeclarationListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterLocalVariableDeclaration(bjmParser.LocalVariableDeclarationContext ctx) {
        Type variableType = this.getExecutionContext().resolveType(ctx.typeType().getText());
        VariableDeclaratorsListener variableDeclaratorsListener = new VariableDeclaratorsListener(this.getExecutionContext());
        ctx.variableDeclarators().enterRule(variableDeclaratorsListener);

        // push variables to current stack
        for (Map.Entry<String, Value> entry: variableDeclaratorsListener.getValues().entrySet()) {
            value = variableType.createValue();
            if (! (entry.getValue() instanceof VoidValue)) {
                value.set(entry.getValue());
            }
            this.getExecutionContext().getCurrentStackframe().declareVariable(entry.getKey(),value);
        }
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
