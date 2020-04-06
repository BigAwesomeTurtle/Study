#include <stdio.h>
#include <stdlib.h>

int main()
{
	int pid, n, m;
	pid = fork();
	n = 500;
	m = 0;
	if(pid == -1){
		perror("fork error");
		exit(1);
	}
	while(1){
		if(pid == 0){
			printf("new pid = %d, ppid =%d \n", getpid(),getppid() );
			n--;
			if(n == 0){
				break;
			}
		}
		else {
			printf("parent pid = %d, ppid =%d \n", getpid(),getppid() );
			m++;
			if(m == 500){
				break;
			}
		}
	}
	printf("Process finished");
	printf("n=%i, m=%i\n", n, m);
	exit(1);
}