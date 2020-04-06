#include <stdio.h>
#include <math.h>
#include <sys/resource.h>
#include <stdlib.h>

void main(int argc, char* argv[])
{
	int m, n, pid;
	m=5000;
	n=1;
	pid = fork();
	if(pid == -1){
		perror("fork error");
		exit(1);
	}
	printf("pid=%i\n", pid);
	if(pid !=0){
		int j;
		for(j = 1; j <= 5000; j++){
			m-=1;
		}
		printf("father: %i\n\n", m);
	}
	else{
		int i;
		for(i = 1; i <= 5000; i++){
			n+=1;
		}
		printf("son: %d\n\n", n);
	}
	printf("Program finished\n");
	exit(1);
}