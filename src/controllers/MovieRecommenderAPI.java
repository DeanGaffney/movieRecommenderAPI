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

	public void addMovie(String title, String year, String url) throws Exception
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

	public void addRating(Long userId,Long movieId,int rating) throws Exception
	{
		Rating userRating = new Rating(userId,movieId,rating);

		User user = userIndex.get(userId);
		user.ratings.add(userRating);

		Movie movie = movieIndex.get(movieId);
		movie.ratings.add(userRating);
	}

	//used for reading in from file to add an entire object.
	public void addFileRating(Rating rating)throws Exception
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

	/*This algorithm is going to be based off the 'Neighbourhood' or nth nearest neighbour algorithm'.
	 * The idea is to make a neighbourhood (list) of neighbours(users) which are similar to our user.
	 * If another user is similar to our 'active user' we add them to  our neighbourhood'. This will result in 
	 * a list of other users which are similar to our user in question . Then I can go through each neighbour
	 * in the neighbourhood and check their ratings. If the user in question hasn't rated a movie then recommend that movie.
	 */
	public ArrayList<Movie> recommendMovies(Long userId)
	{
		
		User user = userIndex.get(userId);
		if(user.ratings.size() == 0)
		{
			
		}
		
		//Creates a neighbourhood of people who are similar to our user
		ArrayList<User> neighbourhood = createNeighbourhood(user,1); 
		
		//I then want to generate a list of recommended movies based off my neighbourhood.
		ArrayList<Movie> recommendations = createRecommendationFromNeighbourhood (neighbourhood, user);

		System.out.println(recommendations);
		return recommendations;

	}

	private ArrayList<Movie> createRecommendationFromNeighbourhood(ArrayList<User> neighbourhood, User user) 
	{
		ArrayList<Movie> neighbourhoodRecommendations = new ArrayList<>();
		//loop over all the people in our neighbourhood
		for(User neighbour : neighbourhood)
		{
			//if user has rated movie ---> write a method here that returns true if a user has rated that movie.
			// go through all ratings for each user in the neighbourhood
			for(Rating neighbourRating : neighbour.ratings)
			{
				//if the user hasn't rated the movie we assume they haven't seen it and recommend it to them
				if(!user.hasRated(neighbourRating.movieId))
				{
					neighbourhoodRecommendations.add(movieIndex.get(neighbourRating.movieId));
				}
			}
		}
		
		return neighbourhoodRecommendations;
	}

	/*our neighbourhood is created by looping through all users and adding similar users
	 * into our neighbourhood.
	 */
	private ArrayList<User> createNeighbourhood(User activeUser,double similarityThreshold)
	{
		ArrayList<User> neighbourhood = new ArrayList<>();
		for(User comparingUser : getUsers())
		{
			if(comparingUser.id != activeUser.id)
			{
				// Inside here I want to return some number that represents a similarity between the two users
				double similarity = calculateSimilarity(activeUser,comparingUser);//write this method

				/*this will be the average difference between all movies rated by the users.
				 * if this is the case add them to the neighbourhood. However a problem may arise here,
				 * depending on what I make the threshold value be it could be the case that nobody
				 * fits into this threshold value and therefore my 'neighbourhood' could have 0 users in it.
				 */
				if(similarity < similarityThreshold) 
				{
					neighbourhood.add(comparingUser);
				}
			}
		}
		return neighbourhood;
	}

	// this method will calculate the similarity between two users and determine.
	// I will user to determine if a user is similar enough to be apart of the neighbourhood.
	private double calculateSimilarity(User activeUser, User comparingUser) 
	{
		//keeps track of the same movies they have rated
		int moviesInCommon = 0;
		//this will act as a guide to determine how similar they are by the difference between their ratings.
		double ratingDifference = 0;
		
		/*go through our active users ratings
		 * and compare them with our comparingUsers ratings
		 * if the movieId is the same then they must have rated the same movie
		 * so I will then find the difference between ratings and see how similar they are with
		 * a threshold value.
		 */
		for(Rating rating1 : activeUser.getRatings())
		{
			for(Rating rating2 : comparingUser.getRatings())
			{
				Long movieId1 = rating1.movieId;
				Movie movie1 = movieIndex.get(movieId1);
				
				Long movieId2 = rating2.movieId;
				Movie movie2 = movieIndex.get(movieId2);
				
				if(movie1.id == movie2.id)
				{
					moviesInCommon ++;
					ratingDifference += Math.abs(rating1.rating - rating2.rating);
					
				}
			}
		}
		if(moviesInCommon > 0)
		{
			//this will give me back the average ratings over all movies rated by both users.
			return ratingDifference / moviesInCommon;
		}
		//if they have no movies in common I will return a large difference which will indicate they are definitely not in similar.
		return Integer.MAX_VALUE;
	}
}



