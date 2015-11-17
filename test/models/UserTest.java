package models;

import static models.Fixtures.users;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class UserTest
{
	User dean = new User (1l, "dean", "gaffney", 19, "m",  "mechanic");

	@Test
	public void testCreate()
	{
		assertSame (1l,				     dean.id);
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
	}

	@Test
	public void testToString()
	{
		assertEquals ("User{" + dean.id + ", dean, gaffney, 19, m, mechanic}", dean.toString());
	}

	@Test
	public void testEquals()
	{
		User dean2 = new User (1l, "dean", "gaffney", 19, "m",  "mechanic"); 
		User bart   = new User (2l, "bart", "simpson", 12, "m",  "skateboarder"); 

		assertEquals(dean, dean);
		assertEquals(dean, dean2);
		assertNotEquals(dean, bart);
	} 
}
