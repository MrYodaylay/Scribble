package scribble.test;

import scribble.api.Scribble;

public abstract class TestPlan {

    Scribble api = null;
    public void connect(Scribble instance) {
        this.api = instance;
    }

    public void message(String message) {
        api.message(message);
    }

    protected Scribble Scribble() {
        return api;
    }

    public abstract void run();

}
