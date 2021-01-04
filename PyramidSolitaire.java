package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import java.io.InputStreamReader;

/**
 * Represents the main function to be used for a game of pyramid solitaire.
 */
public final class PyramidSolitaire {

  /**
   * Starts an interactive game of Pyramid Solitaire.
   * @param args The arguments provided from the user
   */
  public static void main(String[] args) {
    // Setting up local variables that will be used to set up the game
    PyramidSolitaireCreator creator = new PyramidSolitaireCreator();
    PyramidSolitaireController controller =
        new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    int numRow = 7;
    int draw = 3;

    // Determines the type of game the user wants to play
    if (args.length > 0) {
      String key = args[0].toLowerCase();
      switch (key) {
        case "basic":
          model = creator.create(PyramidSolitaireCreator.GameType.BASIC);
          break;
        case "relaxed":
          model = creator.create(PyramidSolitaireCreator.GameType.RELAXED);
          break;
        case "multipyramid":
          model = creator.create(PyramidSolitaireCreator.GameType.MULTIPYRAMID);
          break;
        default:
          throw new IllegalArgumentException();
      }

      // Determines how many rows and draw cards to use in the game if the user has provided the
      // correct integers
      if (args.length > 2) {
        try {
          numRow = Integer.parseInt(args[1]);
          draw = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException();
        }
      }
    }

    // Plays the game using the controller and desired model
    controller.playGame(model, model.getDeck(), true, numRow, draw);
  }
}
