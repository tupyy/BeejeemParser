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
package com.beejeem.parser.type;

import com.beejeem.parser.value.StringValue;
import com.beejeem.parser.value.Value;

public class StringType implements Type {
   public final int UNBOUNDED = -1;
   private final int length;

   public StringType() {
      length = UNBOUNDED;
   }

   public StringType(int length) {
      this.length = length;
   }

   @Override
   public boolean builtIn() {
      return true;
   }

   @Override
   public Value createValue() {
      return new StringValue();
   }

   public int getLength() {
      return length;
   }

   public String toString() {
      return "String";
   }
}