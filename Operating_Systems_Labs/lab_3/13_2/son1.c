#include <stdio.h>
#include <signal.h>
void main(){
	int pid,ppid;
	pid=getpid();
	ppid=getppid();
	printf(" SON_1 PARAMS : pid=%i ppid=%i\nFather creates and waits \n", pid,ppid);
	signal(SIGUSR2, SIG_DFL);
	sleep(1);
}