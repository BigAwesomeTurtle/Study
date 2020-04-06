#include <iostream>
#include <conio.h>
#include <stdio.h>
#include <windows.h>
using namespace std;
int main()
{
char buf[2];
unsigned long avail;
cout << "STD INPUT HANDLE = " <<
GetStdHandle(STD_INPUT_HANDLE) << "\n";
cout << "STD OUTPUT HANDLE = "<<
GetStdHandle(STD_OUTPUT_HANDLE) << "\n";
unsigned long bread;
while(1){
PeekNamedPipe(
GetStdHandle(STD_INPUT_HANDLE),
NULL,
NULL,
NULL,
&avail,
NULL);
if(avail)
{ memset(buf, '\0', sizeof(buf));
ReadFile(
GetStdHandle(STD_INPUT_HANDLE), buf, 1, &bread, NULL);
cout << buf;
}
}
return 0;
}