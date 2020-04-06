#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <sys/resource.h>

void main(int arc, char* argv[]){
  
   if(fork() == 0){
        printf("Son done: PID %d, PPID %d\n",getpid(),getppid());
   }
   else{
        printf("Father done: PID %d, PPID %d\n",getpid(),getppid());
   }
   printf("Programm done\n");
   exit(0);
}