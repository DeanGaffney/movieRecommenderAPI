package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.princeton.cs.introcs.In;
import utils.Serializer;
import utils.XMLSerializer;
import models.Movie;
import models.Rating;
import models.User;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;

public class Main
{
	public MovieRecommenderAPI movieRecommender;
	public Data data = new Data();
	public Main() throws Exception
	{
		File datastore = new File("datastore.xml");
		Serializer serializer = new XMLSerializer(datastore);

		movieRecommender = new MovieRecommenderAPI(serializer);
		if (datastore.isFile())
		{
			movieRecommender.load();
		}
		else
		{
			List<User> users  = data.importUsers("data/users.dat");
			for(User user:users)
			{
				movieRecommender.createUser(user);
			}
			
			List<Movie> movies = data.importMovies("data/items.dat");
			for(Movie movie : movies)
			{
				movieRecommender.addMovie(movie);
			}
			
			List<Rating> ratings = data.importRatings("data/ratings.dat");
			for(Rating rating : ratings)
			{
				movieRecommender.addFileRating(rating);
			}
			movieRecommender.store();
		}
	}
	//returns all users and details of users
	@Command(description="Get all users details")
	public void getUsers ()
	{
		Collection<User> users = movieRecommender.getUsers();
		System.out.println(users);
	}
	//returns a user by id
	@Command(description="Get a user by id")
	public void getUser(@Param(name = "id")Long id)
	{
		movieRecommender.getUser(id);
	}

	@Command(description="Add a new User")
	public void addUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName,
			@Param(name="age") int age, @Param(name="gender") String gender, @Param(name="occupation") String occupation)
	{
		movieRecommender.createUser(firstName, lastName, age, gender, occupation);
	}

	@Command(description="Delete a User")
	public void removeUser (@Param(name="id") Long id)
	{
		movieRecommender.deleteUser(id);
	}
	
	@Command(description = "List all movies")
	public void getMovies()
	{
		Collection<Movie> movies = movieRecommender.getMovies();
		System.out.println(movies);
	}
	@Command(description="Add a Movie")
	public void addMovie (@Param(name="title") String title, @Param(name="year") String year, @Param(name="url") String url)
	{
		movieRecommender.addMovie(title, year, url);
	}
	
	@Command(description="Get a Movie's details")
	public void getMovie(@Param(name="movieId") Long movieId)
	{
		movieRecommender.getMovie(movieId);
	}
	
	@Command(description="Get a users Ratings")
	public void getUserRatings (@Param(name="userId")Long userId)
	{
		movieRecommender.getUserRatings(userId);
	}
	
	@Command(description="Get a movies Ratings")
	public void getMovieRatings (@Param(name="movieId") Long movieId)
	{
		movieRecommender.getMovieRatings(movieId);
	}
	
	@Command(description="Add a rating")
	public void addRating(@Param(name="userId") Long userId, @Param(name="movieId")Long movieId, @Param(name="rating") int rating)
	{
		movieRecommender.addRating(userId, movieId, rating);
	}
	
	@Command(description="List Top 10 Movies")
	public void listTopTenMovies()
	{
		movieRecommender.topTenMovies();
	}
	
	@Command(description="Recommend Movies")
	public void recommendMovies(@Param(name="userId") Long userId)
	{
		movieRecommender.recommendMovies(userId);
	}
	
	public static void main(String[] args) throws Exception
	{
		Main main = new Main();
		Shell shell = ShellFactory.createConsoleShell("lm", "Welcome to movie-recommender - ?help for instructions", main);
		shell.commandLoop();
		main.movieRecommender.store();
	}
}
