package controllers;

import java.io.File;
import java.util.Collection;

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
