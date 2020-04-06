#include <stdio.h>
int main(){
	int pid[2],ppid;
	pid[0]=getpid();
	ppid=getppid();
	printf("\nSON PARAMS: pid=%i ppid=%i\n",pid[0],ppid);
	if((pid[1] = fork()) == 0)
		execl("son4.out", "son4.out", NULL);
	printf("son, called from second fork is doing its calculations\n");
	return 0;
}