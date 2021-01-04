import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardNumber;
import cs3500.pyramidsolitaire.model.hw02.CardSuit;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This class tests the behavior of public methods of the model for a pyramid solitaire game.
 */
public class BasicPyramidSolitaireTest {
  PyramidSolitaireModel<Card> gameNotStarted;
  PyramidSolitaireModel<Card> gamePlay;
  PyramidSolitaireModel<Card> gameShuffle;
  PyramidSolitaireModel<Card> gameRemoveCards;
  PyramidSolitaireModel<Card> gameWin;
  PyramidSolitaireModel<Card> gameEmptyStock;
  List<Card> cardList;
  List<Card> modifiedList;
  List<Card> winList;
  List<Card> emptyStockList;


  // Sets up the instances of the pyramid model to use in unit tests
  @Before
  public void setUp() {
    gameNotStarted = new BasicPyramidSolitaire();
    gamePlay = new BasicPyramidSolitaire();
    gameShuffle = new BasicPyramidSolitaire(2);
    gameRemoveCards = new BasicPyramidSolitaire();
    gameWin = new BasicPyramidSolitaire();
    gameEmptyStock = new BasicPyramidSolitaire();

    cardList = gamePlay.getDeck();
    modifiedList = gameRemoveCards.getDeck();
    winList = gameWin.getDeck();
    emptyStockList = gameEmptyStock.getDeck();

    // Setting the fourth card in the deck to a King
    Card temp = modifiedList.get(3);
    modifiedList.set(3, modifiedList.get(51));
    modifiedList.set(51, temp);

    // Setting the sixth card in the deck to J
    Card pair2 = modifiedList.get(5);
    modifiedList.set(5, modifiedList.get(41));
    modifiedList.set(41, pair2);

    // Setting the seventh card in the deck to J
    Card draw = modifiedList.get(6);
    modifiedList.set(6, modifiedList.get(42));
    modifiedList.set(42, draw);

    // Changing the cards that will fill the pyramid to A, Q, K so that the game is able to be
    // won easily
    Card currentCard = winList.get(2);
    winList.set(2, winList.get(47)); // Changing the card at (1, 1) to Q
    winList.set(47, currentCard);
    currentCard = winList.get(4);
    winList.set(4, winList.get(46)); // Changing the card at (2, 1) to Q
    winList.set(46, currentCard);
    currentCard = winList.get(5);
    winList.set(5, winList.get(51)); // Changing the card at (2, 2) to K
    winList.set(51, currentCard);
    currentCard = winList.get(6);
    winList.set(6, winList.get(45)); // Changing the draw card at index 0 to Q
    winList.set(45, currentCard);

    // Changes the placement between a queen and ace to allow for a valid removeUsingDraw() outcome
    Card c = emptyStockList.get(44);
    emptyStockList.set(44, emptyStockList.get(0));
    emptyStockList.set(0, c);

    // Starting the games
    gamePlay.startGame(cardList, false, 3, 3);
    gameShuffle.startGame(cardList, true, 3, 3);
    gameRemoveCards.startGame(modifiedList, false, 3, 3);
    gameWin.startGame(winList, false, 3, 3);
    gameEmptyStock.startGame(emptyStockList, false, 9, 7);

    // Removing cards from the pyramid to result in an empty pyramid
    gameWin.remove(2, 2);
    gameWin.remove(2, 0, 2, 1);
    gameWin.remove(1, 0, 1, 1);
    gameWin.removeUsingDraw(0, 0,0);
  }

  // ---------------------------------------------------------------------------------------------

  // THIS FIRST SECTION TESTS THE IllegalStateException AMONG DIFFERENT METHODS

  // Testing that remove(int, int, int, int) throws an exception if it's called before the game
  // has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedRemoveTwoCards() {
    gameNotStarted.remove(0,0,1,0);
  }

  // Testing that remove(int, int) throws an exception if it's called before the game
  // has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedRemoveOneCard() {
    gameNotStarted.remove(0,0);
  }

  // Testing that removeUsingDraw() throws an exception if it's called before the game has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedRemoveUsingDraw() {
    gameNotStarted.removeUsingDraw(1, 0,0);
  }

  // Testing that discardDraw() throws an exception if it's called before the game
  // has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedDiscardDraw() {
    gameNotStarted.discardDraw(1);
  }

  // Testing that getRowWidth() throws an exception if it's called before the game has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedGetRowWidth() {
    gameNotStarted.getRowWidth(1);
  }

  // Testing that isGameOver() throws an exception if it's called before the game has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedIsGameOver() {
    gameNotStarted.isGameOver();
  }

  // Testing that getScore() throws an exception if it's called before the game has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedGetScore() {
    gameNotStarted.getScore();
  }

  // Testing that getCardAt() throws an exception if it's called before the game has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedGetCardAt() {
    gameNotStarted.getCardAt(0,0);
  }

  // Testing that getDrawCards() throws an exception if it's called before the game has started
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedGetDrawCards() {
    gameNotStarted.getDrawCards();
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS getDeck() AND startGame()

  // Testing getDeck() returns a valid list of cards (52 cards, no repeating cards, no null
  // cards, no null deck)
  @Test
  public void testGetDeck() {
    List<Card> tempList = gameNotStarted.getDeck();
    assertNotEquals(null, tempList);
    assertEquals(52, tempList.size());

    boolean nullCard = false;
    boolean repeat = false;
    List<Card> copy = new ArrayList<Card>();

    for (int i = 0; i < 52; i++) {
      nullCard = nullCard || tempList.get(i) == null;
      repeat = repeat || copy.contains(tempList.get(i));
      copy.add(tempList.get(i));
    }

    assertEquals(false, nullCard);
    assertEquals(false, repeat);
  }

  // Testing startGame() shuffles the given deck in a random order when shuffle is true by
  // determining how many cards are in a different order than the given deck
  @Test
  public void testStartGameShuffle() {
    assertNotEquals(cardList.get(0), gameShuffle.getCardAt(0,0));
    assertNotEquals(cardList.get(1), gameShuffle.getCardAt(1,0));
    assertNotEquals(cardList.get(2), gameShuffle.getCardAt(1,1));
    assertNotEquals(cardList.get(3), gameShuffle.getCardAt(2,0));
    assertNotEquals(cardList.get(4), gameShuffle.getCardAt(2,1));
    assertNotEquals(cardList.get(5), gameShuffle.getCardAt(2,2));
  }

  // Testing the pyramid stays in tact when startGame() is provided the argument shuffle = false
  @Test
  public void testStartGameNoShufflePyramid() {
    assertEquals(cardList.get(0), gamePlay.getCardAt(0,0));
    assertEquals(cardList.get(1), gamePlay.getCardAt(1,0));
    assertEquals(cardList.get(2), gamePlay.getCardAt(1,1));
    assertEquals(cardList.get(3), gamePlay.getCardAt(2,0));
    assertEquals(cardList.get(4), gamePlay.getCardAt(2,1));
    assertEquals(cardList.get(5), gamePlay.getCardAt(2,2));
  }

  // Testing the draw cards are in order when shuffle is false for startGame()
  @Test
  public void testStartGameNoShuffleDraw() {
    assertEquals(cardList.get(6), gamePlay.getDrawCards().get(0));
    assertEquals(cardList.get(7), gamePlay.getDrawCards().get(1));
    assertEquals(cardList.get(8), gamePlay.getDrawCards().get(2));
  }

  // Testing that a game in progress is restarted correctly when startGame() is called
  @Test
  public void testStartGameInProgress() {
    gameWin.startGame(cardList, false, 4, 4);
    assertEquals(18, gameWin.getScore());
    assertEquals(4, gameWin.getNumRows());
    assertEquals(4, gameWin.getRowWidth(3));
  }

  // --------------------------------------------------------------------------------------------
  // THIS SECTION TESTS EXCEPTION FOR startGame() WHEN THE DECK IS INVALID

  // Testing that startGame() throws an exception when the deck is null
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNullDeck() {
    gameNotStarted.startGame(null, false, 3, 3);
  }

  // Testing that startGame() throws an exception when the cards in the deck are null
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNullCards() {
    cardList.set(5, null);
    gameNotStarted.startGame(cardList, false, 3, 3);
  }

  // Testing that startGame() throws an exception when the cards in the deck repeat
  @Test(expected = IllegalArgumentException.class)
  public void testRepeatingCards() {
    cardList.set(8, new Card(CardNumber.A, CardSuit.Club));
    gameNotStarted.startGame(cardList, false, 3, 3);
  }

  // Testing that startGame() throws an exception when there aren't 52 cards in the deck
  @Test(expected = IllegalArgumentException.class)
  public void testLessThan52Cards() {
    cardList.remove(5);
    gameNotStarted.startGame(cardList, false, 3, 3);
  }

  // Testing that startGame() throws an exception if a full pyramid and draw pile can't be dealt
  // with number of cards in deck
  @Test(expected = IllegalArgumentException.class)
  public void testInsufficientCardAmount() {
    gameNotStarted.startGame(cardList, false, 15, 5);
  }

  // --------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS FOR startGame() WHEN THE PYRAMID ROW OR NUMBER OF DRAW
  // CARDS IS INVALID

  // Testing that startGame() throws an exception when the pyramid rows width is zero
  @Test(expected = IllegalArgumentException.class)
  public void testZeroRows() {
    gameNotStarted.startGame(cardList, false, 0, 3);
  }

  // Testing that startGame() throws an exception when the pyramid rows width is negative
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRows() {
    gameNotStarted.startGame(cardList, false, -3, 3);
  }

  // Testing that startGame() throws an exception when the pyramid has more than 9 rows
  @Test(expected = IllegalArgumentException.class)
  public void testTooManyRows() {
    gameNotStarted.startGame(cardList, false, 10, 3);
  }

  // Testing that startGame() throws an exception when the number of draw cards is negative
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeDrawCards() {
    gameNotStarted.startGame(cardList, false, 3, -3);
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THE METHODS THAT REMOVE CARDS FROM EITHER THE PYRAMID OR DRAW PILE

  // Testing the correct cards are removed (sum to 13 and visible) for remove(int, int, int, int)
  @Test
  public void testRemoveTwoCards() {
    gameRemoveCards.remove(2, 1, 2, 2);
    assertEquals(null, gameRemoveCards.getCardAt(2,1));
    assertEquals(null, gameRemoveCards.getCardAt(2,2));
  }

  // Testing the card is 13 to be removed for remove(int, int)
  @Test
  public void testRemoveOneCard() {
    gameRemoveCards.remove(2, 0);
    assertEquals(null, gameRemoveCards.getCardAt(2,0));
  }

  // Testing the correct cards are removed (sum to 13 and visible) for removeUsingDraw()
  @Test
  public void testRemoveUsingDraw() {
    Card targetDraw = gameRemoveCards.getDrawCards().get(0);
    gameRemoveCards.removeUsingDraw(0, 2, 1);
    assertEquals(null, gameRemoveCards.getCardAt(2,1));
    assertNotEquals(targetDraw, gameRemoveCards.getDrawCards().get(0));
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTION CASES FOR remove(int, int, int, int)

  // Testing that remove(int, int, int, int) throws an exception when given row is negative
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwoCardsNegativeRow() {
    gamePlay.remove(-2, 0, 2, 1);
  }

  // Testing that remove(int, int, int, int) throws an exception when given column is negative
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwoCardsNegativeColumn() {
    gamePlay.remove(2, 0, 2, -1);
  }

  // Testing that remove(int, int, int, int) throws an exception when given row is greater than
  // pyramid size
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwoCardsNonExistentRow() {
    gamePlay.remove(4, 0, 2, 1);
  }

  // Testing that remove(int, int, int, int) throws an exception when given column is greater than
  // pyramid size
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwoCardsNonExistentColumn() {
    gamePlay.remove(2, 4, 2, 1);
  }

  // Testing that remove(int, int, int, int) throws an exception when at least one card isn't
  // visible
  @Test(expected = IllegalArgumentException.class)
  public void testNotVisibleRemoveTwoCards() {
    gamePlay.remove(0, 0, 2, 1);
  }

  // Testing that remove(int, int, int, int) throws an exception when cards don't sum to 13
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSumRemove() {
    gamePlay.remove(2, 0, 2, 1);
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTION CASES FOR remove(int, int)

  // Testing that remove(int, int) throws an exception when given row is negative
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNegativeRow() {
    gamePlay.remove(-1, 0);
  }

  // Testing that remove(int, int) throws an exception when given column is negative
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNegativeColumn() {
    gamePlay.remove(2, -1);
  }

  // Testing that remove(int, int) throws an exception when given row is greater than
  // pyramid size
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNonExistentRow() {
    gamePlay.remove(4, 0);
  }

  // Testing that remove(int, int) throws an exception when given column is greater than
  // pyramid size
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNonExistentColumn() {
    gamePlay.remove(2, 4);
  }

  // Testing that remove(int, int) throws an exception when card isn't visible
  @Test(expected = IllegalArgumentException.class)
  public void testNotVisibleRemoveCard() {
    gamePlay.remove(0, 0);
  }

  // Testing that remove(int, int) throws an exception when card doesn't have a value of 13
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidValueRemove() {
    gamePlay.remove(2, 0);
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTION CASES FOR removeUsingDraw()

  // Testing that removeUsingDraw() throws an exception when given row is negative
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawNegativeRow() {
    gamePlay.removeUsingDraw(0,-1, 0);
  }

  // Testing that removeUsingDraw() throws an exception when given column is negative
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawNegativeColumn() {
    gamePlay.removeUsingDraw(0,2, -1);
  }

  // Testing that removeUsingDraw() throws an exception when given row is greater than pyramid size
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawNonExistentRow() {
    gamePlay.removeUsingDraw(0,4, 0);
  }

  // Testing that removeUsingDraw() throws an exception when given column is greater than pyramid
  // size
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawNonExistentColumn() {
    gamePlay.removeUsingDraw(0,2, 4);
  }

  // Testing that removeUsingDraw() throws an exception when given index is negative
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawNegativeIndex() {
    gamePlay.removeUsingDraw(-1,2, 0);
  }

  // Testing that removeUsingDraw() throws an exception when given index is greater than the max
  // amount of draw cards
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawOutOfBoundsIndex() {
    gamePlay.removeUsingDraw(5,2, 0);
  }

  // Testing that removeUsingDraw throws an exception when the card in the pyramid isn't visible
  @Test(expected = IllegalArgumentException.class)
  public void testNotVisibleRemoveUsingDraw() {
    gamePlay.removeUsingDraw(0,0, 0);
  }

  // Testing that removeUsingDraw throws an exception when cards don't sum to 13
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSumRemoveUsingDraw() {
    gamePlay.removeUsingDraw(0,2, 0);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS discardDraw() FOR VALID AND INVALID CASES

  // Testing the correct card is removed from draw pile for discardDraw()
  @Test
  public void testDiscardDraw() {
    Card draw = gameRemoveCards.getDrawCards().get(0);
    gameRemoveCards.discardDraw(0);
    assertEquals(false, gameRemoveCards.getDrawCards().contains(draw));
  }

  // Testing that discardDraw() throws exception if the index is less than zero
  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDrawIndexNegative() {
    gamePlay.discardDraw(-1);

  }

  // Testing that discardDraw() throws exception if the index is greater than the maximum numbers
  // of cards that can be drawn
  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDrawIndexGreater() {
    gamePlay.discardDraw(5);
  }


  // Testing that discardDraw() throws an exception if a card isn't present at the given index
  // using a game that has a big enough pyramid where the stock is empty and when a draw card is
  // removed, the draw pile gets smaller
  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDrawNoCard() {
    gameEmptyStock.discardDraw(7);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS getNumRows() AND getNumDraw() FOR VALID AND INVALID CASES

  // Testing getNumRows() returns the correct number of rows in the pyramid
  @Test
  public void testGetNumRows() {
    assertEquals(3, gamePlay.getNumRows());
    assertNotEquals(2, gamePlay.getNumRows());
  }

  // Testing getNumDraw() returns the max number of visible draw cards
  @Test
  public void testGetNumDraw() {
    assertEquals(3, gamePlay.getNumDraw());
    assertNotEquals(2, gamePlay.getNumDraw());
    assertEquals(7, gameEmptyStock.getNumDraw());
    // removes a queen and ace from the pyramid to decrease the size of the draw pile
    gameEmptyStock.removeUsingDraw(0, 8, 8);
    assertEquals(7, gameEmptyStock.getNumDraw());
    // removes a card from the draw pile
    gameEmptyStock.discardDraw(1);
    assertEquals(7, gameEmptyStock.getNumDraw());
  }

  // Testing that methods that return an integer, return -1 when the game hasn't started
  @Test
  public void testGameNotStartedInteger() {
    assertEquals(-1, gameNotStarted.getNumRows());
    assertEquals(-1, gameNotStarted.getNumDraw());
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS getRowWidth(), isGameOver() AND getScore() FOR VALID CASES

  // Testing getRowWidth() works when given the correct row width: at least two row widths
  @Test
  public void testGetRowWidth() {
    assertEquals(1, gamePlay.getRowWidth(0));
    assertEquals(2, gamePlay.getRowWidth(1));
    assertEquals(3, gamePlay.getRowWidth(2));
  }

  // Testing isGameOver for when game is over and not over
  @Test
  public void testIsGameOver() {
    assertEquals(false, gamePlay.isGameOver());
    assertEquals(false, gameRemoveCards.isGameOver());
    assertEquals(true, gameWin.isGameOver());
    gameEmptyStock.removeUsingDraw(0, 8, 8);
    assertEquals(true, gameEmptyStock.isGameOver());
  }

  // Testing getScore() (score of zero, positive value, when game has ended, game in progress)
  @Test
  public void testGetScore() {
    assertEquals(8, gamePlay.getScore());
    gameRemoveCards.remove(2, 0);
    gameRemoveCards.remove(2, 1, 2, 2);
    assertEquals(3, gameRemoveCards.getScore());
    assertEquals(0, gameWin.getScore());
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTIONS TESTS THE VALID AND INVALID CASES FOR getCardAt()

  // Testing getCardAt() when given valid coordinates
  @Test
  public void testGetCardAt() {
    assertEquals(new Card(CardNumber.TWO, CardSuit.Club), gamePlay.getCardAt(2, 1));
    assertEquals(new Card(CardNumber.A, CardSuit.Diamond), gamePlay.getCardAt(1, 0));
    assertEquals(new Card(CardNumber.A, CardSuit.Club), gamePlay.getCardAt(0, 0));
  }

  // Testing that a null returns for getCardAt() when there's no card to get
  @Test
  public void testNoCard() {
    gameEmptyStock.removeUsingDraw(0, 8, 8);
    assertEquals(null, gameEmptyStock.getCardAt(8,8));
    assertEquals(null, gameWin.getCardAt(0,0));
  }

  // Testing that getCardAt() throws an exception when the given row is negative
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNegativeRow() {
    gamePlay.getCardAt(-1, 0);
  }

  // Testing that getCardAt() throws an exception when the given column is negative
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNegativeColumn() {
    gamePlay.getCardAt(2, -1);
  }

  // Testing that getCardAt() throws an exception when the given row is nonexistent
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNonExistentRow() {
    gamePlay.getCardAt(4, 0);
  }

  // Testing that getCardAt() throws an exception when the given column is nonexistent
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNonExistentColumn() {
    gamePlay.getCardAt(2,  4);
  }

  // Testing getDrawCards() returns the correct visible cards from the draw pile before and
  // after cards have been removed
  @Test
  public void testGetDrawCards() {
    assertEquals(new Card(CardNumber.J, CardSuit.Heart), gameRemoveCards.getDrawCards().get(0));
    assertEquals(3, gameRemoveCards.getDrawCards().size());
    gameRemoveCards.removeUsingDraw(0,2, 1);
    assertEquals(new Card(CardNumber.THREE, CardSuit.Diamond),
        gameRemoveCards.getDrawCards().get(0));
    assertEquals(new Card(CardNumber.Q, CardSuit.Diamond), gameEmptyStock.getDrawCards().get(0));
  }
}
