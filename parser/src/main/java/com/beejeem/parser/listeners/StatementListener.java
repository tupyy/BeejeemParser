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
import com.beejeem.grammar.bjmParser.LocalVariableDeclarationContext;
import com.beejeem.parser.ExecutionContext;

public class StatementListener extends AbstractListener {

    public StatementListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterStatement(bjmParser.StatementContext ctx) {
        StatementListener statementListener =
                new StatementListener(this.getExecutionContext());
        if (ctx.assignment() != null) {
            ctx.assignment().enterRule(statementListener);
        } else if (ctx.variableDeclaration() != null) {
            ctx.variableDeclaration().enterRule(statementListener);
        }
    }

    public void enterAssignment(bjmParser.AssignmentContext ctx) {
        AssignmentListener assignmentListener = new AssignmentListener(this.getExecutionContext());
        ctx.enterRule(assignmentListener);
    }

    @Override
    public void enterVariableDeclaration(bjmParser.VariableDeclarationContext ctx) {
        LocalVariableDeclarationListener localVariableDeclarationListener
                = new LocalVariableDeclarationListener(this.getExecutionContext());
        ctx.localVariableDeclaration().enterRule(localVariableDeclarationListener);
    }
}
