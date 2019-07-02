/*
 *
 *    * Beejeem2 Copyright 2019, Cosmin Tupangiu
 *    *
 *    *   This program is free software: you can redistribute it and/or modify
 *    *   it under the terms of the GNU General Public License as published by
 *    *   the Free Software Foundation, either version 3 of the License, or
 *    *   (at your option) any later version.
 *    *
 *    *    This program is distributed in the hope that it will be useful,
 *    *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    *    GNU General Public License for more details.
 *    *
 *    *    You should have received a copy of the GNU General Public License
 *    *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.beejeem.parser.value;

import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.type.BooleanType;
import com.beejeem.parser.type.Type;

public class BooleanValue implements Value<Boolean> {
    private boolean value;
    private Type type = new BooleanType();

    public BooleanValue() {
        value = false;
    }

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public Value add(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value and(Value v) {
        if (v instanceof BooleanValue) {
            return new BooleanValue(value && ((BooleanValue) v).isValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Value clone() {
        return new BooleanValue(value);
    }

    @Override
    public Value div(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue eq(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public BooleanValue gt(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue gte(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue in(Value v) {
        throw new InvalidOperationException();
    }

    public boolean isValue() {
        return value;
    }

    @Override
    public BooleanValue lt(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue lte(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value mod(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value mult(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value power(Value v) {
        throw new InvalidOperationException("Power operation not allowed on booleans.");
    }

    @Override
    public Value neg() {
        throw new InvalidOperationException();
    }

    @Override
    public BooleanValue neq(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value not() {
        return new BooleanValue(!value);
    }

    @Override
    public Value or(Value v) {
        if (v instanceof BooleanValue) {
            return new BooleanValue(value | ((BooleanValue) v).isValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public void set(Value v) {
        if (v instanceof BooleanValue) {
            value = ((BooleanValue) v).isValue();
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public void set(Boolean v) {
        this.value = v;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public Value subtract(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
