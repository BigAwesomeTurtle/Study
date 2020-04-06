#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <wait.h>
#include <string.h>
#include <signal.h>
int main(int argc, char *argv[]){
	int sid, pid, pid1, pid2, pid3, ppid, status;
	char command[50];
	pid = getpid();
	ppid = getppid();
	sid = getsid(pid);
	printf("FATHER PARAMS: sid = %i pid=%i ppid=%i \n", sid, pid,ppid);
	if((pid1=fork())==0) execl("son1.out","son1.out", NULL);
	if((pid2 = fork())==0) execl("son2.out","son2.out", argv[1], NULL);
	if((pid3 = fork())==0) execl("son3.out","son3.out", NULL);
	system("ps -s>>output.txt");
	kill(pid1,SIGUSR2);
	kill(pid2,SIGUSR2);
	kill(pid3,SIGUSR2);
	sleep(2);
	system("ps -s>>output.txt");
	wait(&status);
	wait(&status);
	wait(&status);
}