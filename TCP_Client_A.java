package TCP_Assignment;
import java.net.*;
import java.io.*;

/**
 * 
 * @author steve
 *
 * the client side of a TCP protocol to a password generating server 
 */
public class TCP_Client_A 
{

	public static void main(String[] args) throws Exception
	{

		int ch = 0,a,b,c;
		String d,out;
		
		// create the socket and the buffered reader to send the data to the server
		Socket s = new Socket("localhost", 1005);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintStream ps = new PrintStream(s.getOutputStream());
		
		//input to send to the server
		System.out.println("Plese enter minimum number of characters for the password");
		a = Integer.parseInt(br.readLine());
		System.out.println("Plese enter maximum number of characters for the password");
		b = Integer.parseInt(br.readLine());
		System.out.println("Plese enter minimum number of special characters (entering 0 will include no special characters)");
		c = Integer.parseInt(br.readLine());
		System.out.println("Do you want to include numbers in the password? (yes or no)");
		d = (br.readLine());
		System.out.println("Please enter command to be performed \n");
		System.out.println("0. exit \n1. generate password");
		ch = Integer.parseInt(br.readLine());
		
		ps.println(a);
		ps.println(b);
		ps.println(c);
		ps.println(d);
		ps.println(ch);
		
		// after sending the data, read the output of the server
		BufferedReader br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = br1.readLine();
		System.out.println("Password: " + out);
		s.close();
	}

}
