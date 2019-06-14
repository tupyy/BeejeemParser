
/** Taken from "The Definitive ANTLR 4 Reference" by Terence Parr */
// Derived from http://json.org
grammar JSON;

@header {
package com.beejeem.antrl4;
}

json
   : '{' varassignment (',' varassignment )* '}'
   | '{' '}'
   ;

varassignment
   : varname ':' varvalue
   ;

varvalue
   : string
   | number
   | bool
   ;

varname
   : '"'string_content '"'
   ;

string
   : '"' string_content '"'
   ;

string_content
   : WORD (WORD | number)*
   ;

number
   :  ('+' | '-')? (INT | FLOAT)
   ;

bool
   : TRUE
   | FALSE
   ;

INT
   : ('0' .. '9')+
   ;

// no leading zeros

FLOAT
   : ('0' .. '9')* '.' ('0' .. '9')*
   ;

// \- since - means "range" inside [...]

TRUE
   : 'TRUE' | 'true'
   ;

FALSE
   : 'FALSE' | 'false'
   ;

WORD
   : ('a' .. 'z' | 'A' .. 'Z') +
   ;

WS
   : [ \t\n\r] + -> skip
   ;
