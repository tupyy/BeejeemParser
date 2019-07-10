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

import com.beejeem.parser.value.BooleanValue;
import com.beejeem.parser.value.ListValue;
import com.beejeem.parser.value.Value;

public class BooleanType implements Type<Boolean> {

   @Override
   public Value createValue() {
      return new BooleanValue();
   }

   @Override
   public ListValue<Boolean> createList() {
      return new ListValue<>(new BooleanType());
   }

   @Override
   public boolean isEqual(Type anotherType) {
      return anotherType instanceof BooleanType;
   }

}
