#include <stdio.h>
#include <sched.h>
#include <sys/mman.h>
int main(void){
	struct timespec qp;
	struct sched_param shdprm;
	shdprm.sched_priority = 50;
	if (sched_setscheduler (0, SCHED_FIFO, &shdprm) == -1)
		perror ("SCHED_SETSCHEDULER_1");
	if (sched_rr_get_interval (0, &qp) == 0)
		printf ("Квант при циклическом планировании: %g сек\n",qp.tv_sec + qp.tv_nsec / 1000000000.0);
	else
		perror ("SCHED_RR_GET_INTERVAL");
	return 0;
}