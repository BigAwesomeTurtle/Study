#include <stdio.h>
void main(int argc, char *argv[]){
	int pid,ppid;
	pid=getpid();
	ppid=getppid();
	printf("SON_2 PARAMS: pid=%i ppid=%i\n Father finished before son termination without waiting for it \n",pid,ppid);
	sleep(5);
	ppid = getppid();
	printf("SON_2 PARAMS ARE CHANGED: pid=%i ppid=%i\n",pid,ppid);
}