import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.List;

/**
 * Represents a fake model used to test the controller's behavior by verifying the input results
 * in the respective output.
 */
public class FakeModel implements PyramidSolitaireModel<Card> {
  private Appendable out;

  public FakeModel(Appendable out) {
    this.out = out;
  }

  @Override
  public List getDeck() {
    return null;
  }

  @Override
  public void startGame(List deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    // This method is empty because there is no need to start a game using this fake model. This
    // model is only meant for testing the behavior of the controller.
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalArgumentException,
      IllegalStateException {
    try {
      out.append(String.format("rm2 " + row1 + " " + card1 + " " + row2 + " " + card2));
    }
    catch (IOException e) {
      throw new IllegalStateException("Cannot write to appendable from mock model.");
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    try {
      out.append(String.format("rm1 " + row + " " + card));
    }
    catch (IOException e) {
      throw new IllegalStateException("Cannot write to appendable from mock model.");
    }
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalArgumentException,
      IllegalStateException {
    try {
      out.append(String.format("rmwd " + drawIndex + " " + row + " " + card));
    }
    catch (IOException e) {
      throw new IllegalStateException("Cannot write to appendable from mock model.");
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    try {
      out.append(String.format("dd " + drawIndex));
    }
    catch (IOException e) {
      throw new IllegalStateException("Cannot write to appendable from mock model.");
    }
  }

  @Override
  public int getNumRows() {
    return -1;
  }

  @Override
  public int getNumDraw() {
    return 0;
  }

  @Override
  public int getRowWidth(int row) {
    return 0;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 0;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    return null;
  }

  @Override
  public List getDrawCards() throws IllegalStateException {
    return null;
  }
}
