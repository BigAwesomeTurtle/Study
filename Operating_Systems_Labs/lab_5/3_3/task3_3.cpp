#include <stdio.h>
#include <string>
#include <windows.h>

void thread() {
	while(true) {
		Sleep(2000);
	}
}
int main(int argc,char *argv[]) {
	BOOL dynamic;
	HANDLE processHandle, mainThread, secondThread;
	DWORD secondID;
	processHandle = GetCurrentProcess();
	mainThread = GetCurrentThread();
	//create a second thread
	secondThread = CreateThread(NULL,0,(LPTHREAD_START_ROUTINE) thread,NULL,NULL,&secondID);
	//print default values
	printf("0 - dynamic enable, 1-disabled.\n");
	GetProcessPriorityBoost(processHandle,&dynamic);
	printf("Process dynamically default is %d \n",dynamic);
	GetThreadPriorityBoost(mainThread,&dynamic);
	printf("Main thread dynamic default is %d \n" ,dynamic);
	GetThreadPriorityBoost(secondThread,&dynamic);
	printf("Second thread dynamic default is %d\n",dynamic);
	printf("Change Second thread priority\n");
	if(!SetThreadPriorityBoost(secondThread,true)){ 
	//dizable
		printf("Error on thread change!\n");
		return 2;
	}
	GetProcessPriorityBoost(processHandle,&dynamic);
	printf("Process dynamically default is %d\n" ,dynamic);
	GetThreadPriorityBoost(mainThread,&dynamic);
	printf("Main thread dynamic default is %d\n" ,dynamic);
	GetThreadPriorityBoost(secondThread,&dynamic);
	printf("Second thread dynamic default is %d\n\n" ,dynamic);
	//change process dinamically
	if(!SetProcessPriorityBoost(processHandle,true)) {
		printf("Error on prir change!\n");
		return 1;
	}
	printf("After process dynamic drop:\n");
	GetProcessPriorityBoost(processHandle,&dynamic);
	printf("Process is %d\n" , dynamic);
	GetThreadPriorityBoost(mainThread,&dynamic);
	printf("Main thread is %d\n" , dynamic);
	GetThreadPriorityBoost(secondThread,&dynamic);
	printf("Second thread is %d\n\n" , dynamic);
	//may be can change thread dynamic prio?
	if(!SetThreadPriorityBoost(secondThread,false)){ //dizable
		printf("We cannot change if process ban dynamic prio!!!\n");
		return 3;
	} else {
		printf("Thread dynamic prio changed, but process bans it!!!\n");
		GetProcessPriorityBoost(processHandle,&dynamic);
		printf("Process is %d\n" , dynamic);
		GetThreadPriorityBoost(mainThread,&dynamic);
		printf("Main thread is %d\n" , dynamic);
		GetThreadPriorityBoost(secondThread,&dynamic);
		printf("Second thread is %d\n" , dynamic);
	}
	system("pause");
	return 0;
}
