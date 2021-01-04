package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import java.io.IOException;
import java.util.List;

/**
 * Represents the view for the game Pyramid Solitaire.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private Appendable out;

  /**
   * Creates an instance of PyramidSolitaireTextualView to test the behavior of method in this
   * class that don't involve transmitting outputs to the user.
   * @param model The provided model that will be shown using this view
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.out = new StringBuilder();
  }

  /**
   * Creates an instance of PyramidSolitaireTextualView used for playing an interactive game of
   * pyramid solitaire that involves a string view.
   * @param model The provided model that will be shown using this view
   * @param appendable The string view of the game that will be transmitted to the user
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable appendable) {
    this.model = model;
    this.out = appendable;
  }

  /**
   * Returns a string representation of the game for the user to view.
   * @return Empty string if the game hasn't started, "You win!" if the pyramid is empty, "Game
   *         over. Score: " with user's score if the game is over, or a string representation of the
   *         pyramid and draw pile if the game is still in progress.
   */
  @Override
  public String toString() {
    if (this.model.getNumRows() == -1) {
      return "";
    }
    else if (this.model.isGameOver() && this.model.getScore() == 0) {
      return "You win!";
    }
    else if (this.model.isGameOver()) {
      return "Game over. Score: " + this.model.getScore();
    }
    else {
      return this.pyramidToString() + this.drawToString();
    }
  }

  /**
   * Returns a string representation of the pyramid in the game.
   * @return a string of the cards in a pyramid format
   */
  private String pyramidToString() {
    String pyramid = "";
    for (int row = 0; row < this.model.getNumRows(); row++) {
      for (int column = 0; column < this.model.getRowWidth(row); column++) {
        // if the card is the first one in the pyramid, it should be centered and create a new line
        if (column == 0 && column == this.model.getRowWidth(row) - 1) {
          pyramid = pyramid + this.spacing(row, this.model.getNumRows())
              + this.pyramidCardToString(row, column) + "\n";
        }
        // if the card is the first one in the row, it should have spaces before it
        else if (column == 0) {
          pyramid = pyramid + this.spacing(row, this.model.getNumRows())
                  + this.pyramidCardToString(row, column);
        }
        // if the column is the last one in the row, then create a new line
        else if (column == this.model.getRowWidth(row) - 1) {
          pyramid = pyramid + this.pyramidCardToString(row, column) + "\n";
        }
        else {
          pyramid = pyramid + this.pyramidCardToString(row, column);
        }
      }
    }
    return pyramid;
  }

  /**
   * Determines how many spaces are needed for each row before a card is placed to create a
   * pyramid shape.
   * @param row the current row to build the pyramid
   * @param maxRows the total number of rows in the pyramid
   * @return
   */
  private String spacing(int row, int maxRows) {
    String spaces = "";
    for (int r = row; r < maxRows - 1; r++) {
      spaces = spaces + "  ";
    }
    return spaces;
  }

  /**
   * Returns a string representation of the draw pile.
   * @return "Draw:" with the draw card visible to the user
   */
  private String drawToString() {
    String draw = "Draw:";

    List<?> drawList = this.model.getDrawCards();
    for (int index = 0; index < drawList.size(); index++) {
      if (drawList.get(index) != null && index != drawList.size() - 1) {
        draw = draw + " " + drawList.get(index).toString() + ",";
      }
      else if (drawList.get(index) != null) {
        draw = draw + " " + drawList.get(index).toString();
      }
    }
    return draw;
  }

  /**
   * Converts the card from the pyramid to a string to represent it in the view of this game.
   * @param row the row of the card in the pyramid
   * @param column the column of the card in the pyramid
   * @return a string of the card's number and suit
   */
  private String pyramidCardToString(int row, int column) {
    // If a card is present, return two spaces and the card as a string
    if (this.model.getCardAt(row, column) != null) {
      if (column != 0) {
        return "  " + this.model.getCardAt(row,column).toString();
      }
      else {
        return this.model.getCardAt(row,column).toString();
      }
    }
    // If a card is not present, return "."
    else {
      return "  .";
    }
  }

  @Override
  public void render() throws IOException {
    if (this.model == null) {
      throw new IOException("Invalid model.");
    }
    else if (this.out == null) {
      throw new IOException("Invalid input.");
    }
    else {
      this.out.append(this.toString() + "\n");
    }
  }
}
