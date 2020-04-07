#include <stdio.h>
#include <string.h>
#include "rb_tree.h"

int pow2 = 1;
int counter = 0;

struct rb_node *rb_node_init (void *value) {
	struct rb_node *self = malloc(sizeof(struct rb_node));
    if (self) {
        self->red = 1;
        self->link[0] = self->link[1] = NULL;
        self->value = value;
    }
    return self;
}

void rb_node_dealloc (struct rb_node *self) {
    if (self) free(self);
}

static int rb_node_is_red (const struct rb_node *self) {
    return self ? self->red : 0;
}

static struct rb_node *rb_node_rotate (struct rb_node *self, int dir) {
    struct rb_node *result = NULL;
    if (self) {
        result = self->link[!dir];
        self->link[!dir] = result->link[dir];
        result->link[dir] = self;
        self->red = 1;
        result->red = 0;
    }
    return result;
}

static struct rb_node *rb_node_rotate2 (struct rb_node *self, int dir) {
    struct rb_node *result = NULL;
    if (self) {
        self->link[!dir] = rb_node_rotate(self->link[!dir], !dir);
        result = rb_node_rotate(self, dir);
    }
    return result;
}

int rb_tree_node_compare (struct rb_tree *self, struct rb_node *a, struct rb_node *b) {
    return (a->value > b->value) - (a->value < b->value);
}

struct rb_tree *rb_tree_init (rb_tree_comparator node_cmp_cb) {
	struct rb_tree *self= malloc(sizeof(struct rb_tree));
    if (self) {
        self->root = NULL;
        self->size = 0;
        self->cmp = node_cmp_cb ? node_cmp_cb : rb_tree_node_compare;
    }
    return self;
}

void rb_tree_dealloc (struct rb_tree *self) {
    if (self) free(self);
}

void *rb_tree_find(struct rb_tree *self, void *value) {
    void *result = NULL;
    if (self) {
        struct rb_node node = { .value = value };
        struct rb_node *it = self->root;
        int cmp = 0;
        while (it) {
            if ((cmp = self->cmp(self, it, &node))) {
                it = it->link[cmp < 0];
            } else {
                break;
            }
        }
        result = it ? it->value : NULL;
    }
    return result;
}


int rb_tree_insert (struct rb_tree *self, void *value) {
    return rb_tree_insert_node(self, rb_node_init(value));
}

int rb_tree_insert_node (struct rb_tree *self, struct rb_node *node) {
    int result = 0;
    if (self && node) {
        if (self->root == NULL) {
			node->red = 0;
            self->root = node;
            result = 1;
        } else {
            struct rb_node head = { 0 };
            struct rb_node *g, *t;
            struct rb_node *p, *q;       
            int dir = 0, last = 0;
            t = &head;
            g = p = NULL;
            q = t->link[1] = self->root;

            while (1) {
				if (q == NULL) {

					p->link[dir] = q = node;
				}else if (rb_node_is_red(q->link[0]) && rb_node_is_red(q->link[1])) {

					q->red = 1;
					q->link[0]->red = 0;
					q->link[1]->red = 0;
				}

                if (rb_node_is_red(q) && rb_node_is_red(p)) {

                    int dir2 = t->link[1] == g;
                    if (q == p->link[last]) {
                        t->link[dir2] = rb_node_rotate(g, !last);
                    } else {
                        t->link[dir2] = rb_node_rotate2(g, !last);
                    }
                }

                if (self->cmp(self, q, node) == 0) {
                    break;
                }

                last = dir;
                dir = self->cmp(self, q, node) < 0;

                if (g != NULL) {
                    t = g;
                }

                g = p, p = q;
                q = q->link[dir];
            }

            self->root = head.link[1];
        }

        self->root->red = 0;
        ++self->size;
    }
    
    return result;
}

int rb_tree_remove (struct rb_tree *self, void *value) {
    if (self->root != NULL) {
        struct rb_node head = {0}; 
        struct rb_node node = { .value = value }; 
        struct rb_node *q, *p, *g;
        struct rb_node *f = NULL;
        int dir = 1;

      
        q = &head;
        g = p = NULL;
        q->link[1] = self->root;
    
        while (q->link[dir] != NULL) {
            int last = dir;
   
            g = p, p = q;
            q = q->link[dir];
            dir = self->cmp(self, q, &node) < 0;
      
            if (self->cmp(self, q, &node) == 0) {
                f = q;
            }

            if (!rb_node_is_red(q) && !rb_node_is_red(q->link[dir])) {
                if (rb_node_is_red(q->link[!dir])) {
                    p = p->link[last] = rb_node_rotate(q, dir);
                } else if (!rb_node_is_red(q->link[!dir])) {
                    struct rb_node *s = p->link[!last];
                    if (s) {
                        if (!rb_node_is_red(s->link[!last]) && !rb_node_is_red(s->link[last])) {

                            p->red = 0;
                            s->red = 1;
                            q->red = 1;
                        } else {
                            int dir2 = g->link[1] == p;
                            if (rb_node_is_red(s->link[last])) {
                                g->link[dir2] = rb_node_rotate2(p, last);
                            } else if (rb_node_is_red(s->link[!last])) {
                                g->link[dir2] = rb_node_rotate(p, last);
                            }
                            
                            q->red = g->link[dir2]->red = 1;
                            g->link[dir2]->link[0]->red = 0;
                            g->link[dir2]->link[1]->red = 0;
                        }
                    }
                }
            }
        }

        if (f) {
            void *tmp = f->value;
            f->value = q->value;
            q->value = tmp;
            
            p->link[p->link[1] == q] = q->link[q->link[0] == NULL];
			if (self) {
				if (q) {
					rb_node_dealloc(q);
				}
			}
            q = NULL;
        }
		else {
			return 0;
		}

        self->root = head.link[1];

        if (self->root != NULL) {
            self->root->red = 0;
        }

        --self->size;
    }
    return 1;
}

void print_tree_to_file(struct rb_node *root, char *filename,char *mode,char *msg)
{
	pow2 = 1;
	counter = 0;
	FILE *fout;
	fout = fopen(filename, mode);
	if (fout == NULL) {
		printf("Cannot open file");
		exit(0);
	}
	fprintf(fout, "%s",msg);
	int n = 0;
	while (print_layer(root, n,fout)) ++n;
	fprintf(fout, "\n\n");
	fclose(fout);
}

int print_layer(struct rb_node *root, int n, FILE *fout)
{
	while (1)
	{
		if (!root) {
			fprintf(fout, "null ");
			counter++;
			if (counter == pow2) {
				fprintf(fout, "\n");
				counter = 0;
				pow2 = pow2 * 2;
			}
			return 0;
		}
		if (n == 0)
		{	
			fprintf(fout, "%d(%d) ", root->value,root->red);
			counter++;
			if (counter == pow2) {
				fprintf(fout, "\n");
				counter = 0;
				pow2 = pow2 * 2;
			}
			return root->link[0] || root->link[1];
		}
		
		int l = print_layer(root->link[0], n - 1, fout);
		int r = print_layer(root->link[1], n - 1, fout);
		return l || r;
	}
}

struct rb_tree *read_from_file(char *filename) {
	FILE *fin;
	fin = fopen(filename, "r");
	if (fin == NULL) {
		printf("Cannot open file ");
		printf(filename);
		exit(0);
	}
	struct rb_tree *res = rb_tree_init(NULL);
	char c;
	char str[128];
	for (int i = 0; i < 128; i++) {
		str[i] = NULL;
	}
	while ((c = (char)getc(fin)) != EOF) {
		if (c == ' '||c=='\n') {
			
			if (str[0] == ' '|| str[0] == '\n'||str[0]==NULL) {
				memset(str, 0, 128);
				continue;
			}
			if (str[0] != 'n'||str[1] != 'u'||str[2] != 'l'||str[3] != 'l') {
				int result = 0;
				int r_or_b = -1;
				for (int i = 0; i < 128; i++) {
					if (str[i] == NULL) break;
					if (str[i] == '(') {
						r_or_b = str[i+1]%48;
						break;
					}
					result = result * 10;
					result = result + (int)str[i]%48;
				}
				struct rb_node *resNode = rb_node_init(result);
				resNode->red = r_or_b;
				rb_tree_insert_node(res, resNode);
			}
			memset(str, 0, 128);
			continue;
		}
		for (int i = 0; i < 128; i++) {
			if (str[i] == NULL) {
				str[i] = c;
				break;
			}
		}
		
	}
	fclose(fin);
	return res;
}

size_t rb_tree_size (struct rb_tree *self) {
    size_t result = 0;
    if (self) {
        result = self->size;
    }
    return result;
}

