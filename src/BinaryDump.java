/**
 * Created by knappa on 12/11/18.
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 0.1
 */
public class BinaryDump {

  public static void main(String[] args) {

    boolean bit;
    while(!BinaryStdIn.isEmpty()) {
      bit = BinaryStdIn.readBoolean();
      if(bit)
        System.out.println("1");
      else
        System.out.println("0");
    }

  }


}
