package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.visitor.JobfileParser;
import com.beejeem.parser.domain.variables.FloatVariable;
import com.beejeem.parser.domain.variables.IntegerVariable;
import com.beejeem.parser.domain.variables.Variable;

public class NumberVisitor extends VariableVisitor {

    public NumberVisitor(String varName) {
        super(varName);
    }


}
