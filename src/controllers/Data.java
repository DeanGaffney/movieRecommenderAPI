package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;
import utils.XMLSerializer;
import edu.princeton.cs.introcs.In;

public class Data 
{
	private File usersFile = new File("data.xml");

	public Data()
	{

	}
	public List<User> importUsers(String fileName) throws Exception
	{
		List <User> users = new ArrayList<User>();

		In inUsers = new In(fileName);

		//each field is separated(delimited) by a '|'
		String delims = "[|]";
		while (!inUsers.isEmpty()) 
		{
			// get user and rating from data source
			String userDetails = inUsers.readLine();

			// parse user details string
			String[] userTokens = userDetails.split(delims);
			users.add(new User(Long.parseLong(userTokens[0]),userTokens[1],userTokens[2],Integer.parseInt(userTokens[3]),userTokens[4],userTokens[5]));			// output user data to console.
			if (users!=null) 
			{
				System.out.println(users.size());
			}
			else
			{
				throw new Exception("Invalid member length: "+userTokens.length);
			}

		}
		/*for(int i = 0;i<users.size();i++)
		{
			System.out.println(users.get(i));
		}*/
		return users;
	}

	public List<Movie> importMovies(String fileName) throws Exception
	{
		List <Movie> movies = new ArrayList<Movie>();

		In inUsers = new In(fileName);

		//each field is separated(delimited) by a '|'
		String delims = "[|]";
		while (!inUsers.isEmpty()) 
		{
			// get user and rating from data source
			String movieDetails = inUsers.readLine();

			// parse user details string
			String[] movieTokens = movieDetails.split(delims);
			movies.add(new Movie(Long.parseLong(movieTokens[0]),movieTokens[1],movieTokens[2],movieTokens[3]));			// output user data to console.
			if (movies!=null) 
			{
				System.out.println(movies.size());
			}
			else
			{
				throw new Exception("Invalid member length: "+movieTokens.length);
			}

		}
		/*for(int i = 0;i<movies.size();i++)
		{
			System.out.println(movies.get(i));
		}*/
		return movies;	
	}
	
	public List<Rating> importRatings(String fileName) throws Exception
	{
		List <Rating> ratings = new ArrayList<Rating>();

		In inUsers = new In(fileName);

		//each field is separated(delimited) by a '|'
		String delims = "[|]";
		while (!inUsers.isEmpty()) 
		{
			// get user and rating from data source
			String ratingDetails = inUsers.readLine();

			// parse user details string
			String[] ratingTokens = ratingDetails.split(delims);
			ratings.add(new Rating(Long.parseLong(ratingTokens[0]),Long.parseLong(ratingTokens[1]),Integer.parseInt(ratingTokens[2])));			// output user data to console.
			if (ratings!=null) 
			{
				System.out.println(ratings.size());
			}
			else
			{
				throw new Exception("Invalid member length: "+ratingTokens.length);
			}

		}
		/*for(int i = 0;i<ratings.size();i++)
		{
			System.out.println(ratings.get(i));
		}*/
		return ratings;	
	}
	
}

