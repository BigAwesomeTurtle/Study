#include <windows.h>
#include <stdio.h>
#include <conio.h>
#include <string.h>
#include <iostream>
using namespace std;
int main()
{ // �������������� ����������� ���������
STARTUPINFO si = {sizeof(si)};
SECURITY_ATTRIBUTES sa;
PROCESS_INFORMATION pi;
char buf[1024];
char t= '\n';
HANDLE newstdread, newstdwrite;//������ ������� ��� �����
//�������������� ������ ���� SECURITY_ATTRIBUTES
sa.nLength = sizeof(sa);
sa.lpSecurityDescriptor = NULL;
sa.bInheritHandle = true; //��������� ������������ ������������
//������� ��������� �����(������� ���� ��� stdin)
if (!CreatePipe(&newstdread, //��������� �� ��p������� ���� dword,
//����p�� ������� ����� ����� ������ �����
&newstdwrite, //��������� �� ��p������� ���� dword, ����p�� ������� ����� �� ����� ������ �����
&sa, // ��������� �� ��������� ��������� ������������
0)) //������ ������, ������������ �� ���������
{
cout << "I can't CreatePipe";
getch();
return 0;
}
else
cout << "\nPipe Created!\n";
//������� �� ����� ���������� ������ ����� ���������� ������
cout << "The read HANDLE of PIPE = " << newstdread << endl;
//�������� ���� STARTUPINFO � ������ ������ ��������
ZeroMemory(&si, sizeof(STARTUPINFO));
si.cb = sizeof(STARTUPINFO);
si.dwFlags = STARTF_USESTDHANDLES | STARTF_USESHOWWINDOW;
si.wShowWindow = SW_NORMAL;
//��������� ����������� ���������� ����� ������������ ����� ������
si.hStdInput = newstdread;
si.hStdOutput = GetStdHandle(STD_OUTPUT_HANDLE);
si.hStdError = si.hStdOutput;
TCHAR czCommandLine[] = "C:\\lab_6\\1_1\\slave1.exe";;
if (!CreateProcess(NULL, czCommandLine, NULL, NULL, TRUE,
CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi))
{
cout << "Error: �an't CreateProcess";
getch();
CloseHandle(newstdread);
CloseHandle(newstdwrite);
return 0;
}
else
cout << "\nProcess Created!!!\n";
memset(buf, '\0', sizeof(buf));
cout << buf;
unsigned long bread;
cout << "STD INPUT HANDLE = " <<
GetStdHandle(STD_INPUT_HANDLE) << endl;
cout << "STD OUTPUT HANDLE = " <<
GetStdHandle(STD_OUTPUT_HANDLE) << endl;
while(1)
{ memset(buf, '\0', sizeof(buf));
*buf = (char)getch();
cout.put(*buf);
if(*buf==13)
{
*buf = '\n';
cout.put(*buf);
}
WriteFile(newstdwrite, //��������� �� ������� ����� ������
buf, // ��������� �� �����
1, //���-�� ���� ������,������������ � �����
&bread, // ��������� �� ����������, �������� ���-�� ����,
//���������� � �����
NULL); //�.�. 1-� �������� �� ��� ������ � ������
FILE_FLAG_OVERLAPPED;
if(*buf==27)
break;
}
TerminateProcess(pi.hProcess,0); // ���������� ��������
CloseHandle(pi.hThread);
CloseHandle(pi.hProcess);
CloseHandle(newstdread);
CloseHandle(newstdwrite);
system("PAUSE");
return 0;}
