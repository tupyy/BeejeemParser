
/** Taken from "The Definitive ANTLR 4 Reference" by Terence Parr */
// Derived from http://json.org
grammar JSON;

@header {
package com.beejeem.antrl4;
}

json
   : varvalue
   ;

obj
   : '{' varassignment (',' varassignment)* '}'
   | '{' '}'
   ;

varassignment
   : string ':' varvalue
   ;

varvalue
   : string
   | number
   | obj
   | bool
   | 'null'
   ;


string
   : '"' (ESC | SAFECODEPOINT)* '"'
   ;


number
   :  ('+' | '-')? (INT | FLOAT)
   ;

bool
   : TRUE
   | FALSE
   ;

fragment ESC
   : '\\' (["\\/bfnrt] | UNICODE)
   ;


fragment UNICODE
   : 'u' HEX HEX HEX HEX
   ;


fragment HEX
   : [0-9a-fA-F]
   ;


fragment SAFECODEPOINT
   : ~ ["\\\u0000-\u001F]
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

WS
   : [ \t\n\r] + -> skip
   ;
