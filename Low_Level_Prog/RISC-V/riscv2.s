.globl main
.text
main:
test1:
la ra, print_first_result
la a1, polynom_first
la a4, polynom_first
la a3, polynom_length_first
la a7, x_first
j gorner
print_first_result:

li a0, 1
mv a1, a4
ecall

li a0, 11
li a1, '\n'
ecall

test2:
la ra, print_second_result
la a1, polynom_second
la a4, polynom_second
la a3, polynom_length_second
la a7, x_second
j gorner
print_second_result:
li a0, 1
mv a1, a4
ecall
finish:
li a0, 10 # x10 = 10
li a1, 0 # x11 = 0
ecall # ecall при значении x10 = 10 => останов симулятора

gorner:
lw a4,0(a4)
lw a3,0(a3)
lw a7,0(a7)
li t0,1


j loop_check
loop:

slli a6,t0,2 
add a6,a1,a6 
lw a6,0(a6)
mul a4,a4,a7
add a4,a4,a6

addi t0,t0,1
loop_check:
bltu t0,a3,loop
loop_finish:
li a6,0
jr ra

.rodata
x_first:.word 3
polynom_length_first: .word 5
polynom_first: .word 2,-1,13,100,-3
x_second:.word 2
polynom_length_second: .word 8
polynom_second: .word 1,0,2,4,-3,-5,0,100