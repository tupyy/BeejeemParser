/*
 * Beejeem2 Copyright 2019, Cosmin Tupangiu
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.beejeem.parser.test;

import com.beejeem.grammar.bjmLexer;
import com.beejeem.grammar.bjmParser;
import com.beejeem.grammar.bjmParser.ProgramContext;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.StackFrame;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.function.UserDefinedFunction;
import com.beejeem.parser.listeners.ProgramListener;
import com.beejeem.parser.type.FloatType;
import com.beejeem.parser.type.IntegerType;
import com.beejeem.parser.value.ListValue;
import com.beejeem.parser.value.Value;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestParserList {

    @Test
    public void testLists() {
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext = this.parse(readTestFile("lists.txt"));
        final ProgramListener programListener = new ProgramListener(executionContext);
        programContext.enterRule(programListener);

        StackFrame stackFrame = programListener.getLastStack();
        assertEquals(1, ((Value)stackFrame.getVariable("aa")).get());
        assertEquals(3, ((ListValue<?>)stackFrame.getVariable("a")).size());

        assertEquals(4, ((ListValue<?>)stackFrame.getVariable("b")).size());

        ListValue<?> myList = (ListValue<?>) stackFrame.getVariable("b");
        Value<?> b = myList.getAsValue(0);
        assertEquals(1, b.get());

        assertEquals(2, ((ListValue<?>)stackFrame.getVariable("c")).size());

        ListValue<?> myList1 = (ListValue<?>) stackFrame.getVariable("c");
        Value<?> c = myList1.getAsValue(0);
        assertEquals(true, c.get());

        assertEquals(1, ((Value)stackFrame.getVariable("i")).get());
        assertEquals(4, ((Value)stackFrame.getVariable("sum")).get());
    }

    private InputStream readTestFile(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

    private ProgramContext parse(InputStream stream)  {
        try {
            final Lexer lexer = new bjmLexer(CharStreams.fromStream(stream));
            final TokenStream tokenStream = new CommonTokenStream(lexer);
            final bjmParser parser = new bjmParser(tokenStream);
            return parser.program();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
