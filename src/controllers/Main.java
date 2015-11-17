package controllers;

import java.io.File;

import utils.Serializer;
import utils.XMLSerializer;
import edu.princeton.cs.introcs.In;

public class Main
{
	public static void main(String[] args) throws Exception 
	{
		File usersFile = new File("users.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);

		In inUsers = new In("data/users.dat");

		//each field is separated(delimited) by a '|'
		String delims = "[|]";
		while (!inUsers.isEmpty()) 
		{
			// get user and rating from data source
			String userDetails = inUsers.readLine();

			// parse user details string
			String[] userTokens = userDetails.split(delims);
			movieRecommender.createUser(Long.parseLong(userTokens[0]),userTokens[1],userTokens[2],Integer.parseInt(userTokens[3]),userTokens[4],userTokens[5]);
			// output user data to console.
			if (movieRecommender.getUsers()!=null) 
			{
				System.out.println(movieRecommender.getUsers().size());
			}
			else
			{
				throw new Exception("Invalid member length: "+userTokens.length);
			}

		}
		for(int i = 0;i<=movieRecommender.getUsers().size();i++)
		{
			System.out.println(movieRecommender.getUser(Long.valueOf(i)));
		}
		movieRecommender.store();
	}
}

