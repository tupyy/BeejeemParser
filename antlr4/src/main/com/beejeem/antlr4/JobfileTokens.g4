lexer grammar JobfileTokens;

VAR
   : 'VAR' | 'var'
   ;

ENV
   : 'ENV' | 'env'
   ;

RUN
   : 'RUN' | 'run'
   ;

GENERATE
   : 'GENERATE' | 'generate'
   ;

COPY
   : 'COPY' | 'copy'
   ;

RCOPY
   : 'RCOPY' | 'rcopy'
   ;

SUBMIT
   : 'SUBMIT' | 'submit'
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

TRUE
   : 'TRUE' | 'true'
   ;

FALSE
   : 'FALSE' | 'false'
   ;

COMMENT
   : '#' ~ [\r\n]*
   ;

WORD
   : ('a' .. 'z' | 'A' .. 'Z') +
   ;

INT
   : ('0' .. '9')+
   ;

FLOAT
   : ('0' .. '9')* '.' ('0' .. '9')*
   ;

WS
   : [ \r\n\t] + -> skip
   ;