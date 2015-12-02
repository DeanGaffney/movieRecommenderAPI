package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import models.AverageRating;
import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;

public class MovieRecommenderAPI 
{
	private Serializer serializer;

	private Map<Long,User>   userIndex = new HashMap<>();
	private Map<Long, Movie> movieIndex = new HashMap<>();
	//private Map<Long, Rating> ratingIndex = new HashMap<>();
	private ArrayList<AverageRating> averageRatings = new ArrayList<>();

	public MovieRecommenderAPI()
	{}

	public MovieRecommenderAPI(Serializer serializer)
	{
		this.serializer = serializer;
	}
	@SuppressWarnings("unchecked")
	public void load() throws Exception
	{
		serializer.read();
		Movie.counter = (Long) serializer.pop();
		movieIndex = (Map<Long, Movie>) serializer.pop();

		User.counter = (Long) serializer.pop();
		userIndex = (Map<Long, User>)	serializer.pop();
	}

	public void store() throws Exception
	{
		serializer.push(userIndex);
		serializer.push(User.counter);
		serializer.push(movieIndex);
		serializer.push(Movie.counter);
		serializer.write(); 
	}

	public Collection<User> getUsers ()
	{
		return userIndex.values();
	}

	public  void deleteUsers() 
	{
		userIndex.clear();
	}

	public void createUser(User user)
	{
		userIndex.put(user.id, user);
	}

	public void createUser(String firstName, String lastName, int age, String gender, String occupation)
	{
		User user = new User (firstName, lastName, age, gender, occupation);
		userIndex.put(user.id, user);
	}


	public User getUser(Long id) 
	{
		return userIndex.get(id);
	}

	public void deleteUser(Long id) 
	{
		System.out.println("User id: " + userIndex.get(id).id + " First Name: "+ userIndex.get(id).firstName + " has been deleted");
		User user = userIndex.remove(id);
	}

	public void addMovie(String title, String year, String url)
	{
		Movie movie = new Movie (title, year, url);
		movieIndex.put(movie.id, movie);
	}
	
	public void addMovie(Movie movie)
	{
		movieIndex.put(movie.id, movie);
	}

	public Collection<Movie> getMovies ()
	{
		return movieIndex.values();
	}
	
	public void deleteMovies()
	{
		movieIndex.clear();
	}

	public Movie getMovie(Long movieID)
	{
		return movieIndex.get(movieID);
	}
	
	public void addRating(Long userId,Long movieId,int rating)
	{
		Rating userRating = new Rating(userId,movieId,rating);
		
		User user = userIndex.get(userId);
		user.ratings.add(userRating);
		
		Movie movie = movieIndex.get(movieId);
		movie.ratings.add(userRating);
	}
	
	//used for reading in from file to add an entire object.
	public void addFileRating(Rating rating)
	{
		Long userId = rating.userId;
		User user = getUser(userId);
		user.ratings.add(rating);
		
		Long movieId = rating.movieId;
		Movie movie = movieIndex.get(movieId);
		movie.ratings.add(rating);
	}

	public Collection<Rating> getUserRatings(Long userID)
	{
		User user = userIndex.get(userID);
		return user.ratings;
	}
	
	public Collection<Rating> getMovieRatings(Long movieId)
	{
		Movie movie = movieIndex.get(movieId);
		return movie.ratings;
	}
	
	public ArrayList<AverageRating> topTenMovies()
	{
		ArrayList<AverageRating> topTenMovies = new ArrayList<>();
		ArrayList<AverageRating> allAverageRatings = new ArrayList<>();
				
		
		for(Movie movie : getMovies())
		{
			allAverageRatings.add(new AverageRating(movie.id,movie.averageRating()));
		}
		System.out.println(allAverageRatings);
		Collections.sort(allAverageRatings);
		System.out.println(allAverageRatings);

		for(int i = allAverageRatings.size()/-10; i<allAverageRatings.size();i++)
		{
			topTenMovies.add(allAverageRatings.get(i));
		}
		Collections.sort(topTenMovies);
		System.out.println(topTenMovies);
		
		return topTenMovies;
	}
}



