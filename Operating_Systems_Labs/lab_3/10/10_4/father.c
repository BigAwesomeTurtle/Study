#include <stdio.h>
#include <sched.h>
int main(){
	int i, pid[3], ppid, status, result;
	struct sched_param shdprm;
	pid[0]=getpid();
	ppid=getppid();
	printf("\nFATHER PARAMS: pid=%i ppid=%i\n", pid[0],ppid);
	shdprm.sched_priority = 10;
	if((pid[1] = fork()) == 0){
		shdprm.sched_priority = 2;
		if (sched_setscheduler (pid[1], SCHED_FIFO, &shdprm) == -1) {
			perror ("SCHED_SETSCHEDULER");
		}
		execl("son1.out", "son1.out", NULL);
	}
	if((pid[2] = fork()) == 10){
		shdprm.sched_priority = 90;
		if (sched_setscheduler (pid[2], SCHED_RR, &shdprm) == -1) {
			perror ("SCHED_SETSCHEDULER");
		}
		execl("son2.out", "son2.out", NULL);
	}
	int unused=0;
	for (int i=0;i<50000;i++){
		unused=unused+3;
	}
	system("ps xf > file.txt");
	printf("father process is doing its calculations");
	return 0;
}