import cs3500.pyramidsolitaire.model.hw02.CardNumber;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This class tests the behavior for the methods of CardNumber.
 */
public class CardNumberTest {


  // Testing that toString() returns the letter for a card number if its value is 1, 11, 12, or 13
  @Test
  public void testToStringLetters() {
    assertEquals("A", CardNumber.A.toString());
    assertEquals("J", CardNumber.J.toString());
    assertEquals("Q", CardNumber.Q.toString());
    assertEquals("K", CardNumber.K.toString());
  }

  // Testing that toString() returns the number for a card number if its value isn't 1, 11, 12,
  // or 13
  @Test
  public void testToStringNumbers() {
    assertEquals("2", CardNumber.TWO.toString());
    assertEquals("3", CardNumber.THREE.toString());
    assertEquals("4", CardNumber.FOUR.toString());
    assertEquals("5", CardNumber.FIVE.toString());
    assertEquals("6", CardNumber.SIX.toString());
    assertEquals("7", CardNumber.SEVEN.toString());
    assertEquals("8", CardNumber.EIGHT.toString());
    assertEquals("9", CardNumber.NINE.toString());
    assertEquals("10", CardNumber.TEN.toString());
  }
}