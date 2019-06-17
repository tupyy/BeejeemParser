package com.beejeem.parser.test;

import com.beejeem.parser.domain.DefaultProgram;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.variables.StringVariable;
import com.beejeem.parser.domain.variables.Variable;
import org.junit.Assert;
import org.junit.Test;

public class TestUpdateVariable {

    @Test
    public void testUpdateVariable() {
        Program p = new DefaultProgram();
        Variable old = new StringVariable("test","value");
        p.add(old);

        Variable newVar = new StringVariable("test", "newvalue");
        p.updateVariable(newVar);
        Assert.assertEquals(p.getVariables().get(0).getValue(),"newvalue");
    }
}
