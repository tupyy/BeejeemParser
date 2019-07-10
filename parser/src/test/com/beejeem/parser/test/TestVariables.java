package com.beejeem.parser.test;

import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestVariables {

    private FloatValue floatValue;
    private IntegerValue integerValue;
    private StringValue stringValue;
    private BooleanValue booleanValue;

    @BeforeEach
    public void initVariables() {
        floatValue = new FloatValue(1.0f);
        integerValue = new IntegerValue(1);
        stringValue = new StringValue("string");
        booleanValue = new BooleanValue(true);
    }
    @Test
    public void testVariables() {
        assertEquals(1.0f, floatValue.get());
        assertEquals(1, integerValue.get());
        assertEquals("string", stringValue.get());
        assertEquals(true, booleanValue.get());
    }

    @Test
    public void testFloatValue() {
        assertThrows(InvalidOperationException.class, () -> {
            floatValue.setValue(stringValue);
        });

        assertThrows(InvalidOperationException.class, () -> {
            floatValue.eq(stringValue);
        });

        floatValue.setValue(new FloatValue(2.0f));
        assertEquals(2.0f,floatValue.get());

        floatValue.setValue(new IntegerValue(2));
        assertEquals(2.0f,floatValue.get());

        assertEquals(3.0f,floatValue.add(new FloatValue(1.0f)).get());
        assertEquals(3.0f,floatValue.add(new IntegerValue(1)).get());

        assertEquals(1.0f,floatValue.subtract(new FloatValue(1.0f)).get());
        assertEquals(1.0f,floatValue.subtract(new IntegerValue(1)).get());

        assertEquals(4.0f,floatValue.mult(new FloatValue(2.0f)).get());
        assertEquals(4.0f,floatValue.mult(new IntegerValue(2)).get());

        assertEquals(1.0f,floatValue.div(new FloatValue(2.0f)).get());
        assertEquals(1.0f,floatValue.div(new IntegerValue(2)).get());

        assertEquals(4.0f,floatValue.power(new FloatValue(2.0f)).get());
        assertEquals(4.0f,floatValue.power(new IntegerValue(2)).get());

        assertFalse(floatValue.lt(new FloatValue(1.0f)).get());
        assertFalse(floatValue.lt(new IntegerValue(1)).get());

        assertTrue(floatValue.lte(new FloatValue(5.0f)).get());
        assertTrue(floatValue.lte(new IntegerValue(5)).get());

        assertTrue(floatValue.gt(new FloatValue(1.0f)).get());
        assertTrue(floatValue.gt(new IntegerValue(1)).get());

        assertTrue(floatValue.gte(new FloatValue(2.0f)).get());
        assertTrue(floatValue.gte(new IntegerValue(2)).get());

        assertTrue(floatValue.eq(new FloatValue(2.0f)).get());
        assertTrue(floatValue.eq(new IntegerValue(2)).get());

        assertFalse(floatValue.neq(new FloatValue(2.0f)).get());
        assertFalse(floatValue.neq(new IntegerValue(2)).get());
    }

    @Test
    public void testIntegerValue() {
        assertThrows(InvalidOperationException.class, () -> {
            integerValue.setValue(stringValue);
        });

        assertThrows(InvalidOperationException.class, () -> {
            integerValue.eq(stringValue);
        });

        integerValue.setValue(new FloatValue(2.0f));
        assertEquals(2,integerValue.get());

        integerValue.setValue(new IntegerValue(2));
        assertEquals(2,integerValue.get());

        assertEquals(3.0f,integerValue.add(new FloatValue(1.0f)).get());
        assertEquals(3,integerValue.add(new IntegerValue(1)).get());

        assertEquals(1.0f,integerValue.subtract(new FloatValue(1.0f)).get());
        assertEquals(1,integerValue.subtract(new IntegerValue(1)).get());

        assertEquals(4.0f,integerValue.mult(new FloatValue(2.0f)).get());
        assertEquals(4,integerValue.mult(new IntegerValue(2)).get());

        assertEquals(1.0f,integerValue.div(new FloatValue(2.0f)).get());
        assertEquals(1.0f,integerValue.div(new IntegerValue(2)).get());

        assertEquals(4.0f,integerValue.power(new FloatValue(2.0f)).get());
        assertEquals(4,integerValue.power(new IntegerValue(2)).get());

        assertFalse(integerValue.lt(new FloatValue(1.0f)).get());
        assertFalse(integerValue.lt(new IntegerValue(1)).get());

        assertTrue(integerValue.lte(new FloatValue(5.0f)).get());
        assertTrue(integerValue.lte(new IntegerValue(5)).get());

        assertTrue(integerValue.gt(new FloatValue(1.0f)).get());
        assertTrue(integerValue.gt(new IntegerValue(1)).get());

        assertTrue(integerValue.gte(new FloatValue(2.0f)).get());
        assertTrue(integerValue.gte(new IntegerValue(2)).get());


        assertEquals(1,integerValue.decrement().get());

        assertThrows(InvalidOperationException.class, () -> {
            integerValue.eq(new FloatValue(2.0f));
        });
        assertTrue(integerValue.eq(new IntegerValue(1)).get());

        assertThrows(InvalidOperationException.class, () -> {
            integerValue.neq(new FloatValue(2.0f));
        });
        assertFalse(integerValue.neq(new IntegerValue(1)).get());
    }

    @Test
    public void testBoolean() {
        assertTrue(booleanValue.and(new BooleanValue(true)).get());
        assertTrue(booleanValue.or(new BooleanValue(false)).get());
    }

    @Test
    public void testString() {
        stringValue.setValue(new StringValue("string1"));
        assertEquals("string1", stringValue.get());

        stringValue.set("string2");
        assertEquals("string2", stringValue.get());
    }
    @Test
    public void testList() {
        ListValue<Integer> listValue = new ListValue<>();
        listValue.add(1);
        listValue.add(2);

        assertTrue(listValue.getAsValue(0) instanceof IntegerValue);
        assertNull(listValue.getAsValue(3));
    }
}
