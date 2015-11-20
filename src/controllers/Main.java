package controllers;

import java.io.File;
import java.util.Collection;

import edu.princeton.cs.introcs.In;
import utils.Serializer;
import utils.XMLSerializer;
import models.User;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;

public class Main
{
	public MovieRecommenderAPI movieRecommender;

	public Main() throws Exception
	{
		File datastore = new File("datastore.xml");
		Serializer serializer = new XMLSerializer(datastore);

		movieRecommender = new MovieRecommenderAPI(serializer);
		if (datastore.isFile())
		{
			movieRecommender.load();
		}
		else
		{
			In inUsers = new In("data/users5.dat");

			//each field is separated(delimited) by a '|'
			String delims = "[|]";
			while (!inUsers.isEmpty()) 
			{
				// get user and rating from data source
				String userDetails = inUsers.readLine();

				// parse user details string
				String[] userTokens = userDetails.split(delims);
				movieRecommender.createFileUser(Long.parseLong(userTokens[0]),userTokens[1],userTokens[2],Integer.parseInt(userTokens[3]),userTokens[4],userTokens[5]);			// output user data to console.
				if (movieRecommender.getUsers()!=null) 
				{
					System.out.println(movieRecommender.getUsers().size());
				}
				else
				{
					throw new Exception("Invalid member length: "+userTokens.length);
				}

			}
			for(int i = 0;i<=movieRecommender.getUsers().size();i++)
			{
				System.out.println(movieRecommender.getUser(Long.valueOf(i)));
			}
			movieRecommender.store();
		}
	}

	@Command(description="Get all users details")
	public void getUsers ()
	{
		Collection<User> users = movieRecommender.getUsers();
		System.out.println(users);
	}

	@Command(description="Add a new User")
	public void addUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName,
			@Param(name="age") int age, @Param(name="gender") String gender, @Param(name="occupation") String occupation)
	{
		movieRecommender.createUser(firstName, lastName, age, gender, occupation);
	}

	@Command(description="Delete a User")
	public void removeUser (@Param(name="id") Long id)
	{
		movieRecommender.deleteUser(id);
	}
	@Command(description="Add a Movie")
	public void addMovie (@Param(name="title") String title, @Param(name="year") String year, @Param(name="url") String url)
	{
		movieRecommender.addMovie(title, year, url);
	}
	public static void main(String[] args) throws Exception
	{
		Main main = new Main();
		Shell shell = ShellFactory.createConsoleShell("lm", "Welcome to movie-recommender - ?help for instructions", main);
		shell.commandLoop();
		main.movieRecommender.store();
	}
}
