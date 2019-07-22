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
package com.beejeem.parser.function;

import java.util.Hashtable;

public class RuntimeFunctionFactory {
   private final Hashtable<String, RuntimeFunction> functions = new Hashtable<String, RuntimeFunction>();

   public RuntimeFunctionFactory() {

   }

   private void addFunction(String name, RuntimeFunction runtimeFunction) {
      functions.put(name.toLowerCase(), runtimeFunction);
   }

   public RuntimeFunction getRuntimeFunction(String name) {
      final RuntimeFunction runtimeFunction = functions.get(name.toLowerCase());
      if (null != runtimeFunction) {
         return runtimeFunction;
      } else {
         return null;
      }
   }
}
