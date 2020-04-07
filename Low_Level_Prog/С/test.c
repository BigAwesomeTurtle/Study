#include <stdio.h>
#include <stdlib.h>
#include "rb_tree.h"



void assertEquals(char *actual, char *expected) {

	FILE *file1, *file2;
	char c1, c2;
	file1 = fopen(actual, "r");
	file2 = fopen(expected, "r");
	
	if (file1 == NULL || file2 == NULL) {
		printf("Cannot open file");
	}
	else {
		c1 = (char)fgetc(file1);
		c2 = (char)fgetc(file2);
		while (c1 != EOF && c2 != EOF) {

			if (c1 != c2) {
				printf("Test failed\n");
			}

			c1 = (char)fgetc(file1);
			c2 = (char)fgetc(file2);

		}

	}

	if (c1 == EOF && c2 != EOF && c2 != '\n') {

		printf("Test failed 1\n");

	}

	if (c2 == EOF && c1 != EOF && c1 != '\n') {

		printf("Test failed 2\n");
	}

	printf("Test passed\n");
	fclose(file1);
	fclose(file2);

}



void printTreeTest(struct rb_tree *self) {
	print_tree_to_file(self->root,"test_print_fact.txt","w","");
	printf("Print Test: ");
	assertEquals("test_print_fact.txt", "test_print_exp.txt");
}



void insertTest_1(struct rb_tree *self) {
	rb_tree_insert(self,5);
	print_tree_to_file(self->root, "test_insert_fact_1.txt", "w","");
	printf("Insert Test 1: ");
	assertEquals("test_insert_fact_1.txt", "test_insert_exp_1.txt");
}

void insertTest_2(struct rb_tree *self) {
	struct rb_node *nodeTest = rb_node_init(4);
	nodeTest->red = 1;
	rb_tree_insert_node(self, nodeTest);
	print_tree_to_file(self->root, "test_insert_fact_2.txt", "w", "");
	printf("Insert Test 2: ");
	assertEquals("test_insert_fact_2.txt", "test_insert_exp_2.txt");
}

void findTest_1(struct rb_tree *self) {
	printf("Find Test 1: ");
	if (rb_tree_find(self, 5)) {
		printf("Test passed\n");
	}
	else
	{
		printf("Test failed\n");
	}
}

void findTest_2(struct rb_tree *self) {
	printf("Find Test 2: ");
	if (rb_tree_find(self, 22)) {
		printf("Test failed\n");
	}
	else
	{
		printf("Test passed\n");
	}
}

void removeTest_1(struct rb_tree *self) {
	rb_tree_remove(self,0);
	print_tree_to_file(self->root, "test_remove_fact_1.txt", "w", "");
	printf("Remove Test 1: ");
	assertEquals("test_remove_fact_1.txt", "test_remove_exp_1.txt");

}

void removeTest_2(struct rb_tree *self) {
	rb_tree_remove(self, 4);
	print_tree_to_file(self->root, "test_remove_fact_2.txt", "w","");
	printf("Remove Test 2: ");
	assertEquals("test_remove_fact_2.txt", "test_remove_exp_2.txt");

}


void sizeTest(struct rb_tree *self) {
	printf("Size Test: ");
	if (rb_tree_size(self)==6) {
		printf("Test passed\n");
	}
	else
	{
		printf("Test failed\n");
	}
}

int main(int argc, char *argv[]) {

	struct rb_tree *tree = rb_tree_init(NULL);
	rb_tree_insert(tree, 10);
	rb_tree_insert(tree, 6);
	rb_tree_insert(tree, 45);
	rb_tree_insert(tree, 4);
	rb_tree_insert(tree, 8);
	rb_tree_insert(tree, 0);
	printTreeTest(tree);
	insertTest_1(tree);
	removeTest_1(tree);
	findTest_1(tree);
	findTest_2(tree);
	sizeTest(tree);
	rb_tree_dealloc(tree);

	struct rb_tree *tree2 = rb_tree_init(NULL);
	struct rb_node *node1 = rb_node_init(10);
	node1->red = 0;
	struct rb_node *node2 = rb_node_init(6);
	node2->red = 1;
	struct rb_node *node3 = rb_node_init(45);
	node3->red = 0;
	rb_tree_insert_node(tree2, node1);
	rb_tree_insert_node(tree2, node2);
	rb_tree_insert_node(tree2, node3);
	insertTest_2(tree2);
	rb_tree_dealloc(tree2);

	struct rb_tree *tree3 = rb_tree_init(NULL);
	struct rb_node *node4 = rb_node_init(3);
	node4->red = 0;
	struct rb_node *node5 = rb_node_init(2);
	node5->red = 0;
	struct rb_node *node6 = rb_node_init(4);
	node6->red = 0;
	struct rb_node *node7 = rb_node_init(1);
	node7->red = 1;
	rb_tree_insert_node(tree3, node4);
	rb_tree_insert_node(tree3, node5);
	rb_tree_insert_node(tree3, node6);
	rb_tree_insert_node(tree3, node7);
	removeTest_2(tree3);
	rb_tree_dealloc(tree3);

	scanf("%d");
	return 0;

}
