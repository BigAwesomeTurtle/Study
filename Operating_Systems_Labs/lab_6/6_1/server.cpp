#include "windows.h"
#include <stdio.h>
#include <conio.h>
#define g_szMailslot "\\\\.\\mailslot\\SuperMailSlot" // имя почтового слота
#define BUFFER_SIZE 1024

int main(int argc, char* argv[])
{
HANDLE hMailslot;
//создаю почтовый слот
hMailslot = CreateMailslot(
g_szMailslot, //имя слота
BUFFER_SIZE, //размер входного буфера
MAILSLOT_WAIT_FOREVER, //отсутствие таймаута
NULL);
//обработка ошибки создания почтового слота
if (INVALID_HANDLE_VALUE == hMailslot) {
printf("\nError occurred while creating the mailslot: %d", GetLastError());
_getch();
return 1; //Error
}
else
printf("\nCreateMailslot() was successful.");
//почтовые слоты - однонапревленное средство связи, так что сервер будет только считывать
char szBuffer[BUFFER_SIZE];
DWORD cbBytes;
BOOL bResult;
printf("\nWaiting for client connection...");
while (1){
//читаем клиентское сообщение
bResult = ReadFile(
hMailslot,
szBuffer,
sizeof(szBuffer),
&cbBytes,
NULL);
//обработка возникновения ошибки при чтении

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
