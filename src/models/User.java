package models;

import java.util.ArrayList;
import java.util.List;



import com.google.common.base.Objects;

public class User 
{
	public static Long counter = 1l;
	public Long id;
	public String firstName;
	public String lastName;
	public int age;
	public String gender;
	public String occupation;


	public List<Rating> ratings = new ArrayList<>();

	//used for file reading and fixtures (because fixtures cant handle exceptions).
	public User(Long id,String firstName, String lastName,int age,String gender,String occupation)
	{
		this.id        = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;

		counter++;
	}



	public User(String firstName, String lastName, int age, String gender,String occupation) throws Exception
	{
		if(firstName.isEmpty())
		{
			throw new Exception("You must enter a first name for your profile!");
		}
		if(lastName.isEmpty())
		{
			throw new Exception("You must enter a year for your profile!");
		}
		if(age > 120 || age < 0)
		{
			throw new Exception("You must enter a realistic number for your age");
		}
		if(gender.isEmpty())
		{
			throw new Exception("You must enter a gender for your profile!");
		}
		if(occupation.isEmpty())
		{
			throw new Exception("You must enter an occupation for your profile!");
		}

		this.id = counter++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
	}

	//returns all users ratings.
	public List<Rating> getRatings()
	{
		if(ratings.size()==0)
		{
			System.out.println("This user currently has no ratings!");
		}
		return ratings;
	}

	public boolean hasRated(Long movieId)
	{
		boolean hasRated = false;
		for(Rating rating : ratings)
		{
			if(rating.movieId == movieId)
			{
				hasRated =  true;
			}
			else
			{
				hasRated = false;
			}
		}
		return hasRated;
	}

	public String toString()
	{
		return "ID: " + id + "\n" + "First Name: " + firstName + "\n" + "Last Name: " + lastName + "\n" +
				"Occupation: " + occupation + "\n" + "Gender: " + gender + "\n" +
				"No. of Ratings " + ratings.size() + "\n" + "\n";
	}

	@Override  
	public int hashCode()  
	{  
		return Objects.hashCode(this.firstName, this.lastName,this.age,this.gender,this.occupation);  
	}  

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof User)
		{
			final User other = (User) obj;
			return Objects.equal(firstName,   other.firstName) 
					&&  Objects.equal(lastName,    other.lastName)
					&& Objects.equal(age,			other.age)
					&& Objects.equal(gender, other.gender)
					&&  Objects.equal(occupation,       other.occupation);
		}
		else
		{
			return false;
		}
	}
}
