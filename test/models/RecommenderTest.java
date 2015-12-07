package models;

import java.util.ArrayList;

import org.junit.Test;

import controllers.MovieRecommenderAPI;
import static models.Fixtures.movies;
import static models.Fixtures.users;
import static models.Fixtures.recommenderRatings;
import static org.junit.Assert.*;


public class RecommenderTest 
{
	MovieRecommenderAPI movieRecommender;
	
	/*Throughout all of the following tests they will test all methods needed to 
	 * recommend a movie to a given user. They will test that the correct similarity is calculated,
	 * the correct users are added to the 'neighbourhood' (see movieRecommenderAPI for details on neighbourhood),
	 * and it will finally test that with the correct users the correct movies are recommended to the user.
	 */
	
	@Test
	public void testCalculateSimilarity() throws Exception
	{
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(null);
		
		for(int i = 0; i<users.length;i++)
		{
			movieRecommender.createUser(users[i]);
		}

		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}

		for(Rating rating : recommenderRatings)
		{
			movieRecommender.addRating(rating.userId, rating.movieId, rating.rating);
		}

		System.out.println(movieRecommender.getUser(1l));
		movieRecommender.calculateSimilarity(movieRecommender.getUser(1l),movieRecommender.getUser(2l));
		System.out.println(movieRecommender.calculateSimilarity(movieRecommender.getUser(1l),movieRecommender.getUser(2l)));
		assertEquals(movieRecommender.calculateSimilarity(movieRecommender.getUser(1l),movieRecommender.getUser(2l)),movieRecommender.calculateSimilarity(movieRecommender.getUser(1l),movieRecommender.getUser(2l)),.10000);

	}
	
	@Test
	public void testNeighbourhood() throws Exception
	{
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(null);
		
		for(int i = 0; i<users.length;i++)
		{
			movieRecommender.createUser(users[i]);
		}

		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}

		for(Rating rating : recommenderRatings)
		{
			movieRecommender.addRating(rating.userId, rating.movieId, rating.rating);
		}

		System.out.println(movieRecommender.getUser(1l));
		
		
		/*according to the fixtures the user 'Dave Mustaine' should
		 * be similar enough to the fixtures user 'Dean Gaffney' 
		 * 'Dave Mustaine' should be added to Dean Gaffneys neighbourhood.
		 * due to the small data of fixtures I am using a high threshold value
		 * of 3 for my similarity threshold.
		 */
		
		movieRecommender.createNeighbourhood(movieRecommender.getUser(1l), 3);
		System.out.println(movieRecommender.createNeighbourhood(movieRecommender.getUser(1l), 3));
		
		//this test makes sure that when this method is called it will only contain the similar user 'Dave Mustaine' or getUser(2l)
		assertTrue(movieRecommender.createNeighbourhood(movieRecommender.getUser(1l),3).contains(movieRecommender.getUser(2l)));
		
		//go through the rest of the users and make sure they aren't involved in the similarity.
		for(Long i = 3l; i < movieRecommender.getUsers().size();i++)
		{
			assertFalse(movieRecommender.createNeighbourhood(movieRecommender.getUser(1l),3).contains(movieRecommender.getUser(i)));
		}
	}
	
	@Test
	public void testCreateRecommendationFromNeighbourhood() throws Exception
	{
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(null);
		ArrayList<Movie> recommendedMovies = new ArrayList<>();
		ArrayList<User> neighbourhood = new ArrayList<>();
		
		for(int i = 0; i<users.length;i++)
		{
			movieRecommender.createUser(users[i]);
		}

		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}

		for(Rating rating : recommenderRatings)
		{
			movieRecommender.addRating(rating.userId, rating.movieId, rating.rating);
		}
		
		//create a neighbourhood with threshold value 3.
		neighbourhood = movieRecommender.createNeighbourhood(movieRecommender.getUser(1l),3);
		
		System.out.println(movieRecommender.createRecommendationFromNeighbourhood(neighbourhood, movieRecommender.getUser(1l)));
		movieRecommender.createRecommendationFromNeighbourhood(neighbourhood, movieRecommender.getUser(1l));
		
		/*from the fixture set up the movies that 'Dave Mustaine' has seen which 'Dean Gaffney'
		 * hasn't seen is 'milliesMovie' and 'davesMovie or movies[3] and movies[1] in fixtures. Therefore my recommendation list should consist of
		 * 'nosferatu' and 'milliesMovie' and 'orlaithsMovie'. This is because my algorithm will suggest movies which the user hasn't seen
		 * but it will also only recommend a movie that has Not been seen that has a rating of 3 and above.
		 * Hence why not all movies from fixtures are recommended as only the ones that are rated 3 and above are recommended.
		 */
		assertTrue(movieRecommender.createRecommendationFromNeighbourhood(neighbourhood, movieRecommender.getUser(1l)).contains(movieRecommender.getMovie(4l)));
		assertTrue(movieRecommender.createRecommendationFromNeighbourhood(neighbourhood, movieRecommender.getUser(1l)).contains(movieRecommender.getMovie(3l)));
		assertTrue(movieRecommender.createRecommendationFromNeighbourhood(neighbourhood, movieRecommender.getUser(1l)).contains(movieRecommender.getMovie(10l)));

		assertFalse(movieRecommender.createRecommendationFromNeighbourhood(neighbourhood, movieRecommender.getUser(1l)).contains(movieRecommender.getMovie(2l)));

		
	}
}
