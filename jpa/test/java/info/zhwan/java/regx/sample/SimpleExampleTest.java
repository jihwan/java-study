package info.zhwan.java.regx.sample;

import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class SimpleExampleTest {
    private Candidates candidates;

    @Before
    public void setUp() {
        candidates = new Candidates();
    }

    @Test
    public void simple_regex() {
        // [A-Za-z]+_[A-Za-z]+@[A-Za-z]+\.org
        String emailRegex = "[A-Za-z]+_[A-Za-z]+@[A-Za-z]+\\.org";

        candidates
                .addMatchers("homer_simpson@burns.org")
                .addMatchers("m_burns@burns.org");

        candidates
                .addNonMatchers("wsmithers@burns.com")
                .addNonMatchers("Homer9_simpson@somewhere.org");

        Pattern p = Pattern.compile(emailRegex);
        for (String c : candidates.thatMatch()) {
            Matcher m = p.matcher(c);
            assertThat(c.matches(emailRegex), is(true));
        }

        for (String c : candidates.notMatching())
            assertThat(p.matcher(c).matches(), is(false));
    }

    @Test
    public void case_insensitive_searches_with_pattern_flags() {
        String regex = "A really long sentence";
        String candidate = "A really long SENTENCE";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(candidate);
        assertThat(m.matches(), is(true));
    }

    @Test
    public void search_with_find() {
        String odysseyPartOne =
                "Athene, you are my favorite" +
                        "Athene, you are my favorite" +
                        "Athene, you are my favorite" +
                        "Athene, you are my favorite" +
                        "Athene, you are my favorite";
        String regexToMatch = "Athene";
        Pattern p = Pattern.compile(regexToMatch);
        Matcher m = p.matcher(odysseyPartOne);
        int count = 0;
        while (m.find()) {
            count++;
            assertThat(m.group(), is(regexToMatch));
        }
        assertThat(count, is(5));
    }

    @Test
    public void groups() {
        String regex = "(\\w)(\\d)";
        String candidates = "J2 comes before J5, which both come before H7";
        String[] matchers = {"J2", "J5", "H7"};
        Matcher m = Pattern.compile(regex).matcher(candidates);
        int i = 0;
        while (m.find()) {
            assertThat(m.group(), is(matchers[i]));
            assertThat(m.group(1), is(matchers[i].substring(0, 1)));
            assertThat(m.group(2), is(matchers[i].substring(1, 2)));
            i++;
        }
    }

    @Test
    public void repeat_words() {
        // \b(\w+)\1\b \b:word boundary
        String regex = "\\b(\\w+)\\1\\b";
        String cadidate =
                "The froofroo tutu cost more than than the gogo boots.";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cadidate);
        String[] matchers = {"froo", "tu", "go"};
        int i = 0;
        while (m.find())
            assertThat(m.group(1), is(matchers[i++]));
    }

    @Test
    public void phone_number() {
        // ^\((\d{3})\)\s(\d{3})-(\d{4})$
        String regex = "^\\((\\d{3})\\)\\s(\\d{3})-(\\d{4})$";
        String phoneNumber = "(404) 555-1234";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(phoneNumber);
        assertThat(m.matches(), is(true));
        assertThat(m.group(1), is("404"));
        assertThat(m.group(2), is("555"));
        assertThat(m.group(3), is("1234"));
    }

    @Test
    public void phone_number_alternative_to_slash_death() {
        // ^[(](\d{3})[)]\s(\d{3})-(\d{4})$
        String regex = "^[(](\\d{3})[)]\\s(\\d{3})-(\\d{4})$";
        String phoneNumber = "(404) 555-1234";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(phoneNumber);
        assertThat(m.matches(), is(true));
        assertThat(m.group(1), is("404"));
        assertThat(m.group(2), is("555"));
        assertThat(m.group(3), is("1234"));
    }

    @Test
    public void time() {
        // (1[012]|[1-9]):[0-5][0-9]\s(am|pm)
        String regex = "(1[012]|[1-9]):[0-5][0-9]\\s(am|pm)";
        String[] times = {"9:30 am", "10:00 pm"};
        Pattern pattern = Pattern.compile(regex);
        String[] groups = {"9", "10"};
        int i = 0;
        for (String time : times) {
            Matcher m = pattern.matcher(time);
            assertThat(m.matches(), is(true));
            assertThat(m.group(1), is(groups[i++]));
        }
    }

    @Test
    public void phone_numbers() {
        // (\d{3})?\d{3}\d{4}
        String regexp = "(\\d{3})?\\d{3}\\d{4}";
        candidates
                .addMatchers("404-555-1234")
                .addMatchers("(404) 555  - 1234")
                .addMatchers("404.555.1234");
        for (String num : candidates.thatMatch()) {
            String scrubbed = num.replaceAll("\\p{Punct}|\\s", "");
            assertThat(Pattern.compile(regexp).matcher(scrubbed).matches(), is(true));
        }
    }

    @Test
    public void non_capturing_groups() {
        // add sigil(?:) in order not to capture
        // (?:\d{4}-)?\d{3}-\d{4}
        String regexp = "(?:\\d{3}-)?\\d{3}-\\d{4}";
        String candidate = "404-555-1234";
        Matcher m = Pattern.compile(regexp).matcher(candidate);
        assertThat(m.matches(), is(true));
        assertThat(m.groupCount(), is(0));
    }

    @Test
    public void more_non_capturing_sub_groups() {
        // (?:<li>)(.*)(?:</li>)
        String regex = "(?:<li>)(.*)(?:</li>)";
        String candidate = "<li>Now is the time for all good men</li>";
        Matcher m = Pattern.compile(regex).matcher(candidate);
        assertThat(m.matches(), is(true));
        assertThat(m.groupCount(), is(1));
        assertThat(m.group(), is(candidate));
        assertThat(m.group(1), is("Now is the time for all good men"));
    }

    @Test
    public void greediness_try_to_match_as_many_characters_as_possible() {
        String regex = "^.*([0-9]+)";
        String candidate = "copyright 2004";
        Matcher matcher = Pattern.compile(regex).matcher(candidate);
        assertThat(matcher.matches(), is(true));
        assertThat(matcher.group(1), not("2004"));
        assertThat(matcher.group(1), is("4"));
    }

    @Test
    public void reluctant_try_to_match_as_little_as_possible() {
        String regex = "^.*?([0-9]+)";
        String candidate = "copyright 2004";
        Matcher matcher = Pattern.compile(regex).matcher(candidate);
        assertThat(matcher.matches(), is(true));
        assertThat(matcher.group(1), is("2004"));
        assertThat(matcher.group(1), not("4"));
    }

    @Test
    public void greedy_backtrack() {
        // ".*"
        String regex = "\".*\"";
        String candiate =
                "The name \"regex\" abbreviates \"Regular Expression\".";
        Matcher m = Pattern.compile(regex).matcher(candiate);
        assertThat(m.find(), is(true));
        assertThat(m.group(),
                is("\"regex\" abbreviates \"Regular Expression\""));
    }

    @Test
    public void reluctant_backtrack() {
        // ".*"
        String regex = "\".*?\"";
        String candiate =
                "The name \"regex\" abbreviates \"Regular Expression\".";
        Matcher m = Pattern.compile(regex).matcher(candiate);
        assertThat(m.find(), is(true));
        assertThat(m.group(), is("\"regex\""));
        assertThat(m.find(), is(true));
        assertThat(m.group(), is("\"Regular Expression\""));
        assertThat(m.find(), is(false));
    }

    @Test
    public void simple_lookahead() {
        // <li>(.*)(?=</li>)
        String regex = "<li>(.*)(?=</li>)";
        String candidateThatMatches = "<li>list item</li>";
        String nonMatchingCandidate = "<li>list item";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(candidateThatMatches);
        assertThat(m.find(), is(true));
        assertThat(m.group(1), is("list item"));
        assertThat(p.matcher(nonMatchingCandidate).find(), is(false));
    }

    @Test
    public void file_name_with_negative_lookahead() {
        // .*[.](?!bat$|exe$).*$
        String regex = ".*[.](?!bat$|exe$).*$";
        candidates
                .addMatchers("foo.batch")
                .addMatchers("foo.doc")
                .addNonMatchers("foo.bat")
                .addNonMatchers("foo.exe");
        Pattern p = Pattern.compile(regex);
        for(String s : candidates.thatMatch())
            assertThat(p.matcher(s).matches(), is(true));
        for(String s : candidates.notMatching())
            assertThat(p.matcher(s).matches(), is(false));
    }

    @Test
    public void wrap_around() {
        // (?<=<li>).*(?=</li>)
        String regex = "(?<=<li>).*(?=</li>)";
        String candidate = "<li>Now is the time for all good men</li>";
        Matcher m = Pattern.compile(regex).matcher(candidate);
        assertThat(m.find(), is(true));
        assertThat(m.groupCount(), is(0));
        assertThat(m.group(), is("Now is the time for all good men"));
    }
}