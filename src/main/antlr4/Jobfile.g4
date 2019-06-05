grammar Jobfile;
import JobfileTokens;

prog
    : line + EOF
    ;

line
    : statement | COMMENT
    ;

statement
    : varstmt
    ;

varstmt
   : VAR? variabletype varname EQ varvalue
   ;

variabletype
    : TYPE_INT
    | TYPE_FLOAT
    | TYPE_STRING
    | TYPE_BOOLEAN
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



