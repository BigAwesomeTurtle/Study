
`timescale 1ns/1ns

module tb1_sums();

reg c_in;
reg signed [2:0] a;
reg signed [2:0] b;
wire c_out;
wire signed [2:0] res;

integer i,j;
integer filewrite;

sum test(a,b,c_in,res,c_out);

initial begin
	for(i=0;i<8;i=i+1)
		for(j=0;j<8;j=j+1)begin
			$timeformat(-9,0,"ns",3);
			filewrite = $fopen("tb1_sums.dat");
			a=i;
			b=j;
			c_in=1'b0;
			#15;
			$fdisplay(filewrite," TIME = %t, a = %d, b = %d, c_in = %d, res = %d, c_out = %d", $time, a, b, c_in, res, c_out);
			#15;
			c_in=1'b1;
			#15;
			$fdisplay(filewrite," TIME = %t, a = %d, b = %d, c_in = %d, res = %d, c_out = %d", $time, a, b, c_in, res, c_out);
			#15;
		end
		$stop;
	
end
endmodule 
