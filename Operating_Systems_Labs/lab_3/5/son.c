#include <stdio.h>

int main()
{
	int pid,ppid;
	pid=getpid();
	ppid=getppid();
	printf("\n\nson param: pid=%i ppid=%i\n\n",pid,ppid);
	sleep(15);
	return 0;
}