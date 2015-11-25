package models;

public class Fixtures
{
	public static User[] users =
		{
			new User ("dean", "gaffney", 19, "m",  "mechanic"),
			new User ("dave",  "mustaine", 19, "m",   "guitarist"),
			new User ("orlaith",  "daly", 20, "f",   "hairdresser"),
			new User ("millie","daly", 1, "f", "dog")
		};

	public static Movie[] movies =
		{
			new Movie("deansMovie", "1996","www.dean.com"),
			new Movie("davesMovie", "2000","www.dave.com"),
			new Movie("orlaithsMovie", "1960","www.orlaith.com"),
			new Movie("milliesMovie", "1995","www.millie.com"),
		};
	public static Rating[] ratings = 
		{
			new Rating(users[0].id,movies[0].id,3),
			new Rating(users[0].id,movies[1].id,0),
			new Rating(users[1].id,movies[2].id,5),
			new Rating(users[1].id,movies[3].id,2),
		};
}