import java.util.HashMap;
import java.util.Scanner;

/**
 * Created on 9/24/18
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 0.1
 */
public class EditDistance {

  static HashMap<String, Integer> memos = new HashMap<>();

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine().toUpperCase();
    String t = scanner.nextLine().toUpperCase();
    System.out.println(editDistance(s, t));
  }

  /**
   * Computes the edit (or Levenstein) distance between two strings
   *
   * @param s a string to compare
   * @param t a string to compare
   * @return the minimal number of edits to convert s to t, or vice versa
   */
  static int editDistance(String s, String t) {
    System.out.println(s + "\t" + t);
    if (s.length() == 0) {
      return t.length();
    } else if (t.length() == 0) {
      return s.length();
    }

    String key = s + "," + t;
    if (memos.containsKey(key)) {
      return memos.get(key);
    } else if (s.charAt(0) == t.charAt(0)) { // we know that s and t have at least one character
      int editDist = editDistance(s.substring(1), t.substring(1));
      memos.put(key, editDist);
      return editDist;
    } else {
      // s and t have positive length and their first characters do not match

      // first chars could be != from a mutation
      int mutDist = 1 + editDistance(s.substring(1), t.substring(1));

      // first chars could be != from an insertion in s
      int insertInSDist = 1 + editDistance(s.substring(1), t);

      // first chars could be != from an insertion in t
      int insertInTDist = 1 + editDistance(s, t.substring(1));

      int editDist = Math.min(mutDist, Math.min(insertInSDist, insertInTDist));
      memos.put(key, editDist);
      return editDist;
    }
  }

}
