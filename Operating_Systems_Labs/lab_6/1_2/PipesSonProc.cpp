#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <Windows.h>
int main(int argc, char* argv[]) {
char strtosend[100]; //������ ��� ��������
char getbuf[100]; //����� ������
int bytestosend; //����� ������������ ����
DWORD bytessended,bytesreaded; //����� ���������� � �������� ����
for(int i=0;i<10;i++) {
bytestosend=sprintf(strtosend,"message num %d",i+1);
//������������ ������ ���
//��������
strtosend[bytestosend]=0;
fprintf(stderr,"client sended: \"%s\"\n",strtosend);
if(!WriteFile(GetStdHandle(STD_OUTPUT_HANDLE),strtosend,bytestosend+1,&bytesreaded,NULL)) {
fprintf(stderr,"Error with writeFile\n Wait 5 sec GetLastError= %d\n",GetLastError());
Sleep(5000);
return 1000;
}
if (!ReadFile(GetStdHandle(STD_INPUT_HANDLE), getbuf, 100, &bytesreaded, NULL)) {
fprintf(stderr,"Error with readFile\n Wait 5 sec GetLastError= %d\n",GetLastError());
Sleep(5000);
return 1001;
}
fprintf(stderr,"Get msg from server: \"%s\"\n",getbuf);
}
fprintf(stderr,"client ended work\n Wait 5 sec");
Sleep(5000);
return 0;
}