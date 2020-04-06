#include <windows.h>
#include <stdio.h>
#define SIZE_OF_BUF 1000
	
DWORD WINAPI threadHandler(LPVOID param){
	HANDLE hNamedPipe = (HANDLE)param;
	DWORD readbytes, writebytes; //����� ���� ����������� � ����������
	char buf[SIZE_OF_BUF]; // ����� ��� �������� ������ ����� �����
	while (1)
	{
		// �������� ��������� ������� ����� ����� Pipe
		if (ReadFile(hNamedPipe, buf, SIZE_OF_BUF, &readbytes, NULL))
		{
			// �������� ��� ������� ������� �����������
			// ����������
			if (!WriteFile(hNamedPipe, buf, strlen(buf) + 1, &writebytes, NULL))
				break;
			// ������� �������� ������� �� �������
			printf("Get client msg: %s\n", buf);
			// ���� ������ ������� "exit",
			// ��������� ������ ����������
			if (!strncmp(buf, "exit", 4))
			break;
		}
		else
		{
			fprintf(stdout, "ReadFile: Error %ld\n", GetLastError());
			getchar();
			break;
		}
	}
	CloseHandle(hNamedPipe);
	ExitThread(0);
}
int main()
{
	HANDLE hNamedPipe; // ������������� ������ Pipe
	HANDLE t; //��� ������
	LPCSTR lpszPipeName = "\\\\.\\pipe\\$$MyPipe$$"; // ��� ������������ ������ Pipe
	printf("Server is started. Try to create named pipe\n");
	// ������� ����� Pipe, ������� ��� lpszPipeName
	while (1){
		hNamedPipe = CreateNamedPipe(
		lpszPipeName, //��� ������
		PIPE_ACCESS_DUPLEX, //������ � �� ������ � �� ������
		PIPE_TYPE_MESSAGE | PIPE_READMODE_MESSAGE | PIPE_WAIT, //�������� ��������� (��� ������,
			//��� � ������)
			5, //������������ ����� ����������� ������� ����� 5 (����� ��������)
			SIZE_OF_BUF, SIZE_OF_BUF, 5000, NULL); //������� ��������� � �������� ������� ������, 5
			//������ - ������������ ��� ������� WaitNamedPipe
		if (hNamedPipe == INVALID_HANDLE_VALUE) // ���� �������� ������, ������� �� ��� � ���������
		//������ ����������
		{
			fprintf(stdout, "CreateNamedPipe: Error %ld\n", GetLastError());
			getchar();
			return 1000;
		}
		printf("Named pipe created successfully\n");
		// ������� ��������� � ������ �������� �������� ������
		printf("waiting for connect\n");
		// ������� ���������� �� ������� �������
		if (!ConnectNamedPipe(hNamedPipe, NULL))
		{
			// ��� ������������� ������ ������� �� ���
			printf("error with function ConnectNamedPipe\n");
			getchar();
			CloseHandle(hNamedPipe);
			return 1001;
		}
	// ������� ��������� �� �������� �������� ������
	fprintf(stdout, "Client connected\n");
	t = CreateThread(NULL, 0, threadHandler, (LPVOID)hNamedPipe, 0, NULL);
	CloseHandle(t);
	}
	printf("server is ending\n press any key\n");
	getchar();
	return 0;
}
