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
    : varstmt
    ;

varstmt
   : VAR? varassignment
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


