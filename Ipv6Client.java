/*
*	Author: Manvinder Toor
*	IPV6 Packet



******** RESPONSES FROM SERVER ****************
*	0xCAFEBABE | Packet was correct 
	0xCAFED00D | Incorrect version 
	0xDEADF00D | One of the “do not implement” ﬁelds was not all 0s 
	0xBBADBEEF | Payload length is wrong 
	0xFEEDFACE | Next header is wrong 
	0xFEE1DEAD | Hop limit is wrong 
	0xDEADC0DE | Bad source address 
	0xABADCAFE | Bad destination address
************************************************
*/
public class Ipv6Client{
	public static void main(String[] args){
		try(Socket socket = new Socket("",)){
			//Connected to server

			//Setting up input and output of client to and from server
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			//Setup source and dest address

			//initialize variables

			//Send 12 packets
			//Any packet not implemented is set to zero
			for(int i = 0< i <12;i++){
				//Total Length of packet
				int totalLength = length + dataLength;
				//initialize packet as array of bytes
				packet = new byte[totalLength];
				
				//Set up IVP6 version
				//Traffic Class not implemented
				//Flow label not implemented
				//Payload Length
				//Next Header - UDP
				//Hop Limit set to 20
				//Source Address ipv4 to ipv6
				//Dest address ipv4 to ipv6
				
				//Write to server
				
				//Recieve magic number from server that let's us know response

				System.out.print("Server response: 0x");
				for (int k = 0; k < 4; k++) {
					System.out.printf("%02X", is.read());
				}
				System.out.println("\n");
				
				// Change data length (start at 2 bytes, double each round)
				dataLength = dataLength * 2;
			}



		}
		//Disconnected from Server
	}
}