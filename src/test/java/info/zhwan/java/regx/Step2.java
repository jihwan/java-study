package info.zhwan.java.regx;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class Step2 extends AbstractRegExTest {
  /**
   * [abc] : [] 로 감싸진 문자를 찾는다. a 혹은 b 혹은 c 가 들어간 문자를 모두 찾는다.
   * [] 는 문자 1개 이다.
   */
  @Test
  public void testSquareBrackets() {
    Pattern p = Pattern.compile("[oyu]");
    Matcher m = p.matcher("How do you do?");
    assertThat(findResolver(m)).isEqualTo(6);

    p = Pattern.compile("[o].");
    m = p.matcher("How do you do?");
    assertThat(findResolver(m)).isEqualTo(4); // 'ow'   'o '   'ou'   'o?'


    p = Pattern.compile("[o][wu]");
    m = p.matcher("How do you do?");
    assertThat(findResolver(m)).isEqualTo(2); // 'ow'   'ou'
  }

  /**
   * [ABC] 이렇게 적을수도 있지만 '-' 를 사용하여 [A-C] 이렇게 표현가능하다.
   */
  @Test
  public void testDash() {
    Pattern p = Pattern.compile("[ㄱ-ㄷ]");
    Matcher m = p.matcher("abcdefgABCDEFG1234567ㄱㄴㄷㄹㅁㅂ");
    assertThat(findResolver(m)).isEqualTo(3);

    p = Pattern.compile("[a-c1-4ㄱㄹ]");
    m = p.matcher("abcdefgABCDEFG1234567ㄱㄴㄷㄹㅁㅂ");
    assertThat(findResolver(m)).isEqualTo(9);
  }

  /**
   * [^] : [] 내부에 ^ 는 not 의 의미임.
   * [^ABC] 는 A 혹은 B 혹은 C 를 제외한 것을 찾겠다는 의미
   */
  @Test
  public void testCaretInSquareBrackets() {
    Pattern p = Pattern.compile("[^ㄱ-ㄷ]");
    Matcher m = p.matcher("abcdefgABCDEFG1234567ㄱㄴㄷㄹㅁㅂ");
    assertThat(findResolver(m)).isEqualTo(24);

  }
}