/**
 * Created on 9/6/18
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 0.1
 */
public class CommandLine {

  public static void main(String[] args) {

    int firstArg = Integer.valueOf(args[0]);
    System.out.println(firstArg + 1);

    //System.out.println(Arrays.toString(args));

    /*
    for( int i = 0; i < args.length; i++) {
      System.out.println(args[i]);
    }
    */

  }
}
