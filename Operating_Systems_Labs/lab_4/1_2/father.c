#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
int main(){
	int pid, ppid, status, a, b, c;
	pid = getpid();
	ppid = getppid();
	printf("\n\nFATHER PARAM: pid=%i ppid=%i\n", pid, ppid);
	if ((a = fork()) == 0)
		execl("son1.out", "son1.out", NULL);
	if ((b = fork()) == 0)
		execl("son2.out", "son2.out", NULL);
	if ((c = fork()) == 0)
		execl("son3.out", "son3.out", NULL);
	sleep(6);
	kill(a, SIGINT);
	kill(b, SIGCHLD);
	kill(c, SIGUSR1);
	system("ps xf > file.txt");
	//wait(&status);
	return 0;
}