#include <stdio.h>
int main(){
	int i, pid[3], ppid, status, result;
	pid[0]=getpid();
	ppid=getppid();
	printf("\nFATHER PARAMS: pid=%i ppid=%i\n", pid[0],ppid);
	if((pid[1] = fork()) == 0)
		execl("son1.out", "son1.out", NULL);
	if((pid[2] = fork()) == 0)
		execl("son2.out", "son2.out", NULL);
	system("ps xf > file.txt");
	printf("father process is doing its calculations");
	return 0;
}
