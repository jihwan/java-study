package info.zhwan.java;

import org.junit.Test;

/**
 * @author zhwan
 */
public class PrimativeTest {
  @Test
  public void name() throws Exception {

    String a = new String("a");
    String b = new String("a");
    System.err.println(a + "\t" + b);
    System.err.println( a == b );
    System.err.println(a.equals(b));

    System.err.println("==========================================================");
    b = a;
    System.err.println(a + "\t" + b);
    System.err.println( a == b );
    System.err.println(a.equals(b));

    System.err.println("==========================================================");
    a = "aaaaaaa";
    b = a;
    System.err.println(a + "\t" + b);
    System.err.println( a == b );
    System.err.println(a.equals(b));


    System.err.println("==========================================================");
    a = "ccc";
    String c = "ccc";
    System.err.println(a + "\t" + c);
    System.err.println( a == c );
    System.err.println(a.equals(c));

  }
}
