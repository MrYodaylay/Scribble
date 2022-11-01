package scribble.api;

import java.util.Objects;

public class TestKey {

    public enum Event {
        AFTER_SETUP,
        BEFORE_DRAW,
        AFTER_DRAW,
        BEFORE_EXIT
    }

    int frameNum;
    Event event;

    public TestKey(int frameNum, Event event) {
        this.frameNum = frameNum;
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestKey testKey = (TestKey) o;
        return frameNum == testKey.frameNum && event == testKey.event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(frameNum, event);
    }
}
