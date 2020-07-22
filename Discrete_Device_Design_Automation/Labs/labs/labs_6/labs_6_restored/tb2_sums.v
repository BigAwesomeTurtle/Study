
`timescale 1ns/1ns

module tb2_sums();

reg c_in;
reg signed [2:0] a;
reg signed [2:0] b;
wire c_out;
wire signed [2:0] res;

integer i,j;

sum test(a,b,c_in,res,c_out);
wire signed [2:0] exp = a + b + c_in;
initial begin : summ_test
	for(i=0;i<8;i=i+1)
		for(j=0;j<8;j=j+1)begin
			$timeformat(-9,0,"ns",3);
			a=i;
			b=j;
			c_in=1'b0;
			test_t2;
			#15;
			$display(" TIME = %t, a = %d, b = %d, c_in = %d, res = %d, c_out = %d", $time, a, b, c_in, res, c_out);
			#15;
			c_in=1'b1;
			#15;
			$display(" TIME = %t, a = %d, b = %d, c_in = %d, res = %d, c_out = %d", $time, a, b, c_in, res, c_out);
			#15;
		end
		$display("SUM TESTED");
		$stop;
	
end

task test_t2;
begin

		if(res != exp) begin
			$display("Something is wrong");
			$display("Expected %d, but got %d",exp,res);
			$stop;
		end
end
endtask
endmodule 
