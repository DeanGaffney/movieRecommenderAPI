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
	//public so I can test that they are added
	public ArrayList<AverageRating> averageRatings = new ArrayList<>();

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
		System.out.println(userIndex.get(id));
		return userIndex.get(id);
	}

	public void deleteUser(Long id) 
	{
		System.out.println("User id: " + userIndex.get(id).id + "\n" + " First Name: "+ userIndex.get(id).firstName + " , has been deleted");
		userIndex.remove(id);
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

	public void deleteMovie(Long id)
	{
		System.out.println("Movie id: " + movieIndex.get(id).id + "\n" + " Title: "+ movieIndex.get(id).title + " , has been deleted");
		movieIndex.remove(id);
	}

	public void deleteMovies()
	{
		movieIndex.clear();
	}

	public Movie getMovie(Long movieID)
	{
		System.out.println(movieIndex.get(movieID));
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
		System.out.println(user.ratings);
		return user.ratings;
	}

	public Collection<Rating> getMovieRatings(Long movieId)
	{
		Movie movie = movieIndex.get(movieId);
		System.out.println(movie.ratings);
		return movie.ratings;
	}

	public ArrayList<AverageRating> topTenMovies()
	{
		//make two lists one to hold all average ratings for all movies
		//the other will contain a sub list of the allAverageRatings which will return just the last ten movies of the sorted list.
		ArrayList<AverageRating> topTenMovies = new ArrayList<>();
		ArrayList<AverageRating> allAverageRatings = new ArrayList<>();

		//add an average rating to every movie in the database
		for(Movie movie : getMovies())
		{
			//movie has to have more than five ratings to be in top 10
			//making this check because if a movie only has 1 or 2 ratings the average isn't going to be very accurate.
			if(movie.ratings.size() > 50)
			{
				allAverageRatings.add(new AverageRating(movie.id,movie.averageRating()));
			}
		}

		//sort them based on the highest average ratings ---> using average rating comparable interface.
		Collections.sort(allAverageRatings);

		//make topTenMovies a new array list which is a sublist of the last ten movies in allAverageRatings list. This will be top 10.
		topTenMovies = new ArrayList<AverageRating>(allAverageRatings.subList(allAverageRatings.size()-10, allAverageRatings.size()));

		//print the movies to the user. No need to sort this as it was previously sorted in allAverageRatings.
		System.out.println(topTenMovies);

		return topTenMovies;
	}

	public ArrayList<Movie> recommendMovies(Long userId)
	{

		ArrayList<Movie>recommendedMovies = new ArrayList<>();
		User user = getUser(userId);
		/* I need to be able to get all of the users ratings and check
		 * the given users ratings against all other user ratings.
		 * I should apply the cross product with the given users ratings
		 * against all others user ratings for likeliness
		 */

		//if it's a new user and they have never rated before just recommend top ten movies.
		if(user.ratings.size() == 0)
		{
			topTenMovies();
		}


		for(int i = 0;i < user.ratings.size()-1;i++)
		{
			Long userMovieId = user.ratings.get(i).movieId;
			int userRating = user.ratings.get(i).rating;

			for(Long j = 1l; j < getUsers().size()-1;j++)
			{
				User comparingUser = getUser(j);
				if(comparingUser.id!=user.id)
				{
					int similarity = compareUserRatings(user,comparingUser);
					if(similarity > 0 && similarity <=5)
					{
						for(Rating rating : comparingUser.ratings)
						{
							if(!user.ratings.contains(rating.movieId))
							{
								recommendedMovies.add(movieIndex.get(rating.movieId));
							}
						}
					}
				}
			}

		}
		System.out.println(recommendedMovies);
		return recommendedMovies;
	}

	//this method will return a similarity rating between two users
	// i will use this for my recommendations method
	public int compareUserRatings(User user1,User user2)
	{
		//use this to rate users on their similarity
		int usersSimilarity = 0;
		int ratingDifference = 0;
		int differenceRange = 1;
		// will set true if they are within similarity range
		boolean similar = false;

		//will need both users ratings to compare them and find similarity
		ArrayList<Rating> user1Ratings = new ArrayList<>();
		ArrayList<Rating> user2Ratings = new ArrayList<>();

		//populate array with user1 ratings
		for(Rating rating : user1.ratings)
		{
			user1Ratings.add(new Rating(rating.userId,rating.movieId,rating.rating));
		}

		//populate array with user2 ratings
		for(Rating rating : user2.ratings)
		{
			user2Ratings.add(new Rating(rating.userId,rating.movieId,rating.rating));
		}

		//sort both arrays based on highest rating.
		Collections.sort(user1Ratings);
		Collections.sort(user2Ratings);

		//go through each rating that user1 has so we can compare them with user2
		for(Rating rating : user1Ratings)
		{
			//get the moveId from the rating to make sure they compare same movies
			Long ratingMovieId = rating.movieId;

			for(int i = 0; i < user2.ratings.size();i++)
			{
				Long comparingMovieId = user2.ratings.get(i).movieId;
				if(ratingMovieId == comparingMovieId)
				{
					ratingDifference = rating.rating - user2.ratings.get(i).rating;
					if(ratingDifference <= 1)
					{
						similar = true;
						usersSimilarity +=5;
					}
					else if(ratingDifference > 1 && ratingDifference <=2)
					{
						similar = true;
						usersSimilarity +=4;
					}
					else if(ratingDifference > 2 && ratingDifference <=3)
					{
						similar = true;
						usersSimilarity +=3;
					}
					else if(ratingDifference > 3 && ratingDifference <=4)
					{
						similar = true;
						usersSimilarity +=2;
					}
					else if(ratingDifference > 4 && ratingDifference <=5)
					{
						similar = true;
						usersSimilarity +=1;
					}
					else if(ratingDifference > 5)
					{
						usersSimilarity = 0;
					}
				}
			}
		}
		return usersSimilarity;

	}
}



