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
    private Type type = new FloatType();

    public FloatValue() {
        super(0f);
    }

    public FloatValue(float value) {
        super(value);
    }

    @Override
    public FloatValue add(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new FloatValue(this.get() + ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new FloatValue(this.get() + ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public FloatValue div(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new FloatValue(this.get() / ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new FloatValue(this.get() / ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue eq(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get().intValue() == ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() == ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue gt(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get().intValue() > ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() > ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue gte(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get().intValue() >= ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() >= ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue lt(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get().intValue() < ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() < ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue lte(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get().intValue() <= ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() <= ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public FloatValue mult(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new FloatValue(this.get() * ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new FloatValue(this.get() * ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public FloatValue power(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new FloatValue((float) pow(this.get() ,((IntegerValue) v).get()));
        } else if (v instanceof FloatValue) {
            return new FloatValue((float) pow(this.get(),((FloatValue) v).get()));
        } else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public BooleanValue neq(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new BooleanValue(this.get().intValue() != ((IntegerValue) v).get());
        } else if (v instanceof FloatValue) {
            return new BooleanValue(this.get() != ((FloatValue) v).get());
        } else {
            throw new InvalidOperationException();
        }
    }

    public void setValue(Value<?> v) {
        if (v instanceof FloatValue) {
            this.set(((FloatValue) v).get());
        } else if (v instanceof IntegerValue) {
            this.set((float) ((IntegerValue) v).get());
        }
        else {
            throw new InvalidOperationException();
        }
    }

    @Override
    public String toString() {
        return Double.toString(this.get());
    }

    @Override
    public FloatValue subtract(Value<?> v) {
        if (v instanceof IntegerValue) {
            return new FloatValue(this.get() - ((IntegerValue) v).get());
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
