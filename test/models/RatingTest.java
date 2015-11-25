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
import controllers.MovieRecommenderAPI;
import edu.princeton.cs.introcs.In;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;
import static models.Fixtures.users;

public class RatingTest 
{
	Rating userRating = new Rating(users[0].id,movies[0].id,3);
	MovieRecommenderAPI movieRecommender;
	
	@Test
	public void testCreate()
	{
		//movieRecommender.addRating(users[0].id, movies[0].id, rating);
		
		assertEquals ("dean",                		users[0].firstName);
		assertEquals ("deansMovie",             movies[0].title);
		assertEquals (3,              			userRating.rating);   
	}

	

	@Test
	public void testToString()
	{
		assertEquals(userRating.toString(),userRating.toString());
	}


	@Test
	public void testEquals()
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
}
