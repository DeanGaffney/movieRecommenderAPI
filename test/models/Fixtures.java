package models;

import java.util.List;

public class Fixtures 
{ 	
	public static User[] users =
		{
			new User (1l,"dean", "gaffney", 19, "m",  "mechanic"),
			new User (2l,"dave",  "mustaine", 19, "m",   "guitarist"),
			new User (3l,"orlaith",  "daly", 20, "f",   "hairdresser"),
			new User (4l,"millie","daly", 1, "f", "dog")
		};

	public static Movie[] movies =
		{
			new Movie(1l,"deansMovie", "1996","www.dean.com"),
			new Movie(2l,"davesMovie", "2000","www.dave.com"),
			new Movie(3l,"orlaithsMovie", "1960","www.orlaith.com"),
			new Movie(4l,"milliesMovie", "1995","www.millie.com"),
			new Movie(5l,"algorithmsMovie","1980","www.algorithms.com"),
			new Movie(6l,"kevinsMind","1970","www.kevsmind.org"),
			new Movie(7l,"spinalTap","1983","www.spinaltap.com"),
			new Movie(8l,"unity3D","2007","www.unity.com"),
			new Movie(9l,"kung-fury","2015","www.kung-fury.com"),
			new Movie(10l,"nosferatu","1922","www.nosferatu.com"),
		};
	public static Rating[] ratings  = 
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
	
	public static Rating[] recommenderRatings  = 
		{
			new Rating(users[0].id,movies[0].id,3),
			new Rating(users[0].id,movies[0].id,4),
			new Rating(users[0].id,movies[0].id,-4),

			new Rating(users[0].id,movies[0].id,5),
			new Rating(users[0].id,movies[0].id,4),
			new Rating(users[0].id,movies[0].id,3),
			
			new Rating(users[0].id,movies[0].id,5),
			new Rating(users[0].id,movies[0].id,2),
			new Rating(users[0].id,movies[0].id,4),
			
			new Rating(users[1].id,movies[0].id,5),
			new Rating(users[1].id,movies[0].id,5),
			new Rating(users[1].id,movies[0].id,5),
			
			new Rating(users[1].id,movies[3].id,5),
			new Rating(users[1].id,movies[1].id,0),
			new Rating(users[1].id,movies[1].id,2),
			
			new Rating(users[1].id,movies[1].id,-4),
			new Rating(users[1].id,movies[1].id,-2),
			new Rating(users[1].id,movies[1].id,-1),
			
			new Rating(users[2].id,movies[1].id,1),
			new Rating(users[2].id,movies[1].id,-1),
			new Rating(users[2].id,movies[1].id,1),
			
			new Rating(users[3].id,movies[2].id,3),
			new Rating(users[3].id,movies[2].id,2),
			new Rating(users[3].id,movies[2].id,2),
			
			new Rating(users[3].id,movies[2].id,5),
			new Rating(users[3].id,movies[2].id,3),
			new Rating(users[3].id,movies[2].id,0),
			
			new Rating(users[3].id,movies[3].id,4),
			new Rating(users[3].id,movies[9].id,1),
			new Rating(users[3].id,movies[9].id,1),
		};
}