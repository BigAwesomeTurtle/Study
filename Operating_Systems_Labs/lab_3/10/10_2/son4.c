#include <stdio.h>
int main(){
	int pid,ppid;
	pid=getpid();
	ppid=getppid();
	printf("\nSON PARAMS: pid=%i ppid=%i\n",pid,ppid);
	printf("son, called from fork in second son is doing its calculations\n");
	return 0;
}