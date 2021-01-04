package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a standard card that has a number from 1 - 13 (where 1 is Ace, 11 is Jack, 12 is
 * Queen, and 13 is King) and a suit that can either be Clubs, Diamonds, Hearts, or Spade.
 */
public class Card {
  private final CardNumber number;
  private final CardSuit suit;

  /**
   * Creates an instance of a Card given its number and suit.
   * @param num The numeric value of the card
   * @param suit The suit of the card
   */
  public Card(CardNumber num, CardSuit suit) {
    this.number = num;
    this.suit = suit;
  }

  /**
   * Creates a representation of card using its number and suit.
   * @return a string representation of a card
   */
  @Override
  public String toString() throws IllegalArgumentException {
    String suitName = this.suit.name();
    switch (suitName) {
      case "Club": suitName = "♣";
      break;
      case "Diamond": suitName = "♦";
      break;
      case "Heart": suitName = "♥";
      break;
      case "Spade": suitName = "♠";
      break;
      default: throw new IllegalArgumentException("Invalid suit");
    }
    return this.number.toString() + suitName;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Card)) {
      return false;
    }
    else {
      Card card2 = (Card)o;
      return this.number.equals(card2.number) && this.suit.equals(card2.suit);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, suit);
  }

}
