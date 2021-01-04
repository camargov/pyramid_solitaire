package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.List;

/**
 * Represents a relaxed version of the game Pyramid Solitaire.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire
    implements PyramidSolitaireModel<Card> {

  /**
   * The constructor for this class which inherits the fields from BasicPyramidSolitaire.
   */
  public RelaxedPyramidSolitaire() {
    super();
  }

  /**
   * Execute a two-card move on the pyramid, using the two specified card positions. Considers
   * the new relaxed case where one covered card can be removed if it's covered by a card with
   * one of  the given coordinates and the two cards sum to 13.
   * @param row1    row of first card position, numbered from 0 from the top of the pyramid
   * @param card1   card of first card position, numbered from 0 from left
   * @param row2    row of second card position
   * @param card2   card of second card position
   * @throws IllegalArgumentException if the move is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalArgumentException,
      IllegalStateException {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    else if (row1 < 0 || row2 < 0 || row1 >= this.pyramid.length || row2 >= this.pyramid.length) {
      throw new IllegalArgumentException("Invalid row.");
    }
    else if (card1 < 0 || card2 < 0 || card1 >= this.pyramid.length
        || card2 >= this.pyramid.length) {
      throw new IllegalArgumentException("Invalid column.");
    }
    else if (!(this.isCardVisible(row1, card1) || this.isCardVisible(row2, card2))
        || !this.cardsOverlaying(row1, card1, row2, card2)) {
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

  /**
   * Determines whether two cards from each list sum to 13 so that they can be removed. Adding to
   * the conditions the case where the bottom card is covered by only one card and the sum of
   * that top card and bottom card is 13.
   * @param list1 first list of cards
   * @param list2 second list of cards
   * @return true if cards can be removed, false if not
   */
  @Override
  protected boolean canRemoveTwoCards(List<Card> list1, List<Card> list2) {
    boolean remove = false;
    for (Card value : list1) {
      for (Card card : list2) {
        remove = remove || this.cardValue(value) + this.cardValue(card) == 13;
      }
    }
    return remove || this.canRemoveOverlayingCards();
  }

  /**
   * Determines if one card is only covered by a second card.
   * @param row1 row of first card position, numbered from 0 from the top of the pyramid
   * @param card1 card of first card position, numbered from 0 from left
   * @param row2 row of second card position
   * @param card2 card of second card position
   * @return true if bottom card is only covered by the other card with the given coordinates
   */
  protected boolean cardsOverlaying(int row1, int card1, int row2, int card2) {
    Card top = this.getCardAt(row2, card2); // assuming second coordinates are the top card
    Card left = null; // default
    Card right = null; // default

    if (row1 < row2) {
      // reset card that's below and right of bottom card
      right = this.getCardAt(row1 + 1, card1 + 1);
      left = this.getCardAt(row1 + 1, card1);  // assuming bottom card is first in row

    }

    if (row2 < row1) {
      top = this.getCardAt(row1, card1); // first coordinates are top card
      // setting card that's below and to the right of bottom card
      right = this.getCardAt(row2 + 1, card2 + 1);
      // setting card that's below and to the left of bottom card
      left = this.getCardAt(row2 + 1, card2);
    }

    // if the new case applies, then true, if the cards are in the same row or are both null,
    // then return false
    return !(left == null && right == null)
        && ((left == null && right == top) || (left == top && right == null));
  }

  /**
   * Determines if there are cards in the pyramid in which the bottom card is only covered by the
   * second card and the two cards sum to 13.
   * @return true if cards that are overlaying each other can be removed from the pyramid.
   */
  protected boolean canRemoveOverlayingCards() {
    boolean removable = false;
    int leftValue; // value of bottom left card
    int rightValue; // value of bottom right card
    int value; // value of current card
    Card currentCard; // current card
    Card rightCard; // bottom right card
    Card leftCard; // bottom left card

    for (int i = 0; i < this.pyramid.length; i++) {
      for (int j = 0; j < this.getRowWidth(i); j++) {
        // Making sure that a card in the last row is not being checked if there's a card
        // covering it because that can result in an error
        if (i != this.pyramid.length - 1) {
          currentCard = this.getCardAt(i, j);
          rightCard = this.getCardAt(i + 1, j + 1);
          leftCard = this.getCardAt(i + 1, j);

          if (currentCard != null && rightCard != null) {
            value = this.cardValue(currentCard);
            rightValue = this.cardValue(rightCard);

            // If cards are overlaying and they sum to 13 return true
            removable = removable || (this.cardsOverlaying(i, j,i + 1,j + 1)
                && (value + rightValue == 13));
          }
          if (currentCard != null && leftCard != null) {
            value = this.cardValue(currentCard);
            leftValue = this.cardValue(leftCard);

            // If cards are overlaying and they sum to 13 return true
            removable = removable
                || (this.cardsOverlaying(i, j,i + 1, j) && (value + leftValue == 13));
          }
        }
      }
    }
    return removable;
  }

  // Overriding the equals method for comparing models to only check that both objects are of the
  // same type of model.
  @Override
  public boolean equals(Object o) {
    return o instanceof RelaxedPyramidSolitaire;
  }

  // Overriding the hashCode method for comparing models to only check that both objects are of the
  // same type of model.
  @Override
  public int hashCode() {
    return 1;
  }
}
