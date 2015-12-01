package models;

import utils.ToJsonString;

import com.google.common.base.Objects;

public class Rating implements Comparable<Rating>
{
	public int rating;
	public Long movieId;
	public Long userId;
	
	public Rating(Long userId,Long movieId,int rating)
	{
		this.movieId = movieId;
		this.userId = userId;
		setRating(this.rating);
		this.rating = rating;
	}
	
	// make sure they give an appropriate rating
	public void setRating(int rating)
	{
		if(rating >= -5 && rating <=5)
		{
			this.rating = rating;
		}
		else
		{
			System.out.println("This vaule is not in the rating system.Please rate between 0(very bad) and 5(Excellent)");
		}
	}
	
	public String toString()
	{
		return new ToJsonString(getClass(), this).toString();
	}

	@Override  
	public int hashCode()  
	{  
		return Objects.hashCode(this.rating);  
	}  

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof Rating)
		{
			final Rating other = (Rating) obj;
			return Objects.equal(rating,   other.rating) ;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int compareTo(Rating other) 
	{
		if(this.userId < other.userId)   return -1;
		if(this.userId > other.userId)   return +1;
		if(this.movieId < other.movieId) return -1;
		if(this.movieId > other.movieId) return +1;
		if(this.rating < other.rating) 	 return -1;
		if(this.rating > other.rating)   return +1;
		return 0;
	}
}
