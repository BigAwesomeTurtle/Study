#include <windows.h>
#include <stdio.h>
#include <string.h>
int main(int argc, char** argv[])
{
printf("Program started\n");
char LpCommandLine[60];
strncpy(LpCommandLine,"C:\\WINDOWS\\system32\\calc.exe",60);
STARTUPINFO startupInfo;
PROCESS_INFORMATION processInfo; //информация о процессе
ZeroMemory(&startupInfo, sizeof(STARTUPINFO));
startupInfo.cb = sizeof(startupInfo);
if(!CreateProcess(NULL, LpCommandLine, NULL, NULL, false, HIGH_PRIORITY_CLASS | CREATE_NEW_CONSOLE, NULL,NULL,&startupInfo, &processInfo))
{
printf("Error creating process: %d\n", GetLastError());
return -1;
}
else
{
printf("new process Handle: %d Handle of thread:%d\n",processInfo.dwProcessId,processInfo.dwThreadId);
printf("Successfully created new process!\n");
}
CloseHandle(processInfo.hThread);
CloseHandle(processInfo.hProcess);
printf("Program finished\n");
getchar();
return 0;
}