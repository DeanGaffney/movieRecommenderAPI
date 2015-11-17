package controllers;

import java.io.File;

import edu.princeton.cs.introcs.In;


public class ParseExample 
{
	public static void main(String[] args) throws Exception 
	{
		File usersFile = new File("data/users5.dat");
		In inUsers = new In(usersFile);
		//each field is separated(delimited) by a '|'
		String delims = "[|]";
		while (!inUsers.isEmpty()) {
			// get user and rating from data source
			String userDetails = inUsers.readLine();

			// parse user details string
			String[] userTokens = userDetails.split(delims);

			// output user data to console.
			if (userTokens.length == 7) {
				System.out.println("UserID: "+userTokens[0]+",First Name:"+
						userTokens[1]+",Surname:" + userTokens[2]+",Age:"+
						Integer.parseInt(userTokens[3])+",Gender:"+userTokens[4]+",Occupation:"+
						userTokens[5]);

			}else
			{
				throw new Exception("Invalid member length: "+userTokens.length);
			}
		}
	}
	
}


