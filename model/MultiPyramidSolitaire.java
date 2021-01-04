package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardNumber;
import cs3500.pyramidsolitaire.model.hw02.CardSuit;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a version of the game Pyramid Solitaire that uses multiple pyramids.
 */
public class MultiPyramidSolitaire extends BasicPyramidSolitaire
    implements PyramidSolitaireModel<Card> {

  /**
   * Constructor for a game of Pyramid Solitaire that has multiple pyramids.
   */
  public MultiPyramidSolitaire() {
    super();
  }


  // This method creates a valid deck for a game of Multi Pyramid Solitaire which is 2 standard
  // decks (104 cards) with no repeating cards nor null cards
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

    for (CardNumber cardNumber : numList) {
      for (CardSuit cardSuit : suitList) {
        cardList.add(new Card(cardNumber, cardSuit));
      }
    }
    // Add the cards in the list again
    cardList.addAll(cardList);

    return cardList;
  }



  // This method determines if the deck used for a game of MultiPyramidSolitaire is valid
  @Override
  protected boolean isDeckInvalid(List<Card> deck, int numRows, int numDraw) {

    boolean deckNull = false;
    List<Card> sub = new ArrayList<Card>();
    boolean repeat = false;

    for (Card c: deck) {
      deckNull = deckNull || c == null;
      repeat = repeat || (deck.indexOf(c) == deck.lastIndexOf(c));

      // making sure that the first index is lower than the second index
      if (deck.indexOf(c) != deck.lastIndexOf(c)) {
        sub = deck.subList(deck.indexOf(c) + 1, deck.lastIndexOf(c));
        repeat = repeat || sub.contains(c);
      }
    }
    return deckNull || repeat || deck.size() != 104;
  }



  // This method determines if the given number of rows is greater than max number of rows needed
  // to play the game. For this game of MultiPyramidSolitaire, the pyramid can have no more than
  // 8 rows
  @Override
  protected boolean isMaxRow(int numRows) {
    return numRows > 8;
  }



  // This method creates a pyramid for a game of MultiPyramidSolitaire
  @Override
  protected void createPyramid(List<Card> deck) {
    int firstRow; // how many columns should the first row have
    int index = 0; // keeps track of the index of the deck of cards

    // If the number of rows is odd
    if ((this.rows % 2) != 0) {
      firstRow = this.rows;
      // making the pyramid bigger for this version of the game
      this.pyramid = new Card[this.rows][(this.rows * 2) - 1];
    }
    // If the number of rows is even
    else {
      firstRow = this.rows + 1;
      // making the pyramid bigger for this version of the game
      this.pyramid = new Card[this.rows][this.rows * 2];
    }

    // this fills up the pyramid with the given deck
    for (int r = 0; r < this.rows; r++) { // going through the rows
      for (int c = 0; c < firstRow + r; c++) { // going through the columns
        if (!this.isNullCard(r, c)) {
          this.pyramid[r][c] = deck.get(index);
        }
        index++; // increasing the index for the deck of cards
      }
    }

    // this fills up the draw pile
    for (int i = 0; i < this.maxDraw; i++) {
      this.drawCards.add(deck.get(index));
      index = index + 1;
    }

    // this fills up the stock pile with the rest of the cards
    for (int j = 0; j < 104 - index; j++) {
      this.stockPile.add(deck.get(index));
      index = index + 1;
    }
  }


  // Determines if a card with the given coordinates in the pyramid should be null
  private boolean isNullCard(int row, int column) {
    int half = Math.round(this.getRowWidth(row) / 2); // the middle card in the row

    // For the first row in pyramid
    if (row == 0) {
      return (column != 0) && (column != this.getRowWidth(row) - 1) && (column != half);
    }
    // For the second row in pyramid
    else if (row == 1) {
      return (column != 0) && (column != 1) && (column != half) && (column != half - 1)
          && (column != this.getRowWidth(row) - 1) && (column != this.getRowWidth(row) - 2);
    }
    // For the second row and 8 rows in pyramid
    else if (row == 2 && this.rows == 8) {
      return (column != 0) && (column != 1) && (column != 2) // first three cards
          && (column != half - 1) && (column != half) && (column != half + 1) // middle cards
          && (column != this.getRowWidth(row) - 3) && (column != this.getRowWidth(row) - 2)
          && (column != this.getRowWidth(row) - 1); // last three cards
    }
    else {
      return false;
    }
  }


  // Returning the numbers of spaces in the given row, starting from the first card to the last
  // card.
  @Override
  public int getRowWidth(int row) throws IllegalStateException, IllegalArgumentException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else if (row < 0 || row > this.rows - 1) {
      throw new IllegalArgumentException("Row is invalid.");
    }
    else {
      if (this.rows % 2 == 0) {
        return this.rows + 1 + row;
      }
      else {
        return this.rows + row;
      }
    }
  }


  // Overriding the equals method for comparing models to only check that both objects are of the
  // same type of model.
  @Override
  public boolean equals(Object o) {
    return o instanceof MultiPyramidSolitaire;
  }

  // Overriding the hashCode method for comparing models to only check that both objects are of the
  // same type of model.
  @Override
  public int hashCode() {
    return 2;
  }
}
