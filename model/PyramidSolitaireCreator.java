package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import java.util.Objects;

/**
 * This class creates a game of pyramid solitaire.
 */
public class PyramidSolitaireCreator {
  /**
   * An enum used that contains the different versions of pyramid solitaire.
   */
  public enum GameType {  BASIC, RELAXED, MULTIPYRAMID }

  /**
   * Returns an instance of a PyramidSolitaireModel depending on the given GameType.
   * @param type identifies which type of pyramid solitaire game to play
   * @return one of the classes that implements PyramidSolitaireModel
   */
  public static PyramidSolitaireModel<?> create(GameType type) {
    Objects.requireNonNull(type); // throws exception if type is null
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case MULTIPYRAMID:
        return new MultiPyramidSolitaire();
      default:
        return null;
    }
  }
}
