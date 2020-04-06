%{
#include "y.tab.h"
%}

%% 
"eles"  {yyerror("Warning: eles, did you mean else?");yylval.value = strdup(yytext); return VAR_NAME;}
"if"	{return _IF;}
"else"  {return _ELSE;}
[a-zA-Z][0-9a-zA-Z_]*   { yylval.value = strdup(yytext); return VAR_NAME; } 
[0-9]+		            { yylval.value = strdup(yytext);  return VAR_VALUE;}  
"+"	|
"-"	|
"*"	|
"/"	|
"=" |
"("	|
")"	| 
"{"	| 
"}"	| 
";"	|
":"	{return yytext[0];} 
[ \n\t]	; 
%% 

int yywrap() {return 1;}