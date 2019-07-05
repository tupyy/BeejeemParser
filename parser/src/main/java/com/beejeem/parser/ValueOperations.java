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

package com.beejeem.parser;

import com.beejeem.grammar.bjmLexer;
import com.beejeem.parser.value.IntegerValue;
import com.beejeem.parser.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValueOperations {

    private static Map<Integer, BiFunction<Value,Value,Value>> biOperations;
    private static Map<Integer, Function<Value,Value>> uniOperations;

    static {
        Map<Integer, BiFunction<Value,Value,Value>> map = new HashMap<>();
        map.put(bjmLexer.Add, Value::add);
        map.put(bjmLexer.Subtract, Value::subtract);
        map.put(bjmLexer.Multiply, Value::mult);
        map.put(bjmLexer.Divide, Value::div);
        map.put(bjmLexer.GT, Value::gt);
        map.put(bjmLexer.GTEquals, Value::gte);
        map.put(bjmLexer.LT, Value::lt);
        map.put(bjmLexer.LTEquals, Value::lte);
        map.put(bjmLexer.Equals, Value::eq);
        map.put(bjmLexer.NEquals, Value::neq);
        map.put(bjmLexer.And, Value::and);
        map.put(bjmLexer.Or, Value::or);
        map.put(bjmLexer.Pow, Value::power);
        biOperations = Collections.unmodifiableMap(map);

        Map<Integer, Function<Value,Value>> unimap = new HashMap<>();
        unimap.put(bjmLexer.Excl, Value::not);
        unimap.put(bjmLexer.Subtract, (value) -> value.mult(new IntegerValue(-1)));
        unimap.put(bjmLexer.Increment, (value) -> value.add(new IntegerValue(1)));
        unimap.put(bjmLexer.Decrement, (value) -> value.subtract(new IntegerValue(1)));
        uniOperations = Collections.unmodifiableMap(unimap);
    }

    public static Map<Integer, BiFunction<Value,Value,Value>> getBiOperations() {
        return biOperations;
    }

    public static Map<Integer, Function<Value,Value>> getUniOperations() {return uniOperations;}

}
