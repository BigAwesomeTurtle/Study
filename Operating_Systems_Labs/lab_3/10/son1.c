#include <stdio.h>
int main(){
	int pid[2],ppid;
	pid[0]=getpid();
	ppid=getppid();
	if((pid[1] = fork()) == 0)
		execl("son3.out", "son3.out", NULL);
	printf("son, called from first fork is doing its calculations\n");
	return 0;
}