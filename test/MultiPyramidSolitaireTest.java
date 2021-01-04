import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardNumber;
import cs3500.pyramidsolitaire.model.hw02.CardSuit;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;


/**
 * This class tests the behavior of the methods in MultiPyramidSolitaire that applies new rules
 * to a basic game of pyramid solitaire.
 */
public class MultiPyramidSolitaireTest {
  PyramidSolitaireModel<Card> model;
  List<Card> deck;

  @Before
  public void setUp() {
    model = new MultiPyramidSolitaire();
    deck = model.getDeck();
  }

  // --------------------------------------------------------------------------------------------
  // THIS SECTION TESTS startGame() FOR THE NEW RULES OF MultiPyramidSolitaire

  // Testing that startGame() throws an exception when numRows is greater than 8 for a game of
  // MultiPyramid
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidRow() {
    model.startGame(deck, true, 9, 3);
  }

  // Testing that startGame() throws an exception when deck is less than 104 cards
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckLess() {
    deck.remove(2);
    model.startGame(deck, true, 5, 3);
  }

  // Testing that startGame() throws an exception when deck has more than two of each card
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckGreater() {
    deck.add(new Card(CardNumber.EIGHT, CardSuit.Club));
    model.startGame(deck, true, 7, 3);
  }

  // Testing that startGame() throws an exception when deck has null cards
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckNullCards() {
    deck.set(3, null);
    model.startGame(deck, true, 7, 3);
  }

  // Testing that startGame() throws an exception when deck is null
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidNullDeck() {
    model.startGame(null, true, 3, 3);
  }

  // Testing that startGame() throws an exception when there are more than 2 of each card in the
  // deck
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckRepeatingCards() {
    deck.set(6, new Card(CardNumber.FIVE, CardSuit.Diamond));
    deck.set(18, new Card(CardNumber.FIVE, CardSuit.Diamond));
    deck.set(26, new Card(CardNumber.FIVE, CardSuit.Diamond));
    model.startGame(deck, true, 3, 3);
  }

  // --------------------------------------------------------------------------------------------
  // THIS SECTION TESTS getDeck() FOR THE NEW RULES OF MultiPyramidSolitaire

  // Testing that getDeck() returns a deck of 104 cards
  @Test
  public void testDeckTotal() {
    assertEquals(104, model.getDeck().size());
  }

  // Testing that getDeck() returns a deck with only two of each card
  @Test
  public void testCardsRepeat() {
    int countRepeat = 0; // number of cards that repeat more than once

    for (Card c: deck) {
      // the difference between the two indices should be 52 since getDeck() doesn't shuffle the
      // cards and returns them in the same order twice
      if (deck.lastIndexOf(c) - deck.indexOf(c) != 52) {
        countRepeat++;
      }
    }
    assertEquals(0, countRepeat);
  }

  // Testing that getDeck() doesn't return a null deck
  @Test
  public void testNullDeck() {
    assertNotEquals(null, model.getDeck());
  }

  // Testing that getDeck() doesn't return a deck with null cards
  @Test
  public void testNullCards() {
    int countNull = 0; // number of cards that are null
    for (Card c: deck) {
      if (c == null) {
        countNull++;
      }
    }
    assertEquals(0, countNull);
  }

  // --------------------------------------------------------------------------------------------
  // THIS SECTION TESTS getRowWidth()

  // Testing a pyramid that has 1 row (for edge case)
  @Test
  public void testWidth1Row() {
    model.startGame(deck, false, 1, 3);
    assertEquals(1, model.getRowWidth(0));
  }

  // Testing a pyramid that has 4 rows (for case of even number of rows)
  @Test
  public void testWidth4Rows() {
    model.startGame(deck, false, 4, 3);

    assertEquals(5, model.getRowWidth(0)); // first row, with null cards
    assertEquals(6, model.getRowWidth(1)); // second row, without null cards
    assertEquals(7, model.getRowWidth(2)); // third row, without null cards
    assertEquals(8, model.getRowWidth(3)); // last row, without null cards
  }

  // Testing a pyramid that has 5 rows (for case of odd number of rows)
  @Test
  public void testWidth5Rows() {
    model.startGame(deck, false, 5, 3);

    assertEquals(5, model.getRowWidth(0)); // first row, with null cards
    assertEquals(6, model.getRowWidth(1)); // second row, without null cards
    assertEquals(7, model.getRowWidth(2)); // third row, without null cards
    assertEquals(8, model.getRowWidth(3)); // third row, without null cards
    assertEquals(9, model.getRowWidth(4)); // last row, without null cards
  }

  // Testing a pyramid that has 8 rows (for edge case)
  @Test
  public void testWidth8Rows() {
    model.startGame(deck, false, 8, 3);

    assertEquals(9, model.getRowWidth(0)); // first row, with null cards
    assertEquals(10, model.getRowWidth(1)); // second row, with null cards
    assertEquals(11, model.getRowWidth(2)); // third row, with null cards
    assertEquals(12, model.getRowWidth(3)); // fourth row, without null cards
    assertEquals(13, model.getRowWidth(4)); // fifth row, without null cards
    assertEquals(16, model.getRowWidth(7)); // last row, without null cards
  }

  // --------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE createPyramid() METHOD FOR PYRAMIDS OF DIFFERENT NUMBER OF ROWS

  // Testing a pyramid that has 1 row (for edge case)
  @Test
  public void testFirstEdgeCase() {
    model.startGame(deck, false, 1, 3);
    // Checking the general structure of the pyramid
    assertEquals(1, model.getNumRows());
    assertEquals(1, model.getRowWidth(0));
  }

  // Testing a pyramid that has 2 rows (for case of even rows without null cards in first row)
  @Test
  public void test2Rows() {
    boolean nullCards = false;
    model.startGame(deck, false, 2, 3);
    assertEquals(2, model.getNumRows());
    assertEquals(3, model.getRowWidth(0));
    assertEquals(4, model.getRowWidth(1));

    for (int i = 0; i < model.getNumRows(); i++) {
      for (int j = 0; j < model.getRowWidth(i); j++) {
        nullCards = nullCards || model.getCardAt(i, j) == null;
      }
    }

    assertFalse(nullCards); // checking if there are any null cards in a pyramid with 2 rows
  }

  // Testing a pyramid that has 3 rows (for case of odd rows without null cards in first row)
  @Test
  public void test3Rows() {
    boolean nullCards = false;
    model.startGame(deck, false, 3, 3);
    // Checking the general structure of the pyramid
    assertEquals(3, model.getNumRows());
    assertEquals(3, model.getRowWidth(0)); // edge case (first row)
    assertEquals(4, model.getRowWidth(1));
    assertEquals(5, model.getRowWidth(2)); // edge case (last row)

    for (int i = 0; i < model.getNumRows(); i++) {
      for (int j = 0; j < model.getRowWidth(i); j++) {
        nullCards = nullCards || model.getCardAt(i, j) == null;
      }
    }

    assertFalse(nullCards); // checking if there are any null cards in a pyramid with 3 rows
  }

  // ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---
  // THIS SECTION TESTS PYRAMIDS OF 4 AND 5 ROWS

  // Testing that the first row of a pyramid that has 4 rows has null cards in the correct places
  @Test
  public void test4RowsFirstRow() {
    model.startGame(deck, false, 4, 3);

    // Checking the rows that have null cards
    assertNotEquals(null, model.getCardAt(0, 0)); // check card isn't null
    assertNull(model.getCardAt(0, 1)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 2)); // check card isn't null
    assertNull(model.getCardAt(0, 3)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 4)); // check card isn't null
  }

  // Testing that the first row of a pyramid that has 5 rows has null cards in the correct places
  @Test
  public void test5RowsFirstRow() {
    model.startGame(deck, false, 5, 3);

    // Checking the rows that have null cards
    assertNotEquals(null, model.getCardAt(0, 0)); // check card isn't null
    assertNull(model.getCardAt(0, 1)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 2)); // check card isn't null
    assertNull(model.getCardAt(0, 3)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 4)); // check card isn't null
  }

  // ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---
  // THIS SECTION TESTS PYRAMIDS OF 6 AND 7 ROWS

  // Testing a pyramid that has 6 rows (for card of even rows with null cards in second row) has
  // the correct numbers of cards in each row
  @Test
  public void test6RowsStructure() {
    model.startGame(deck, false, 6, 3);

    // Checking the general structure of the pyramid
    assertEquals(6, model.getNumRows());
    assertEquals(7, model.getRowWidth(0)); // edge case of row with null cards
    assertEquals(8, model.getRowWidth(1)); // second row with null cards
    assertEquals(9, model.getRowWidth(2)); // edge case of row without null cards
    assertEquals(12, model.getRowWidth(5)); // edge case of last row
  }

  // Testing that the first row of a pyramid that has 6 rows has null cards in the correct places
  @Test
  public void test6RowsFirstRow() {
    model.startGame(deck, false, 6, 3);

    // Checking the first row that has null cards
    assertNotEquals(null, model.getCardAt(0, 0)); // check card isn't null
    assertNull(model.getCardAt(0, 1)); // check card is null
    assertNull(model.getCardAt(0, 2)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 3)); // check card isn't null
    assertNull(model.getCardAt(0, 4)); // check card is null
    assertNull(model.getCardAt(0, 5)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 6)); // check card isn't null
  }

  // Testing that the second row of a pyramid that has 6 rows has null cards in the correct places
  @Test
  public void test6RowsSecondRow() {
    model.startGame(deck, false, 6, 3);

    // Check the second row that has null cards
    assertNotEquals(null, model.getCardAt(1, 0)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 1)); // check card isn't null
    assertNull(model.getCardAt(0, 2)); // check card is null
    assertNotEquals(null, model.getCardAt(1, 3)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 4)); // check card isn't null
    assertNull(model.getCardAt(0, 5)); // check card is null
    assertNotEquals(null, model.getCardAt(1, 6)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 7)); // check card isn't null
  }

  // Testing a pyramid that has 7 rows (for card of odd rows with null cards in second row) has
  // the correct numbers of cards in each row
  @Test
  public void test7RowsStructure() {
    model.startGame(deck, false, 7, 3);

    // Checking the general structure of the pyramid
    assertEquals(7, model.getNumRows());
    assertEquals(7, model.getRowWidth(0)); // edge case of row with null cards
    assertEquals(8, model.getRowWidth(1)); // second row with null cards
    assertEquals(9, model.getRowWidth(2)); // edge case of row without null cards
    assertEquals(13, model.getRowWidth(6)); // edge case of last row
  }

  // Testing that the first row of a pyramid that has 7 rows has null cards in the correct places
  @Test
  public void test7RowsFirstRow() {
    model.startGame(deck, false, 7, 3);

    // Checking the first row that has null cards
    assertNotEquals(null, model.getCardAt(0, 0)); // check card isn't null
    assertNull(model.getCardAt(0, 1)); // check card is null
    assertNull(model.getCardAt(0, 2)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 3)); // check card isn't null
    assertNull(model.getCardAt(0, 4)); // check card is null
    assertNull(model.getCardAt(0, 5)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 6)); // check card isn't null
  }

  // Testing that the second row of a pyramid that has 7 rows has null cards in the correct places
  @Test
  public void test7RowsSecondRow() {
    model.startGame(deck, false, 7, 3);

    // Check the second row that has null cards
    assertNotEquals(null, model.getCardAt(1, 0)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 1)); // check card isn't null
    assertNull(model.getCardAt(0, 2)); // check card is null
    assertNotEquals(null, model.getCardAt(1, 3)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 4)); // check card isn't null
    assertNull(model.getCardAt(0, 5)); // check card is null
    assertNotEquals(null, model.getCardAt(1, 6)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 7)); // check card isn't null
  }

  // ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---  ---
  // THIS SECTION TESTS PYRAMID OF 8 ROWS

  // Testing a pyramid that has 8 rows (for edge case) has the correct numbers of cards in each row
  @Test
  public void testLastEdgeCase() {
    model.startGame(deck, false, 8, 3);

    // Checking the general structure of the pyramid
    assertEquals(8, model.getNumRows());
    assertEquals(9, model.getRowWidth(0)); // edge case of row with null cards
    assertEquals(10, model.getRowWidth(1)); // second row with null cards
    assertEquals(11, model.getRowWidth(2)); // third row with null cards
    assertEquals(12, model.getRowWidth(3)); // edge case of row without null cards
    assertEquals(16, model.getRowWidth(7)); // edge case of last row
  }

  // Testing that the first row of a pyramid that has 8 rows has null cards in the correct places
  @Test
  public void test8RowsFirstRow() {
    model.startGame(deck, false, 8, 3);

    // Checking the first row that has null cards
    assertNotEquals(null, model.getCardAt(0, 0)); // check card isn't null
    assertNull(model.getCardAt(0, 1)); // check card is null
    assertNull(model.getCardAt(0, 2)); // check card is null
    assertNull(model.getCardAt(0, 3)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 4)); // check card isn't null
    assertNull(model.getCardAt(0, 5)); // check card is null
    assertNull(model.getCardAt(0, 6)); // check card is null
    assertNull(model.getCardAt(0, 7)); // check card is null
    assertNotEquals(null, model.getCardAt(0, 8)); // check card isn't null
  }

  // Testing that the second row of a pyramid that has 8 rows has null cards in the correct places
  @Test
  public void test8RowsSecondRow() {
    model.startGame(deck, false, 8, 3);

    // Check the second row that has null cards
    assertNotEquals(null, model.getCardAt(1, 0)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 1)); // check card isn't null
    assertNull(model.getCardAt(0, 2)); // check card is null
    assertNull(model.getCardAt(0, 3)); // check card is null
    assertNotEquals(null, model.getCardAt(1, 4)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 5)); // check card isn't null
    assertNull(model.getCardAt(0, 6)); // check card is null
    assertNull(model.getCardAt(0, 7)); // check card is null
    assertNotEquals(null, model.getCardAt(1, 8)); // check card isn't null
    assertNotEquals(null, model.getCardAt(1, 9)); // check card isn't null
  }

  // Testing that the second row of a pyramid that has 8 rows has null cards in the correct places
  @Test
  public void test8RowsThirdRow() {
    model.startGame(deck, false, 8, 3);

    // Check the second row that has null cards
    assertNotEquals(null, model.getCardAt(2, 0)); // check card isn't null
    assertNotEquals(null, model.getCardAt(2, 1)); // check card isn't null
    assertNotEquals(null, model.getCardAt(2, 2)); // check card isn't null
    assertNull(model.getCardAt(0, 3)); // check card is null
    assertNotEquals(null, model.getCardAt(2, 4)); // check card isn't null
    assertNotEquals(null, model.getCardAt(2, 5)); // check card isn't null
    assertNotEquals(null, model.getCardAt(2, 6)); // check card isn't null
    assertNull(model.getCardAt(0, 7)); // check card is null
    assertNotEquals(null, model.getCardAt(2, 8)); // check card isn't null
    assertNotEquals(null, model.getCardAt(2, 9)); // check card isn't null
    assertNotEquals(null, model.getCardAt(2, 10)); // check card isn't null
  }
}