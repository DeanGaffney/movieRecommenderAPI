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
			new Movie("paulsMovie", "2000","www.paul.com"),
			new Movie("sandrasMovie", "1960","www.sandra.com"),
			new Movie("orlaithsMovie", "1995","www.orlaith.com"),
		};
}