import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the behavior of the methods of RelaxedPyramidSolitaire which applies more
 * relaxed rules to a basic game of pyramid solitaire.
 */
public class RelaxedPyramidSolitaireTest {
  PyramidSolitaireModel<Card> losingModel;
  PyramidSolitaireModel<Card> winningModel;
  PyramidSolitaireModel<Card> emptyStockModel;
  List<Card> losingDeck;
  List<Card> winningDeck;

  @Before
  public void setUp() {
    losingModel = new RelaxedPyramidSolitaire();
    winningModel = new RelaxedPyramidSolitaire();
    emptyStockModel = new RelaxedPyramidSolitaire();
    losingDeck = losingModel.getDeck();
    winningDeck = winningModel.getDeck();


    // Rearranging the cards in losing deck so when they deal a pyramid with removal cards but
    // that can't be won
    Card temp = losingDeck.get(1); // Storing the 2nd card in the deck (A)
    losingDeck.set(1, losingDeck.get(47)); // Setting the 2nd card in the deck to a Q
    losingDeck.set(47, temp); // Replacing the 48th card with A

    temp = losingDeck.get(2);// Storing the 3rd card in the deck (A)
    losingDeck.set(2, losingDeck.get(35)); // Setting the 3rd card in the deck to a 9
    losingDeck.set(35, temp); // Replacing the 36th card with A

    temp = losingDeck.get(4);// Storing the 5th card in the deck (2)
    losingDeck.set(4, losingDeck.get(51)); // Setting the 4th card in the deck to a K
    losingDeck.set(51, temp); // Replacing the last card with 2

    temp = losingDeck.get(5);// Storing the 6th card in the deck (2)
    losingDeck.set(5, losingDeck.get(50)); // Setting the 5th card in the deck to a K
    losingDeck.set(50, temp); // Replacing the 51st card with 2


    // Rearranging the cards in winning deck so when they deal a pyramid with removal cards and
    // the game is winnable
    temp = winningDeck.get(1); // Storing the 2nd card in the deck (A)
    winningDeck.set(1, winningDeck.get(47)); // Setting the 2nd card in the deck to a Q
    winningDeck.set(47, temp); // Replacing the 48th card with A

    temp = winningDeck.get(2);// Storing the 3rd card in the deck (A)
    winningDeck.set(2, winningDeck.get(35)); // Setting the 3rd card in the deck to a 9
    winningDeck.set(35, temp); // Replacing the 36th card with A

    temp = winningDeck.get(3);// Storing the 3rd card in the deck (A)
    winningDeck.set(3, winningDeck.get(50)); // Setting the 3rd card in the deck to a K
    winningDeck.set(50, temp); // Replacing the 51st card with A

    temp = winningDeck.get(4);// Storing the 5th card in the deck (2)
    winningDeck.set(4, winningDeck.get(15)); // Setting the 4th card in the deck to a 4
    winningDeck.set(15, temp); // Replacing the 16th card with 2

    temp = winningDeck.get(5);// Storing the 6th card in the deck (2)
    winningDeck.set(5, winningDeck.get(51)); // Setting the 5th card in the deck to a K
    winningDeck.set(51, temp); // Replacing the last card with 2


    // Starting the games for each model
    losingModel.startGame(losingDeck, false, 3, 3);
    winningModel.startGame(winningDeck, false, 3, 3);
    emptyStockModel.startGame(emptyStockModel.getDeck(), false, 9, 7);

    // Removing cards here that are necessary to reach valid and failed cases
    losingModel.remove(2, 1);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS THROWN FOR INVALID REMOVAL OF TWO CARDS IN THE PYRAMID

  // Testing an exception is thrown when one of the cards (the bottom one) trying to be removed is
  // not there
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyBottomCard() {
    // removing cards to get to this test case
    losingModel.remove(2,0,1,0);
    // exception case
    losingModel.remove(2,2,1,0);
  }

  // Testing an exception is thrown when the two cards are visible but don't sum to 13
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSum() {
    // removing cards to get to this test case
    losingModel.remove(2,0,1,0);
    losingModel.remove(2,2);
    // exception case
    losingModel.remove(1,1,0,0);
  }

  // Testing an exception is thrown when two cards on the same row are trying to be removed and
  // they don't sum to 13 (reassuring that an existing case still fails)
  @Test(expected = IllegalArgumentException.class)
  public void testRegularCase() {
    winningModel.remove(2,0,2,1);
  }

  // Testing an exception is thrown when one of the cards trying to be removed is still
  // covered by two cards
  @Test(expected = IllegalArgumentException.class)
  public void testCoveredCard() {
    winningModel.remove(2,0,1,0);
  }

  // Testing an exception is thrown when one of the cards trying to be removed is not behind the
  // top card
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPosition() {
    winningModel.remove(2,0,1,1);
  }

  // Testing an exception is thrown when both cards are not there
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCards() {
    // removing cards to get to this test case
    winningModel.remove(2,0);
    winningModel.remove(2,2);
    winningModel.remove(2,1, 1, 1);
    // exception case
    winningModel.remove(2,1, 1, 1);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE VALID CASES FOR REMOVING CARDS FROM THE PYRAMID


  // Testing that two cards that are overlaying each other and the bottom card is not covered by
  // another card allows for both to be removed when the cards sum to 13
  @Test
  public void testValidTwoCardRemoval() {
    // removing cards in order to allow for a valid removal of two overlaying cards
    winningModel.remove(2,0);
    winningModel.remove(2, 2);
    // test case
    winningModel.remove(2, 1,1, 1);
    assertEquals(null, winningModel.getCardAt(2, 1));
    assertEquals(null, winningModel.getCardAt(1, 1));

    // edge case where both cards are at the beginning of their respective rows
    losingModel.remove(2,0,1,0);
    assertEquals(null, losingModel.getCardAt(2, 0));
    assertEquals(null, losingModel.getCardAt(1, 0));
  }

  // Testing that when two cards are left in the pyramid that are overlaying each other,
  // isGameOver() returns the correct result considering the new relaxed case
  @Test
  public void testIsGameOver() {
    // removing cards in order to reach the case where there are two cards left in the pyramid
    winningModel.remove(2,0);
    winningModel.remove(2, 2);
    winningModel.remove(2, 1,1, 1);
    // isGameOver() should return false because the cards in the pyramid can be removed
    assertFalse(winningModel.isGameOver());

    // removing cards in order to reach the case where there are two cards left in the pyramid
    losingModel.remove(2,0,1,0);
    losingModel.remove(2, 2);
    // isGameOver() should return false because the stock pile is not empty
    assertFalse(losingModel.isGameOver());

    // isGameOver() should return true because the stock pile is empty and the cards in the
    // pyramid can't be removed because there isn't a pair that sums to 13
    assertTrue(emptyStockModel.isGameOver());
  }
}