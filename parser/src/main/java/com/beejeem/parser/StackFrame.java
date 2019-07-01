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

import com.beejeem.parser.value.Value;

import java.util.HashMap;

/**
 * a stack frame, containing variables
 */
public class StackFrame {
   /**
    * variables
    */
   private HashMap<String, Value> variables = new HashMap<String, Value>();
   /**
    * functions and procedures
    */
   private final HashMap<String, FunctionDefintion> functions = new HashMap<String, FunctionDefintion>();

   public void declareFunction(FunctionDefintion functionOrProcedureDefinition) {
       functions.put(functionOrProcedureDefinition.getName(), functionOrProcedureDefinition);
   }

    /**
    * variable name and its value (which has it's type)
    */
   public void declareVariable(String name, Value value) {
      variables.put(name, value);
   }

   public FunctionDefintion getFunctionOrProcedureDefinition(String name) {
      return functions.get(name.toLowerCase());
   }

   /**
    * get variable
    */
   public Value getVariable(String name) {
      return variables.get(name);
   }

   /**
    * remove variable
    */
   public void removeVariable(String name) {
      variables.remove(name);
   }

   public void setVariables(HashMap<String, Value> variables) {
      this.variables = variables;
   }
}
