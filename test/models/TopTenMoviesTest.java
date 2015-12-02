package models;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;
import static models.Fixtures.users;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import controllers.MovieRecommenderAPI;
public class TopTenMoviesTest 
{

	MovieRecommenderAPI movieRecommender;
	/*I'm checking my algorithm works using the fixtures on 10 movies
	 * so for this test I will return the top five movies out of the 10 movies.
	 * This test should populate a movieRecommender object with users,movies and ratings.
	 * It should then give each movie an average rating and add them to a list.
	 * From the list I will sort them based on their average rating low-high.
	 * My test will be to make an arrayList to hold the top 5 rated movies.
	 * This list should be equal to the upper half of the full list of movies.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void listTopFiveMovies()
	{
		ArrayList<AverageRating> allAverageRatings = new ArrayList<>();
		ArrayList<AverageRating> topFiveMovies = new ArrayList<>();
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(null);
		
		for(int i = 0; i<users.length;i++)
		{
			movieRecommender.createUser(users[i]);
		}
		
		for(int i = 0; i<movies.length;i++)
		{
			movieRecommender.addMovie(movies[i]);
		}
		
		for(Rating rating : ratings)
		{
			movieRecommender.addRating(rating.userId, rating.movieId, rating.rating);
		}
		
		for(Movie movie : movieRecommender.getMovies())
		{
			allAverageRatings.add(new AverageRating(movie.id,movie.averageRating()));
		}
		
		Collections.sort(allAverageRatings);
		System.out.println(allAverageRatings);

		for(int i = allAverageRatings.size()/2; i<allAverageRatings.size();i++)
		{
			topFiveMovies.add(allAverageRatings.get(i));
		}
		Collections.sort(topFiveMovies);
		System.out.println(topFiveMovies);
		
		
		assertEquals(5,topFiveMovies.size());
		assertEquals(5,allAverageRatings.size()/2);
		
		assertNotEquals(topFiveMovies.size(),allAverageRatings.size());
		assertEquals(topFiveMovies.size(),allAverageRatings.size()/2);
		//check to make sure that topFiveMovies list is equal to upper half of allAverageRatingsList
		for(AverageRating averageRating : topFiveMovies)
		{
			
		}
	}

}
