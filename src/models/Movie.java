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



	@Override
	public String toString()
	{
		return new ToJsonString(getClass(), this).toString();
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