package models;

import utils.ToJsonString;

public class AverageRating implements Comparable<AverageRating> 
{
	public Long movieId;
	public double averageRating;
	
	public AverageRating(Long movieId,double averageRating)
	{
		this.movieId=movieId;
		this.averageRating = averageRating;
	}

	@Override
	public int compareTo(AverageRating other)
	{
		if(this.averageRating < other.averageRating) return -1;
		if(this.averageRating > other.averageRating) return +1;
		
		return 0;
	}
	
	@Override
	public String toString()
	{
		return "Movie ID: " + movieId + "/n" + "Average Rating: " + averageRating;
	}
}
