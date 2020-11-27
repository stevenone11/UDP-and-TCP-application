package TCP_Assignment;
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


/**
 * 
 * @author Steven Centeno
 *
 * This TCP server has the logical operations capable of generating a password given 
 * the maximum and minimum length for the password
 */
public class TCP_Server_A 
{

	public static void main(String[] args) throws Exception
	{
		
		// sets up the server's socket for input
		String numChoice;
		int choice, minAmtOfChars = 0, maxAmtOfChars = 0, minAmtOfSChars= 0;
		boolean nums = false;
		ServerSocket ss = new ServerSocket(1024);
		Socket s = ss.accept();
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		// now reads input from the server's socket
		// first will ask for the minimum amount of characters
		// will catch the error if the user inputed a character instead
		minAmtOfChars = Integer.parseInt(br.readLine());
		
		// then will ask for the maximum amount of characters
		// will catch the error if the user inputed a character instead
		maxAmtOfChars = Integer.parseInt(br.readLine());
		
		//gets the minimum amount of special characters
		minAmtOfSChars = Integer.parseInt(br.readLine());
		
		// decides whether the user wants to have numbers or not
		numChoice = br.readLine();
		
		// finally it will ask if the user wants to (0: quit, 1: generate a password) 
		choice = Integer.parseInt(br.readLine());
		
		// the decision of whether to include numbers or not
		if (numChoice == "yes" || numChoice == "yes")
		{
			nums = true;
		}
		else if (numChoice == "No" || numChoice == "no")
		{
			nums = false;
		}
		
		// the choice the user selected
		String password = "";
		if (choice != 0) 
		{
			
			// creates a password with a minimum of 1 character and with numbers
			password = GeneratePass(minAmtOfChars, maxAmtOfChars, minAmtOfSChars, nums);
			
		}
		
		else if (choice == 0)
		{
			password = "No password generated: Exited program.";
		}
		
		else 
		{
			password = "No password generated: Invalid input.";
		}
		
		PrintStream pr = new PrintStream(s.getOutputStream());
		pr.println(password);
		ss.close();
		s.close();
		
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
