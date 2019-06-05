lexer grammar BeejeemTokens;

VAR
   : 'VAR' | 'var'
   ;

ENV
   : 'ENV' | 'env'
   ;

RUN
   : 'RUN' | 'run'
   ;

EQ
   : '='
   ;

LEFTPAREN
   : '['
   ;

RIGHTPAREN
   : ']'
   ;

COMMA
   : ','
   ;

QUOTE
   : '"'
   ;
/*
 Types definition
*/
TYPE_INT
   : 'INT' | 'int'
   ;

TYPE_FLOAT
   : 'FLOAT' | 'float'
   ;

TYPE_STRING
   : 'STRING' | 'string'
   ;

TYPE_LIST
   : 'LIST' | 'list'
   ;

COMMENT
   : '#' ~ [\r\n]*
   ;

WORD
   : ('a' .. 'z' | 'A' .. 'Z') +
   ;
DIGIT
   : ('0' .. '9')+
   ;
FLOAT
   : ('0' .. '9')* '.' ('0' .. '9') + (('e' | 'E') ('0' .. '9') +)*
   ;
WS
   : [ \r\n\t] + -> skip
   ;