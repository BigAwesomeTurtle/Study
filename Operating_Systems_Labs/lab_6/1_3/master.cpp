#include <windows.h>
#include <stdio.h>
#include <conio.h>

int main()
{
	// ���� ��������� �������� ������
	BOOL fConnected;
	// ������������� ������ Pipe
	HANDLE hNamedPipe;
	// ��� ������������ ������ Pipe
	LPCSTR lpszPipeName = "\\\\.\\pipe\\$MyPipe$";
	// ����� ��� �������� ������ ����� �����
	char szBuf[512];
	// ���������� ���� ������, �������� ����� �����
	DWORD cbRead;
	// ���������� ���� ������, ���������� ����� �����
	DWORD cbWritten;
	printf("Named pipe was creating \n");
	// ������� ����� Pipe, ������� ��� lpszPipeName
	hNamedPipe = CreateNamedPipe(lpszPipeName, //��� ������
	PIPE_ACCESS_DUPLEX, // ����� �������� ������ - ���������������
	//��������� ������ pipe:
	PIPE_TYPE_MESSAGE //������ ������������ � ����� � ���� ������ ���������
	| PIPE_READMODE_MESSAGE //������ ����������� � ���� ������ ���������
	| PIPE_WAIT,// ����������� �����
	PIPE_UNLIMITED_INSTANCES, //�������������� ���-�� "�����������" � ������
	512, 512, //���-�� ���� �������� � ��������� �������
	0, //����-��� � 50 ����������� (�� ���������)
	NULL ); // ���������� ������������ �� ���������
	// ���� �������� ������, ������� �� ��� �
	// ��������� ������ ����������
	if(hNamedPipe == INVALID_HANDLE_VALUE)
	{
		fprintf(stdout,"CreateNamedPipe: Error %ld\n",
		GetLastError());
		getch();
		return 0;
	}
	// ������� ��������� � ������ �������� �������� ������
	fprintf(stdout,"Waiting for connect...\n");
	// ������� ���������� �� ������� �������
	fConnected = ConnectNamedPipe(hNamedPipe, // ��� ������
		NULL); // overlapped=null
	// ��� ������������� ������ ������� �� ���
	if(!fConnected)
	{
		switch(GetLastError())
		{
		case ERROR_NO_DATA:
			fprintf(stdout,"ConnectNamedPipe: ERROR_NO_DATA");
			getch();
			CloseHandle(hNamedPipe);
			return 0;
			break;
		case ERROR_PIPE_CONNECTED:
			fprintf(stdout,"ConnectNamedPipe: ERROR_PIPE_CONNECTED");
			getch();
			CloseHandle(hNamedPipe);
			return 0;
			break;
		case ERROR_PIPE_LISTENING:
			fprintf(stdout,"ConnectNamedPipe: ERROR_PIPE_LISTENING");
			getch();
			CloseHandle(hNamedPipe);
			return 0;
			break;
		case ERROR_CALL_NOT_IMPLEMENTED:
			fprintf(stdout,"ConnectNamedPipe: ERROR_CALL_NOT_IMPLEMENTED");
			getch();
			CloseHandle(hNamedPipe);
			return 0;
			break;
		default:
			fprintf(stdout,"ConnectNamedPipe: Error %ld\n",
			GetLastError());
			getch();
			CloseHandle(hNamedPipe);
			return 0;
			break;
		}
	CloseHandle(hNamedPipe);
	getch();
	return 0;
	} // ������� ��������� �� �������� �������� ������
	fprintf(stdout,"\nConnected. Waiting for command...\n");
	// ���� ��������� ������ �� ������
	while(1)
	{ 
		if(ReadFile(hNamedPipe, szBuf, 512, &cbRead, NULL))
		{
			// ������� �������� ������� �� �������
			printf("Received: <%s>\n", szBuf);
			// ���� ������ ������� "exit",
			// ��������� ������ ����������
			if(!strcmp(szBuf, "exit"))
				break;
			// ���������� ���-�����
		if(!WriteFile(hNamedPipe, szBuf, strlen(szBuf) + 1, &cbWritten, NULL)) break;
		}
		else
			{getch();
			break; }
	}
	CloseHandle(hNamedPipe);
return 0;}