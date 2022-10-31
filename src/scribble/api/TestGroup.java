package scribble.api;

import java.util.ArrayList;
import java.util.List;

public class TestGroup {

    private List<Test> tests = new ArrayList<>();

    public TestGroup(Test t) {
        tests.add(t);
    }

    public void add(Test t) {
        tests.add(t);
    }

    public List<Test> getTests() {
        return tests;
    }

}
