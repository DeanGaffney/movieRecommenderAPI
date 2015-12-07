package models;
import static org.junit.Assert.*;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;
import static models.Fixtures.users;

import java.io.File;
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
public class MovieTest 
{

	Movie movie = new Movie ("deansMovie", 1996,"www.dean.com");
	MovieRecommenderAPI movieRecommender;
	Data data;
	
	@Rule 
	public final ExpectedException exception = ExpectedException.none();
	//make a simple function to return the average of a movie
	double getAverageRating(Movie movie)
	{
		double averageMovieRating = 0;
		double sum = 0;

		for(int i = 0;i<movie.ratings.size();i++)
		{
			sum += movie.ratings.get(i).rating;
		}
		averageMovieRating = sum / movie.ratings.size();
		
		return averageMovieRating;
	}
	
	
	@Test
	public void testCreate()
	{
		assertEquals ("deansMovie",                movie.title);
		assertEquals (1996,             		   movie.year);
		assertEquals ("www.dean.com",              movie.url);   
	}
	
	@Test 
	public void blankTitle() throws Exception
	{
		exception.expect(Exception.class);
		new Movie("","1990","www.blankTitle");
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
		assertEquals("ID: " + movie.id + "\n" + "Title: " + movie.title + "\n" + "Year: " + movie.year + "\n" +
				"URL: " + movie.url + "\n" + "No. of Ratings " + movie.ratings.size() + "\n" + "\n",movie.toString());
	}


	@Test
	public void testEquals()
	{
		Movie movie2 = new Movie ("deansMovie", 1996,"www.dean.com"); 
		Movie movie3  = new Movie ("algorithmsMovie", 2015,"www.algorithms.com"); 

		assertEquals(movie, movie);
		assertEquals(movie, movie2);
		assertNotEquals(movie, movie3);
	} 

	@Test
	public void getMovieById() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);

		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}
		movieRecommender.store();

		//loads movieRecommender2 with the new data and tests their equality
		MovieRecommenderAPI movieRecommender2 =  new MovieRecommenderAPI(serializer);
		movieRecommender2.load();

		/*the for loop will go through each user in movieRecommender
		 * and will get the id's of each user. It will then test the movieRecommender
		 * id's against the id's of movieRecommender2 assuring that the getUser(id) function
		 * is working correctly.
		 */
		for (Movie movie : movieRecommender.getMovies())
		{
			assertEquals(movie.id,movieRecommender2.getMovie(movie.id).id);
		}
	}
	
	@Test
	public void addMovie() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		MovieRecommenderAPI movieRecommender2 = new MovieRecommenderAPI(serializer);
		
		//put movies from fixtures into movieRecommender and store them.
		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}
		
		//make sure that they were added successfully by checking the size of movieRecommender against fixtures.
		movieRecommender.store();
		assertEquals(movieRecommender.getMovies().size(),movies.length);
		
		//load movieRecommender 2 and check the size against movieRecommender
		movieRecommender2.load();
		assertEquals(movieRecommender.getMovies().size(),movieRecommender2.getMovies().size());
		
		/*If the next test passes it is safe to say that every movie in
		 * movieRecommender2 contains the same movies as movieRecommender,
		 * meaning that the movies were successfully added in both 
		 * movieRecommender and movieRecommender2.
		 */
		for(Movie movie : movieRecommender.getMovies())
		{
			assertTrue(movieRecommender2.getMovies().contains(movie));
		}
	}
	
	@Test
	public void deleteMovieByID() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		MovieRecommenderAPI movieRecommender2 = new MovieRecommenderAPI(serializer);
		
		//always make sure I am starting with blank movieRecommender objects
		assertEquals(0,movieRecommender.getMovies().size());
		assertEquals(0,movieRecommender2.getMovies().size());

		
		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}
		
		movieRecommender.store();
		
		movieRecommender2.load();
		
		movieRecommender.deleteMovie(2l);
		
		assertNotEquals(movieRecommender.getMovies().size(),movieRecommender2.getMovies().size());
		
		movieRecommender2.deleteMovie(3l);
		
		assertEquals(movieRecommender.getMovies().size(),movieRecommender2.getMovies().size());
		
	}
	@SuppressWarnings("deprecation")
	@Test
	public void averageRatingTest() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);

		for(int i = 0; i<users.length;i++)
		{
			movieRecommender.createUser(users[i]);
		}

		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}

		for(int i =0; i < ratings.length ;i++)
		{
			movieRecommender.addFileRating(ratings[i]);
		}
		

		movieRecommender.store();
		
		//this result in average rating of 1.0 when printed out.
		System.out.println(movieRecommender.getMovie(2l).averageRating());
		
		//make sure our movieRecommender supplies the same result
		assertEquals(1.0,movieRecommender.getMovie(2l).averageRating(),.1);
		
		//now make sure our local method and movieRecommender return the same averages for movies.
		assertEquals(movieRecommender.getMovie(2l).averageRating(),getAverageRating(movieRecommender.getMovie(2l)),.1);
		assertNotEquals(movieRecommender.getMovie(2l).averageRating(),getAverageRating(movieRecommender.getMovie(3l)),.1);
		
		//now test the adding of the averageRatings to the movieRecommender averageRatings list.
		assertEquals(0,movieRecommender.averageRatings.size());
		movieRecommender.averageRatings.add(new AverageRating(movieRecommender.getMovie(2l).id,movieRecommender.getMovie(2l).averageRating()));
		assertEquals(1,movieRecommender.averageRatings.size());
	}
}



