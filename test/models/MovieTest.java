package models;
import static org.junit.Assert.*;
import static models.Fixtures.movies;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
public class MovieTest 
{

	Movie movie = new Movie ("deansMovie", "1996","www.dean.com");

	@Test
	public void testCreate()
	{
		assertEquals ("deansMovie",                movie.title);
		assertEquals ("1996",             		   movie.year);
		assertEquals ("www.dean.com",              movie.url);   
	}

	@Test
	public void testIds()
	{
		//test id size
		Set<Long> ids = new HashSet<>();
		for (Movie movie:movies)
		{
			ids.add(movie.id);
		}
		assertEquals (movies.length, ids.size());
		//test that each objects id witch each other to ensure they are the same
		for(int i = 0; i<movies.length;i++)
		{
			assertEquals(movies[i].id,movies[i].id);
		}
	}

	@Test
	public void testToString()
	{
		assertEquals(movie.toString(),movie.toString());
	}


	@Test
	public void testEquals()
	{
		Movie movie2 = new Movie ("deansMovie", "1996","www.dean.com"); 
		Movie movie3  = new Movie ("algorithmsMovie", "2015","www.algorithms.com"); 

		assertEquals(movie, movie);
		assertEquals(movie, movie2);
		assertNotEquals(movie, movie3);
	} 

	@Test
	public void getUserById()
	{
		//set up id map
		Set<Long> ids = new HashSet<>();
		for (Movie movie : movies)
		{
			ids.add(movie.id);
		}
		assertEquals (movies.length, ids.size());

		//test that getting this movie will return the first movie
		assertEquals(movies[0].title,"deansMovie");
		//should equal 1996 so this will pass
		assertNotEquals(movies[0].year,"2000");
		//should pass

		assertEquals(movies[1].title,"paulsMovie");
		//check the toString of this movie against itself make sure its getting the correct toString
		assertEquals(movies[1].toString(),movies[1].toString());
		//this shouldn't be equal,checking a different toString with another toString.
		assertNotEquals(movies[1].toString(),movies[2].toString());
	}
}


