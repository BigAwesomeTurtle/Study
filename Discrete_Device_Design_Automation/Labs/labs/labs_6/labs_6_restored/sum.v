
module sum(a, b, c_in, res, c_out);
(* altera_attribute= " -name IO_STANDARD\"2.5 V\"", chip_pin="46, 25, 24" *)
input [2:0] a;
(* altera_attribute= " -name IO_STANDARD\"2.5 V\"", chip_pin="90, 91, 49" *)
input [2:0] b;
(* altera_attribute= " -name IO_STANDARD\"2.5 V\"", chip_pin="64" *)
input c_in;
(* altera_attribute= " -name IO_STANDARD\"3.3-V LVTTL\"", chip_pin="70, 71, 72" *)
output [2:0] res;
(* altera_attribute= " -name IO_STANDARD\"3.3-V LVTTL\"", chip_pin="69" *)
output c_out;

wire [3:0] cout;
assign cout[0]=c_in;
assign c_out=cout[3];
genvar i;

generate
		for(i=0; i<3; i=i+1) begin : summator
				summ summ_(a[i],b[i],cout[i],res[i],cout[i+1]);
		end
endgenerate

endmodule

module summ(a_,b_,c_in_,res_,c_out_);
	input a_,b_,c_in_;
	output res_, c_out_;
	
	wire u1, u2, u3;
	and(u1, a_, b_);
	and(u3, u2, c_in_);
	xor(u2, a_, b_);
	xor(res_, c_in_, u2);
	or(c_out_, u3, u1);
endmodule
