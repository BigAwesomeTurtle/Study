#include <stdio.h>
#include <sched.h>
int main(){
	int pid[2],ppid;
	struct sched_param shdprm;
	pid[0]=getpid();
	ppid=getppid();
	printf("\nSON PARAMS: pid=%i ppid=%i\n",pid[0],ppid);
	if((pid[1] = fork()) == 0){
		shdprm.sched_priority = 10;
		if (sched_setscheduler (pid[1], SCHED_FIFO, &shdprm) == -1) {
			perror ("SCHED_SETSCHEDULER");
		}
		execl("son4.out", "son4.out", NULL);
	}
	int unused=0;
	for (int i=0;i<50000;i++){
		unused=unused+3;
	}
	printf("son, called from second fork is doing its calculations\n");
	return 0;
}