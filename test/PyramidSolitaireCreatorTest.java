import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This class tests the behavior of the methods of PyramidSolitaireCreator.
 */
public class PyramidSolitaireCreatorTest {
  PyramidSolitaireCreator creator;
  PyramidSolitaireModel<?> pyramid;

  // Sets up the variables to be used in the unit tests
  @Before
  public void setUp() {
    creator = new PyramidSolitaireCreator();
  }

  // Testing that a null object passed to create() throws an exception
  @Test(expected = NullPointerException.class)
  public void testNull() {
    creator.create(null);
  }

  // Testing that the create() method returns a BasicPyramidSolitaire model when the GameType is
  // BASIC
  @Test
  public void createBasic() {
    pyramid = creator.create(PyramidSolitaireCreator.GameType.BASIC);
    assertEquals(pyramid, new BasicPyramidSolitaire());
    assertEquals(pyramid.hashCode(), new BasicPyramidSolitaire().hashCode());
  }

  // Testing that the create() method returns a RelaxedPyramidSolitaire model when the GameType is
  // RELAXED
  @Test
  public void createRelaxed() {
    pyramid = creator.create(PyramidSolitaireCreator.GameType.RELAXED);
    assertEquals(pyramid, new RelaxedPyramidSolitaire());
    assertEquals(pyramid.hashCode(), new RelaxedPyramidSolitaire().hashCode());
  }

  // Testing that the create() method returns a MultiPyramidSolitaire model when the GameType is
  // MULTIPYRAMID
  @Test
  public void createMulti() {
    pyramid = creator.create(PyramidSolitaireCreator.GameType.MULTIPYRAMID);
    assertEquals(pyramid, new MultiPyramidSolitaire());
    assertEquals(pyramid.hashCode(), new MultiPyramidSolitaire().hashCode());
  }
}