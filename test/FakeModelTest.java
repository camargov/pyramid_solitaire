import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This is used for testing FakeModel to ensure that certain methods append to the output.
 */
public class FakeModelTest {
  PyramidSolitaireModel<Card> model;
  Appendable out;

  // Setting up the instances of model and output that will be used in the unit tests
  @Before
  public void setUp() {
    out = new StringBuilder();
    model = new FakeModel(out);
  }

  // Testing remove(int, int) adds to the appendable the correct message: rm1 num1 num2
  @Test
  public void testRemoveOne() {
    model.remove(1, 1);
    assertEquals("rm1 1 1", out.toString());

    model.remove(10, 12);
    assertEquals("rm1 1 1rm1 10 12", out.toString());
  }

  // Testing remove(int, int, int, int) adds to the appendable the correct message: rm2 num1 num2
  // num3 num4
  @Test
  public void testRemoveTwo() {
    model.remove(2, 2, 2,1);
    assertEquals("rm2 2 2 2 1", out.toString());

    model.remove(12, 2, 2,11);
    assertEquals("rm2 2 2 2 1rm2 12 2 2 11", out.toString());
  }

  // Testing removeUsindDraw() adds to the appendable the correct message: rmwd num1 num2 num 3
  @Test
  public void testRemoveUsingDraw() {
    model.removeUsingDraw(3, 2, 2);
    assertEquals("rmwd 3 2 2", out.toString());

    model.removeUsingDraw(12, 2, 1);
    assertEquals("rmwd 3 2 2rmwd 12 2 1", out.toString());
  }

  // Testing discardDraw() adds to the appendable the correct message: dd num
  @Test
  public void testDiscardDraw() {
    model.discardDraw(2);
    assertEquals("dd 2", out.toString());

    model.discardDraw(12);
    assertEquals("dd 2dd 12", out.toString());
  }

  // Testing multiple moves are appended in the correct order to the output
  @Test
  public void testMultipleMoves() {
    model.discardDraw(2);
    assertEquals("dd 2", out.toString());
    model.removeUsingDraw(3, 2, 2);
    assertEquals("dd 2rmwd 3 2 2", out.toString());
  }
}