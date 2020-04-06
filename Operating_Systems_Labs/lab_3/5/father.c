#include <stdio.h>

int main()
{
	int pid, ppid, status;
	pid=getpid();
	ppid=getppid();
	printf("\n\nfather param: pid=%i ppid=%i\n", pid,ppid);
	if(fork()==0)
	{
		execl("son_out","son_out", NULL);
	}
	system("ps -xf > file.txt");
	wait(&status);
	printf("\n\nChild proccess is finished with status %d\n\n", status);
	return 0;
}