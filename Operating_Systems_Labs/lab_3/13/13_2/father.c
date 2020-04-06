#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <wait.h>
#include <string.h>
int main(int argc, char *argv[]){
	int sid, pid, pid1, ppid, status;
	char command[50];
	pid = getpid();
	ppid = getppid();
	sid = getsid(pid);
	sprintf(command, "ps xjf | grep \"STAT\\|%d\" > %s", sid, argv[1]);
	printf("FATHER PARAMS: sid = %i pid=%i ppid=%i \n", sid, pid,ppid);
	if(fork()==0) execl("son.out","son.out",NULL);
	system(command);
}