%{
	void calculate( void );
%}

%%
[IVXLCD]*(IIII|VV|XXXX|LL|CCCC|DD)[IVXLCD]* {
	printf("This roman number is not correct: %s\n Too much identical letters in a row\n", yytext);
}
[IVXLCD]+	{ calculate(); }

[ivxlcd]+	{
	printf("It's not a roman number: %s\n Use capital letters\n", yytext);
	};

.*[0-9]+.*  {
	printf("It's not a roman number: %s\n Input contains decimal numbers\n", yytext);
	};

.+	{
	printf("It's not a roman number: %s\n", yytext);
	};

\n|\t ;	

%%

#ifndef yywrap
int yywrap() { return( 1 ); }
#endif

void calculate( void ) {
	int nums [strlen(yytext)];
	fromRomanToInt(&nums);
	if (check(&nums) !=0) {
		printf("%s :\t %d\n", yytext, sum(&nums));
    };
}
int fromRomanToInt(int *nums) {
	char prev=yytext[0];
	char next=yytext[1];
	int i=0;
	int j=0;
	do {
		if (prev=='I'){
		i++;
		if(next=='V') nums[j]=4;
		else if (next=='X') nums[j] = 9;
		else if (next=='L') nums[j] = 49;
		else if (next=='C') nums[j] = 99;
		else if (next=='D') nums[j] = 499;
		else {
			 nums[j] = 1;
			i--;
		}
	} else if (prev=='X'){
		i++;
		if(next=='L') nums[j]=40;
		else if (next=='C') nums[j] = 90;
		else if (next=='D') nums[j] = 490;
	
		else {
			 nums[j] = 10;
			i--;
		}
	} else if (prev=='C' && next== 'D'){
		nums[j] = 400;
			i++;
		}
	else {
		switch (prev) {
			case 'V':nums[j] = 5; break;
			case 'L':nums[j] = 50; break;
			case 'C':nums[j] = 100; break;
			case 'D':nums[j] = 500; break;
			}
	}
	j++;
	i++;
	prev = yytext[i];
	next = yytext[i + 1];
} while (prev != '\0');
	nums[j] = -1;
}
int check(int *nums) {
	int count = 1;
	int prev;
	int next;
	int i = 0;
	while (nums[i + 1] != -1) {
		prev = nums[i];
		next = nums[i + 1];
		if (prev < next){
			printf("This roman number is not correct: %s\n Small value befor big\n" , yytext);
			return 0;
		}
		else count=1;
		i++;
		}
	return 1;
}
int sum(int *nums){
	int res = 0;
	int i=0;
	while(nums[i]!=-1){
		res += nums[i];
		i++;
	}
	return res;
}
		
int main() { while( yylex() ); return 0;}



