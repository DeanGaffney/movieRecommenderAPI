import static org.junit.Assert.*;

import java.io.File;








import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import controllers.MovieRecommenderAPI;
import edu.princeton.cs.introcs.In;


public class DataTest 
{
	MovieRecommenderAPI movieRecommender;

	void deleteFile(String fileName)
	{
		File datastore = new File ("testdatastore.xml");
		if (datastore.exists())
		{
			datastore.delete();
		}
	}

	void importData() throws Exception
	{

	}


	@Test
	public void testXMLSerializer() throws Exception
	{ 
		File usersFile = new File("data/users.dat");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);

		In inUsers = new In(usersFile);

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
	
		MovieRecommenderAPI movieRecommender2 =  new MovieRecommenderAPI(serializer);
		movieRecommender2.load();

		assertEquals (movieRecommender.getUsers().size(), movieRecommender2.getUsers().size());
		for (User user : movieRecommender.getUsers())
		{
			assertTrue (movieRecommender2.getUsers().contains(user));
		}
		deleteFile ("testdatastore.xml");
	}
}
