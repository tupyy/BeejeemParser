package com.beejeem.parser.parser.visitors;

import com.beejeem.parser.domain.variables.StringVariable;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.antrl4.visitor.JobfileParser;

public class StringVisitor extends VariableVisitor {

    public StringVisitor(String varName) {
        super(varName);
    }

    public Variable visitString(JobfileParser.StringContext ctx) {
        return new StringVariable(this.getVariableName(),ctx.getText());
    }
}
