Changes to Model,View, and Controller for Assignment 4

-  I added "\n" to the render() method in PyramidSolitaireTextualView instead of adding it in
 PyramidSolitaireTextualController where I originally had it.
- In PyramidSolitaireTextualController in the readInput() method, I call the render() method for
 the view instead of the toString() method
 - In my readInput() method in PyramidSolitaireTextualController, I deleted an if statement that
  was redundant for calling the render() method in the view class
 - I added the case in playGame() in PyramidSolitaireTextualController that when the model throws
  an exception, the controller throws an IllegalStateException. Before I just appended a message
  to the user about how the game could not start. I also created tests to make sure this case
  throws an exception. 
  - I deleted my helper method isPyramidEmpty in BasicPyramidSolitaire (used in isGameOver())
    because I realized I could use the getScore() method to determine if the pyramid has no more
   cards in the case that the user wins the game
  - In getNumDraw() in BasicPyramidSolitaire, I am returning the size of the list of draw cards
   instead of the number of available cards which is what I had previously
   - In my helper method callsToModel() in PyramidSolitaireTextualController, I changed the 
   try-catch statements for each move type to throw an IllegalStateException if those methods
    throw an exception in the model and I added more tests in
     PyramidSolitaireTextualControllerTest to test this behavior
   - In BasicPyramidSolitaire, I made the fields and helper methods protected so that the new
    model classes can access those variables and fields and code does not need to be duplicated
   - In BasicPyramidSolitaire, I changed some math in my isCardVisible() helper method because it
    was using the wrong logic for determining if a card is covered by another card on its left
     side. 
   - I added a helper method in BasicPyramidSolitaire for startGame() to check if the given
    number of rows is greater than the max needed to play the game because this was the only
     difference between MultiPyramidSolitaire and BasicPyramidSolitaire for the startGame() method
   - I added helper methods to my callsToModel() method in PyramidSolitaireTextualController to
    help reduce duplication of code
   - I added an override of the equals method in BasicPyramidSolitaire in order to test the
    create() method of my PyramidSolitaireCreatorTest class
   
   
    
  
 

