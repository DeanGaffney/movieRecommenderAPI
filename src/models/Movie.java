package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import utils.ToJsonString;

public class Movie
{
	public static Long counter = 1l;

	public Long id;

	public String title;
	public String year;
	public String url;

	public List<Rating> ratings = new ArrayList<>();

	public Movie(String title, String year, String url)
	{
		this.id = counter++;
		this.title = title;
		this.year = year;
		this.url = url;
	}

	public Movie(Long id, String title, String year, String url) 
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.url = url;

		counter++;
	}

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
}