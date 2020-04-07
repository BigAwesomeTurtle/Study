#ifndef RB_TREE_H_
#define RB_TREE_H_ 1

#include <stdio.h>
#include <stdint.h>
#include <stddef.h>
#include <stdlib.h>

struct rb_node;
struct rb_tree;

typedef int(*rb_tree_comparator)(struct rb_tree *self, struct rb_node *a, struct rb_node *b);

struct rb_node {
    int red;
    struct rb_node *link[2];
    void *value;
};

struct rb_tree {
    struct rb_node *root;
    rb_tree_comparator cmp;
    size_t size;
};

int rb_tree_node_compare(struct rb_tree *self, struct rb_node *a, struct rb_node *b);
struct rb_node *rb_node_init(void *value);
void rb_node_dealloc(struct rb_node *self);
struct rb_tree *rb_tree_init(rb_tree_comparator cmp);
void rb_tree_dealloc(struct rb_tree *self);
void *rb_tree_find(struct rb_tree *self, void *value);
int  rb_tree_insert(struct rb_tree *self, void *value);
int  rb_tree_remove(struct rb_tree *self, void *value);
size_t rb_tree_size(struct rb_tree *self);
void print_tree_to_file(struct rb_node *root, char *filename, char *mode, char *msg);
int rb_tree_insert_node(struct rb_tree *self, struct rb_node *node);
struct rb_tree *read_from_file(char *filename);

#endif
