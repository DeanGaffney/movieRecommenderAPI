package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;

public class MovieRecommenderAPI 
{
	private Serializer serializer;

	private Map<Long,User>   userIndex = new HashMap<>();
	private Map<Long, Movie> movieIndex = new HashMap<>();

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

	public User createUser(User user)
	{
		userIndex.put(user.id, user);
		return user;
	}


	public User createUser(String firstName, String lastName, int age, String gender, String occupation)
	{
		User user = new User (firstName, lastName, age, gender, occupation);
		userIndex.put(user.id, user);
		return user;
	}


	public User getUser(Long id) 
	{
		System.out.println(userIndex.get(id));
		return userIndex.get(id);
	}

	public void deleteUser(Long id) 
	{
		System.out.println("User id: " + userIndex.get(id).id + " First Name: "+ userIndex.get(id).firstName + " has been deleted");
		User user = userIndex.remove(id);
	}

	public Movie addMovie(String title, String year, String url)
	{
		Movie movie = new Movie (title, year, url);
		movieIndex.put(movie.id, movie);
		return movie;
	}
	
	public Movie addMovie(Movie movie)
	{
		movieIndex.put(movie.id, movie);
		return movie;
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

	public Collection<Rating> getUserRatings(Long userID)
	{
		User user = userIndex.get(userID);
		return user.ratings;
	}
}



