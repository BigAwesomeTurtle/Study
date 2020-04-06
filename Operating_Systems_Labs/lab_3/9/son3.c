#include <stdio.h>
#include <stdlib.h>
int main(){
	int pid,ppid;
	pid=getpid();
	ppid=getppid();
	printf("\nSON PARAMS: pid=%i ppid=%i\n",pid,ppid);
	sleep(15);
	exit(-1);
}