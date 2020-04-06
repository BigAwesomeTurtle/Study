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
long long counters[7] = { 0, 0, 0, 0, 0, 0, 0 }; //счетчики для потоков
int priority[7] = {THREAD_PRIORITY_IDLE,THREAD_PRIORITY_LOWEST,
THREAD_PRIORITY_BELOW_NORMAL,THREAD_PRIORITY_NORMAL,
THREAD_PRIORITY_ABOVE_NORMAL, THREAD_PRIORITY_HIGHEST,
THREAD_PRIORITY_TIME_CRITICAL}; //массив приоритетов в порядке возрастания

int main(int argc, char* argv[]) { //в командной строке аргументом задаем время жизни потоков
    SetPriorityClass(GetCurrentProcess(), REALTIME_PRIORITY_CLASS); //смена класса
    int priorIdx = getPriorityIndex(GetPriorityClass(GetCurrentProcess()));
    printf("Process priority:%s\n", charProcPrio[priorIdx]);
    stop = 10;
    DWORD targetThreadId;
    bool runFlag = true; //инициализация структур потока-таймера
    __int64 end_time;
    LARGE_INTEGER end_time2;
    HANDLE tm1 = CreateWaitableTimer(NULL, false, NULL); //создание таймера
    end_time = -1 * stop * 10000000;
    end_time2.LowPart = (DWORD) (end_time & 0xFFFFFFFF);
    end_time2.HighPart = (LONG) (end_time >> 32);
    SetWaitableTimer(tm1, & end_time2, 0,NULL, NULL, false);//запуск таймера
    for (int i = 0; i < 7; i++) {
        params* param = (params*)malloc(sizeof(params));
        param->num = i;
        param->runflg = & runFlag;
        HANDLE t1 = CreateThread(NULL, 0, Thread1, param, 0, &targetThreadId); //порождение потока
        SetThreadPriority(t1, priority[i]); //задание ему приоритета
        PBOOL ptr1 = (PBOOL)malloc(sizeof(BOOL));
        GetThreadPriorityBoost(t1, ptr1);
        SetThreadPriorityBoost(t1, true); //проверка динамического распределения приоритетов
        CloseHandle(t1); //очистка памяти
    }
    WaitForSingleObject(tm1,INFINITE); //ожидание потока таймера
    runFlag = false; //флаг завершения работы
    CloseHandle(tm1);
    printf("\n");
    for (int i = 0; i < 7; i++) {
       printf("%d - %ld\n",i, counters[i]); //вывод результатов
    }
    system("pause");
    return 0;
}

DWORD WINAPI Thread1(LPVOID prm) {
    while (1) {
        DWORD WINAPI thrdid = GetCurrentThreadId(); //идентификатор вызывающего потока
        HANDLE WINAPI handle = OpenThread(THREAD_QUERY_INFORMATION , false, thrdid); //дескриптор потока
        int WINAPI prio = GetThreadPriority(handle); //приоритет для определяемого потока
        params arg = *((params*)prm);
        counters[arg.num]++;
        if(prio != priority[arg.num])
            printf("\nPriority of %d is %d %d changed\n", arg.num, priority[arg.num], prio); //при динамическом распределении приоритетов
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
