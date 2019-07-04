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
import com.beejeem.parser.type.IntegerType;
import com.beejeem.parser.type.Type;

import static java.lang.Math.pow;

public class IntegerValue extends AbstractValue<Integer> {

    private final Type type = new IntegerType();

    public IntegerValue() {
        super(0);
    }

    public IntegerValue(int value) {
        super(value);
    }

    @Override
    public Value add(Value v) {
        if (v instanceof IntegerValue) {
            return new IntegerValue(this.get() + ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new FloatValue(this.get() + ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Value and(Value v) {
        throw new InvalidOperationException();
    }

    public void decrement() {
        this.set(this.get() - 1);
    }

    @Override
    public Value clone() {
        return new IntegerValue(this.get());
    }

    @Override
    public Value div(Value v) {
        if (v instanceof IntegerValue) {
            return new FloatValue((float) this.get() / ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new FloatValue(this.get() / ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue eq(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get().equals( ((IntegerValue)v).get()));
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public String toString() {
        return Integer.toString(this.get());
    }

    @Override
    public BooleanValue gt(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get() > ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() > ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue gte(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get() >= ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() >= ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    public void increment() {
        this.set(this.get() + 1);
    }

    @Override
    public BooleanValue lt(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get() < ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() < ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue lte(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get() <= ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() <= ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Value mod(Value v) {
        throw new InvalidOperationException();
    }

    @Override
    public Value mult(Value v) {
        if (v instanceof IntegerValue) {
            return new IntegerValue(this.get() * ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new FloatValue(this.get() * ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Value power(Value v) {
        if (v instanceof IntegerValue) {
            return new FloatValue((float) pow(this.get() ,((IntegerValue) v).get()));
        } else if (v instanceof FloatValue) {
            return new FloatValue((float) pow(this.get(),((FloatValue) v).get()));
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Value neg() {
        return new IntegerValue(this.get() * -1);
    }

    @Override
    public BooleanValue neq(Value v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get() != ((IntegerValue) v).get().intValue());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public void set(Value v) {
        if (v instanceof IntegerValue) {
            this.set(((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            this.set(((FloatValue) v).get().intValue());
        }
        else {
            throw new InvalidOperationException(
                    String.format("Cannot cast %s to %s",v.getType(), this.getType()));
        }
    }

    @Override
    public Value subtract(Value v) {
        if (v instanceof IntegerValue) {
            return new IntegerValue(this.get() - ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new FloatValue(this.get() - ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public Type getType() {
        return this.type;
    }
}
