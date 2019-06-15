package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.JSONBaseVisitor;
import com.beejeem.antrl4.JSONParser;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.visitors.VarJsonAssigmentVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class JsonVisitor extends JSONBaseVisitor<List<Variable>> {

    public List<Variable> visitJson(JSONParser.JsonContext ctx) {
        List<Variable> variables = ctx.children.stream()
                                               .filter(c -> c instanceof JSONParser.VarassignmentContext)
                                               .map(c -> c.accept(new VarJsonAssigmentVisitor()))
                                               .collect(Collectors.toList());
        return variables;
    }
}
