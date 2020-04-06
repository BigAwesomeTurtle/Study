#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/signal.h>

int rec_sig[20]; // массив сигналов
unsigned int nfree_elem = 0; // номер элемента

void sighandler(int signo) { // обработчик
	rec_sig[nfree_elem++] = signo; // сохраняем номер принятого сигнала
	return;
}

int main(int argc, char * argv[]) {
	sigset_t mask;
	struct sigaction act;
	int i;
	memset(&act, 0, sizeof(act));
	sigemptyset(&mask); // иниц. набора сигналов пустми значениями
	//добавляем в маску сигналы
	sigaddset(&mask, SIGRTMIN);
	sigaddset(&mask, SIGRTMIN + 1);
	sigaddset(&mask, SIGRTMIN + 2);
	sigaddset(&mask, SIGUSR1);
	sigaddset(&mask, SIGUSR1);
	sigaddset(&mask, SIGUSR2);
	sigaddset(&mask, SIGCHLD);
	//устанавливаем обработчик для сигнала
	act.sa_handler = sighandler;
	act.sa_mask = mask;
	sigaction(SIGRTMIN, &act, NULL);
	sigaction(SIGRTMIN + 1, &act, NULL);
	sigaction(SIGRTMIN + 2, &act, NULL);
	sigaction(SIGUSR1, &act, NULL);
	sigaction(SIGUSR2, &act, NULL);
	sigaction(SIGCHLD, &act, NULL);
	//блокируем обработчик сигналов
	sigprocmask(SIG_BLOCK, &mask, NULL);
	//посылаем сигналы самому себе в случайном порядке
	raise(SIGUSR2);
	raise(SIGUSR2);
	raise(SIGRTMIN + 2);
	raise(SIGRTMIN + 1);
	raise(SIGUSR1);
	raise(SIGCHLD);
	raise(SIGUSR1);
	raise(SIGRTMIN);
	raise(SIGRTMIN + 1);
	raise(SIGRTMIN);
	raise(SIGUSR1);
	raise(SIGCHLD);
	raise(SIGUSR1);
	raise(SIGRTMIN + 1);
	raise(SIGRTMIN + 2);
	raise(SIGCHLD);
	raise(SIGRTMIN);
	raise(SIGRTMIN);
	raise(SIGCHLD);
	raise(SIGUSR1);
	//разблокируем сигналы, что вызовет их немедленную отправку
	sigprocmask(SIG_UNBLOCK, &mask, NULL);
	//выводим сигналы в порядке их получения
	for(i=0;i<nfree_elem;++i){
		if (rec_sig[i] == SIGUSR1) {
			printf("We get SIGUSR1\n");
		} else if (rec_sig[i] == SIGCHLD) {
			printf("We get SIGCHLD\n");
		} else if (rec_sig[i] == SIGRTMIN) {
			printf("We get SIGRTMIN\n");
		} else if (rec_sig[i] == SIGRTMIN + 1) {
			printf("We get SIGRTMIN + 1\n");
		} else if (rec_sig[i] == SIGRTMIN + 2) {
			printf("We get SIGRTMIN + 2\n");
		} else if (rec_sig[i] == SIGUSR2) {
			printf("We get SIGUSR2\n");
		}
	}
	return 0;
}