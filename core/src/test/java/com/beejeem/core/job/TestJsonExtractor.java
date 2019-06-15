package com.beejeem.core.job;

import com.beejeem.core.command.result.JsonExtractor;
import org.junit.Assert;
import org.junit.Test;

public class TestJsonExtractor {

    @Test
    public void testExtractor() {
        String okJson = "noise123*) {test}";
        Assert.assertEquals("{test}", JsonExtractor.extract(okJson));
    }

    @Test
    public void testExtractor2() {
        String okJson = "noise123*)";
        Assert.assertEquals("{}", JsonExtractor.extract(okJson));
    }

    @Test
    public void testExtractor3() {
        String okJson = "noise123*) \n {test} dsfdsf2.1:;ù$";
        Assert.assertEquals("{test}", JsonExtractor.extract(okJson));
    }
}
