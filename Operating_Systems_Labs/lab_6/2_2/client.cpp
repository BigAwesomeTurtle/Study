#include <windows.h>
#include <stdio.h>
#define SIZE_OF_BUF 1000

int main(int argc, char *argv[]) {
	HANDLE hNamedPipe; // ������������� ������ Pipe
	DWORD readbytes,writebytes; // ���������� ���� �������� � ���������� � �����
	char buf[SIZE_OF_BUF]; // ����� ��� �������� ������
	printf("Client is started\n Try to use WaitNamedPipe\n"); //"�������" ���� ����������� �����
	LPCSTR szPipeName = "\\\\.\\pipe\\$$MyPipe$$";
	if (!WaitNamedPipe(szPipeName, NMPWAIT_WAIT_FOREVER)) {
		printf("pipe wasn't created\n getlasterror = %d", GetLastError());
		getchar();
		return 1000;
	} // ������� ����� � ���������-��������
	hNamedPipe = CreateFile(szPipeName, GENERIC_READ | GENERIC_WRITE, 0, NULL, OPEN_EXISTING, 0, NULL); // ���� �������� ������, ������� �� ��� �
	// ��������� ������ ����������
	if(hNamedPipe == INVALID_HANDLE_VALUE) {
		fprintf(stdout,"CreateFile: Error %ld\n",GetLastError());
		getchar();
		return 1001;
	} // ������� ��������� � �������� ������
	printf("successfully connected\n input message\n"); // ���� ������ ������� � ��������� ���������
	while(1) { // ������� ����������� ��� ����� ������� printf("cmd>"); // ������ ��������� ������
		fgets(buf,SIZE_OF_BUF,stdin); // �������� ��������� ������ ���������� �������� // � �������� �������
		if(!WriteFile(hNamedPipe, buf, strlen(buf) + 1, &writebytes, NULL)) {
			printf("connection refused\n");
			break;
		} // �������� ��� �� ������� ������� �� �������
		if(ReadFile(hNamedPipe, buf, SIZE_OF_BUF, &readbytes, NULL))
			printf("Received from server: %s\n", buf); // ���� ��������� ������, ������� �� ��� � // ��������� ������ ����������
		else {
			fprintf(stdout,"ReadFile: Error %ld\n", GetLastError());
			getchar();
			break;
		} // � ����� �� ������� "exit" ��������� ���� // ������ ������� � ��������� ���������
		if(!strncmp(buf, "exit",4))
			break;
	} // ��������� ������������� ������
	//CloseHandle(hNamedPipe);
	printf("client is ending\n Press any key\n");
	getchar();
	return 0;
}
