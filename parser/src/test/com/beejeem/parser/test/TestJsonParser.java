package com.beejeem.parser.test;

import com.beejeem.parser.domain.variables.StringVariable;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestJsonParser {

    @Test
    public void testStringVariable() {
           try {
            String data = new String(Files.readAllBytes(Paths.get("/home/cosmin/Projects/BeejeemParser/parser/src/test/com/beejeem/parser/test/json.txt")));
            final List<Variable> result = new JsonParser().parse(data);
            Assert.assertEquals(4, result.size());

            Assert.assertTrue(result.get(0) instanceof StringVariable);
            StringVariable s = (StringVariable) result.get(0);
            Assert.assertEquals("var1", s.getName());
            Assert.assertEquals("foo", s.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
