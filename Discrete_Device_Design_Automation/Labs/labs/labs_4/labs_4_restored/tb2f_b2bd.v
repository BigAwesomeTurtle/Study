
`timescale 1ns/1ns

module tb2f_b2bd();

localparam CLKPERIOD=20;

reg [3:0] bin;
wire [7:0] res;

reg [3:0] in_data [15:0];
reg [7:0] exp [15:0];

integer i;
reg clk;

b2bd test(bin,res);

initial begin: clk_generation
clk=1'b0;
forever #(CLKPERIOD/2) clk=~clk; 
end

initial begin : loadData
$readmemb("input_b2bd.dat",in_data);
$readmemb("exp_b2bd.dat",exp);
end

initial begin
for(i=0; i<=15; i=i+1)
begin
	@(negedge clk);
		bin=in_data[i];
	@(posedge clk);
		if(res != exp[i]) begin
			$display("Something is wrong");
			$display("Expected %d, but got %d",exp[i],res);
			$stop;
		end
	#(CLKPERIOD/2);
	$display("B2BD TESTED");
end
#CLKPERIOD $stop;
end
endmodule

