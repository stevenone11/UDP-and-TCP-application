package UDP_Assignment;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 * 
 * @author steve
 *
 * The client side of a UDP protocol communicating with a password generating server
 */
public class UDP_Client_A 
{
	
	public static void main(String[] args) throws IOException
	{
		
		// assigning the Datagram socket and buffered reader to send the data to the server
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket ds = new DatagramSocket();
		InetAddress ip = InetAddress.getByName("localhost");
		
		byte[] sendData = new byte[1024];
		byte[] recData = new byte[1024];
		
		// the inputs that will be sent to the server all in the same input
		// each new input is separated by a single space to avoid sending multiple 
		// segments of data thus avoiding ordering failures per new input
		System.out.print("Enter the minimum number of characters for the password :");
		String input = br.readLine();
		System.out.print("Enter maximum number of characters for the password :");
		input = input.concat(" " + br.readLine());
		System.out.print("Enter minimum number of special characters for the password :");
		input = input.concat(" " + br.readLine());
		System.out.print("Enter whether you want or dont want numbers in the password (yes or no) :");
		input = input.concat(" " + br.readLine());
		System.out.print("do you want to generate a password? (yes or no) : ");
		input = input.concat(" " + br.readLine());
		
		// send the data through a datagram
		sendData = input.getBytes();
		DatagramPacket dp = new DatagramPacket(sendData, sendData.length, ip, 2100);
		ds.send(dp);
		
		// now receive the output from the server
		DatagramPacket dp1 = new DatagramPacket(recData, recData.length);
		ds.receive(dp1);
		String output = new String(dp1.getData());
		System.out.println("Password = " + output);
		ds.close();

	}

}
