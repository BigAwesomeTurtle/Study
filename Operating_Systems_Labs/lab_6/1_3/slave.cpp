#include <windows.h>
#include <stdio.h>
#include <conio.h>

int main(int argc, char *argv[])
{ 
	// ������������� ������ Pipe
	// ������������� ������ Pipe
	HANDLE hNamedPipe;
	// ���������� ����, ���������� ����� �����
	DWORD cbWritten;
	// ���������� ����, �������� ����� �����
	DWORD cbRead;
	// ����� ��� �������� ������
	char szBuf[256];
	// ����� ��� ����� ������ Pipe
	LPCSTR szPipeName="\\\\.\\pipe\\$MyPipe$";
	printf("Named pipe client demo\n");
	printf("Syntax: pipec [servername]\n");
	// ��������� handle ������ ����������� pipe
	hNamedPipe = CreateFile(
		szPipeName, // pipe name
		GENERIC_READ | // read and write access
		GENERIC_WRITE,
		0, // no sharing
		NULL, // default security attributes
		OPEN_EXISTING, // opens existing pipe
		0, // default attributes
		NULL); // no template file
	// ���� �������� ������, ������� �� ��� �
	// ��������� ������ ����������
	if(hNamedPipe == INVALID_HANDLE_VALUE)
	{
		fprintf(stdout,"CreateFile: Error %ld\n",
		GetLastError());
		getch();
		return 0;
	}
	// ������� ��������� � �������� ������
	fprintf(stdout,"\nConnected. Type 'exit' to terminate\n");
	// ���� ������ ������� � ��������� ���������
	while(1)
	{ // ������� ����������� ��� ����� �������
		printf("cmd>");
		// ������ ��������� ������
		gets(szBuf);
		// �������� ��������� ������ ���������� ��������
		// � �������� �������
		if(!WriteFile(hNamedPipe,szBuf,strlen(szBuf)+1,&cbWritten, NULL)) break;
		// �������� ��� �� ������� ������� �� �������
		if(ReadFile(hNamedPipe, szBuf, 512, &cbRead, NULL))
			printf("Received back: <%s>\n", szBuf);
		// � ����� �� ������� "exit" ��������� ����
		// ������ ������� � ��������� ���������
		if(!strcmp(szBuf, "exit"))
			break;
		}
	// ��������� ������������� ������
	CloseHandle(hNamedPipe);
	return 0;
}