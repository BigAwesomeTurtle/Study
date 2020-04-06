#include <stdio.h>
main() {
char* name[50];
memset(name,'\0',50);
scanf("%s", name);
if (name[0] != '\0' ) {
if(fopen(name,"r")==NULL ) {
printf("failure to open file %s\n", name);
} else {
printf("file %s open\n", name);
}
} else {
printf("file not specified\n");
}
}
