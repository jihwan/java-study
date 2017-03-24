package info.zhwan.java;

import org.adrianwalker.multilinestring.Multiline;
import org.adrianwalker.multilinestring.SqlMultiline;
import org.junit.Test;

/**
 * Created by zhwan on 17. 3. 15.
 */
public class MultilineTest {

  /**
   * multi
   * line
   */
  @Multiline
  String m;

  /**
   * select [*+ hint *] *
   * from
   *  table
   */
  @SqlMultiline
  String sql;

  @Test
  public void testMultiline() throws Exception {
    System.out.println(m);
    System.out.println(sql);
  }
}
