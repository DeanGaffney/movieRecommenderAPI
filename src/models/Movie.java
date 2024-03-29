package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;


public class Movie implements Comparable<Movie> 
{
	public static Long counter = 1l;

	public Long id;

	public String title;
	public String year;
	public int fixtureYear;
	public String url;

	public List<Rating> ratings = new ArrayList<>();

	//used for console input
	public Movie(String title, String year, String url) throws Exception
	{
		if(title.isEmpty())
		{
			throw new Exception("You must enter a title for the movie!");
		}
		if(year.isEmpty())
		{
			throw new Exception("You must enter a year for the movie!");
		}
		if(url.isEmpty())
		{
			throw new Exception("You must enter a url for the movie!");
		}
		this.id = counter++;
		this.title = title;
		this.year = year;
		this.url = url;
	}

	//used for reading in from file/this constructor is also used for fixtures because fixtures cant handle exceptions.
	public Movie(Long id, String title, String year, String url) 
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.url = url;

		counter++;
	}
	
	/*//this constructor is used for fixtures because it can't handle exceptions
	public Movie(String fixtureTitle, int fixtureYear, String fixtureUrl) 
	{
		this.title = fixtureTitle;
		this.fixtureYear = fixtureYear;
		this.url = fixtureUrl;

		this.id = counter++;
	}*/

	public double averageRating()
	{
		double averageMovieRating = 0;
		double sum = 0;

		for(int i = 0;i<ratings.size();i++)
		{
			sum += ratings.get(i).rating;
		}
		averageMovieRating = sum / ratings.size();
		
		return averageMovieRating;

	}
	
	//returns all movie ratings.
	private List<Rating> getRatings()
	{
		return ratings;
	}


	@Override
	public String toString()
	{
		return "ID: " + id + "\n" + "Title: " + title + "\n" + "Year: " + year + "\n" +
				"URL: " + url + "\n" + "No. of Ratings " + ratings.size() + "\n" + "\n";
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.id, this.title, this.year, this.url);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof Movie)
		{
			final Movie other = (Movie) obj;
			return Objects.equal(title, other.title)
					&& Objects.equal(year, other.year)
					&& Objects.equal(url, other.url);
		}
		else
		{
			return false;
		}
	}

	@Override
	public int compareTo(Movie that) 
	{
		for(Rating thisMovie : getRatings())
		{
			for(Rating thatMovie : that.getRatings())
			{
				if(thisMovie.rating < thatMovie.rating) return -1;
				if(thisMovie.rating < thatMovie.rating) return +1;
			}
		}
		
		return 0;
	}
}