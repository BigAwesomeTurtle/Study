#include <stdio.h>
void main(){
	int pid,ppid;
	pid=getpid();
	ppid=getppid();
	printf(" SON_1 PARAMS : pid=%i ppid=%i\n Father creates and waits \n", pid,ppid);
	sleep(3);
}