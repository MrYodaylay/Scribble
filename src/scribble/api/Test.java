package scribble.api;

import java.util.function.BooleanSupplier;

public class Test {

    public String name = null;

    // For test cases
    BooleanSupplier test = null;
    private boolean result;

    // For setup tests
    Runnable function = null;

    public Test(String name, BooleanSupplier test) {
        this.name = name;
        this.test = test;
    }

    public boolean isSupplier() {
        return test != null;
    }

    public boolean getResult() {
        return this.result;
    }

    public Test(Runnable function) {
        this.function = function;
    }

    public void execute() {
        if (this.test != null) {
            result = test.getAsBoolean();
        } else {
            function.run();
        }
    }
}
