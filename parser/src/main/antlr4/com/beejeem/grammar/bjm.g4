/*
 * Beejeem2 Copyright 2019, Cosmin Tupangiu
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

grammar bjm;

program
 : block EOF
 ;

block
 : ( statement | functionDecl )* ( Return expression SColon )?
 ;

statement
 : assignment SColon
 | incrementStatement SColon
 | variableDeclaration
 | functionCall SColon
 | collectionCall SColon
 | ifStatement
 | forStatement
 | whileStatement
 ;

assignment
 : Identifier '=' expression
 ;

incrementStatement
 : Identifier op=(Increment | Decrement)
 ;

variableDeclaration
 : localVariableDeclaration SColon
 ;

localVariableDeclaration
 : typeType variableDeclarators
 ;

variableDeclarators
    : variableDeclarator (',' variableDeclarator)*
    ;

variableDeclarator
    : variableDeclaratorId ('=' variableInitializer)?
    ;

variableDeclaratorId
    : Identifier ('[' expression ']')*
    ;

variableInitializer
    : arrayInitializer
    | expression
    ;

arrayInitializer
    : '{' (variableInitializer (',' variableInitializer)* (',')? )? '}'
    ;

functionCall
 : Identifier '(' exprList? ')'
 ;

ifStatement
 : ifStat elseIfStat* elseStat?
 ;

ifStat
 : If OParen expression CParen OBrace block CBrace
 ;

elseIfStat
 : Else If OParen expression CParen OBrace block CBrace
 ;

elseStat
 : Else OBrace block CBrace
 ;

functionDecl
 : resultType Function Identifier '(' parameters? ')' OBrace block CBrace
 ;

forStatement
 : For OParen forControl CParen OBrace block CBrace
 ;

forControl
 : indexVariableDeclaration SColon expression SColon (assignment | expression)
 ;

indexVariableDeclaration
 : typeType variableDeclarator
 | assignment
 ;

whileStatement
 : While OParen expression CParen OBrace block CBrace
 ;

parameters
 : parameter ( ',' parameter )*
 ;

parameter
 : typeType Identifier;

exprList
 : expression ( ',' expression )*
 ;

expression
 : '-' expression                                       #unaryMinusExpression
 | '!' expression                                       #notExpression
 | expression postfix=('++' | '--')                     #incrementExpression
 | <assoc=right> expression '^' expression              #powerExpression
 | expression op=( '*' | '/' | '%' ) expression         #multExpression
 | expression op=( '+' | '-' ) expression               #addExpression
 | expression op=( '>=' | '<=' | '>' | '<' ) expression #compExpression
 | expression op=( '==' | '!=' ) expression             #eqExpression
 | expression op=( '&&' | '||' ) expression             #logicExpression
 | expression '?' expression ':' expression             #ternaryExpression
 | expression In expression                             #inExpression
 | Integer                                              #integerExpression
 | FloatNumber                                          #floatExpression
 | Bool                                                 #boolExpression
 | functionCall                                         #functionCallExpression
 | collectionCall                                       #listCallExpression
 | Identifier indexes?                                  #identifierExpression
 | String indexes?                                      #stringExpression
 | '(' expression ')' indexes?                          #expressionExpression
 ;

collectionCall
 : Identifier '.' Identifier OParen exprList? CParen
 ;

indexes
 : ( '[' expression ']' )+
 ;

resultType
  : typeType
  | Void
  ;

 typeType
  : Boolean
  | Int
  | Float
  | StringType
  | collectionType
  ;

collectionType
  : List LT typeType GT
  | Map LT typeType Comma typeType GT
  ;

fragment A
   : ('a' | 'A')
   ;


fragment B
   : ('b' | 'B')
   ;


fragment C
   : ('c' | 'C')
   ;


fragment D
   : ('d' | 'D')
   ;


fragment E
   : ('e' | 'E')
   ;


fragment F
   : ('f' | 'F')
   ;


fragment G
   : ('g' | 'G')
   ;


fragment H
   : ('h' | 'H')
   ;


fragment I
   : ('i' | 'I')
   ;


fragment J
   : ('j' | 'J')
   ;


fragment K
   : ('k' | 'K')
   ;


fragment L
   : ('l' | 'L')
   ;


fragment M
   : ('m' | 'M')
   ;


fragment N
   : ('n' | 'N')
   ;


fragment O
   : ('o' | 'O')
   ;


fragment P
   : ('p' | 'P')
   ;


fragment Q
   : ('q' | 'Q')
   ;


fragment R
   : ('r' | 'R')
   ;


fragment S
   : ('s' | 'S')
   ;


fragment T
   : ('t' | 'T')
   ;


fragment U
   : ('u' | 'U')
   ;


fragment V
   : ('v' | 'V')
   ;


fragment W
   : ('w' | 'W')
   ;


fragment X
   : ('x' | 'X')
   ;


fragment Y
   : ('y' | 'Y')
   ;


fragment Z
   : ('z' | 'Z')
   ;


Function : F U N C T I O N;
If       : I F;
Else     : E L S E;
Return   : R E T U R N;
For      : F O R;
While    : W H I L E;
To       : T O;
Boolean  : B O O L;
Int      : I N T;
In       : I N;
Float    : F L O A T;
Void     : V O I D;
List     : L I S T;
Map      : M A P;

Or       : '||';
And      : '&&';
Equals   : '==';
NEquals  : '!=';
GTEquals : '>=';
LTEquals : '<=';
Pow      : '^';
Excl     : '!';
GT       : '>';
LT       : '<';
Add      : '+';
Increment: '++';
Decrement: '--';
Subtract : '-';
Multiply : '*';
Divide   : '/';
Modulus  : '%';
OBrace   : '{';
CBrace   : '}';
OBracket : '[';
CBracket : ']';
OParen   : '(';
CParen   : ')';
SColon   : ';';
Assign   : '=';
Comma    : ',';
QMark    : '?';
Colon    : ':';

Integer
 : NUM_INT
 ;

FloatNumber
 : NUM_REAL
 ;

StringType
 : S T R I N G
 ;

Bool
 : True
 | False
 ;

Identifier
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

String
 : ["] ( ~["\r\n\\] | '\\' ~[\r\n] )* ["]
 | ['] ( ~['\r\n\\] | '\\' ~[\r\n] )* [']
 ;

Comment
 : ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip
 ;

BlockComment
 : ( '/*' .*? '*/' ) -> skip
 ;

Space
 : [ \t\r\n\u000C] -> skip
 ;

True
   : T R U E
   ;

False
   : F A L S E
   ;

fragment NUM_REAL
 : NUM_INT ( '.' Digit* )?
 ;

fragment NUM_INT
 : [1-9] Digit*
 | '0'
 ;

fragment Digit
 : [0-9]
 ;
