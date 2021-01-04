import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import org.junit.Before;
import org.junit.Test;
import java.io.StringReader;
import static org.junit.Assert.assertEquals;

/**
 * Testing the methods of PyramidSolitaireTextualController behave properly and throw exceptions
 * when appropriate.
 */
public class PyramidSolitaireTextualControllerTest {
  PyramidSolitaireController controller;
  PyramidSolitaireModel<Card> model;
  PyramidSolitaireModel<Card> fakeModel;
  PyramidSolitaireModel<Card> relaxedModel;
  PyramidSolitaireModel<Card> multiModel;
  PyramidSolitaireView view;
  Appendable output;




  @Before
  public void setUp() {
    output = new StringBuilder();
    fakeModel = new FakeModel(output);
    model = new BasicPyramidSolitaire();
    view = new PyramidSolitaireTextualView(model, output);
    relaxedModel = new RelaxedPyramidSolitaire();
    multiModel = new MultiPyramidSolitaire();
  }

  // Testing the constructor for PyramidSolitaireTextual Controller throws an exception when
  // Readable is null
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorReadable() {
    controller = new PyramidSolitaireTextualController(null, output);
  }

  // Testing the constructor for PyramidSolitaireTextual Controller throws an exception when
  // Appendable is null
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorAppendable() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 1 1"), null);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THE DIFFERENT CASES FOR REMOVING ONE CARD FROM THE PYRAMID


  // Testing that the controller removes the card from the user's command by using a fake model
  // to ensure the correct coordinates are passed to remove(int, int) in the model class
  @Test
  public void testRemoveOne() {
    // testing the command: "rm1 3 3"
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 3 3"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2", output.toString());

    // testing the command: "rm1 3 3"
    controller = new PyramidSolitaireTextualController(new StringReader(" ce rm1 3 2"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2rm1 2 1", output.toString());

    // testing the command: "rm1 3 3"
    controller = new PyramidSolitaireTextualController(new StringReader("rm1   3\n1"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2rm1 2 1rm1 2 0", output.toString());
  }


  // Testing that the controller removes the card from the user's command even when other letters
  // are present between numbers
  @Test
  public void testRemoveOneGibberish() {
    // testing the command: "rm1 haha 3 3"
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 haha 3 3"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2", output.toString());

    // testing the command: "rm1\n3 haha 3"
    controller =
        new PyramidSolitaireTextualController(new StringReader("rm1\n3 haha 3"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2rm1 2 2", output.toString());

    // testing the command: "rm1 3 3 haha"
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 3 3 haha"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2rm1 2 2rm1 2 2Invalid move. Play again. Use a valid move "
        + "type: rm1, rm2, rmwd, dd, q, or Q.\n", output.toString());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THE DIFFERENT CASES FOR REMOVING TWO CARDS FROM THE PYRAMID

  // Testing that the controller removes the cards from the user's command by using a fake model
  // to ensure the correct coordinates are passed to remove(int, int, int, int) in the model class
  @Test
  public void testRemoveTwo() {
    // testing the command: "rm2 3 1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 3 1 3 2"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm2 2 0 2 1", output.toString());

    // testing the command: "rm2 3 1 3 3"
    controller = new PyramidSolitaireTextualController(new StringReader("rm2\n3 1 3 3"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm2 2 0 2 1rm2 2 0 2 2", output.toString());

    // testing the command: "rm2 3 2 3 3"
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 3 2\n 3   3"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm2 2 0 2 1rm2 2 0 2 2rm2 2 1 2 2", output.toString());
  }

  // Testing that the controller removes the cards from the user's command even when other letters
  // are present between numbers
  @Test
  public void testRemoveTwoGibberish() {
    // testing the command: "rm2 3 1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 3 hahaha 1 3 2"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm2 2 0 2 1", output.toString());

    // testing the command: "rm2 3 1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 3 1 hahahah 3 2"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm2 2 0 2 1rm2 2 0 2 1", output.toString());

    // testing the command: "rm2 3 1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 3 h 1 ha 3 2"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm2 2 0 2 1rm2 2 0 2 1rm2 2 0 2 1", output.toString());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THE DIFFERENT CASES FOR REMOVING ONE CARD FROM THE PYRAMID AND ONE CARD
  // FROM THE DRAW PILE


  // Testing the controller provides the correct arguments for removeUsingDraw()
  @Test
  public void testRemoveUsingDraw() {
    // testing the command: "rmwd 1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd 1 3 2"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rmwd 0 2 1", output.toString());

    // testing the command: "rmwd 2 3 1"
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd   2   3      1"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rmwd 0 2 1rmwd 1 2 0", output.toString());

    // testing the command: "rmwd 3 3 3"
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd\n3 3\n3"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rmwd 0 2 1rmwd 1 2 0rmwd 2 2 2", output.toString());
  }


  // Testing the controller provides the correct arguments for removeUsingDraw() when there
  // are letters present between numbers
  @Test
  public void testRemoveUsingDrawGibberish() {
    // testing the command: "rmwd 1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd a 1 3 2"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rmwd 0 2 1", output.toString());

    // testing the command: "rmwd 1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd 1 h 3\n2"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rmwd 0 2 1rmwd 0 2 1", output.toString());

    // testing the command: "rmwd 1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("a rmwd 1 3 ha 2"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rmwd 0 2 1rmwd 0 2 1rmwd 0 2 1", output.toString());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THE DIFFERENT CASES FOR REMOVING A DRAW CARD


  // Testing the controller provides the correct arguments for discardDraw() method in the model
  @Test
  public void testDiscardDraw() {
    // testing the command: "dd 1"
    controller = new PyramidSolitaireTextualController(new StringReader("dd 1"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("dd 0", output.toString());

    // testing the command: "dd 2"
    controller = new PyramidSolitaireTextualController(new StringReader("dd\n2"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("dd 0dd 1", output.toString());

    // testing the command: "dd 3 "
    controller = new PyramidSolitaireTextualController(new StringReader("dd   3"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("dd 0dd 1dd 2", output.toString());
  }


  // Testing the controller provides the correct arguments for discardDraw() method in the model
  // when there are letters between the numbers
  @Test
  public void testDiscardDrawGibberish() {
    // testing the command: "dd 1"
    controller = new PyramidSolitaireTextualController(new StringReader("haha dd 1"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("dd 0", output.toString());

    // testing the command: "dd 2"
    controller = new PyramidSolitaireTextualController(new StringReader("dd haha   2\nhaha"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("dd 0dd 1Invalid move. Play again. Use a valid move type: rm1, rm2,"
        + " rmwd, dd, q, or Q.\n", output.toString());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS WHEN THE USER PROVIDES MULTIPLE MOVES IN ONE LINE


  // Testing the controller can handle commands that are on the same line and the commands call
  // the same method in the model
  @Test
  public void testSameRemoves() {
    // testing the command: "rm1 3 3 rm1 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 3 3 rm1 3 2"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2rm1 2 1", output.toString());

    // testing the command: "rm2 3 3 3 2 rm2 3 1 2 2"
    controller =
        new PyramidSolitaireTextualController(new StringReader("rm2 3 3 3 2 rm2 3 1 2 2"),
            output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2rm1 2 1rm2 2 2 2 1rm2 2 0 1 1", output.toString());

    // testing the command: "rmwd 3 3 1 rmwd 2 3 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd 3 3 1 rmwd 2 3 2"),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2rm1 2 1rm2 2 2 2 1rm2 2 0 1 1rmwd 2 2 0rmwd 1 2 1",
        output.toString());

    // testing the command: "dd 3 dd 2"
    controller = new PyramidSolitaireTextualController(new StringReader("dd 3 dd 2"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2rm1 2 1rm2 2 2 2 1rm2 2 0 1 1rmwd 2 2 0rmwd 1 2 1dd 2dd 1",
        output.toString());
  }


  // Testing the controller can handle commands that are on the same line and the commands call
  // different methods in the model
  @Test
  public void testDifferentRemoves() {
    // testing the command: "rm1 3 3 dd 2"
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 3 3 dd 2"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2dd 1", output.toString());

    // testing the command: "rm2 3 3 3 2 rmwd 2 3 1"
    controller =
        new PyramidSolitaireTextualController(new StringReader("rm2 3 3 3 2 rmwd 2 3 1"),
            output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 2 2dd 1rm2 2 2 2 1rmwd 1 2 0", output.toString());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS WHEN THE USER PROVIDES INCORRECT INPUTS AND THE CONTROLLER MUST PROMPT
  // THE USER TO CONTINUE

  // Testing that the controller asks the user to complete a move if the user didn't originally
  // provide the correct inputs for the remove methods of the model
  @Test
  public void testBadInputsRemove() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 3"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Invalid move. Play again. Missing 1 number(s) to complete move.\n",
        output.toString());

    controller = new PyramidSolitaireTextualController(new StringReader("rm2 3 2 "),
        output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Invalid move. Play again. Missing 1 number(s) to complete move.\n"
            + "Invalid move. Play again. Missing 2 number(s) to complete move.\n",
        output.toString());
  }


  // Testing that the controller asks the user to complete a move if the user didn't originally
  // provide the correct inputs for methods involving the draw pile
  @Test
  public void testBadInputsDraw() {
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd b 2 a"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Invalid move. Play again. Missing 2 number(s) to complete move.\n",
        output.toString());

    controller = new PyramidSolitaireTextualController(new StringReader("dd e"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Invalid move. Play again. Missing 2 number(s) to complete move.\n"
            + "Invalid move. Play again. Missing 1 number(s) to complete move.\n",
        output.toString());
  }
  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THE DIFFERENT CASES FOR QUITTING THE GAME


  // Testing the controller ends the game and finds the score when the user uses the "q" or "Q"
  // command
  @Test
  public void testQuit() {
    // testing the command: "q"
    controller = new PyramidSolitaireTextualController(new StringReader("q"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Game quit!\nState of game when quit:\nScore: 0", output.toString());

    // testing the command: "q"
    controller = new PyramidSolitaireTextualController(new StringReader("Q"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Game quit!\nState of game when quit:\nScore: 0Game quit!\nState of"
        + " game when quit:\nScore: 0", output.toString());
  }


  // Testing the controller ends the game and finds the score when the user uses the "q" or "Q"
  // command and there are other letters surrounding the 'q'
  @Test
  public void testQuitGibberish() {
    controller = new PyramidSolitaireTextualController(new StringReader("haha q"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Game quit!\nState of game when quit:\nScore: 0", output.toString());

    controller = new PyramidSolitaireTextualController(new StringReader("rm 1 2 q"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Game quit!\nState of game when quit:\nScore: 0Game quit!\nState of"
        + " game when quit:\nScore: 0", output.toString());
  }


  // Testing the controller ends the game and finds the score when the user uses the "q" or "Q"
  // command and there is a valid move before 'q'
  @Test
  public void testQuitAfterValidMove() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 1 q"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 1 0Game quit!\nState of game when quit:\nScore: 0",
        output.toString());

    controller = new PyramidSolitaireTextualController(new StringReader("dd 2 Q"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("rm1 1 0Game quit!\nState of game when quit:\nScore: 0dd 1Game quit!\n"
            + "State of game when quit:\nScore: 0", output.toString());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THAT playGame() THROWS AN EXCEPTION FOR INVALID ARGUMENTS TO START THE GAME

  // Testing playGame() throws an exception when the provided model is null
  @Test(expected = IllegalArgumentException.class)
  public void testModelNull() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"),
        output);
    controller.playGame(null, model.getDeck(),false,7,3);
  }

  // Testing playGame() throws an exception when the provided deck is null
  @Test(expected = IllegalArgumentException.class)
  public void testDeckNull() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"),
        output);
    controller.playGame(model,null,false,7,3);
  }

  // Testing playGame() throws an exception when the number of rows is negative
  @Test(expected = IllegalStateException.class)
  public void testNegativeNumRows() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"),
        output);
    controller.playGame(model, model.getDeck(),false,-10,3);

  }

  // Testing playGame() throws an exception when number of rows is more than 9
  @Test(expected = IllegalStateException.class)
  public void testTooManyNumRows() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"),
        output);
    controller.playGame(model, model.getDeck(),false,10,3);

  }

  // Testing playGame() throws an exception when the number of draw cards is negative
  @Test(expected = IllegalStateException.class)
  public void testNegativeNumDraw() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"),
        output);
    controller.playGame(model, model.getDeck(),false,7,-5);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THAT playGame() THROWS AN EXCEPTION FOR INVALID ARGUMENTS FOR THE REMOVE
  // AND DISCARD METHODS

  // Testing playGame() throws an exception when remove() is called and the provided row is negative
  @Test(expected = IllegalStateException.class)
  public void testNegativeRowRemoveOne() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 -2 2"),
        output);
    controller.playGame(model, model.getDeck(),false,7,-5);
  }

  // Testing playGame() throws an exception when remove() is called and the provided column is
  // negative
  @Test(expected = IllegalStateException.class)
  public void testNegativeCardRemoveOne() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 7 -2"),
        output);
    controller.playGame(model, model.getDeck(),false,7,-5);
  }

  // Testing playGame() throws an exception when remove() is called and the provided row is out
  // of bounds
  @Test(expected = IllegalStateException.class)
  public void testOutOfBoundsRowRemoveTwo() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 7 7 9 5"),
        output);
    controller.playGame(model, model.getDeck(),false,7,-5);
  }

  // Testing playGame() throws an exception when remove() is called and the provided column is out
  // of bounds
  @Test(expected = IllegalStateException.class)
  public void testOutOfBoundsCardRemoveTwo() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 7 7 7 9"),
        output);
    controller.playGame(model, model.getDeck(),false,7,-5);
  }

  // Testing playGame() throws an exception when removeUsingDraw() is called and the card is
  // covered by two other cards
  @Test(expected = IllegalStateException.class)
  public void testCoveredCardRemoveUsingDraw() {
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd 1 2 7"),
        output);
    controller.playGame(model, model.getDeck(),false,7,-5);
  }

  // Testing playGame() throws an exception when removeUsingDraw() is called and the cards don't
  // sum to 13
  @Test(expected = IllegalStateException.class)
  public void testInvalidSumRemoveUsingDraw() {
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd 1 7 7"),
        output);
    controller.playGame(model, model.getDeck(),false,7,-5);
  }

  // Testing playGame() throws an exception when discardDraw() is called and index is invalid
  @Test(expected = IllegalStateException.class)
  public void testZeroIndexDiscardDraw() {
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd 0 7 7"),
        output);
    controller.playGame(model, model.getDeck(),false,7,-5);
  }

  // ----------------------------------------------------------------------------------------------
  // Testing playGame() throws an exception when the controller is given an input that contains
  // letters other than the ones that trigger a method for the model
  @Test
  public void testInvalidLetterInput() {
    controller = new PyramidSolitaireTextualController(new StringReader("hahaha"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Invalid move. Play again. Use a valid move type: rm1, rm2, rmwd, dd, q, "
        + "or Q.\n", output.toString());

    controller = new PyramidSolitaireTextualController(new StringReader("rm 3 3"), output);
    controller.playGame(fakeModel, model.getDeck(), false, 3, 3);
    assertEquals("Invalid move. Play again. Use a valid move type: rm1, rm2, rmwd, dd, q, "
        + "or Q.\nInvalid move. Play again. Use a valid move type: rm1, rm2, rmwd, dd, q, or Q.\n",
        output.toString());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS RelaxedPyramidSolitaire MODEL THROWS THE SAME EXCEPTIONS AS
  // BasicPyramidSolitaire MODEL

  // Testing playGame() throws an exception when the provided deck is null
  @Test(expected = IllegalArgumentException.class)
  public void testRelaxedModelDeckNull() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"), output);
    controller.playGame(relaxedModel,null,false,7,3);
  }

  // Testing playGame() throws an exception when the number of rows is negative
  @Test(expected = IllegalStateException.class)
  public void testRelaxedModelNegativeNumRows() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"), output);
    controller.playGame(relaxedModel, relaxedModel.getDeck(),false,-10,3);
  }

  // Testing playGame() throws an exception when number of rows is more than 9
  @Test(expected = IllegalStateException.class)
  public void testRelaxedModelTooManyNumRows() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"), output);
    controller.playGame(relaxedModel, relaxedModel.getDeck(),false,10,3);
  }
  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS RelaxedPyramidSolitaire MODEL THROWS THE SAME EXCEPTIONS FOR INVALID
  // RULES ACCORDING TO THE NEW RELAXED RULES


  // add tests for checking if cards can be removed if they're not visible (look at tests for
  // relaxed pyramid), making sure they don't throw exceptions

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS MultiPyramidSolitaire MODEL THROWS THE SAME EXCEPTIONS AS
  // BasicPyramidSolitaire MODEL

  // Testing playGame() throws an exception when the provided deck is null
  @Test(expected = IllegalArgumentException.class)
  public void testMultiModelDeckNull() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"), output);
    controller.playGame(multiModel,null,false,7,3);
  }

  // Testing playGame() throws an exception when the number of rows is negative
  @Test(expected = IllegalStateException.class)
  public void testMultiModelNegativeNumRows() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"), output);
    controller.playGame(multiModel, multiModel.getDeck(),false,-10,3);
  }

  // Testing playGame() throws an exception when number of rows is more than 8
  @Test(expected = IllegalStateException.class)
  public void testMultiModelTooManyNumRows() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"), output);
    controller.playGame(multiModel, multiModel.getDeck(),false,9,3);
  }

  // Testing playGame() throws an exception when the provided deck is too small
  @Test(expected = IllegalStateException.class)
  public void testMultiModelDeckTooSmall() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 2 2"), output);
    controller.playGame(multiModel,model.getDeck(),false,7,3);
  }

  // Testing playGame() throws an exception when remove() is called and the provided row is out
  // of bounds
  @Test(expected = IllegalStateException.class)
  public void testMultiModelOutOfBoundsRowRemoveTwo() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 8 8 9 5"),
        output);
    controller.playGame(multiModel, multiModel.getDeck(),false,8,-5);
  }

  // Testing playGame() throws an exception when remove() is called and the provided column is out
  // of bounds
  @Test(expected = IllegalStateException.class)
  public void testMultiModelOutOfBoundsCardRemoveTwo() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 8 8 8 17"),
        output);
    controller.playGame(multiModel, multiModel.getDeck(),false,8,-5);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS MultiPyramidSolitaire MODEL ALLOWS FOR HIGHER INDICES THAN
  // BasicPyramidSolitaire()

  // add tests removing cards with higher indices, making sure they don't throw exceptions

  // Testing that the controller is able to handle indices that are unique to a game of pyramid
  // solitaire with multiple pyramids
  /*
  @Test
  public void testRemoveCardsMultiModel() {
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 8 2 8 6"), output);
    controller.playGame(multiModel, multiModel.getDeck(),false,8,3);
  }

   */
}