package info.zhwan.java.regx;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class Step3 extends AbstractRegExTest {
  /**
   * (abc|efg) : abc 혹은 efg 문자열을 찾을 경우
   */
  @Test
  public void testSubPattern() {
    final String INPUT = "Monday Tuesday Friday";

    Pattern p = Pattern.compile("(on|ue|rid)");
    Matcher m = p.matcher(INPUT);
    assertThat(findResolver(m)).isEqualTo(3);

    p = Pattern.compile("(on|ues)day");
    m = p.matcher(INPUT);
    assertThat(findResolver(m)).isEqualTo(2);

    p = Pattern.compile("..(n|ri)day");
    m = p.matcher(INPUT);
    assertThat(findResolver(m)).isEqualTo(2);
  }
}