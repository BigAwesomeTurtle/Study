#include <stdio.h>
#include <stdlib.h>
#include "rb_tree.h"

int main(int argc, char *argv[]) {
	if (argc != 3) {
		printf("”кажите входной и выходной файлы");
		return 0;
	}
	FILE *fclear;
	fclear = fopen(argv[2], "w");
	fclose(fclear);
	struct rb_tree *tree = read_from_file(argv[1]);
	print_tree_to_file(tree->root, argv[2],"a","Input file:\n");
	rb_tree_insert(tree, 30);
	print_tree_to_file(tree->root, argv[2], "a", "Adding 30:\n");
	rb_tree_remove(tree,6);
	print_tree_to_file(tree->root, argv[2], "a","Removing 6:\n");
	FILE *fout;
	fout = fopen(argv[2], "a");
	fprintf(fout,"Finding 45:");
	if (rb_tree_find(tree, 45)) {
		fprintf(fout, "Found!\n");
	}
	else {
		fprintf(fout, "No...!\n");
	}
	fprintf(fout, "Finding 121:");
	if (rb_tree_find(tree, 121)) {
		fprintf(fout, "Found!\n");
	}
	else {
		fprintf(fout, "No...\n");
	}
	
}