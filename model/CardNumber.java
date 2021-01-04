package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents the numbers for a standard card where 1 is represented by Ace (A), 11 is Jack (J),
 * 12 is Queen (Q) and 13 is King (K).
 */
public enum CardNumber {
  A(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
  SEVEN(7), EIGHT(8), NINE(9), TEN(10), J(11), Q(12),
  K(13);

  private final int value;

  /**
   * An instance of a CardNumber using the given value to determine the numeric value of this
   * CardNumber.
   * @param value the value of this CardNumber
   */
  CardNumber(int value) {
    this.value = value;
  }

  // ** Does each case end because of return statement? Check in test **
  @Override
  public String toString() {
    switch (this.value) {
      case 1:
        return "A";
      case 11:
        return "J";
      case 12:
        return "Q";
      case 13:
        return "K";
      default:
        return Integer.toString(this.value);
    }
  }
}
