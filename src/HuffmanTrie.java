import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * A binary trie implementation of Huffman codes.
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 1.0
 */
public class HuffmanTrie {

  private final HuffmanNode root;

  /**
   * A private constructor for use inside class.
   *
   * @param root trie's root
   */
  private HuffmanTrie(HuffmanNode root) {
    this.root = Objects.requireNonNull(root);
  }

  /**
   * Compresses or decompresses standard in to standard out according to command line argument.
   *
   * @param args specifies compression (c) or decompression (d)
   */
  public static void main(String[] args) {

    if (args.length != 1) {
      System.err.println("Provide a single command line argument to say if");
      System.err.println("I should be compressing (c) or decompressing (d).");
      return;
    }

    switch (args[0]) {
      case "c":
        compressStdIn();
        break;
      case "d":
        decompressStdIn();
        break;
      default:
        System.err.println("Invalid argument");
    }

  }

  /**
   * Decompress standard input to standard output.
   */
  private static void decompressStdIn() {

    HuffmanTrie trie = HuffmanTrie.readBinaryRepresentation();

    char c = trie.readChar();
    while (c != '\0') {
      System.out.print(c);
      c = trie.readChar();
    }

  }

  /**
   * Compress standard input to standard output.
   */
  private static void compressStdIn() {

    ArrayList<Character> input = new ArrayList<>();

    Map<Character, Integer> charCounts = new HashMap<>();

    while (!BinaryStdIn.isEmpty()) {
      char c = BinaryStdIn.readChar();
      input.add(c);
      charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
    }

    // add EOF character
    input.add('\0');
    charCounts.put('\0', 1);

    // build the trie and get the character encodings
    HuffmanTrie trie = buildTrie(charCounts);
    Map<Character, Runnable> encoding = trie.getCode();

    // write everything to StdOut
    trie.writeBinaryRepresentation();
    input.forEach(c -> encoding.get(c).run());

    BinaryStdOut.flush();

  }

  /**
   * Constructs a HuffmanTrie based on the characters and counts given
   *
   * @param charCounts an array of counts of words such that {@code charCounts[(int)c]} is
   *                   the number of occurrences of the character {@code c}.
   * @return a new instance of {@code HuffmanTrie} with tree structure given by Huffman's algorithm.
   */
  private static HuffmanTrie buildTrie(Map<Character, Integer> charCounts) {

    assert charCounts != null;

    // create and populate a min-pq with the leaf nodes of the trie
    PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
    for (char c : charCounts.keySet()) {
      queue.add(new HuffmanNode(c, charCounts.get(c)));
    }

    // create internal nodes from the nodes of smallest count, until there is a single node
    while (queue.size() > 1) {
      queue.add(new HuffmanNode(queue.poll(), queue.poll()));
    }

    return new HuffmanTrie(queue.poll());

  }

  /**
   * Creates an new instance of {@code HuffmanTrie} by reading standard input using
   * {@code BinaryStdIn}.
   *
   * @return a new instance of {@code HuffmanTrie}
   */
  public static HuffmanTrie readBinaryRepresentation() {
    return new HuffmanTrie(readNode());
  }

  private static HuffmanNode readNode() {
    if (BinaryStdIn.readBoolean()) {
      return new HuffmanNode(BinaryStdIn.readChar(), 0);
    } else {
      return new HuffmanNode(readNode(), readNode());
    }
  }

  /**
   * Returns a {@code Map} giving Huffman code determined by this trie. Code words
   * are represented by Strings of 0's and 1's.
   *
   * @return {@code Map} of {@code Character}s to {@code Runnable}s. Where each
   * {@code Runnable} writes the corresponding char's codeword to BinaryStdOut as
   * specified by this trie
   */
  public Map<Character, Runnable> getCode() {
    Map<Character, Runnable> code = new HashMap<>();
    root.fillInCode(code, () -> {
    });
    return code;
  }

  /**
   * Reads an encoded character from standard input using {@code BinaryStdIn}
   *
   * @return decoded character. We let '\0' denote a special end-of-file character. The file
   * _should_ end after this character is returned and {@code readChar()} should not be called
   * again.
   */
  public char readChar() {
    return root.readChar();
  }

  /**
   * Gives string representation of the trie via its pre-order traversal.
   *
   * @return String representation of the trie via its pre-order traversal
   */
  @Override
  public String toString() {
    return root.preOrder().toString();
  }

  /**
   * Write the binary representation of this trie to standard output using {@code BinaryStdOut}.
   */
  private void writeBinaryRepresentation() {
    root.writeRep();
  }

  /**
   * Inner class for nodes of the binary trie.
   */
  private static class HuffmanNode implements Comparable<HuffmanNode> {
    final char data;
    final int count;
    final HuffmanNode left;
    final HuffmanNode right;

    HuffmanNode(char character, int charCount) {
      this.data = character;
      this.count = charCount;
      this.left = null;
      this.right = null;
    }

    HuffmanNode(HuffmanNode left, HuffmanNode right) {
      this.data = '\7'; // bell char :)
      this.left = Objects.requireNonNull(left);
      this.right = Objects.requireNonNull(right);
      this.count = left.count + right.count;
    }

    /**
     * Determines if this node is a leaf node
     *
     * @return {@code true} is {@code this} is a leaf (i.e. no children) Otherwise, {@code false}
     */
    boolean isLeaf() {
      return this.left == null;
    }

    /**
     * implementation of the comparable interface, so that nodes can be comparared by their stored
     * counts.
     *
     * @param other another HuffmanNode
     * @return signed int as specified by the Comparable interface
     */
    @Override
    public int compareTo(HuffmanNode other) {
      return Integer.compare(this.count, other.count);
    }

    /**
     * populates {@code Map} of codewords recursively.
     *
     * @param codeWords {@code Map} to populate
     * @param codeSoFar {@code Runnable} which writes prefix for all codewords under this node to
     *                  BinaryStdOut
     */
    void fillInCode(Map<Character, Runnable> codeWords, Runnable codeSoFar) {
      if (this.isLeaf()) {
        codeWords.put(data, codeSoFar);
      } else {
        left.fillInCode(codeWords, () -> {
          codeSoFar.run();
          BinaryStdOut.write(false);
        });
        right.fillInCode(codeWords, () -> {
          codeSoFar.run();
          BinaryStdOut.write(true);
        });
      }
    }

    void writeRep() {
      if (this.isLeaf()) {
        BinaryStdOut.write(true);
        BinaryStdOut.write(data);
      } else {
        BinaryStdOut.write(false);
        left.writeRep();
        right.writeRep();
      }
    }

    public char readChar() {
      if (this.isLeaf()) {
        return data;
      } else {
        if (BinaryStdIn.readBoolean()) {
          return right.readChar();
        } else {
          return left.readChar();
        }
      }
    }

    StringBuilder preOrder() {
      if (this.isLeaf()) {
        return new StringBuilder("1").append(data);
      } else {
        return new StringBuilder("0").append(left.preOrder()).append(right.preOrder());
      }
    }

    @Override
    public String toString() {
      return "(" + data + "," + count + ")";
    }

  }

}
