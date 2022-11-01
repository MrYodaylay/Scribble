package scribble.api;

import scribble.log.Logger;
import scribble.test.TestPlan;
import scribble.test.TestRunner;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Scribble {

    private final TestRunner testRunner;
    private final TestPlan testPlan;

    public Scribble(TestRunner testRunner, TestPlan testPlan) {
        this.testRunner = testRunner;
        this.testPlan = testPlan;

        this.testPlan.connect(this);
    }

    public void run() {
        this.testPlan.run();
    }

    public void message(String message) {
        Logger.message(message);
    }

    public void forAll(Consumer<TestCandidate> candidateConsumer) {
        testRunner.runAll(candidateConsumer);
    }

    private void forSome(Predicate<TestCandidate> predicate, Consumer<TestCandidate> sketchConsumer) {
        ;
    }

}
