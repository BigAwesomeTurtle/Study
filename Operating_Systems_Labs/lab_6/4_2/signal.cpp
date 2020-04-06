#include <windows.h>
#include <stdio.h>
BOOL CtrlHandler(DWORD fdwCtrlType)
{
switch (fdwCtrlType) //��� �������
{
// Handle the CTRL-C signal.
case CTRL_C_EVENT:
printf( "Ctrl-C event\n\n" );
Beep( 750, 300 );
return( TRUE );
//CTRL-CLOSE: confirm that the user wants to exit.
case CTRL_CLOSE_EVENT:
Beep(600, 200);
printf("Ctrl-Close event\n\n");
return(TRUE);
// Pass other signals to the next handler.
case CTRL_BREAK_EVENT:
Beep(900, 200);
printf("Ctrl-Break event\n\n");
printf("press ctrl+break one more time to leave\n");
SetConsoleCtrlHandler( (PHANDLER_ROUTINE) CtrlHandler, FALSE );
return TRUE;
case CTRL_LOGOFF_EVENT:
Beep(1000, 200);
printf("Ctrl-Logoff event\n\n");
return FALSE;
case CTRL_SHUTDOWN_EVENT:
Beep(750, 500);
printf("Ctrl-Shutdown event\n\n");
return FALSE;
default:
return FALSE;
}
}
int main(int argc, char *argv[])
{
if (SetConsoleCtrlHandler((PHANDLER_ROUTINE)CtrlHandler, TRUE)){
printf("\nThe Control Handler is installed.\n");
printf("\n Press Ctrl+C or Ctrl+Break");
printf("\n       (...waiting...)\n\n");
while (1){}
}else{
printf("\nERROR: Could not set control handler");
return 1;
}
return 0;
}