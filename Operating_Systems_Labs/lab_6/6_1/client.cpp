#include <stdio.h>
#include "windows.h"
//Даю имя почтовому слоту
#define g_szMailslot "\\\\.\\mailslot\\SuperMailSlot"
#define BUFFER_SIZE 1024

int main(int argc, char* argv[])
{
HANDLE hMailslot;
//подсоединение к создавшему слот серверу
hMailslot = CreateFile(
g_szMailslot, //имя слота
GENERIC_WRITE, //слот только на запись (у сервера же - на чтение)
FILE_SHARE_READ,
NULL, //настройки безопасности дефолтные
OPEN_EXISTING, //открывать существующий почтовый слот
FILE_ATTRIBUTE_NORMAL,
NULL);
if (INVALID_HANDLE_VALUE == hMailslot){
printf("\nError occurred while connecting to the server: %d", GetLastError());
return 1; //Error
}else{
printf("\nCreateFile() was successful.");
}
//склиент только пишет
char szBuffer[BUFFER_SIZE];
DWORD cbBytes;
while (true) {
printf("\nEnter a message to be sent to the server: ");
gets(szBuffer);
//посылка сообщения серверу
BOOL bResult = WriteFile(
hMailslot,
szBuffer, //буфер, откуда пишем
strlen(szBuffer) + 1, //сколько байт писать (+NULL)
&cbBytes, //сколько байт написали
NULL);
//обработчик ошибок
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
