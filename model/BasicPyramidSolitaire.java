package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Represents the model for a basic game of pyramid solitaire.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  protected boolean gameStarted; // the state of the game
  protected Card [][] pyramid; // the pyramid of the game containing the playing cards
  protected List<Card> drawCards; // the draw cards that are visible to the user
  protected List<Card> stockPile; // the rest of the draw cards
  protected int maxDraw; // the max amount of cards from draw pile that are visible at a time
  protected int rows;
  private Random rand; // allows for a random shuffle in startGame() if applicable

  /**
   * Sets up a basic game of pyramid solitaire where the game hasn't started yet, there's a
   * random object in case cards need to be shuffled and the piles for draw cards and the stock
   * pile are empty lists.
   */
  public BasicPyramidSolitaire() {
    this.gameStarted = false;
    this.drawCards = new ArrayList<Card>();
    this.stockPile = new ArrayList<Card>();
    this.maxDraw = 3;
    this.rand = new Random();
  }

  /**
   * Sets up a basic game of pyramid solitaire for testing random when the cards
   * need to be shuffled.
   * @param seed a random object that can be used for testing
   */
  public BasicPyramidSolitaire(int seed) {
    this.gameStarted = false;
    this.drawCards = new ArrayList<Card>();
    this.stockPile = new ArrayList<Card>();
    this.rand = new Random(seed);
  }

  // This method creates a new deck by using the constructor of Card
  @Override
  public List<Card> getDeck() {
    // This is the list of card numbers
    List<CardNumber> numList = Arrays.asList(CardNumber.A, CardNumber.TWO, CardNumber.THREE,
        CardNumber.FOUR, CardNumber.FIVE, CardNumber.SIX, CardNumber.SEVEN, CardNumber.EIGHT,
        CardNumber.NINE, CardNumber.TEN, CardNumber.J, CardNumber.Q, CardNumber.K);

    // This is the list of suits
    List<CardSuit> suitList = Arrays.asList(CardSuit.Club, CardSuit.Diamond, CardSuit.Heart,
        CardSuit.Spade);

    // This is the empty list to fill with cards
    List<Card> cardList = new ArrayList<Card>();

    for (int num = 0; num < numList.size(); num++) {
      for (int suit = 0; suit < suitList.size(); suit++) {
        cardList.add(new Card(numList.get(num), suitList.get(suit)));
      }
    }
    return cardList;
  }


  // This method starts the game and sets up the fields of this class by filling in the pyramid,
  // draw cards, stock pile and setting gameStarted and numDraw
  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    if (deck == null || this.isDeckInvalid(deck, numRows, numDraw)) {
      throw new IllegalArgumentException("Invalid deck.");
    }
    else if (numRows <= 0 || this.isMaxRow(numRows)) {
      throw new IllegalArgumentException("Invalid number of rows in pyramid.");
    }
    else if (numDraw < 0) {
      throw new IllegalArgumentException("Invalid number of draw cards");
    }
    else if (shuffle) {
      List<Card> randomList = new ArrayList<Card>();
      while (randomList.size() < deck.size()) {
        Card nextCard = deck.get(rand.nextInt(deck.size()));
        if (randomList.contains(nextCard)) {
          nextCard = deck.get(rand.nextInt(deck.size()));
        }
        else {
          randomList.add(nextCard);
        }
      }
      this.pyramid =  new Card [numRows][numRows];
      this.maxDraw = numDraw;
      this.gameStarted = true;
      this.rows = numRows;
      this.createPyramid(randomList);
    }
    else {
      this.pyramid =  new Card [numRows][numRows];
      this.maxDraw = numDraw;
      this.gameStarted = true;
      this.rows = numRows;
      this.createPyramid(deck);
    }
  }

  /**
   * Determines whether the deck is invalid.
   * @param deck List of cards to be used in the game.
   * @return true if deck is invalid, false if deck is valid.
   */
  protected boolean isDeckInvalid(List<Card> deck, int numRows, int numDraw) {
    boolean deckNull = false;
    boolean repeat = false;
    List<Card> copy = new ArrayList<Card>();
    for (int i = 0; i < deck.size(); i++) {
      deckNull = deckNull || deck.get(i) == null;
      repeat = repeat || copy.contains(deck.get(i));
      copy.add(deck.get(i));
    }
    return deckNull || repeat || deck.size() < ((numRows * (numRows + 1)) / 2) + numDraw
        || deck.size() < 52;
  }

  /**
   * Determines whether the given number of rows to start the game is not more than the
   * max number of rows to play.
   * @param numRows The given number of rows.
   * @return true if the given number of rows is more than 9.
   */
  protected boolean isMaxRow(int numRows) {
    return numRows > 9;
  }

  /**
   * Updates the pyramid field and draw pile to include the deck which will be used in the game.
   * @param deck List of cards to be used in the pyramid and the draw pile
   */
  protected void createPyramid(List<Card> deck) {
    int index = 0;
    int column = 0;

    // this fills up the pyramid with the given deck
    for (int row = 0; row < this.pyramid.length; row++) {
      while (column < row + 1) {
        this.pyramid[row][column] = deck.get(index);
        index = index + 1;
        column = column + 1;
      }
      column = 0;
    }

    // this fills up the draw pile
    for (int i = 0; i < this.maxDraw; i++) {
      this.drawCards.add(deck.get(index));
      index = index + 1;
    }

    // this fills up the stock pile with the rest of the cards
    for (int j = 0; j < 52 - (((this.pyramid.length
        * (this.pyramid.length + 1)) / 2) + this.maxDraw); j++) {
      this.stockPile.add(deck.get(index));
      index = index + 1;
    }
  }


  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else if (row1 < 0 || row2 < 0 || row1 >= this.pyramid.length || row2 >= this.pyramid.length) {
      throw new IllegalArgumentException("Invalid row.");
    }
    else if (card1 < 0 || card2 < 0 || card1 >= this.pyramid.length
        || card2 >= this.pyramid.length) {
      throw new IllegalArgumentException("Invalid column.");
    }
    else if (!this.isCardVisible(row1, card1) || !this.isCardVisible(row2, card2)) {
      throw new IllegalArgumentException("At least one card is not visible.");
    }
    else if (this.cardValue(getCardAt(row1, card1))
        + this.cardValue(getCardAt(row2, card2)) != 13) {
      throw new IllegalArgumentException("Cards don't sum to 13.");
    }
    else {
      this.pyramid[row1][card1] = null;
      this.pyramid[row2][card2] = null;
    }
  }


  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else if (row < 0 || row >= this.pyramid.length) {
      throw new IllegalArgumentException("Invalid row");
    }
    else if (card < 0 || card >= this.pyramid.length) {
      throw new IllegalArgumentException("Invalid column");
    }
    else if (!this.isCardVisible(row, card)) {
      throw new IllegalArgumentException("Card is not visible.");
    }
    else if (this.cardValue(getCardAt(row, card)) != 13) {
      throw new IllegalArgumentException("Card does not have a value of 13.");
    }
    else {
      this.pyramid[row][card] = null;
    }
  }


  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else if (row < 0 || row >= this.pyramid.length) {
      throw new IllegalArgumentException("Invalid row.");
    }
    else if (card < 0 || card >= this.pyramid.length) {
      throw new IllegalArgumentException("Invalid column.");
    }
    else if (drawIndex < 0 || drawIndex > this.maxDraw) {
      throw new IllegalArgumentException("Invalid index for draw card.");
    }
    else if (!this.isCardVisible(row, card)) {
      throw new IllegalArgumentException("Card in the pyramid is not visible.");
    }
    else if (this.cardValue(getCardAt(row, card))
        + this.cardValue(this.drawCards.get(drawIndex)) != 13) {
      throw new IllegalArgumentException("Cards don't sum to 13.");
    }
    else {
      this.pyramid[row][card] = null;
      this.discardDraw(drawIndex);
    }
  }

  /**
   * Determines whether the card at the given coordinates is visible.
   * @param row row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card column of the desired card position, numbered from 0 from the left of the pyramid
   * @return true if card is visible, false is card is not visible.
   */
  protected boolean isCardVisible(int row, int card) {
    // if the given row is not the last one in the pyramid, check if the next row of cards covers
    // this current card
    if (row != this.pyramid.length - 1) {
      return this.pyramid[row + 1][card + 1] == null && this.pyramid[row + 1][card] == null;
    }
    else {
      return row == this.pyramid.length - 1;
    }
  }


  /**
   * Finds the card's value using its representation as a string.
   * @param card the card whose value is needed
   * @return the card's value as an int
   */
  protected int cardValue(Card card) {
    String cardNum = card.toString().substring(0, 2);
    String firstStr = cardNum.substring(0,1);

    if (cardNum.equals("10")) {
      return 10;
    }
    else {
      switch (firstStr) {
        case "A": return 1;
        case "J": return 11;
        case "Q": return 12;
        case "K": return 13;
        default: return Integer.parseInt(firstStr);
      }
    }
  }


  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else if (drawIndex < 0 || drawIndex >= this.maxDraw) {
      throw new IllegalArgumentException("Invalid index.");
    }
    else if (this.drawCards.size() <= drawIndex) {
      throw new IllegalArgumentException("No card at provided index.");
    }
    else {
      if (this.stockPile.size() > 0) {
        Card temp = this.stockPile.get(0);
        this.drawCards.set(drawIndex, temp);
        this.stockPile.remove(temp);
      }
      else {
        this.drawCards.set(drawIndex, null);
      }
    }
  }


  @Override
  public int getNumRows() {
    if (!gameStarted) {
      return -1;
    }
    else {
      return this.rows;
    }
  }


  @Override
  public int getNumDraw() {
    if (!gameStarted) {
      return -1;
    }
    else {
      return this.drawCards.size();
    }
  }


  @Override
  public int getRowWidth(int row) throws IllegalStateException, IllegalArgumentException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else if (row < 0 || row > this.pyramid.length - 1) {
      throw new IllegalArgumentException("Row is invalid.");
    }
    else {
      return row + 1;
    }
  }


  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else {
      List<Card> drawList = this.getDrawCards();
      List<Card> visibleCards = new ArrayList<Card>();
      for (int i = 0; i < this.pyramid.length; i++) {
        for (int j = 0; j < this.getRowWidth(i); j++) {
          if (this.pyramid[i][j] != null && this.isCardVisible(i, j)) {
            visibleCards.add(this.pyramid[i][j]);
          }
        }
      }
      return (this.getScore() == 0) || (!this.canRemoveCard(visibleCards)
          && !this.canRemoveTwoCards(visibleCards, drawList)
          && !this.canRemoveTwoCards(visibleCards, visibleCards) && this.stockPile.size() == 0);
    }
  }

  /**
   * Determines whether the list of cards has a card that has a value of 13 so that it can be
   * removed.
   * @param visibleCards list of visible cards to check
   * @return true if a card can be removed, false if not
   */
  private boolean canRemoveCard(List<Card> visibleCards) {
    boolean remove = false;
    for (int i = 0; i < visibleCards.size(); i++) {
      remove = remove || this.cardValue(visibleCards.get(i)) == 13;
    }
    return remove;
  }

  /**
   * Determines whether two cards from each list sum to 13 so that they can be removed.
   * @param list1 first list of cards
   * @param list2 second list of cards
   * @return true if cards can be removed, false if not
   */
  protected boolean canRemoveTwoCards(List<Card> list1, List<Card> list2) {
    boolean remove = false;
    for (int i = 0; i < list1.size(); i++) {
      for (int j = 0; j < list2.size(); j++) {
        remove = remove || this.cardValue(list1.get(i)) + this.cardValue(list2.get(j)) == 13;
      }
    }
    return remove;
  }


  @Override
  public int getScore() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    int score = 0;
    for (int i = 0; i < this.pyramid.length; i++) {
      for (int j = 0; j < this.pyramid.length; j++) {
        if (this.pyramid[i][j] != null) {
          score = score + this.cardValue(this.pyramid[i][j]);
        }
      }
    }
    return score;
  }


  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException, IllegalArgumentException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else if (row < 0 || card < 0 || row >= this.rows || card >= this.getRowWidth(row)) {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
    else {
      Card copy = this.pyramid[row][card];
      return copy;
    }
  }


  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else {
      List<Card> availableCards = new ArrayList<Card>();
      for (int i = 0; i < this.drawCards.size(); i++) {
        if (this.drawCards.get(i) != null) {
          availableCards.add(this.drawCards.get(i));
        }
      }
      return availableCards;
    }
  }

  // Overriding the equals method for comparing models to only check that both objects are of the
  // same type of model.
  @Override
  public boolean equals(Object o) {
    return o instanceof BasicPyramidSolitaire;
  }

  // Overriding the hashCode method for comparing models to only check that both objects are of the
  // same type of model.
  @Override
  public int hashCode() {
    return 0;
  }
}
