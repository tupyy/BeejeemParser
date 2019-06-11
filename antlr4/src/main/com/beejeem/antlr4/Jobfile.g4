grammar Jobfile;
import JobfileTokens;

@header {
package com.beejeem.antrl4;
}

program
    : line + EOF
    ;

line
    : statement | COMMENT
    ;

statement
    : varassignment
    | commandstatement
    ;

varassignment
    : varname EQ varvalue
    ;

varname
   : WORD (WORD | number)*
   ;

varvalue
   : number
   | string
   | bool
   ;

commandstatement
   : generatestm
   | runstm
   | copystm
   | rcopystm
   | submitstm
   ;

generatestm
   : GENERATE string
   ;

runstm
   : RUN string
   ;

copystm
   : COPY string string
   ;

rcopystm
   : RCOPY string string
   ;

submitstm
   : SUBMIT string
   ;

number
   :  ('+' | '-')? (INT | FLOAT)
   ;

string
   : QUOTE (WORD | INT | COMMA)* QUOTE
   ;

bool
   : TRUE
   | FALSE
   ;


