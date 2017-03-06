package info.zhwan.java.regx;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

/**
 * Meta 문자
 *
 * \w : 문자 + 숫자 + _ [A-z0-9_]
 * \W : \w의 반대 [^A-z0-9_]
 *
 * \s : 공백문자 [ ]
 * \S : \s의 반대 [^ ]
 *
 * \d : 숫자 [0-9]
 * \D : \d 반대 [^0-9]
 *
 * \b : 문자와 공백사이의 문자 찾
 * \B : 문자와 공백사이 문자가 아닌 문자 찾기
 */
public class Step6 extends AbstractRegExTest {
  @Test
  public void test문자() {
    final Pattern p = Pattern.compile("\\w");
    final Matcher m = p.matcher("a bbbb 123 : & __");
    assertThat(findResolver(m)).isEqualTo(10);
  }

  @Test
  public void test공백문자() {
    final Pattern p = Pattern.compile("[^\\s]+");
    final Matcher m = p.matcher("a bbbb 123 : & __");
    assertThat(findResolver(m)).isEqualTo(6);
  }

  @Test
  public void test숫자() {
    Pattern p = Pattern.compile("\\d+");
    Matcher m = p.matcher("Page 123; published: 1234 id=12#24@112");
    assertThat(findResolver(m)).isEqualTo(5);

    p = Pattern.compile("\\D+");
    m = p.matcher("Page 123; published: 1234 id=12#24@112");
    assertThat(findResolver(m)).isEqualTo(5);
  }

  @Test
  public void testBoundary() {
    final String INPUT_TEXT = "With more than 20 years of experience in industrial automation solution, \n" +
        "aim systems provides integration services that helps clients obtain competitiveness with technologies related to various fields in IT including consulting, \n" +
        "planning, engineering and customization service on automation system.";

    final Pattern p = Pattern.compile("\\b.{5}(?=\\s)");
    final Matcher m = p.matcher(INPUT_TEXT);
    findResolver(m);
  }
}
