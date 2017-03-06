package info.zhwan.java.regx;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 수량자에서는 특정 문자의 반복 횟수, 반복 횟수 범위를 지정 할 수 있다.
 */
public class Step5 extends AbstractRegExTest {

  final String INPUT = "One ring to bring them all and in the darkness bind them";

  @Test
  public void X문자가3번반복() {
    Pattern p = Pattern.compile("x{3}");
    Matcher m = p.matcher("abx xbc xx xxx");
    assertThat(findResolver(m)).isEqualTo(1);
  }

  @Test
  public void X문자가2번이상4번이하() {
    Pattern p = Pattern.compile("x{2,4}");
    Matcher m = p.matcher("abx xbc xx xxx xxxx xxxxxbbb");
    assertThat(findResolver(m)).isEqualTo(4);
  }

  @Test
  public void X문자가2번이상() {
    Pattern p = Pattern.compile("x{2,}");
    Matcher m = p.matcher("abx xbc xx xxx xxxx xxxxxbbb");
    assertThat(findResolver(m)).isEqualTo(4);
  }
  /**
   * 수량자 표기 뒤에 ? 를 붙이면???
   *
   * *? : 0
   * +? : 1
   * ?? : 0
   */
  @Test
  public void test() {
    Pattern p = Pattern.compile("<div>.+?</div>");
    Matcher m = p.matcher("<div></div><div>abc</div><div>def</div><div></div>");
    assertThat(findResolver(m)).isEqualTo(2);
  }
}