/**
 * Created on 10/8/18
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 0.1
 */
public class GraphicsDemo {


  public static void main(String[] args) {
    StdDraw.enableDoubleBuffering();

    double theta = 0;
    while (true) {
      StdDraw.clear();

      StdDraw.setPenRadius(0.05);
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.point(0.5, 0.5);
      StdDraw.setPenColor(StdDraw.MAGENTA);
      StdDraw.line(0.2, 0.2, 0.8, 0.2);

      StdDraw.setPenColor(StdDraw.GREEN);
      StdDraw.point(0.5 + 0.25 * Math.cos(theta),
                    0.5 + 0.25 * Math.sin(theta));

      StdDraw.setPenRadius(0.005);
      StdDraw.circle(0.5, 0.5, 0.25);

      StdDraw.show();

      theta += 0.001;
    }

  }

}
