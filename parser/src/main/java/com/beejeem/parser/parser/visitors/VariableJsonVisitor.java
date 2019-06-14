package com.beejeem.parser.parser.visitors;

import com.beejeem.parser.domain.variables.*;
import com.beejeem.antrl4.JSONBaseVisitor;
import com.beejeem.antrl4.JSONParser;

public class VariableJsonVisitor extends JSONBaseVisitor<Variable> {

    private final String varName;

    public VariableJsonVisitor(String varName) {
        this.varName = varName;
    }

    public String getVariableName() {
        return this.varName;
    }

    public Variable visitVarvalue(JSONParser.VarvalueContext ctx) {
        Variable v = visitChildren(ctx);
        return v;
    }

    public Variable visitNumber(JSONParser.NumberContext ctx) {
        Variable variable;
        if (ctx.FLOAT() != null) {
            variable = new FloatVariable(this.getVariableName(), Float.valueOf(ctx.FLOAT().getText()));
        } else {
            variable = new IntegerVariable(this.getVariableName(), Integer.valueOf(ctx.INT().getText()));
        }
        return variable;
    }

    public Variable visitString(JSONParser.StringContext ctx) {
        return new StringVariable(this.getVariableName(),ctx.string_content().getText());
    }

    public Variable visitBool(JSONParser.BoolContext ctx) {
        return new BooleanVariable(this.getVariableName(), Boolean.valueOf(ctx.getText()));
    }
}
