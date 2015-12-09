# movieRecommenderAPI

- all features have been implemented

- I started by reading in the data from the file using the prinveton libray method.

-With each data type (users,ratings,movies) I would read them in, save and load them, write setters and getters and then writes tests
  for serialisation.
  
- The majority of my tests include basic exception testing, making sure items are added and delted as they should be, and I also used 
  serilisation within a lot of my tests between two API objects to ensure they has the same contents as one another.
  
- I have a test coverage of 78.3% when you run my source 'test' folder. Howevere I had an issue of running all test at the same time.
  It seemed that 3 tests fail when the entire test folder is run at once. But if you run the failed tests serperatley they all pass.
  I was unable to figure out why or where this issue was coming from I assume it could be interference from the fixtures class.
  However all tests do pass if the test classes are run independently.
  
- My top ten movies algorithm is very basic I made a comparable class called 'Average Rating' which mnages a movies average rating.
   I went through all movies and any movies that had more than 50 ratings I added them to an arraylist of type 'Average Rating'.
    I then sorted the list based on the averahe rating loswest-highest. I then made a new list which was basically a sub list,
    of the average ratings which contained the top ten movies. The sub list is what is presented to the end user.
    
- My recommender algorithm is based off the nearest neighbour algorithm. I check similarity between two users and then decide if they
  are similar by using a threshold value. If theey are within the threshold value then I add them to the neighbourhood(similar users list).
  I then get recommendations from the nighbourhood by checking if the users in the neighbourhood and the user in question have rated the same movies.
  If they have rated the same movies then I avoid adding them. If the comoparing user has rated a movie 3 and ABOVE and which the actuve user
  has not rated well then I assume the user has not seen the movie and will add that movie to recommended list. I then return the movies to
  the end user.
