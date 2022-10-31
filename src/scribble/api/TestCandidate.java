package scribble.api;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class TestCandidate {

    public int lifetime = 0;
    public boolean visible = true;
    public Map<TestKey, TestGroup> store = new HashMap<>();
    public PApplet processingApp;

    public TestCandidate() {
    }

    public void runFor(int frames) {
        lifetime = frames;
    }

    public void onAfterSetup(String testName, BooleanSupplier testFunction) {
        TestKey k = new TestKey(0, TestKey.Event.AFTER_SETUP);
        Test t = new Test(testName, testFunction);
        store(k, t);
    }

    public void onBeforeDraw(int frame, String testName, BooleanSupplier testFunction) {
        TestKey k = new TestKey(frame, TestKey.Event.BEFORE_DRAW);
        Test t = new Test(testName, testFunction);
        store(k, t);
    }

    public void onBeforeDraw(int frame, Runnable setupFunction) {
        TestKey k = new TestKey(frame, TestKey.Event.BEFORE_DRAW);
        Test t = new Test(setupFunction);
        store(k, t);
    }

    public void onAfterDraw(int frame, String testName, BooleanSupplier testFunction) {
        TestKey k = new TestKey(frame, TestKey.Event.AFTER_DRAW);
        Test t = new Test(testName, testFunction);
        store(k, t);
    }

    public void onBeforeExit(String testName, BooleanSupplier testFunction) {
        TestKey k = new TestKey(lifetime, TestKey.Event.BEFORE_EXIT);
        Test t = new Test(testName, testFunction);
        store(k, t);
    }

    private void store(TestKey k, Test t) {
        if (store.containsKey(k)) {
            store.get(k).add(t);
        } else {
            store.put(k, new TestGroup(t));
        }
    }

    public TestGroup get(TestKey k) {
        return store.get(k);
    }

    public TestField findField(String filter) {
        try {
            return new TestField(processingApp, processingApp.getClass().getField(filter));
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private void mouse(int mouseX, int mouseY) {
        processingApp.mouseX = mouseX;
        processingApp.mouseY = mouseY;
    }

    public void mouseDown() {
        processingApp.mousePressed = true;
        processingApp.mousePressed();
    }

    public void mouseUp() {
        processingApp.mousePressed = false;
        processingApp.mouseReleased();
    }

    public void mouseMove(int mouseX, int mouseY) {
        mouse(mouseX, mouseY);
        processingApp.mouseMoved();
    }

    public void mouseDrag(int mouseX, int mouseY) {
        mouse(mouseX, mouseY);
        processingApp.mouseDragged();
    }

    public void mouseDown(int mouseX, int mouseY) {
        mouse(mouseX, mouseY);
        mouseDown();
    }

    public void mouseUp(int mouseX, int mouseY) {
        mouse(mouseX, mouseY);
        mouseUp();
    }

    public void executeAll(TestKey testKey) {
        TestGroup tests = store.get(testKey);

        if (tests == null) {
            return;
        }

        for (Test test : tests.getTests()) {
            test.execute();
        }
    }

    public List<Test> results() {

        ArrayList<Test> temp = new ArrayList<>();
        for (Map.Entry<TestKey, TestGroup> group: store.entrySet()) {
            temp.addAll(group.getValue().getTests());
        }

        return temp;
    }

    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }
}
