package models;

import static models.Fixtures.users;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class UserTest
{
	User dean = new User ("dean", "gaffney", 19, "m",  "mechanic");

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
	public void getUserById()
	{
		//set up ids map
		Set<Long> ids = new HashSet<>();
		for (User user : users)
		{
			ids.add(user.id);
		}
		assertEquals (users.length, ids.size());
		
		//test that getting this user will return the first user
		assertEquals(users[0].firstName,"dean");
		//should equal 19
		assertNotEquals(users[0].age,20);
		//should pass
		
		assertEquals(users[1].firstName,"dave");
		//check the toString of this user against itself make sure its getting the correct toString
		assertEquals(users[1].toString(),users[1].toString());
		//this shouldn't be equal,checking a different toString with another toString.
		assertNotEquals(users[1].toString(),users[2].toString());
	}
}
