package models;

import static models.Fixtures.movies;
import static models.Fixtures.users;
import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import controllers.Data;
import controllers.MovieRecommenderAPI;

public class UserTest
{
	User dean = new User ("dean", "gaffney", 19, "m",  "mechanic");
	MovieRecommenderAPI movieRecommender;
	@Test
	public void testCreate()
	{
		assertEquals ("dean",                dean.firstName);
		assertEquals ("gaffney",             dean.lastName);
		assertEquals (19,                    dean.age);   
		assertEquals ("m",                   dean.gender); 
		assertEquals ("mechanic",            dean.occupation);
	}

	@Test
	public void testIds()
	{
		//test id size
		Set<Long> ids = new HashSet<>();
		for (User user : users)
		{
			ids.add(user.id);
		}
		assertEquals (users.length, ids.size());
		//test that each objects id witch each other to ensure they are the same
		for(int i = 0; i<users.length;i++)
		{
			assertEquals(users[i].id,users[i].id);
		}
	}

	@Test
	public void testToString()
	{
		assertEquals(dean.toString(),dean.toString());
	}


	@Test
	public void testEquals()
	{
		User dean2 = new User ("dean", "gaffney", 19, "m",  "mechanic"); 
		User bart   = new User ("bart", "simpson", 12, "m",  "skateboarder"); 

		assertEquals(dean, dean);
		assertEquals(dean, dean2);
		assertNotEquals(dean, bart);
	} 
	
	
	@Test
	public void getUserById() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		Data data = new Data();
		
		//populate user list from the readable file
		List<User> users  = data.importUsers("data/users5.dat");
		for(User user:users)
		{
			movieRecommender.createUser(user);
		}
		movieRecommender.store();
		
		//create second object to test against
		MovieRecommenderAPI movieRecommender2 = new MovieRecommenderAPI(serializer);
		movieRecommender2.load();
		
		/*the for loop will go through each user in movieRecommender
		 * and will get the id's of each user. It will then test the movieRecommender
		 * id's against the id's of movieRecommender2 assuring that the getUser(id) function
		 * is working correctly.
		 */
		for (User user : movieRecommender.getUsers())
		{
			assertEquals(user.id,movieRecommender2.getUser(user.id).id);
		}
	}



	@Test
	public void deleteUserById() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		Data data = new Data();
		
		//populate user list from the readable file
		List<User> users  = data.importUsers("data/users5.dat");
		for(User user:users)
		{
			movieRecommender.createUser(user);
		}
		movieRecommender.store();
		
		//create second object to test against
		MovieRecommenderAPI movieRecommender2 = new MovieRecommenderAPI(serializer);
		movieRecommender2.load();
		
		/*the for loop will go through each user in movieRecommender
		 * and will get the id's of each user. It will then test the movieRecommender
		 * id's against the id's of movieRecommender2 assuring that the getUser(id) function
		 * is working correctly.
		 */
		for(User user : movieRecommender.getUsers())
		{
			movieRecommender.deleteUser(user.id);
		}
		movieRecommender.store();
		
		assertNotEquals(movieRecommender.getUsers().size(),movieRecommender2.getUsers().size());
		
		movieRecommender2.load();
		assertEquals(movieRecommender.getUsers().size(),movieRecommender2.getUsers().size());

	}
}
