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

package com.beejeem.parser.listeners.functionlisteners;

import com.beejeem.grammar.bjmParser;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.function.UserDefinedFunction;
import com.beejeem.parser.listeners.AbstractListener;
import com.beejeem.parser.type.Type;
import com.beejeem.parser.value.Value;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeclarationListener extends AbstractListener {

    private Value value;

    public FunctionDeclarationListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override
    public void enterFunctionDecl(bjmParser.FunctionDeclContext ctx) {
        Type resultType = this.getExecutionContext().resolveType(ctx.resultType().getText());

        // get parameters list
        List<ParametersListener.Parameter> parameters = new ArrayList<>();
        if (ctx.parameters() != null) {
            ParametersListener parametersListener = new ParametersListener(this.getExecutionContext());
            ctx.parameters().enterRule(parametersListener);
            parameters = parametersListener.getParameters();
        }

        UserDefinedFunction userDefinedFunction
                = new UserDefinedFunction(ctx.Identifier().getText(), parameters, ctx.block(), resultType);
        this.getExecutionContext().getCurrentStackframe().declareFunction(userDefinedFunction);
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
