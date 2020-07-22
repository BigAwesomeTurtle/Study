
`timescale 1ns/1ns

module tb1_b2bd();

reg [3:0] bin;
wire [7:0] res;

integer i;

b2bd test(bin,res);

initial begin
	for(i=0;i<16;i=i+1)begin
		bin=i;
		#100;
	end
	$stop;
	
end
endmodule 

