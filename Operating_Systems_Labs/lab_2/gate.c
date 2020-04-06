#include <stdio.h>

main (int argc, char *argv []){
	if (argc>1) {
		FILE *file;
		file=fopen(argv[1],"r");
		if (file) {
			char str[64];
			while ( fgets(str, sizeof(str), file) ) {
				printf("%s", str);
			}
			fclose(file);
		}
		else printf("%s: Can't open file\n", argv[0], argv[1]);
	}
	else printf("%s: There is no filename\n", argv[0]);
}