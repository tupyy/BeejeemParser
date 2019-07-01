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

import com.beejeem.parser.type.Type;

public interface Value<T> extends Cloneable {

   /**
    * Addition operation
    * @param v value to be added to the current one
    * @return result
    */
   Value add(Value v);

   /**
    * Logic and operation
    * @param v operand
    * @return result
    */
   Value and(Value v);

   /**
    * Division operation
    * @param v value
    * @return result
    */
   Value div(Value v);

   /**
    * Test if the values are equal
    * @param v value to be test against
    * @return result
    */
   BooleanValue eq(Value v);

   /**
    * Greater than
    * @param v value to be test against
    * @return result
    */
   BooleanValue gt(Value v);

   /**
    * Greater or equal test
    * @param v value to be test against
    * @return result
    */
   BooleanValue gte(Value v);

   /**
    * Less than test
    * @param v value to be test against
    */
   BooleanValue lt(Value v);

   /**
    * Less or equal test
    * @param v value to be test against
    */
   BooleanValue lte(Value v);

   /**
    * Test for list. Test if a value is found in a list
    * @param v test value
    * @return true if value is contained in the list
    */
   BooleanValue in(Value v);

   /**
    * Mod operation
    */
   Value mod(Value v);

   /**
    * Multiplication operation
    */
   Value mult(Value v);

   /**
    * Not equal
    */
   Value neg();

   /**
    * Not equal
    */
   BooleanValue neq(Value v);

   Value not();

   /**
    * Logic or
    */
   Value or(Value v);

   /**
    * Setter
    * @param v new value
    */
   void set(Value v);

   /**
    * Setter
    * @param v
    */
   void set(T v);

   /**
    * Substract operation
    */
   Value subtract(Value v);

   /**
    * Return the type of the value
    * @return {@link Type}
    */
   Type getType();

   /**
    * Getter
    */
   T getValue();

   /**
    * Clone the current value
    * @return clone value
    */
   Value clone();

   /**
    * To string
    */
   String toString();
}
