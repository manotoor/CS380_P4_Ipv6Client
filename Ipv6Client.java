/*
*	Author: Manvinder Toor
*	IPV6 Packet



******** RESPONSES FROM SERVER ****************
*	0xCAFEBABE | Packet was correct 
*	0xCAFED00D | Incorrect version 
*	0xDEADF00D | One of the “do not implement" fields was not all 0s 
*	0xBBADBEEF | Payload length is wrong 
*	0xFEEDFACE | Next header is wrong 
*	0xFEE1DEAD | Hop limit is wrong 
*	0xDEADC0DE | Bad source address 
*	0xABADCAFE | Bad destination address
************************************************
*/
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Ipv6Client{
	public static void main(String[] args){
		try(Socket socket = new Socket("18.221.102.182", 38004)){
			//Connected to server
			System.out.println("Connected to Server.");

			//Setting up input and output of client to and from server
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			//Setup source and dest address
			byte[] srcAddress = {127,0,0,1};
			byte[] destAddress = {18,(byte)221,102,(byte)182};

			//initialize variables
			int dataLength = 2;
			int ipv6Length = 40;
			int version = 6;
			byte[] packet;

			//Send 12 packets
			//Any packet not implemented is set to zero
			for(int i = 0;i<12;i++){
				//Total Length of packet
				int totalLength = dataLength + ipv6Length;
				//initialize packet as array of bytes
				packet = new byte[totalLength];
				
				//Set up IVP6 version (0110 0000)
				packet[0] = (byte)((version << 4 & 0xF0) | (0 & 0xFF));
				//Traffic Class not implemented
				packet[1] = 0;
				packet[2] = 0;
				//Flow label not implemented
				packet[3] = 0;
				//Payload Length
				byte payloadUpper = (byte)(dataLength >>> 8);
				byte payloadLower = (byte)(dataLength);
				packet[4] = payloadUpper;
				packet[5] = payloadLower;
				//Next Header - UDP
				packet[6] = 17;
				//Hop Limit set to 20
				packet[7]= 20;
				//Source Address ipv4 to ipv6 
				//(first 10 to zero next 2 = 1111 1111 (255) and last 4 are 
				//src address 127,0,0,1 or anything in this case)
				for(int j = 8; j <=17;j++){
					packet[j] = 0;
				}
				packet[18] = (byte)255;
				packet[19] = (byte)255;
				for(int j = 0; j < srcAddress.length;j++){
					packet[20+j] = srcAddress[j];
				}
				//Dest address ipv4 to ipv6
				//first 10 to zero next 2 = (255) and last 4 are
				//dest address 18,221,102,182 (but 221 and 182 are not actually 221 and 182 because signed)
				// 24 to 33 set to 0, extended address
				for (int j = 24; j <= 33; j++) {
					packet[j] = 0;
				}
				// 34 to 39
				packet[34] = (byte)255;
				packet[35] = (byte)255;
				for (int j = 0; j < destAddress.length; j++) {
					packet[36 + j] = destAddress[j];
				}
				
				//Write to server
				System.out.println("Data length: " + dataLength);
				os.write(packet);
				
				//Receive magic number
				System.out.print("Server response: 0x");
				for (int j = 0; j < 4; j++) {
					System.out.printf("%02X", is.read());
				}
				System.out.println("\n");
				
				// Change data length (start at 2 bytes, double each round)
				dataLength = dataLength * 2;
			}
		}catch(Exception e){
			System.out.println("Something went wrong.");
		}
		//Disconnected from Server
		System.out.println("Disconnected from server.");
	}
}