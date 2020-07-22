
`timescale 1ns/1ns

module tb2f_sums();

localparam CLKPERIOD=20;

reg c_in;
reg signed [2:0] a;
reg signed [2:0] b;
wire c_out;
wire signed [2:0] res;

reg signed [2:0] in_data [23:0];
reg signed [2:0] exp [15:0];

integer i;
reg clk;

sum test(a,b,c_in,res,c_out);

initial begin: clk_generation
clk=1'b0;
forever #(CLKPERIOD/2) clk=~clk; 
end

initial begin : loadData
$readmemb("input_sums.dat",in_data);
$readmemb("exp_sums.dat",exp);
end

initial begin
for(i=0; i<8; i=i+1)
begin
	@(negedge clk) begin
		a=in_data[3*i];
		b=in_data[3*i+1];
		c_in=in_data[3*i+2];
		end
	@(posedge clk)begin
			if(res != exp[2*i]) begin
				$display("Something is wrong");
				$display("Expected %d, but got %d",exp[2*i],res);
				$stop;
			end
			if(c_out != exp[2*i+1]) begin
				$display("Something is wrong");
				$display("Expected %d, but got %d",exp[2*i+1],c_out);
				$stop;
			end
		end
	#(CLKPERIOD/2);
	$display("SUM TESTED");
end
#CLKPERIOD $stop;
end
endmodule

