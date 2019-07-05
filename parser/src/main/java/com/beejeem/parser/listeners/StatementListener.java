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
import com.beejeem.parser.StackFrame;
import com.beejeem.parser.listeners.assignment.AssignmentListener;
import com.beejeem.parser.listeners.forstatement.ForStatementListener;
import com.beejeem.parser.listeners.functionlisteners.FunctionCallListener;
import com.beejeem.parser.listeners.ifstatement.IfStatementListener;
import com.beejeem.parser.listeners.increment.IncrementStatementListener;
import com.beejeem.parser.listeners.vardeclaration.LocalVariableDeclarationListener;
import com.beejeem.parser.value.Value;

import java.util.Map;

public class StatementListener extends AbstractListener {

    public StatementListener(ExecutionContext executionContext) {
        super(executionContext);
    }

    public void enterStatement(bjmParser.StatementContext ctx) {
        StatementListener statementListener =
                new StatementListener(this.getExecutionContext());
        if (ctx.assignment() != null) {
            ctx.assignment().enterRule(statementListener);
        } else if (ctx.incrementStatement() != null) {
            ctx.incrementStatement().enterRule(statementListener);
        } else if (ctx.variableDeclaration() != null) {
            ctx.variableDeclaration().enterRule(statementListener);
        } else if (ctx.ifStatement() != null) {
            ctx.ifStatement().enterRule(statementListener);
            ctx.ifStatement().exitRule(statementListener);
        } else if (ctx.forStatement() != null) {
            ctx.forStatement().enterRule(statementListener);
            ctx.forStatement().exitRule(statementListener);
        } else if (ctx.functionCall() != null) {
            ctx.functionCall().enterRule(statementListener);
        }
    }

    @Override
    public void enterAssignment(bjmParser.AssignmentContext ctx) {
        AssignmentListener assignmentListener = new AssignmentListener(this.getExecutionContext());
        ctx.enterRule(assignmentListener);
    }

    @Override
    public void enterIncrementStatement(bjmParser.IncrementStatementContext ctx) {
        IncrementStatementListener incrementStatementListener =
                new IncrementStatementListener(this.getExecutionContext());
        ctx.enterRule(incrementStatementListener);
    }

    @Override
    public void enterVariableDeclaration(bjmParser.VariableDeclarationContext ctx) {
        LocalVariableDeclarationListener localVariableDeclarationListener
                = new LocalVariableDeclarationListener(this.getExecutionContext());
        ctx.localVariableDeclaration().enterRule(localVariableDeclarationListener);
    }

    @Override
    public void enterIfStatement(bjmParser.IfStatementContext ctx) {
        // push new frame on the stack and copy all global variables
        Map<String, Value> variables = this.getExecutionContext().getCurrentStackframe().getVariables();
        StackFrame stackFrame = this.getExecutionContext().pushStackframe();
        stackFrame.setVariables(variables);

        IfStatementListener ifStatementListener = new IfStatementListener(this.getExecutionContext());
        ctx.enterRule(ifStatementListener);
    }

    public void exitIfStatement(bjmParser.IfStatementContext ctx) {
        this.getExecutionContext().popStackframe();
    }

    public void enterForStatement(bjmParser.ForStatementContext ctx) {
        // push new frame on the stack and copy all global variables
        Map<String, Value> variables = this.getExecutionContext().getCurrentStackframe().getVariables();
        StackFrame stackFrame = this.getExecutionContext().pushStackframe();
        stackFrame.setVariables(variables);

        ForStatementListener forStatementListener = new ForStatementListener(this.getExecutionContext());
        ctx.enterRule(forStatementListener);
    }

    public void exitForStatement(bjmParser.ForStatementContext ctx) {
        this.getExecutionContext().popStackframe();
    }

    @Override
    public void enterFunctionCall(bjmParser.FunctionCallContext ctx) {
        FunctionCallListener functionCallListener = new FunctionCallListener(this.getExecutionContext());
        ctx.enterRule(functionCallListener);
    }
}
