/**
 * Created on 10/4/18
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 0.1
 */
public interface Vector {

  /**
   * Computes the sum of two vectors, this and other
   * @param other another Vector
   * @return the vector sum of this and other
   */
  Vector add(Vector other);

  /**
   * Computes scalar multiple of this by d
   * @param d the scalar
   * @return the scalar multiple of this by d
   */
  Vector multiply(double d);

}
