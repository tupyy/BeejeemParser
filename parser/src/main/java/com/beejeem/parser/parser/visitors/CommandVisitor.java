package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.JobfileBaseVisitor;
import com.beejeem.antrl4.JobfileParser;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.commands.LocalCommand;
import com.beejeem.parser.domain.commands.RemoteCommand;
import com.beejeem.parser.domain.variables.Variable;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class CommandVisitor extends JobfileBaseVisitor<Command> {

    private static Logger logger = LogManager.getLogger(CommandVisitor.class);

    public Command visitCommandstatement(JobfileParser.CommandstatementContext ctx) {
        logger.debug("Parsing command: " + ctx.getText());
        Command command = visitChildren(ctx);
        return command;
    }

    public Command visitGeneratestm(JobfileParser.GeneratestmContext ctx) {
        return null;
    }

    public Command visitRunstm(JobfileParser.RunstmContext ctx) {
        Command runCommand = new LocalCommand(Command.CommandType.RUN);
        Variable variable = ctx.string().accept(new VariableVisitor("command"));
        runCommand.add(variable);
        return runCommand;
    }

    public Command visitGenerateStm(JobfileParser.GeneratestmContext ctx) {
        Command generateCommand = new LocalCommand(Command.CommandType.GENERATE);
        Variable variable = ctx.string().accept(new VariableVisitor("arg"));
        generateCommand.add(variable);
        return generateCommand;
    }

    public Command visitCopystm(JobfileParser.CopystmContext ctx) {
        Command copyCommand = new LocalCommand(Command.CommandType.COPY);
        List<Variable> arguments = ctx.children.stream()
                                               .filter(context -> !(context instanceof TerminalNode))
                                               .map(context -> context.accept(new VariableVisitor("arg")))
                                               .collect(Collectors.toList());
        copyCommand.add(arguments);
        return copyCommand;
    }

    public Command visitRcopystm(JobfileParser.RcopystmContext ctx) {
        Command rCopyCommand = new RemoteCommand(Command.CommandType.RCOPY);
        List<Variable> arguments = ctx.children.stream()
                                               .filter(context -> !(context instanceof TerminalNode))
                                               .map(context -> context.accept(new VariableVisitor("arg")))
                                               .collect(Collectors.toList());
        rCopyCommand.add(arguments);
        return rCopyCommand;
    }

    public Command visitSubmitstm(JobfileParser.SubmitstmContext ctx) {
        Command submitCommand = new RemoteCommand(Command.CommandType.SUBMIT);
        Variable variable = ctx.string().accept(new VariableVisitor("arg"));
        submitCommand.add(variable);
        return submitCommand;
    }
}
