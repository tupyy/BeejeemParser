package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.JSONBaseVisitor;
import com.beejeem.antrl4.JSONParser;
import com.beejeem.parser.domain.variables.Variable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VarJsonAssigmentVisitor extends JSONBaseVisitor<Variable> {

    private static Logger logger = LogManager.getLogger(VarJsonAssigmentVisitor.class);

    public Variable visitVarassignment(JSONParser.VarassignmentContext ctx) {
        String varName = ctx.varname().string_content().getText();
        logger.debug("Parsing variable: " + ctx.getText());
        return ctx.varvalue().accept(new VariableJsonVisitor(varName));
    }
}
