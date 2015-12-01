package models;

public class AverageRating implements Comparable<AverageRating> 
{
	public int averageRating;
	
	public AverageRating(int averageRating)
	{
		this.averageRating = averageRating;
	}

	@Override
	public int compareTo(AverageRating other)
	{
		if(this.averageRating < other.averageRating) return -1;
		if(this.averageRating > other.averageRating) return +1;
		
		return 0;
	}
}
