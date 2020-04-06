#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
int main(){
	int pid,ppid;
	signal(SIGCHLD, SIG_IGN);
	pid=getpid();
	ppid=getppid();
	printf("\n\nSON2 PARAMS: pid=%i ppid=%i\n\n",pid,ppid);
	sleep(10);
	return 0; // статус завершения 0
}