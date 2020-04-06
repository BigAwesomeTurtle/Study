./server.out &
for value in $(seq 1 $1)
do
./client.out &
done