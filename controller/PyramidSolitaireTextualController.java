package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a controller for a game of pyramid solitaire that uses String to visually create
 * the game.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private final Readable in;
  private final Appendable out;
  private PyramidSolitaireModel<?> model;
  private PyramidSolitaireView view;


  /**
   * Sets up an instance of a PyramidSolitaireTextualController using the given Readable and
   * Appendable.
   * @param rd The Readable that contains the inputs from the user
   * @param ap The Appendable that contains the message that will be transmitted to the user
   * @throws IllegalArgumentException when the Readable or Appendable is null
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null) {
      throw new IllegalArgumentException("Invalid input.");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Invalid output.");
    }
    this.in = rd;
    this.out = ap;
  }


  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
                           int numRows, int numDraw) throws IllegalArgumentException,
      IllegalStateException {

    if (model == null) {
      throw new IllegalArgumentException("Invalid model.");
    }
    else if (deck == null) {
      throw new IllegalArgumentException("Invalid deck.");
    }
    else {
      this.model = model;
      this.view = new PyramidSolitaireTextualView(this.model);
      // If the game hasn't started yet, then the game will be started unless there is an
      // exception, then the controller will throw an exception
      if (this.model.getNumRows() == -1) {
        try {
          model.startGame(deck, shuffle, numRows, numDraw);
          this.readInput();
        } catch (IllegalArgumentException exception) {
          throw new IllegalStateException("This game cannot start. Check for invalid deck, invalid"
              + " number of rows, or invalid number of draw cards.");
        }
      }
      // If the game is still in progress, the user's inputs need to be read
      else {
        this.readInput();
      }
    }
  }


  /**
   * Reads the input from the user and determines what move the user wants to do.
   */
  private void readInput() {
    Scanner scan = new Scanner(this.in);
    boolean invalidMove = false;
    // Checking through the input to see if there is a move that can be made
    while (scan.hasNext()) {
      String current = scan.next();
      switch (current) {
        case "rm1":
          callsToModel(current, findNum(2, scan), 2);
          break;
        case "rm2":
          callsToModel(current, findNum(4, scan), 4);
          break;
        case "dd":
          callsToModel(current, findNum(1, scan), 1);
          break;
        case "rmwd":
          callsToModel(current, findNum(3, scan), 3);
          break;
        case "q":
        case "Q":
          quitCommand();
          break;
        default:
          if (!scan.hasNext()) {
            invalidMove = true;
          }
          break;
      }
      // Transmitting the rendered game to the user
      try {
        this.view.render();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // Telling the user that the move is an invalid move type
    if (invalidMove) {
      try {
        this.out.append("Invalid move. Play again. Use a valid move type: rm1, rm2, rmwd, dd, q, "
            + "or Q.\n");
      } catch (IOException e) {
        throw new IllegalStateException();
      }
    }
  }


  /**
   * Returns the list of integers that are required to complete a move in the game.
   * @param max The amount of numbers needed to complete the move
   * @param rest The input of the user that may consist of integers to use for a move
   */
  private List<Integer> findNum(int max, Scanner rest) {
    List<Integer> numList = new ArrayList<Integer>();
    // Looking through each input and stopping when either there aren't any more inputs left or
    // the max amount of integers have been found to complete the move type.
    while (rest.hasNext() && numList.size() < max) {
      String current = rest.next();
      try {
        // Adding the next possible integer, but subtracting 1 since the user starts with an
        // index of 1 instead of 0
        numList.add(Integer.parseInt(current) - 1);

      } catch (NumberFormatException e) {
        // If there is a quit move, then the game will quit
        if (current.equals("Q") || current.equals("q")) {
          this.quitCommand();
        }
      }
    }
    return numList;
  }


  /**
   * Performs method calls to the model based on the move the user wishes to make.
   * @param call A string that contains the method call to the model.
   * @param numbers The list of numbers containing the integers needed to complete the given move
   *               type.
   * @param max The amount of integers need to complete the move type.
   */
  private void callsToModel(String call, List<Integer> numbers, int max) {
    // If the given list does not contain the correct amount of integers to complete this move
    // type, an error message will be transmitted to the user.
    if (numbers.size() < max) {
      try {
        this.out.append("Invalid move. Play again. Missing " + (max - numbers.size())
            + " number(s) to complete move.\n");
      } catch (IOException e) {
        throw new IllegalStateException();
      }
    }
    else {
      switch (call) {
        // The case for the method remove(int, int) in model
        case "rm1":
          this.callToRemove1(numbers);
          break;
        // The case for the method remove(int, int, int, int) in model
        case "rm2":
          this.callToRemove2(numbers);
          break;
        // The case for the method removeUsingDraw() in model
        case "rmwd":
          this.callToRemoveUsingDraw(numbers);
          break;
        // The case for the method discardDraw() in model
        case "dd":
          this.callToDiscard(numbers);
          break;
        // If the given string does not match any of the move types from above, an exception will
        // be thrown.
        default:
          throw new IllegalArgumentException("Invalid method call to model.");
      }
    }
  }


  /**
   * Calls the remove(int, int) method from the model and applies the given numbers.
   * @param numbers List of numbers containing integers needed to complete the given move type.
   * @throws IllegalStateException when the move is invalid.
   */
  private void callToRemove1(List<Integer> numbers) throws IllegalStateException {
    try {
      this.model.remove(numbers.get(0), numbers.get(1));
    } catch (IllegalArgumentException removeException) {
      throw new IllegalStateException("Invalid move. Check position of card or presence of"
          + " card.");
    }
  }

  /**
   * Calls the remove(int, int, int, int) method from the model and applies the given numbers.
   * @param numbers List of numbers containing integers needed to complete the given move type.
   * @throws IllegalStateException when the move is invalid.
   */
  private void callToRemove2(List<Integer> numbers) throws IllegalStateException {
    try {
      this.model.remove(numbers.get(0), numbers.get(1), numbers.get(2), numbers.get(3));
    } catch (IllegalArgumentException removeException) {
      throw new IllegalStateException("Invalid move. Check position of cards or presence of"
          + " cards.");
    }
  }

  /**
   * Calls the removeUsingDraw() method from the model and applies the given numbers.
   * @param numbers List of numbers containing integers needed to complete the given move type.
   * @throws IllegalStateException when the move is invalid.
   */
  private void callToRemoveUsingDraw(List<Integer> numbers) throws IllegalStateException {
    try {
      this.model.removeUsingDraw(numbers.get(0), numbers.get(1), numbers.get(2));
    } catch (IllegalArgumentException removeException) {
      throw new IllegalStateException("Invalid move. Check position of cards or presence of"
          + " cards.");
    }
  }

  /**
   * Calls the discardDraw() method from the model and applies the given numbers.
   * @param numbers List of numbers containing integers needed to complete the given move type.
   */
  private void callToDiscard(List<Integer> numbers) {
    try {
      this.model.discardDraw(numbers.get(0));
    } catch (IllegalArgumentException removeException) {
      throw new IllegalStateException("Invalid move. Check position of card or presence of"
          + " card.");
    }
  }



  /**
   * Adds to the output: "Game quit!" along with the state of the game when the user has quit and
   * the user score.
   */
  private void quitCommand() {
    try {
      this.out.append("Game quit!\nState of game when quit:\n");
      this.view.render();
      this.out.append("Score: " + this.model.getScore());
    }
    catch (IOException e) {
      throw new IllegalStateException();
    }
  }
}
