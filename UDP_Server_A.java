package UDP_Assignment;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 
 * @author steve
 *
 * The server side of the UDP protocol to generate a password
 */
public class UDP_Server_A 
{

	public static void main(String[] args) throws IOException
	{
		
		// setting up the datagram to receive data
		DatagramSocket ss = new DatagramSocket(2100);
		byte[] sendData = new byte[1024];
		byte[] recData = new byte[1024];
		String minAmtOfCharsStr = "", maxAmtOfCharsStr = "", minAmtOfSCharsStr = "", choiceNums = "", choice = "", password = "";
		int a, b, c, count, n = 0, i = 0;
		boolean d;
		String e;
		
		DatagramPacket dp = new DatagramPacket(recData, recData.length);
		ss.receive(dp);
		String input = new String(dp.getData());
		
		// reads through the string
		// every time there is an empty space, 
		for (count = 0; count < input.length(); count ++) 
		{
			if (input.charAt(count) == ' ') 
			{
				n++;
				if (n == 1) 
				{
					minAmtOfCharsStr = input.substring(i,count);
					i = count;
				}
				
				if (n == 2) 
				{
					maxAmtOfCharsStr = input.substring(i + 1, count);
					i = count;
				}
				if (n == 3)
				{
					minAmtOfSCharsStr = input.substring(i + 1, count);
					i = count;
				}
				if (n == 4)
				{
					choiceNums = input.substring(i + 1, count);
					i = count;
					choice = input.substring(i + 1, input.length());
				}
			}
		}
		
		// translates the user's input to their respective types to run the GeneratePass function
		InetAddress ip = dp.getAddress();
		a = Integer.parseInt(minAmtOfCharsStr);
		b = Integer.parseInt(maxAmtOfCharsStr);
		c = Integer.parseInt(minAmtOfSCharsStr);

		if (choiceNums.equals("Yes") || choiceNums.equals("yes")) 
		{
			d = true;
		}
		else
		{
			d = false;
		}
		
		choice = choice.trim(); // gets rid of ending trail of whitespace
		if (choice.equals("Yes") || choice.equals("yes")) 
		{
			password = GeneratePass(a,b,c,d);
		}
		else 
		{
			password = "No password generated.";
		}
		
		// sends the response message to the user
		int port = dp.getPort();
		String output = password;
		sendData = output.getBytes();
		DatagramPacket dp1 = new DatagramPacket(sendData, sendData.length, ip, port);
		ss.send(dp1);
		ss.close();
		
	}
	
	
	
	/*
	 * method that generates the password given the min-max chars and min amount of special
	 * chars
	 */
	public static String GeneratePass(int minAmtChars, int maxAmtChars, int sChars, boolean nums) {
		
		String password = "";
		
		// randomly decide the length of the password
		Random randNum = new Random();
		int passLength = (randNum.nextInt((maxAmtChars + 1) - minAmtChars)) + minAmtChars;
		
		// now decides the number of special characters
		int numOfSChars = 0;
		if (sChars > 0) 
		{
			numOfSChars = (randNum.nextInt((int)(Math.floor(passLength * .4))));
			
			// if the randomly generated number is still less than the minimum number of special 
			// characters just simply consider the minimal amount
			if (numOfSChars < sChars) {
				numOfSChars = sChars;
			}
			
			// now generate the number of special characters needed 
			for (int i = 0; i < numOfSChars; i ++) 
			{
				char SpecialChar = (char)(randNum.nextInt(14) + '!');
				password = password + SpecialChar;
			}
		}
		
		// now adds the numbers if needed
		if (nums)
		{
			int numOfNumbers = (randNum.nextInt((int)(Math.floor((passLength - numOfSChars) * .5))));
			
			// if the random number of numbers is 0 then increase it by one
			if (numOfNumbers == 0) {
				numOfNumbers = 1;
			}
			
			for (int i = 0; i < numOfNumbers; i++) 
			{
				char SpecialChar = (char)(randNum.nextInt(10) + '0');
				password = password + SpecialChar;
			}
		}
		
		// now adds the remaining characters randomly capital and lower case
		int alphabet = passLength - password.length();
		for (int j = 0; j < alphabet; j++) 
		{
			char AsciiChar = (char)(randNum.nextInt(26) + 'a');
			int randUpper = randNum.nextInt(2);
			if (randUpper == 1) 
			{
				AsciiChar = Character.toUpperCase(AsciiChar);
			}
			password = password + AsciiChar;
		}
		
		// now shuffle the characters
		java.util.List<String> shuffledList = Arrays.asList(password.split(""));
        Collections.shuffle(shuffledList);
        StringBuilder builtList = new StringBuilder();
        
        for (String letter : shuffledList) 
        {
        	builtList.append(letter);
        }
        password = builtList.toString();
		
		return password;
	}
}
