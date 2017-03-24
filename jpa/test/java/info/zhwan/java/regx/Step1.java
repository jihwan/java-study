package info.zhwan.java.regx;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class Step1 extends AbstractRegExTest {
  /**
   * ABC : 'ABC' 문자열을 찾는다.
   */
  @Test
  public void test() {
    Pattern p = Pattern.compile("ABC");
    Matcher m = p.matcher("ABC12345ABCabcdef");

    assertThat(findResolver(m)).isEqualTo(2);
  }


  /**
   * ^ABC : 'ABC' 문자열이 맨 처음 시작할 것이라고 기대하고 찾는다.
   */
  @Test
  public void testCaret() {
    Pattern p = Pattern.compile("^ABC");
    Matcher m = p.matcher("ABC12345ABC");

    assertThat(findResolver(m)).isEqualTo(1);

    assertThat(findResolver(p.matcher("abcABC"))).isEqualTo(0);
  }

  /**
   * $ABC : 'ABC' 문자열이 맨 마지막에 나올 것이라고 기대하며 찾는다.
   */
  @Test
  public void testDollar() {
    Pattern p = Pattern.compile("ABC$");
    Matcher m = p.matcher("ABC12345ABC");

    assertThat(findResolver(m)).isEqualTo(1);

    assertThat(findResolver(p.matcher("ABCabc"))).isEqualTo(0);
  }

  /**
   * \^ABC : '^ABC' 문자열을 찾고 싶을 경우. ('\' 를 escape 라고 한다.)
   */
  @Test
  public void testFindCaret() {
    Pattern p = Pattern.compile("\\^ABC");
    Matcher m = p.matcher("^ABC12345ABC");

    assertThat(findResolver(m)).isEqualTo(1);

    assertThat(findResolver(p.matcher("ABC12345ABC"))).isEqualTo(0);
  }

  /**
   * \$ABC : '$ABC' 문자열을 찾고 싶을 경우. ('\' 를 escape 라고 한다.)
   */
  @Test
  public void testFindDollar() {
    Pattern p = Pattern.compile("ABC\\$");
    Matcher m = p.matcher("ABC$12345ABCABC$");

    assertThat(findResolver(m)).isEqualTo(2);

    assertThat(findResolver(p.matcher("ABC12345ABC"))).isEqualTo(0);
  }

  /**
   * . : 어떤 문자 1개
   * ... : 어떤 문자 3개
   * ..... : 어떤 문자 5개
   */
  @Test
  public void testDot() {
    Pattern p = Pattern.compile(".");
    Matcher m = p.matcher("^$.12ab");
    assertThat(findResolver(m)).isEqualTo(7);

    p = Pattern.compile("...");
    m = p.matcher("^$.12ab");
    assertThat(findResolver(m)).isEqualTo(2); // ^$. 와 12a 결국, 어떤문자 3개씩 반복해서 찾음

    p = Pattern.compile("\\..");
    m = p.matcher("^$.12ab");
    assertThat(findResolver(m)).isEqualTo(1); // .1
  }
}
