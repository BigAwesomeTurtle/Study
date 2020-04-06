%union {
char* value;
};

%{
#include <stdlib.h> 
#include <string.h>
int counter=0;
int else_counter=0;
int md_counter=0;
%} 

%token  _IF _ELSE
%token	<value>VAR_NAME
%token	<value>VAR_VALUE
%start	prog

%%
prog		:	statement {printf("L:%d\n",counter+1);}
			|   statement prog ;
statement	:	if_start cond body else_start body | if_start cond body
			|   if_start body {yyerror("Error: Missing if condition");exit(1);}; 

if_start	:	_IF   {counter++; else_counter=0; printf("L:%d\n",counter);};
else_start	:	_ELSE {counter++; else_counter++; printf("    jmp L%d\n", counter+1); 
					   printf("L:%d\n",counter);};

cond		:	'(' exp ')' {printf("    mov bx, %s\n    cmp ax, bx\n", "0");
					 		 printf("    je L%d\n", counter+1); md_counter=0;};

va		:	VAR_NAME   {$<value>$ = $<value>1;}
		|	VAR_VALUE  {$<value>$ = $<value>1;};

exp_md		: 	va '*' va { 
							printf("    mov a%d, %s\n", md_counter,$<value>1);
							printf("    mul a%d, %s\n", md_counter, $<value>3);$<value>$=md_counter;}
			|	va '/' va { 
							printf("    mov a%d, %s\n", md_counter, $<value>1);
							printf("    div a%d, %s\n", md_counter, $<value>3);$<value>$=md_counter;}

			|   exp_md '*' va { printf("    mul a%d, %s\n", md_counter, $<value>3);$<value>$=md_counter;}
			|   exp_md '/' va { printf("    div a%d, %s\n", md_counter, $<value>3);$<value>$=md_counter;}

exp 		:   va {printf("    mov ax, %s\n", $<value>1);}
			|   exp '+' va { printf("    add ax, %s\n", $<value>3);}
			|	exp '-' va { printf("    sub ax, %s\n", $<value>3);}
			|   exp '+' exp_md { printf("    add ax, a%d\n", $<value>3); md_counter++;}
			|	exp '-' exp_md { printf("    sub ax, a%d\n", $<value>3); md_counter++;}
			|   exp_md {printf("    mov ax, a%d\n", $<value>1); md_counter++;};

body		:	'{' nest_code '}' | '{' '}';

nest_code	:	assign | statement | assign nest_code | statement nest_code;

assign		:	VAR_NAME '=' va ';' {printf("    mov %s, %s\n", $<value>1, $<value>3); md_counter=0;}
			|	VAR_NAME '=' exp ';' {printf("    mov %s, ax\n", $<value>1);md_counter=0;}
			|   VAR_VALUE '=' va ';' {yyerror("Error : You can't assing value to an integer");exit(1);}
			|   VAR_VALUE '=' exp ';' {yyerror("Error : You can't assing value to an integer");exit(1);};

%%