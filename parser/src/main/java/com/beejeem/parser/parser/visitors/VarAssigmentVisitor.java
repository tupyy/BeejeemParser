package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.JobfileBaseVisitor;
import com.beejeem.antrl4.JobfileParser;
import com.beejeem.parser.domain.variables.Variable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VarAssigmentVisitor extends JobfileBaseVisitor<Variable> {

    private static Logger logger = LogManager.getLogger(VarAssigmentVisitor.class);

    public Variable visitVarassignment(JobfileParser.VarassignmentContext ctx) {
        String varName = ctx.varname().getText();
        logger.debug("Parsing variable: " + ctx.getText());
        return ctx.varvalue().accept(new VariableVisitor(varName));
    }
}
