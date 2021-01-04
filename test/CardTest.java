import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardNumber;
import cs3500.pyramidsolitaire.model.hw02.CardSuit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This class tests the behavior of public methods of Card.
 */
public class CardTest {
  Card aceHeart;
  Card aceHeart2;
  Card twoSpade;
  Card tenDiamond;
  Card queenClub;

  @Before
  public void setUp() {
    aceHeart = new Card(CardNumber.A, CardSuit.Heart);
    aceHeart2 = new Card(CardNumber.A, CardSuit.Heart);
    twoSpade = new Card(CardNumber.TWO, CardSuit.Spade);
    tenDiamond = new Card(CardNumber.TEN, CardSuit.Diamond);
    queenClub = new Card(CardNumber.Q, CardSuit.Club);
  }

  // Testing Card is represented in a string by its number and suit
  @Test
  public void testToString() {
    assertEquals("A Heart", aceHeart.toString());
    assertEquals("2 Spade", twoSpade.toString());
    assertEquals("10 Diamond", tenDiamond.toString());
    assertEquals("Q Club", queenClub.toString());
  }

  // Testing that two cards that have the same fields are equal and two cards that don't are not.
  @Test
  public void testEquals() {
    assertEquals(false, aceHeart.equals(twoSpade));
    assertEquals(true, aceHeart.equals(aceHeart));
    assertEquals(true, aceHeart.equals(aceHeart2));
  }

  // Testing that two different objects are not equal using equals(Object)
  @Test
  public void testEqualsDifferentObjects() {
    assertEquals(false, aceHeart.equals("A Heart"));
    assertEquals(false, aceHeart.equals(1));
  }

  // Testing that two card that have the same fields share the same hashCode, and two cards that
  // don't have the same fields produce different hashCodes.
  @Test
  public void testHashCode() {
    assertNotEquals(twoSpade.hashCode(), aceHeart.hashCode());
    assertEquals(aceHeart.hashCode(), aceHeart.hashCode());
    assertEquals(aceHeart.hashCode(), aceHeart2.hashCode());
  }

  // Testing that two different objects do not have the same hashCode
  @Test
  public void testHashCodeDifferentObjects() {
    String str = "A Heart";
    assertNotEquals(str.hashCode(), aceHeart.hashCode());
    assertNotEquals(CardNumber.A.hashCode(), aceHeart.hashCode());
  }

}