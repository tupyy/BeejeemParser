grammar Jobfile;
import JobfileTokens;

@header {
package com.beejeem.antrl4.visitor;
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
   : VAR? variabletype varassignment
   ;

variabletype
    : TYPE_INT
    | TYPE_FLOAT
    | TYPE_STRING
    | TYPE_BOOLEAN
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
   | list
   ;

number
   :  ('+' | '-')? (INT | FLOAT)
   ;

string
   : QUOTE (WORD | INT | COMMA)* QUOTE
   ;

list
   : LEFTPAREN elements RIGHTPAREN
   ;

elements
   : element (COMMA element)*
   ;

element
   : number | string
   ;



