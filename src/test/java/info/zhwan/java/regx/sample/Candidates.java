package info.zhwan.java.regx.sample;

import java.util.ArrayList;
import java.util.List;

public class Candidates {
    private List<String> good;
    private List<String> bad;

    public Candidates() {
        good = new ArrayList<String>(5);
        bad = new ArrayList<String>(5);
    }

    public Candidates addMatchers(String goodCandidates) {
        good.add(goodCandidates);
        return this;
    }

    public Candidates addNonMatchers(String badCandidates) {
        bad.add(badCandidates);
        return this;
    }

    public List<String> thatMatch() {
        return good;
    }

    public List<String> notMatching() {
        return bad;
    }
}