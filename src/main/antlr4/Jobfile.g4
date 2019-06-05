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
   : VAR? variabletype varname EQ exprlist
   ;

variabletype
    : TYPE_INT
    | TYPE_FLOAT
    | TYPE_STRING
    | TYPE_LIST
    ;
varname
   : WORD (WORD | number)*
   ;

exprlist
   : number
   | string
   | list
   ;

number
   :  ('+' | '-')? (DIGIT | FLOAT)
   ;

string
   : QUOTE (WORD | DIGIT | COMMA)* QUOTE
   ;

list
   : LEFTPAREN (number | string) (COMMA (number | string))* RIGHTPAREN
   ;



