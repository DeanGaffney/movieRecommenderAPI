package models;


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
		return "User ID: " + userId + "\n" + "Movie ID: " + movieId + "\n" +
				"Rating: " + rating + "\n" + "\n";
	}


	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rating other = (Rating) obj;
		if (movieId == null) {
			if (other.movieId != null)
				return false;
		} else if (!movieId.equals(other.movieId))
			return false;
		if (rating != other.rating)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Rating that) 
	{
		if(this.rating < that.rating) return -1;
		if(this.rating > that.rating) return +1;
		
		return 0;
	}
}
