
`timescale 1ns/1ns
module tb2_b2bd();

localparam CLKPERIOD=20;

reg [3:0] bin;
wire [7:0] res;
reg [7:0] exp;

reg clk;
integer i;

b2bd test(bin,res);

initial begin: clk_generation
clk=1'b0;
forever #(CLKPERIOD/2) clk=~clk; 
end

initial begin : dmux_test
for(i=0;i<=15;i=i+1)
	begin
	bin=i;
	#CLKPERIOD;
	end
		
$display("B2BD TESTED");
#CLKPERIOD $stop;
end


initial begin: test_t2

	@(posedge clk)#0.1
		exp[7:4]=(bin>9)?1:0;
		exp[3:0]=bin % 10;
		
		if(res != exp) begin
			$display("Something is wrong");
			$display("Expected %d, but got %d",exp,res);
			$stop;
		end

end

endmodule

