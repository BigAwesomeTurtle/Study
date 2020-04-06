#include <pthread.h> 
#include <signal.h> 
#include <stdio.h> 
#include <sys/types.h> 
#include <linux/unistd.h> 
#include <sys/syscall.h> 
pthread_t t1, t2; 
void hnd (int sig) { 
if(sig == SIGUSR1) { 
printf("Catched SIGUSR1 %d\n",sig); 
signal(sig,hnd); //восстановление диспозицииобработчиком 
return; 
} 
if(sig == SIGUSR2) { 
printf("Catched SIGUSR2 %d\n",sig); 
signal(sig, hnd); 
return; 
} 
if(sig == SIGRTMIN+1) { 
printf("Catched SIGRTMIN+1 %d\n",sig); 
signal(sig, hnd); 
return; 
} 
if(sig == SIGRTMIN+2) { 
printf("Catched SIGRTMIN+2 %d\n",sig); 
signal(sig, hnd); 
return; 
} 
if(sig == SIGRTMIN+3) { 
printf("Catched SIGRTMIN+3 %d\n",sig); 
signal(sig, hnd); 
return; 
} 
if(sig == SIGRTMIN+4) { 
printf("Catched SIGRTMIN+4 %d\n",sig); 
signal(sig, hnd); 
return; 
} 
if(sig == SIGRTMIN+5) { 
printf("Catched SIGRTMIN+5 %d\n",sig); 
signal(sig, hnd); 
return; 
}
if(sig == SIGINT) { 
printf("Catched SIGINT %d\n",sig); 
signal(sig, hnd); 
return; 
}

if(sig == SIGCHLD) { 
printf("Catched SIGCHLD %d\n",sig); 
signal(sig, hnd); 
return; 
}
}
void* thread1() { 
int i, count = 0; 
int tid, pid; 
tid = syscall(SYS_gettid); 
pid = getpid(); 
printf("Thread_1 with thread id = %d and pid = %d is started\n", tid, pid); 
sigset_t mask; 
sigemptyset(&mask); 	// иниц. набора сигналов пустми значениями
	
	//добавляем в маску сигналы
	 sigaddset(&mask, SIGRTMIN);
	 sigaddset(&mask, SIGRTMIN + 1);
	 sigaddset(&mask, SIGRTMIN + 2);
	 sigaddset(&mask, SIGUSR1);
	 sigaddset(&mask, SIGUSR1);
	 sigaddset(&mask, SIGUSR2);
	 sigaddset(&mask, SIGCHLD);

int n=1; //можно установить любым 
for (i = 0; i < n; i++) { 
count += 1; 
printf("Thread_1: step %d \n", count); 
sleep(5); 
//блокируем обработчик сигналов
	 sigprocmask(SIG_BLOCK, &mask, NULL);
// отправка обычных сигналов из первой нити во вторую 
pthread_kill(t2, SIGUSR1); 
pthread_kill(t2, SIGUSR2); 
pthread_kill(t2, SIGUSR1);
pthread_kill(t2, SIGRTMIN+3);// отправка сигналов реального времени 
pthread_kill(t2, SIGRTMIN+1); 
pthread_kill(t2, SIGRTMIN+5); 
pthread_kill(t2, SIGCHLD); 
pthread_kill(t2, SIGRTMIN+1); 
pthread_kill(t2, SIGINT); 
pthread_kill(t2, SIGINT); 
 sigprocmask(SIG_UNBLOCK, &mask, NULL);

} 
}
 void* thread2() { 
int i, count = 0; 
int tid, pid; 
tid = syscall(SYS_gettid); 
pid = getpid(); 
printf("Thread_2 with thread id = %d and pid = %d is started\n", tid, pid); 
int m=10; 
for (i = 0; i < m; i++) { 
signal(SIGINT,hnd);
signal(SIGUSR1,hnd); 
signal(SIGUSR2,hnd); 
signal(SIGRTMIN+1,hnd); 
signal(SIGRTMIN+3,hnd); 
signal(SIGRTMIN+5,hnd); 
signal(SIGCHLD,hnd); 

count += 1; 
sleep(1); 
printf("Thread_2: step %d\n", count); 
} 
} 
void main() { 
pthread_create(&t1, NULL, thread1, NULL); 
pthread_create(&t2, NULL, thread2, NULL); 
pthread_join(t1, NULL); 
pthread_join(t2, NULL); 
} 

