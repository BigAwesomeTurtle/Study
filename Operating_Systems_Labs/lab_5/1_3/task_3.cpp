#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <windows.h>
#include <assert.h>
#define DEF_BUFLEN 100
int main(int argc, char* argv[]) {
printf("Program started\n");
if (argc < 2) {
printf("Input name of configuration file\n");
exit(999);
}
const char* frd = argv[1];
FILE *f = fopen(frd, "r"); // открываем конфигурационный файл на чтение
if(f==NULL) {
printf("error opening file %s\n",argv[1]);
exit(1000);
}
char commandLine[DEF_BUFLEN]; // буфер для читаемой строки
STARTUPINFO StartupInfo;
PROCESS_INFORMATION ProcessInformation;
while (!feof(f)) {
ZeroMemory(commandLine,DEF_BUFLEN);
fgets(commandLine, DEF_BUFLEN, f);
if(strlen(commandLine) <= 1) {
printf("skipping empty string\n");
continue;
}
commandLine[strlen(commandLine) - 1] = '\0';
ZeroMemory(&StartupInfo, sizeof(STARTUPINFO));
StartupInfo.cb = sizeof(STARTUPINFO);
printf("Try to create new process with command line '%s'\n", commandLine);
if( !CreateProcess(NULL, commandLine, NULL, NULL,false, 0,NULL, NULL, &StartupInfo, &ProcessInformation) ){
printf("Can't create new process.Error is: %d\nContinue with next line\n", GetLastError());
continue;
}
printf("new process Handle: %d Handle of thread: %d\n",
ProcessInformation.dwProcessId,ProcessInformation.dwThreadId);
CloseHandle(ProcessInformation.hThread);
CloseHandle(ProcessInformation.hProcess);
}
fclose(f);
printf("Program finished\n");
getchar();
return 0;
}