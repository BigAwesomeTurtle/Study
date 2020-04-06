#define DEF_KEY_FILE "key"
#define BUF_SIZE 100

typedef struct {
	long type;
	char buf[100];
} Message;

static struct sembuf setFree[1] = {0,BUF_SIZE,0};
static struct sembuf waitNotEmpty[1] = {1,-1,0};
static struct sembuf releaseEmpty[1] = {0,1,0};
static struct sembuf releaseFull[1] = {1,1,0};
static struct sembuf waitNotFull[1] = {0,-1,0};
static struct sembuf mem_lock[1] = {2,-1,0};
static struct sembuf mem_unlock[1] = {2,1,0};