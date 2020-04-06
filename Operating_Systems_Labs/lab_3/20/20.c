#include <signal.h>
#include <pthread.h>
#include <stdio.h>
#include <signal.h>
pthread_t t1, t2;
void* thread1() {
	int i,cnt = 0;
	int tid,pid;
	pid = getpid();
	printf("Thread_1 with thread pid = %d is started\n", pid);
	for (i = 0; i<10; i++) {
		cnt++;
		printf("thread1: %i\n", cnt);
		sleep(5);
		if (cnt == 1) pthread_kill(t2, SIGUSR2);
	}
}

void* thread2() {
	int i,cnt = 0;
	int pid;
	pid = getpid();
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
}