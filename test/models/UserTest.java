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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import utils.Serializer;
import utils.XMLSerializer;
import controllers.Data;
import controllers.MovieRecommenderAPI;

public class UserTest
{
	User dean = new User (1l,"dean", "gaffney", 19, "m",  "mechanic");
	MovieRecommenderAPI movieRecommender;
	
	@Rule 
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testCreate()
	{
		assertSame(1l,                     dean.id);
		assertEquals ("dean",                dean.firstName);
		assertEquals ("gaffney",             dean.lastName);
		assertEquals (19,                    dean.age);   
		assertEquals ("m",                   dean.gender); 
		assertEquals ("mechanic",            dean.occupation);
	}
	@Test 
	public void blankfirstName() throws Exception
	{
		exception.expect(Exception.class);
		new User("","Gaffney",19,"m","mechanic");
	}
	
	@Test
	public void blankLastName() throws Exception
	{
		exception.expect(Exception.class);
		new User("Dean","",19,"m","mechanic");
	}

	@Test 
	public void highAge() throws Exception
	{
		exception.expect(Exception.class);
		new User("Dean","Gaffney",1000,"m","mechanic");
	}
	
	public void lowAge() throws Exception
	{
		exception.expect(Exception.class);
		new User("Dean","Gaffney",-1,"m","mechanic");
	}
	
	@Test 
	public void blankGender() throws Exception
	{
		exception.expect(Exception.class);
		new User("Dean","Gaffney",19,"","mechanic");
	}
	
	@Test 
	public void blankOccupation() throws Exception
	{
		exception.expect(Exception.class);
		new User("Dean","Gaffney",19,"m","");
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
		assertEquals("ID: " + dean.id + "\n" + "First Name: " + dean.firstName + "\n" + "Last Name: " + dean.lastName + "\n" +
				"Occupation: " + dean.occupation + "\n" + "Gender: " + dean.gender + "\n" +
				"No. of Ratings " + dean.ratings.size() + "\n" + "\n",dean.toString());
	}


	@Test
	public void testEquals()
	{
		User dean2 = new User (1l,"dean", "gaffney", 19, "m",  "mechanic"); 
		User bart   = new User (2l,"bart", "simpson", 12, "m",  "skateboarder"); 

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
	public void addUser() throws Exception
	{

		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		MovieRecommenderAPI movieRecommender2 = new MovieRecommenderAPI(serializer);
		
		
		//put users from fixtures into movieRecommender and store them.
		for(int i = 0; i<users.length;i++)
		{
			movieRecommender.createUser(users[i]);
		}
		
		//make sure that they were added successfully by checking the size of movieRecommender against fixtures.
		movieRecommender.store();
		assertEquals(movieRecommender.getUsers().size(),users.length);
		
		//load movieRecommender 2 and check the size against movieRecommender
		movieRecommender2.load();
		assertEquals(movieRecommender.getUsers().size(),movieRecommender2.getUsers().size());
		
		/*If the next test passes it is safe to say that every user in
		 * movieRecommender2 contains the same users as movieRecommender,
		 * meaning that the users were successfully added in both 
		 * movieRecommender and movieRecommender2.
		 */
		for(User user : movieRecommender.getUsers())
		{
			assertTrue(movieRecommender2.getUsers().contains(user));
		}
	}

	@Test
	public void deleteUserById() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		
		//populate user list from Fixtures.
		for(int i = 0; i<users.length;i++)
		{
			movieRecommender.createUser(users[i]);
		}
		System.out.println(movieRecommender.getUsers());
		movieRecommender.store();
		
		//create second object to test against
		MovieRecommenderAPI movieRecommender2 = new MovieRecommenderAPI(serializer);
		movieRecommender2.load();
		
		/*the for loop will go through each user in movieRecommender
		 * and will get the id's of each user. It will then test the movieRecommender
		 * id's against the id's of movieRecommender2 assuring that the getUser(id) function
		 * is working correctly.
		 */
		for(Long i = 1l;i< movieRecommender.getUsers().size();i++)
		{
			movieRecommender.deleteUser(i);
		}
		
		assertNotEquals(0,movieRecommender.getUsers().size());
		movieRecommender.store();
		
		movieRecommender2.load();
		assertEquals(movieRecommender.getUsers().size(),movieRecommender2.getUsers().size());

	}
}
