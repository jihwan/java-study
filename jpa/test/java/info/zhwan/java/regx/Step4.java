package info.zhwan.java.regx;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 수량자.
 * 이 단어는 뭔가 어려워 보이고, 어떤 의미 인지 느낌이 오지 않는다.
 * 여기서 수량자 의미는 어떤 단어의 반복 횟수 라고 이해 하자.
 */
public class Step4 extends AbstractRegExTest {
  /**
   * * : 0, 1, n
   * + : 1, n
   * ? : 0, 1
   */
  @Test
  public void testQuantifiers() {
    final String INPUT = "aabc abc bc";

    Pattern p = Pattern.compile("a*b");
    Matcher m = p.matcher(INPUT);
    assertThat(findResolver(m)).isEqualTo(3); // 'aab'  'ab' 'b'

    p = Pattern.compile("a+b");
    m = p.matcher(INPUT);
    assertThat(findResolver(m)).isEqualTo(2); // 'aab' 'ab'

    p = Pattern.compile("a?b");
    m = p.matcher(INPUT);
    assertThat(findResolver(m)).isEqualTo(3); // 'ab'  'ab'  'b'
  }

  @Test
  public void test1() {
    final String EXAM_INPUT = "-@- *** -- \"*\" -- *** -@-";

    Pattern p = Pattern.compile("-@?-");
    Matcher m = p.matcher(EXAM_INPUT);
    assertThat(findResolver(m)).isEqualTo(4);

    p = Pattern.compile("\\*\\*");
    m = p.matcher(EXAM_INPUT);
    assertThat(findResolver(m)).isEqualTo(2); // '**'  '**'
  }

  @Test
  public void test2() {
    final String EXAM_INPUT = "-@@@- * ** - - * -- * ** -@@@-";

    Pattern p = Pattern.compile("\\*+");
    Matcher m = p.matcher(EXAM_INPUT);
    assertThat(findResolver(m)).isEqualTo(5);

    p = Pattern.compile("-@+-");
    m = p.matcher(EXAM_INPUT);
    assertThat(findResolver(m)).isEqualTo(2); // '-@@@-'  '-@@@-'

    p = Pattern.compile("[^ ]+");
    m = p.matcher(EXAM_INPUT);
    assertThat(findResolver(m)).isEqualTo(10); // 공백을 제거한 모든 문자열
  }

  @Test
  public void test3() {
    final String EXAM_INPUT = "--XX-@-XX-@@-XX-@@@-XX-@@@@-XX-@@-@@-";

    Pattern p = Pattern.compile("-XX?X?X");
    Matcher m = p.matcher(EXAM_INPUT);
    assertThat(findResolver(m)).isEqualTo(5);

    p = Pattern.compile("-@?-");
    m = p.matcher(EXAM_INPUT);
    assertThat(findResolver(m)).isEqualTo(2);

    p = Pattern.compile("[^@]@?@"); // 2글짜 혹은 3글짜 maching
    m = p.matcher(EXAM_INPUT);
    assertThat(findResolver(m)).isEqualTo(6); // '-@' '-@@'  '-@@'  '-@@'  '-@@'  '-@@'
  }
}