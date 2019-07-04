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
import com.beejeem.parser.type.FloatType;
import com.beejeem.parser.type.Type;

import static java.lang.Math.pow;

public class FloatValue extends AbstractValue<Float> {
    private float value;
    private Type type = new FloatType();

    public FloatValue() {
        value = 0;
    }

    public FloatValue(float value) {
        this.value = value;
    }

    @Override
    public Value add(Value v) {
        if (v instanceof IntegerValue) {
            return new FloatValue(value + ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new FloatValue(value + ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Value div(Value v) {
        if (v instanceof IntegerValue) {
            return new FloatValue(value / ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new FloatValue(value / ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue eq(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(value == ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(value == ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    public Float getValue() {
        return value;
    }

    @Override
    public BooleanValue gt(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(value > ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(value > ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue gte(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(value >= ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(value >= ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue lt(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(value < ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(value < ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue lte(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(value <= ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(value <= ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Value mult(Value v) {
        if (v instanceof IntegerValue) {
            return new FloatValue(value * ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new FloatValue(value * ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Value power(Value v) {
        if (v instanceof IntegerValue) {
            return new FloatValue((float) pow(value ,((IntegerValue) v).getValue()));
        } else if (v instanceof FloatValue) {
            return new FloatValue((float) pow(value,((FloatValue) v).getValue()));
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue neq(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(value != ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(value != ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public void set(Value v) {
        if (v instanceof FloatValue) {
            value = ((FloatValue) v).getValue();
        } else if (v instanceof IntegerValue) {
            value = ((IntegerValue) v).getValue();
        }
        else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public void set(Float v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public Value subtract(Value v) {
        if (v instanceof IntegerValue) {
            return new FloatValue(value - ((IntegerValue) v).getValue());
        } else if (v instanceof FloatValue) {
            return new FloatValue(value - ((FloatValue) v).getValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Value clone() {
        return new FloatValue(value);
    }
}
