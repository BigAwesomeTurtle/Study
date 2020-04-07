.rodata
array_length:
.word 10
x:
.word 4
msg1: .string "f(x)="
array:
.word 8,6,11,2,5,-3,12,100,33,-111
 
.text
main:
.globl main
la a3,array_length
lw a3,0(a3)
la a7,x
lw a7,0(a7)
li a2,1
la a4,array #b
lw a4,0(a4)
la a5,array

jal zero, loop_check
loop:
slli a6,a2,2 
add a6,a5,a6 
lw a6,0(a6)
mul a4,a4,a7
add a4,a4,a6

addi a2,a2,1
loop_check:
bltu a2,a3,loop #a2 <a3 goto loop

finish:
li a0, 4
la a1, msg1
ecall

li a0, 1
mv a1, a4
ecall 

li a0,10
ecall