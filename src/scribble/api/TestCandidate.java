package scribble.api;

import java.util.function.BooleanSupplier;

public class TestCandidate {

    public int lifetime = 0;

    public TestCandidate() {
    }

    public void runFor(int frames) {
        lifetime = frames;
    }

    public void onAfterSetup(BooleanSupplier testFunction) {
        ;
    }

    public void onBeforeDraw(int frame, BooleanSupplier testFunction) {
        ;
    }

    public void onAfterDraw(int frame, BooleanSupplier testFunction) {
        ;
    }

    public void onBeforeExit(BooleanSupplier testFunction) {
        ;
    }

    public TestField findField(String filter) {
        return new TestField();
    }

}
