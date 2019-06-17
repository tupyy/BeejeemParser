package com.beejeem.parser.test;

import com.beejeem.parser.domain.DefaultProgram;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.variables.IntegerVariable;
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

    // cannot add twice the same variable. in this case, it will update the value only
    @Test
    public void testAddVariableTwice() {
        Program p = new DefaultProgram();
        Variable old = new StringVariable("test","value");
        p.add(old);

        Variable newVar = new StringVariable("test", "newvalue");
        p.add(newVar);
        Assert.assertEquals(p.getVariables().get(0).getValue(),"newvalue");
        Assert.assertEquals(p.getVariables().size(), 1);
    }

    // cannot add twice the same variable. in this case, it will update the value only
    @Test
    public void testAddVariableTwice2() {
        Program p = new DefaultProgram();
        Variable old = new StringVariable("test","value");
        p.add(old);

        Variable newVar = new IntegerVariable("test", 2);
        p.add(newVar);
        Assert.assertEquals(p.getVariables().get(0).getValue(),2);
        Assert.assertEquals(p.getVariables().size(), 1);
    }
}
