/**
 * Created on 10/4/18
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 0.1
 */
public class Vector2D implements Vector {

  public static void main(String[] args) {
    // test cases

    Vector v = new Vector2D();
    Vector w = new Vector2D();

    try {
      Vector u = v.add(w);
      // do more very important stuff
      // and some more
      // and some more
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }

  /**
   * Computes the sum of two vectors, this and other
   *
   * @param other another Vector
   * @return the vector sum of this and other
   */
  @Override
  public Vector add(Vector other) {
    if (other instanceof Vector2D) {
      return this.add((Vector2D) other);
    } else {
      throw new RuntimeException("Cannot add vectors of different dimensions!");
    }
  }

  /**
   * Computes the sum of two vectors, this and other
   *
   * @param other another Vector
   * @return the vector sum of this and other
   */
  public Vector2D add(Vector2D other) {
    return null;
  }

  /**
   * Computes scalar multiple of this by d
   *
   * @param d the scalar
   * @return the scalar multiple of this by d
   */
  @Override
  public Vector2D multiply(double d) {
    return null;
  }
}
