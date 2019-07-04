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

public class BooleanValue extends AbstractValue<Boolean> {
    private boolean value;
    private Type type = new BooleanType();

    public BooleanValue() {
        value = false;
    }

    public BooleanValue(boolean value) {
        this.value = value;
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
    public String toString() {
        return Boolean.toString(value);
    }

    public boolean isValue() {
        return value;
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
    public Type getType() {
        return this.type;
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
