package models;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import controllers.Data;
import controllers.MovieRecommenderAPI;
import edu.princeton.cs.introcs.In;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;
import static models.Fixtures.users;

public class RatingTest 
{
	Rating userRating = new Rating(users[0].id,movies[0].id,3);
	MovieRecommenderAPI movieRecommender;
	Data data;

	@Test
	public void testCreate()
	{
		assertSame (1l,                		userRating.userId);
		assertSame (1l,             			userRating.movieId);
		assertEquals(3,              			userRating.rating);   
	}



	@Test
	public void testToString() throws Exception
	{
		assertEquals("User ID: " + userRating.userId + "\n" + "Movie ID: " + userRating.movieId + "\n" +
				"Rating: " + userRating.rating + "\n" + "\n",userRating.toString());
	}


	@Test
	public void testEquals() throws Exception
	{
		Rating userRating2 = new Rating (users[0].id,movies[0].id,3);
		Rating userRating3 = new Rating (users[2].id, movies[2].id,4); 

		assertEquals(userRating, userRating);
		assertEquals(userRating, userRating2);
		assertNotEquals(userRating, userRating3);
	} 


	@Test
	public void getUserRating()
	{
		movieRecommender = new MovieRecommenderAPI(null);
		//make 2 new users to add ratings to
		User dean = users[0];
		User millie = users[3];

		//create 2 ratings from FIXTURES to use for tests.
		Rating deansRating = ratings[0];
		Rating milliesRating = ratings[3];

		//add the ratings to the users ratings list
		dean.ratings.add(deansRating);
		millie.ratings.add(milliesRating);

		//see if the ratings are equal to the ratings that have been added to the users lists.
		assertEquals(deansRating,dean.ratings.get(0));
		assertEquals(milliesRating,millie.ratings.get(0));

		//make sure the separate ratings that were created do not equal each other
		assertNotEquals(deansRating,millie.ratings.get(0));
		assertNotEquals(milliesRating,dean.ratings.get(0));

	}

	@Test
	public void addRating() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		
		//always make sure I am starting with blank movieRecommender objects
		// especially in this case because you can't have a rating without both a movie and a user in this system.
		assertEquals(0,movieRecommender.getUsers().size());
		assertEquals(0,movieRecommender.getMovies().size());

		System.out.println(users[0].id);
		System.out.println(users[1].id);
		for(int i = 0; i<users.length;i++)
		{
			movieRecommender.createUser(users[i]);
		}
		System.out.println(movieRecommender.getUsers());

		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}
		System.out.println(movieRecommender.getMovies());

		for(Rating rating : ratings)
		{
			movieRecommender.addRating(rating.userId, rating.movieId, rating.rating);
		}

		movieRecommender.store();

		assertEquals(movieRecommender.getUsers().size(),users.length);
		assertEquals(movieRecommender.getMovies().size(),movies.length);

		/*There should now be 3 ratings according to the 'Fixtures' class added into users[0] ratings list.
		 * There should be 27 ratings within users[1]of the 'Fixtures' class also.
		 */
		assertEquals(3,users[0].ratings.size());
		assertEquals(27,users[1].ratings.size());
		
		//check and make sure a user can't rate below -5 or above 5.
		movieRecommender.addRating(1l,1l,-10);
		assertEquals(3,users[0].ratings.size());
		
		movieRecommender.store();


		MovieRecommenderAPI movieRecommender2 = new MovieRecommenderAPI(serializer);

		movieRecommender2.load();

		assertEquals(movieRecommender2.getUsers().size(),movieRecommender.getUsers().size());
		assertEquals(movieRecommender2.getMovies().size(),movieRecommender.getMovies().size());

		/* The movieRecommender objects access the ratings and check the ratings at the same position
		 * and the value of the rating itself within the users ratings.
		 * It would be safe to say that the rating has been successfully added to the correct position 
		 * with the correct rating, if this test passes.
		 */
		assertEquals(movieRecommender.getUser(1l).ratings.size(),movieRecommender2.getUser(1l).ratings.size());
		assertEquals(movieRecommender.getUser(1l).ratings.get(0).rating,movieRecommender2.getUser(1l).ratings.get(0).rating);
		
		/*must also check movies because movies have ratings also. Check the size of the ratings list for the movie
		 * and also check the actual value of the rating itself
		 */
		assertEquals(movieRecommender.getMovie(1l).ratings.size(),movieRecommender2.getMovie(1l).ratings.size());
		assertEquals(movieRecommender.getMovie(1l).ratings.get(0).rating,movieRecommender2.getMovie(1l).ratings.get(0).rating);
	}
}
