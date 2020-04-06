#include <signal.h>
#include <pthread.h>
#include <stdio.h>
#include <signal.h>
pthread_t t1, t2;
void sigHandler2(int sigNo) {
	static int i = 0;
	printf(" we catch i = %d times\n",i+1);
	i++;
	if (i == 3){
		printf("now signal ^Z is default \n");
		signal(SIGTSTP,SIG_DFL);
	}
}

void sigHandler(int sigNo) {
	static int i = 0;
	printf(" we catch i = %d times\n",i+1);
	i++;
	if (i == 6){
		printf("now signal ^C is default \n");
		signal(SIGINT,SIG_DFL);
	}
}

void* thread1() {
	int i,cnt = 0;
	int tid,pid;
	pid = getpid();
	signal(SIGINT,sigHandler);
	signal(SIGTSTP,sigHandler2);
	printf("Thread_1 with thread pid = %d is started\n", pid);
	for (i = 0; i<10; i++) {
		cnt++;
		printf("thread1: %i\n", cnt);
		sleep(5);
	}
}

void* thread2() {
	int i,cnt = 0;
	int pid;
	pid = getpid();
	signal(SIGINT,sigHandler);
	signal(SIGTSTP,sigHandler2);
	printf("Thread_2 with thread pid = %d is started\n", pid);
	for (i = 0; i<10; i++) {
		cnt++;
		printf("thread2: %i\n", cnt);
		sleep(1);
	}
}

main() {
	pthread_create(&t1, NULL, thread1, NULL);
	pthread_create(&t2, NULL, thread2, NULL);
	pthread_join(t1, NULL);
	pthread_join(t2, NULL);
	signal(SIGINT,sigHandler);
	signal(SIGTSTP,sigHandler2);
}