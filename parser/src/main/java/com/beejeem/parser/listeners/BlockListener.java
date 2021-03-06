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
import com.beejeem.parser.listeners.expression.ExpressionListener;
import com.beejeem.parser.listeners.functionlisteners.FunctionDeclarationListener;

public class BlockListener extends AbstractListener {
    public BlockListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    // TODO better handling. use antl4 hashtag def
    @Override
    public void enterBlock(bjmParser.BlockContext blockContext) {
        if (blockContext.functionDecl().size() > 0) {
            for (bjmParser.FunctionDeclContext functionDeclContext: blockContext.functionDecl()) {
                FunctionDeclarationListener functionDeclarationListener
                        = new FunctionDeclarationListener(this.getExecutionContext());
                functionDeclContext.enterRule(functionDeclarationListener);
            }
        }

        if (blockContext.statement().size() > 0) {
            for (bjmParser.StatementContext statementContext: blockContext.statement()) {
                StatementListener statementListener = new StatementListener(this.getExecutionContext());
                statementContext.enterRule(statementListener);
            }
        }

        if (blockContext.Return() != null) {
            ExpressionListener expressionListener = new ExpressionListener(this.getExecutionContext());
            blockContext.expression().enterRule(expressionListener);
            this.setVariable(expressionListener.getVariable());
        }
    }
}
