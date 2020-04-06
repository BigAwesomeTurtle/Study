#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
int catch (int a) {
	printf("Son3 catch SIGUSR1\n");
	return 0;
}

int main(){
	int pid,ppid;signal(SIGUSR1, catch);
	pid=getpid();
	ppid=getppid();
	printf("\n\nSON3 PARAMS: pid=%i ppid=%i\n\n",pid,ppid);
	sleep(10);
	return 0; // статус завершения 0
}