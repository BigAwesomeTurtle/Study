#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <windows.h>
#include <assert.h>
#include <string.h>
#define MAX_LEN 250
int main(int argc, char* argv[])
{
const char* frd = "C:\\lab_5\\1_2\\tmp.txt";
FILE *f=fopen(frd,"r");
if(f==NULL) {
printf("Coudn't open file\n");
system("pause");
return 1;
}
for(int i=0; i<2; i++)
{
char* execString = (char*)calloc(MAX_LEN, sizeof(char));
//выделение памяти
fgets(execString, MAX_LEN, f); // чтение строки из файла
execString[strlen(execString) - 1] = '\0';
STARTUPINFO startupInfo;
ZeroMemory(&startupInfo, sizeof(STARTUPINFO));
startupInfo.cb = sizeof(startupInfo);
PROCESS_INFORMATION processInfo;
printf("%s\n", execString);
if( !CreateProcess(NULL, execString, NULL, NULL, false, 0, NULL, NULL, &startupInfo, &processInfo) )
{
printf("Error creating process: %d\n", GetLastError());
system("pause");
return -1;
}
else
printf("Process successfully created!\n");
free(execString);
CloseHandle(processInfo.hThread);
CloseHandle(processInfo.hProcess);
}
system("pause");
return 0;
}