#include <stdio.h>
#include <signal.h>
void handler(){
	puts("^C - signal received");
	signal(SIGINT, SIG_DFL);
}

void main(){
	int pid,ppid;
	pid=getpid();
	ppid=getppid();
	printf(" SON_3 PARAMS : pid=%i ppid=%i\nFather creates and waits \n", pid,ppid);
	signal(SIGUSR2, handler);
	while(1);
	sleep(1);
}