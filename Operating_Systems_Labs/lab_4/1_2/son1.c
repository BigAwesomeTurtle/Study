#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
int main(){
	int pid,ppid;
	signal(SIGINT, SIG_DFL);
	pid=getpid();
	ppid=getppid();
	printf("\n\nSON1 PARAMS: pid=%i ppid=%i\n\n",pid,ppid);
	sleep(10);
	printf("We will not see this, because of SIGINT interruption");
	return 0; //статус завершения 0
}