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
		Set<Long> ids = new HashSet<>();
		for (User user : users)
		{
			ids.add(user.id);
		}
		assertEquals (users.length, ids.size());
		System.out.println(users);
	}

	/*@Test
	public void testToString()
	{
		assertEquals (dean ,gaffney,mechanic,, m, ,dean.id}", dean.toString());
	}
	*/

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
		
	}
}
