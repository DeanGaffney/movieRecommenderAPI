package models;

import utils.ToJsonString;

import com.google.common.base.Objects;

public class Rating 
{
	public int rating;

	public Rating(User userId,Movie movieId,int rating)
	{
		setRating(rating);
	}

	public void setRating(int rating)
	{
		if(rating >= 0 && rating <=5)
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
}
