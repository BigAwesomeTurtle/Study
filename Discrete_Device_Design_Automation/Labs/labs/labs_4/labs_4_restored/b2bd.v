
module b2bd (bin,res);
(* altera_attribute= " -name IO_STANDARD\"2.5 V\"", chip_pin="49, 46, 25, 24" *)
input [3:0] bin;
(* altera_attribute= " -name IO_STANDARD\"3.3-V LVTTL\"", chip_pin="65, 66, 67, 68, 69, 70, 71, 72" *)
output reg [7:0] res;

always @* begin
	res=bin;
	
	if(bin > 9)
		res=res+8'd6;	
end 

endmodule 
