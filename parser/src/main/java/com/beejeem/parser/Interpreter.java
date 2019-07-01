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

import com.beejeem.grammar.bjmLexer;
import com.beejeem.grammar.bjmParser;
import com.beejeem.grammar.bjmParser.ProgramContext;
import com.beejeem.parser.exception.InterpreterException;
import com.beejeem.parser.listeners.ProgramListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class Interpreter {
   /*
    * execution context
    */
   private final ExecutionContext executionContext;

   public Interpreter() {
      executionContext = new ExecutionContext();
   }

   public Interpreter(PrintStream consoleOut) {
      executionContext = new ExecutionContext(consoleOut);
   }

   /**
    * parse program from inputstream
    */
   private ProgramContext parse(InputStream inputStream) throws IOException {
      final Lexer lexer = new bjmLexer(CharStreams.fromStream(inputStream));
      final TokenStream tokenStream = new CommonTokenStream(lexer);
      final bjmParser parser = new bjmParser(tokenStream);
      return parser.program();
   }

   public void run(InputStream inputStream) throws Exception {
      try {
         /*
          * parse program
          */
         final ProgramContext programContext = parse(inputStream);
         if (programContext != null) {
            /*
             * enter program listener
             */
            final ProgramListener programListener = new ProgramListener(executionContext);
            programContext.enterRule(programListener);
         }
      } catch (final Exception e) {
         throw new InterpreterException("Exception in run", e);
      }
   }
}
