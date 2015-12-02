package models;

import java.util.List;

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
			new Movie("algorithmsMovie","1980","www.algorithms.com"),
			new Movie("kevinsMind","1970","www.kevsmind.org"),
			new Movie("spinalTap","1983","www.spinaltap.com"),
			new Movie("unity3D","2007","www.unity.com"),
			new Movie("kung-fury","2015","www.kung-fury.com"),
			new Movie("nosferatu","1922","www.nosferatu.com"),
		};
	public static Rating[] ratings = 
		{
			new Rating(users[0].id,movies[0].id,3),
			new Rating(users[0].id,movies[0].id,4),
			new Rating(users[0].id,movies[0].id,-4),

			new Rating(users[1].id,movies[1].id,5),
			new Rating(users[1].id,movies[1].id,4),
			new Rating(users[1].id,movies[1].id,3),
			
			new Rating(users[1].id,movies[2].id,5),
			new Rating(users[1].id,movies[2].id,2),
			new Rating(users[1].id,movies[2].id,4),
			
			new Rating(users[1].id,movies[3].id,5),
			new Rating(users[1].id,movies[3].id,5),
			new Rating(users[1].id,movies[3].id,5),
			
			new Rating(users[1].id,movies[4].id,5),
			new Rating(users[1].id,movies[4].id,0),
			new Rating(users[1].id,movies[4].id,2),
			
			new Rating(users[1].id,movies[5].id,-4),
			new Rating(users[1].id,movies[5].id,-2),
			new Rating(users[1].id,movies[5].id,-1),
			
			new Rating(users[1].id,movies[6].id,1),
			new Rating(users[1].id,movies[6].id,-1),
			new Rating(users[1].id,movies[6].id,1),
			
			new Rating(users[1].id,movies[7].id,3),
			new Rating(users[1].id,movies[7].id,2),
			new Rating(users[1].id,movies[7].id,2),
			
			new Rating(users[1].id,movies[8].id,5),
			new Rating(users[1].id,movies[8].id,3),
			new Rating(users[1].id,movies[8].id,0),
			
			new Rating(users[1].id,movies[9].id,4),
			new Rating(users[1].id,movies[9].id,1),
			new Rating(users[1].id,movies[9].id,1),
			
		};
}