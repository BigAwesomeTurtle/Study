#include <stdio.h>
#include "windows.h"
//��� ��� ��������� �����
#define g_szMailslot "\\\\.\\mailslot\\SuperMailSlot"
#define BUFFER_SIZE 1024

int main(int argc, char* argv[])
{
HANDLE hMailslot;
//������������� � ���������� ���� �������
hMailslot = CreateFile(
g_szMailslot, //��� �����
GENERIC_WRITE, //���� ������ �� ������ (� ������� �� - �� ������)
FILE_SHARE_READ,
NULL, //��������� ������������ ���������
OPEN_EXISTING, //��������� ������������ �������� ����
FILE_ATTRIBUTE_NORMAL,
NULL);
if (INVALID_HANDLE_VALUE == hMailslot){
printf("\nError occurred while connecting to the server: %d", GetLastError());
return 1; //Error
}else{
printf("\nCreateFile() was successful.");
}
//������� ������ �����
char szBuffer[BUFFER_SIZE];
DWORD cbBytes;
while (true) {
printf("\nEnter a message to be sent to the server: ");
gets(szBuffer);
//������� ��������� �������
BOOL bResult = WriteFile(
hMailslot,
szBuffer, //�����, ������ �����
strlen(szBuffer) + 1, //������� ���� ������ (+NULL)
&cbBytes, //������� ���� ��������
NULL);
//���������� ������
if ((!bResult) || (strlen(szBuffer) + 1 != cbBytes)){
printf("\nError occurred while writing"" to the server: %d", GetLastError());
CloseHandle(hMailslot);
return 1; //Error
}else{
printf("\nWriteFile() was successful.");
}
}
CloseHandle(hMailslot);
return 0; //Success
}
