#include "windows.h"
#include <stdio.h>
#include <conio.h>
#define g_szMailslot "\\\\.\\mailslot\\SuperMailSlot" // ��� ��������� �����
#define BUFFER_SIZE 1024

int main(int argc, char* argv[])
{
HANDLE hMailslot;
//������ �������� ����
hMailslot = CreateMailslot(
g_szMailslot, //��� �����
BUFFER_SIZE, //������ �������� ������
MAILSLOT_WAIT_FOREVER, //���������� ��������
NULL);
//��������� ������ �������� ��������� �����
if (INVALID_HANDLE_VALUE == hMailslot) {
printf("\nError occurred while creating the mailslot: %d", GetLastError());
_getch();
return 1; //Error
}
else
printf("\nCreateMailslot() was successful.");
//�������� ����� - ���������������� �������� �����, ��� ��� ������ ����� ������ ���������
char szBuffer[BUFFER_SIZE];
DWORD cbBytes;
BOOL bResult;
printf("\nWaiting for client connection...");
while (1){
//������ ���������� ���������
bResult = ReadFile(
hMailslot,
szBuffer,
sizeof(szBuffer),
&cbBytes,
NULL);
//��������� ������������� ������ ��� ������

if ((!bResult) || (0 == cbBytes)){
printf("\nError occurred while reading "" from the client: %d", GetLastError());
CloseHandle(hMailslot);
return 1; //Error
}else{
printf("\nReadFile() was successful.");
}
printf("\nClient sent the following message: %s", szBuffer);
}
CloseHandle(hMailslot);
return 0;
}
