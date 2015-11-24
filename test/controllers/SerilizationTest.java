package controllers;
import static models.Fixtures.users;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import models.Fixtures;
import models.Movie;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import controllers.MovieRecommenderAPI;
import edu.princeton.cs.introcs.In;


public class SerilizationTest 
{
	MovieRecommenderAPI movieRecommender;
	Data data;
	Fixtures fixtures = new Fixtures();

	void deleteFile(String fileName)
	{
		File datastore = new File ("testdatastore.xml");
		if (datastore.exists())
		{
			datastore.delete();
		}
	}

	void populate (MovieRecommenderAPI movieRecommender)
	{
		for (User user : users)
		{
			movieRecommender.createUser(user.firstName,user.lastName,user.age,user.gender,user.occupation);
		}
	}

	@Test
	public void testPopulate()
	{
		movieRecommender = new MovieRecommenderAPI(null);
		assertEquals(0, movieRecommender.getUsers().size());
		populate (movieRecommender);

		assertEquals(users.length, movieRecommender.getUsers().size());	     
	}

	@Test
	public void testXMLSerializer() throws Exception
	{ 
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		Data data = new Data();

		populate(movieRecommender);
		movieRecommender.store();

		MovieRecommenderAPI movieRecommender2 = new MovieRecommenderAPI(serializer);
		movieRecommender2.load();

		assertEquals (movieRecommender.getUsers().size(), movieRecommender2.getUsers().size());
		for (User user : movieRecommender.getUsers())
		{
			assertTrue (movieRecommender2.getUsers().contains(user));
		}


		deleteFile ("testdatastore.xml");
	}

	@Test
	public void testUserSerializastion() throws Exception
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
		
		//create new movierecommender2 to test the file has been loaded successfully
		MovieRecommenderAPI movieRecommender2 =  new MovieRecommenderAPI(serializer);
		movieRecommender2.load();
		//makes sure 2nd movieRecommender matches the first movieRecommender
		assertEquals (movieRecommender.getUsers().size(), movieRecommender2.getUsers().size());
		for (User user : movieRecommender.getUsers())
		{
			assertTrue (movieRecommender2.getUsers().contains(user));
		}
		
		//adds a new user to moveiRecommender from the fixtures class
		movieRecommender.createUser("dean","gaffney",19,"m","mechanic");
		//store the data now with the old data + one new member
		movieRecommender.store();

		//they shouldn't equal each other movieRecommender should have one more user than movieRecommender2
		assertNotEquals(movieRecommender.getUsers().size(),movieRecommender2.getUsers().size());
		//load movieRecommender 2  so it has the new member and then test its now the same as the original movieRecommender
		movieRecommender2.load();
		assertEquals(movieRecommender2.getUsers().size(),movieRecommender.getUsers().size());
		//delete all the users from movieRecommender and make sure it doesn't equal moveRecommender2
		
		movieRecommender.deleteUsers();
		movieRecommender.store();
		assertNotEquals(movieRecommender.getUsers().size(),movieRecommender2.getUsers().size());
		//now delete all users from movieRecommender2 and make sure it now equals movieRecommender.
		movieRecommender2.deleteUsers();
		assertEquals(movieRecommender.getUsers().size(),movieRecommender2.getUsers().size());

		deleteFile ("testdatastore.xml");
	}

	@Test
	public void testMovieSerialization() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		Data data = new Data();

		//populates the movies list from the readable file
		List<Movie> movies = data.importMovies("data/items5.dat");
		for(Movie movie : movies)
		{
			movieRecommender.addMovie(movie);
		}
		movieRecommender.store();
		
		//loads movieRecommender2 with the new data and tests their equality
		MovieRecommenderAPI movieRecommender2 =  new MovieRecommenderAPI(serializer);
		movieRecommender2.load();
		assertEquals(movieRecommender.getMovies().size(),movieRecommender2.getMovies().size());
		//tests that the same movies have been loaded in the correct place
		for (Movie movie : movieRecommender.getMovies())
		{
			assertTrue (movieRecommender2.getMovies().contains(movie));
		}
		
		//add a new movie to movieRecommender along with existing file data
		movieRecommender.addMovie("deansMovie","1996","www.deansMovie.com");
		movieRecommender.store();
		//make sure they are not equal (movieRecommnder will have one more than movieRecommender2)
		assertNotEquals(movieRecommender.getMovies().size(),movieRecommender2.getMovies().size());
		//load new data into movieRecommender2
		movieRecommender2.load();
		//movieRecommender and movieRecommender 2 should now be equal
		assertEquals(movieRecommender.getMovies().size(),movieRecommender2.getMovies().size());
		
		//delete all movies then store new state
		movieRecommender.deleteMovies();
		movieRecommender.store();

		assertNotEquals(movieRecommender.getMovies().size(),movieRecommender2.getMovies().size());
		//now delete all users from movieRecommender2 and make sure it now equals movieRecommender.
		movieRecommender2.deleteMovies();
		movieRecommender2.store();
		assertEquals(movieRecommender.getMovies().size(),movieRecommender2.getMovies().size());


		deleteFile ("testdatastore.xml");
	}
}
