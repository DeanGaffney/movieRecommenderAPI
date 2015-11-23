package models;

import java.util.ArrayList;
import java.util.List;

import utils.ToJsonString;

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


	public User()
	{
	}

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



	public User(String firstName, String lastName, int age, String gender,String occupation) 
	{
		this.id = counter++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
	}

	public String toString()
	{
		return new ToJsonString(getClass(), this).toString();
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
					&&  Objects.equal(occupation,       other.occupation)
					&&	Objects.equal(ratings,			other.ratings);
		}
		else
		{
			return false;
		}
	}
}
