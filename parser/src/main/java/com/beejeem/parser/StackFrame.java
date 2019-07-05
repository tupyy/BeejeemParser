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
package com.beejeem.parser;

import com.beejeem.parser.function.UserDefinedFunction;
import com.beejeem.parser.value.AbstractValue;
import com.beejeem.parser.value.IntegerValue;
import com.beejeem.parser.value.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * a stack frame, containing variables
 */
public class StackFrame {

   /**
    * variables
    */
   private Map<String, Value> variables = new HashMap<>();

   /**
    * functions and procedures
    */
   private final Map<String, UserDefinedFunction> functions = new HashMap<>();

   public void declareFunction(UserDefinedFunction functionOrProcedureDefinition) {
       functions.put(functionOrProcedureDefinition.getName(), functionOrProcedureDefinition);
   }

    /**
    * variable name and its value (which has it's type)
    */
   public void declareVariable(String name, Value value) {
      variables.put(name, value);
   }

   public UserDefinedFunction getFunctionDefinition(String name) {
      return functions.get(name.toLowerCase());
   }

   /**
    * get variable
    */
   public Value getVariable(String name) {
      return variables.get(name);
   }

   public boolean hasVariable(String name) {
      return this.variables.containsKey(name);
   }

   /**
    * Get the map of stack variables
    * @return map
    */
   public Map<String,Value> getVariables() {
      Map<String,Value> variables = new HashMap<>();
      for(Map.Entry<String,Value> entry: this.variables.entrySet()) {
         variables.put(entry.getKey(), entry.getValue());
      }
      return variables;
   }

   /**
    * remove variable
    */
   public void removeVariable(String name) {
      variables.remove(name);
   }

   public void setVariables(Map<String, Value> variables) {
      this.variables = variables;
   }
}
