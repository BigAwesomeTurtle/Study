#include <stdio.h>
#include <windows.h>

DWORD WINAPI threadHandler(LPVOID);
int main(int argc, char* argv[])
{
	printf("Program started\n");
	HANDLE t;
	int number = 1;
	t = CreateThread(NULL, 0, threadHandler,(LPVOID)number, 0, NULL);
	CloseHandle(t);
	number = 2;
	t= CreateThread(NULL, 0, threadHandler,(LPVOID)number, 0, NULL);
	CloseHandle(t);
	ExitThread(0); // разкомментировать для второго варианта
	printf("Program finished\n");
	system("pause");
	//return 0; // закомментировать для второго варианта
}

DWORD WINAPI threadHandler(LPVOID param){
	int number = (long) param;
	for(;;) {
		Sleep(1000);
		printf("%d",number);
		fflush(stdout);
	}
	return 0;

}