#include <stdio.h>
#include <windows.h>

DWORD WINAPI Thread1(LPVOID);
	int stop;
	int sleep = 10000;
	struct params {
    int num;
    bool* runflg;
	};

int getPriorityIndex(DWORD prClass);
char charProcPrio[6][10] = { "IDLE", "BELOW", "NORMAL", "ABOVE", "HIGH", "REAL-TIME" };
char charPrio[7][10] = { "IDLE", "LOWEST", "BELOW", "NORMAL", "ABOVE", "HIGHEST", "TIME_CRIT" };
int procPriorities[6] = { IDLE_PRIORITY_CLASS, BELOW_NORMAL_PRIORITY_CLASS, NORMAL_PRIORITY_CLASS,
ABOVE_NORMAL_PRIORITY_CLASS, HIGH_PRIORITY_CLASS, REALTIME_PRIORITY_CLASS };
long long counters[7] = { 0, 0, 0, 0, 0, 0, 0 }; //�������� ��� �������
int priority[7] = {THREAD_PRIORITY_IDLE,THREAD_PRIORITY_LOWEST,
THREAD_PRIORITY_BELOW_NORMAL,THREAD_PRIORITY_NORMAL,
THREAD_PRIORITY_ABOVE_NORMAL, THREAD_PRIORITY_HIGHEST,
THREAD_PRIORITY_TIME_CRITICAL}; //������ ����������� � ������� �����������

int main(int argc, char* argv[]) { //� ��������� ������ ���������� ������ ����� ����� �������
    SetPriorityClass(GetCurrentProcess(), REALTIME_PRIORITY_CLASS); //����� ������
    int priorIdx = getPriorityIndex(GetPriorityClass(GetCurrentProcess()));
    printf("Process priority:%s\n", charProcPrio[priorIdx]);
    stop = 10;
    DWORD targetThreadId;
    bool runFlag = true; //������������� �������� ������-�������
    __int64 end_time;
    LARGE_INTEGER end_time2;
    HANDLE tm1 = CreateWaitableTimer(NULL, false, NULL); //�������� �������
    end_time = -1 * stop * 10000000;
    end_time2.LowPart = (DWORD) (end_time & 0xFFFFFFFF);
    end_time2.HighPart = (LONG) (end_time >> 32);
    SetWaitableTimer(tm1, & end_time2, 0,NULL, NULL, false);//������ �������
    for (int i = 0; i < 7; i++) {
        params* param = (params*)malloc(sizeof(params));
        param->num = i;
        param->runflg = & runFlag;
        HANDLE t1 = CreateThread(NULL, 0, Thread1, param, 0, &targetThreadId); //���������� ������
        SetThreadPriority(t1, priority[i]); //������� ��� ����������
        PBOOL ptr1 = (PBOOL)malloc(sizeof(BOOL));
        GetThreadPriorityBoost(t1, ptr1);
        SetThreadPriorityBoost(t1, true); //�������� ������������� ������������� �����������
        CloseHandle(t1); //������� ������
    }
    WaitForSingleObject(tm1,INFINITE); //�������� ������ �������
    runFlag = false; //���� ���������� ������
    CloseHandle(tm1);
    printf("\n");
    for (int i = 0; i < 7; i++) {
       printf("%d - %ld\n",i, counters[i]); //����� �����������
    }
    system("pause");
    return 0;
}

DWORD WINAPI Thread1(LPVOID prm) {
    while (1) {
        DWORD WINAPI thrdid = GetCurrentThreadId(); //������������� ����������� ������
        HANDLE WINAPI handle = OpenThread(THREAD_QUERY_INFORMATION , false, thrdid); //���������� ������
        int WINAPI prio = GetThreadPriority(handle); //��������� ��� ������������� ������
        params arg = *((params*)prm);
        counters[arg.num]++;
        if(prio != priority[arg.num])
            printf("\nPriority of %d is %d %d changed\n", arg.num, priority[arg.num], prio); //��� ������������ ������������� �����������
        Sleep(0);
        if(*(arg.runflg) == false){
            printf("%s, %d\n",charPrio[arg.num],prio);
            break;
        }
    }
    return 1;
}

int getPriorityIndex(DWORD prClass) {
    for (int i = 0; i < 6; ++i) {
        if (procPriorities[i] == prClass)
            return i;
    }
    return 0;
}
