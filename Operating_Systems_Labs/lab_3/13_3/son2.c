#include <stdio.h>
#include <signal.h>
void sign(int a){
	printf("recive signal\n");
	system("date");
}
void main(){
	int pid,ppid;
	pid=getpid();
	ppid=getppid();
	printf(" SON_2 PARAMS : pid=%i ppid=%i\nFather creates and waits \n", pid,ppid);
	signal(SIGUSR2, sign);
	sleep(5);
}