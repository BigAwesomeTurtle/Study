#include <signal.h>
#include <sys/types.h>
#include "pthread.h"
#include <stdio.h>
pthread_t t1;
void handler(int sig){
	printf("Catched signal %s\n",sig == SIGUSR1 ? "SIGUSR1": "SIGUSR2");
	printf("Parent = %d\n",getppid());
	signal(SIGINT, SIG_DFL);
}

void* thread1(void* ptr){
	signal(SIGUSR1,handler);
	signal(SIGUSR2,handler);
	int count =0;
	while (1){
		printf("Thread_1 итерация %i\n", count);
		count++;
		sleep(1);
	}
}

int main(){
	pthread_create(&t1, NULL, thread1, NULL);
	pthread_join(t1, NULL);
	pthread_kill(t1,SIGUSR2);
	while(1){
		printf("Father thread \n");
		sleep(1);
	}
	return 0;
}