import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * This class tests the public methods of the view for a pyramid solitaire game.
 */
public class PyramidSolitaireTextualViewTest {
  PyramidSolitaireView tempView;
  StringBuilder out;
  PyramidSolitaireModel<Card> model;
  PyramidSolitaireTextualView view;
  PyramidSolitaireModel<Card> complexGame;
  PyramidSolitaireTextualView complexView;
  PyramidSolitaireModel<Card> winGame;
  PyramidSolitaireTextualView winView;
  PyramidSolitaireModel<Card> bigPyramid;
  PyramidSolitaireTextualView viewBig;
  List<Card> complexCards;
  List<Card> winCards;


  @Before
  public void setUp() throws Exception {
    out = new StringBuilder();
    model = new BasicPyramidSolitaire();
    view = new PyramidSolitaireTextualView(model);
    complexGame = new BasicPyramidSolitaire();
    complexCards = complexGame.getDeck();
    winGame = new BasicPyramidSolitaire();
    winCards = winGame.getDeck();
    bigPyramid = new BasicPyramidSolitaire();
    bigPyramid.startGame(bigPyramid.getDeck(), false, 9, 7);
    viewBig = new PyramidSolitaireTextualView(bigPyramid);

    Card tempAce = complexCards.get(3);
    complexCards.set(3, complexCards.get(51)); // Setting the fourth card in the deck to a King
    complexCards.set(51, tempAce);
    complexGame.startGame(complexCards, false, 3, 3);
    complexView = new PyramidSolitaireTextualView(complexGame);

    // Changing the cards that will fill the pyramid to A, Q, K so that the game is able to be
    // won easily
    Card temp = winCards.get(2);
    winCards.set(2, winCards.get(47)); // Changing the card at (1, 1) to Q
    winCards.set(47, temp);
    temp = winCards.get(4);
    winCards.set(4, winCards.get(46)); // Changing the card at (2, 1) to Q
    winCards.set(46, temp);
    temp = winCards.get(5);
    winCards.set(5, winCards.get(51)); // Changing the card at (2, 2) to K
    winCards.set(51, temp);
    temp = winCards.get(6);
    winCards.set(6, winCards.get(45)); // Changing the draw card at index 0 to Q
    winCards.set(45, temp);
    winGame.startGame(winCards, false, 3, 3);
    winGame.remove(2, 2);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS toString() FOR DIFFERENT STATES OF THE GAME

  // Testing that toString() returns an empty string when the game hasn't started yet
  @Test
  public void testToStringGameNotStarted() {
    assertEquals("", view.toString());
  }

  /*
  // Testing that toString() returns an empty string after a call to startGame has thrown an
  // exception
  @Test
  public void testToStringAfterException() {
    model.startGame(complexCards, false, -3, 7);
    assertEquals("", view.toString());
  }
*/

  // Testing that toString() tells the user they won when the pyramid is empty
  @Test
  public void testToStringWin() {
    winGame.remove(2, 0, 2, 1);
    winGame.remove(1, 0, 1, 1);
    winGame.removeUsingDraw(0, 0,0);
    winView = new PyramidSolitaireTextualView(winGame);
    assertEquals("You win!", winView.toString());
  }

  // Testing that toString() tells the user "game over" and the score when the game has ended
  @Test
  public void testToStringGameOver() {
    assertEquals("Game over. Score: 276", viewBig.toString());
  }

  // Testing that toString() returns the pyramid and draw cards when the game is in progress
  @Test
  public void testToStringGameInProgress() {
    assertEquals("    A♣\n  A♦  A♥\nK♠  2♣  2♦\nDraw: 2♥, 2♠, 3♣",
        complexView.toString());

    winView = new PyramidSolitaireTextualView(winGame);
    assertEquals(null, winGame.getCardAt(2, 2));
    assertEquals("    A♣\n  A♦  Q♠\nA♠  Q♥  .\nDraw: Q♦, 2♠, 3♣", winView.toString());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS WHEN render() THROWS AN IOException

  // Testing that render() throw IOException when Appendable is null
  @Test(expected = IOException.class)
  public void testRenderAppendableNull() throws IOException {
    new PyramidSolitaireTextualView(model, null).render();
  }

  // Testing that render() throws IOException when the provided model is null
  @Test(expected = IOException.class)
  public void testRenderModelNull() throws IOException {
    new PyramidSolitaireTextualView(null, new StringBuilder()).render();
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE VALID CASES FOR render()

  // Testing that render() doesn't modify the view's output when the game hasn't started yet
  @Test
  public void testRenderGameNotStarted() throws IOException {
    tempView = new PyramidSolitaireTextualView(model, out);
    tempView.render();
    assertEquals("\n", out.toString());
  }

  // Testing that render() appends the correct message to the output when the game has ended and
  // the user has not won (the user has a score).
  @Test
  public void testRenderGameOverScore() throws IOException {
    tempView = new PyramidSolitaireTextualView(bigPyramid, out);
    tempView.render();
    assertEquals("Game over. Score: 276\n", out.toString());
  }

  // Testing that render() appends the correct message to the output when the game has ended and
  // the user has won.
  @Test
  public void testRenderGameOverWin() throws IOException {
    winGame.remove(2, 0, 2, 1);
    winGame.remove(1, 0, 1, 1);
    winGame.removeUsingDraw(0, 0,0);
    tempView = new PyramidSolitaireTextualView(winGame, out);
    tempView.render();
    assertEquals("You win!\n", out.toString());
  }

  // Testing that render() appends the game as a string to the output as the game is in progress
  // This includes showing the pyramid, draw cards, and current score
  @Test
  public void testRenderGameInProgress() throws IOException {
    tempView = new PyramidSolitaireTextualView(complexGame, out);
    tempView.render();
    assertEquals("    A♣\n  A♦  A♥\nK♠  2♣  2♦\nDraw: 2♥, 2♠, 3♣\n", out.toString());
  }

  // Testing that render() appends the game to the output as the game is in progress and when a
  // card has been removed. This includes showing the pyramid, draw cards, and current score.
  @Test
  public void testRenderGameInProgressRemovedCard() throws IOException {
    tempView = new PyramidSolitaireTextualView(winGame, out);
    tempView.render();
    assertEquals("    A♣\n  A♦  Q♠\nA♠  Q♥  .\nDraw: Q♦, 2♠, 3♣\n", out.toString());
  }

  // Testing that toString() provides correct spacing between the cards with the value of 10
  @Test
  public void testSpacing() {
    Card cardTemp = complexCards.get(3); // Getting the King that's on the last row of the pyramid
    complexCards.set(3, complexCards.get(39)); // Setting a card in the last row to a 10
    complexCards.set(39, cardTemp);

    cardTemp = complexCards.get(4); // Getting the King that's on the last row of the pyramid
    complexCards.set(4, complexCards.get(36)); // Setting a card in the last row to a 10
    complexCards.set(36, cardTemp);

    cardTemp = complexCards.get(5); // Getting the K that's on the last row of the pyramid
    complexCards.set(5, complexCards.get(37)); // Setting a card in the last row to a 10
    complexCards.set(37, cardTemp);

    model.startGame(complexCards, false, 3, 3);
    PyramidSolitaireView viewSpacing = new PyramidSolitaireTextualView(model);

    assertEquals("    A♣\n  A♦  A♥\n10♠  10♣  10♦\nDraw: 2♥, 2♠, 3♣",
        viewSpacing.toString());
  }
}