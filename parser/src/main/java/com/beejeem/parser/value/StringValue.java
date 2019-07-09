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
import com.beejeem.parser.type.StringType;
import com.beejeem.parser.type.Type;

public class StringValue extends AbstractValue<String> {

    private Type type = new StringType();

    public StringValue() {
        super("");
    }

    public StringValue(String value) {
        super(value);
    }

    @Override
    public Value clone() {
        return new StringValue(this.get());
    }

    @Override
    public BooleanValue eq(Value v) {
        throw new InvalidOperationException();
    }


    public void set(Value v) {
        // FIXME remove only quote from the beginning of the word?
        super.set(v.toString().replace("\"",""));
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.get();
    }
}
