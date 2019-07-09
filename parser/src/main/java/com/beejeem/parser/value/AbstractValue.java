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

package com.beejeem.parser.value;

import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.type.Type;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractValue<T> implements Value<T> {

    private T value;

    public AbstractValue(T value) {
        this.value = value;
    }

    @Override
    public Value<?> add(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value<?> and(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value<?> div(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue eq(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue gt(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue gte(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue lt(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue lte(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue in(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value<?> mod(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value<?> mult(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value<?> power(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue neq(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value<?> not() {
        throw new InvalidOperationException();
    }

    @Override
    public Value<?> or(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value<?> subtract(Value<?> v) {
        throw new InvalidOperationException();
    }

    @Override
    public Type getType() {
        throw new InvalidOperationException();
    }

    @Override
    public void set(T v) {
        this.value = v;
    }

    public abstract void setValue(Value<?> value);

    @Override
    public T get() {
        return this.value;
    }

    @Override
    public boolean isValue() {
        return true;
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public boolean isMap() {return false;}

    @Override
    public ListValue<T> asList() {
        ListValue<T> listValue = new ListValue<>();
        listValue.add(this.value);
        return listValue;
    }

    @Override
    public Value<T> asValue() {
        Value<T> value = this.getType().createValue();
        value.set(this.value);
        return value;
    }
}
