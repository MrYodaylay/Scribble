package scribble.api;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class TestField {

    Field field; // The field itself
    Object instance; // The object on which this field exists

    public TestField(Object instance, Field field) {
        this.instance = instance;
        this.field = field;
    }

    /**
     * Determines if this instance of TestField represents an Array.
     */
    public boolean isArray() {
        return field.getType().isArray();
    }

    /**
     * If this instance of TestField represents an Array, return its length, otherwise return 0.
     */
    public int length() {
        try {
            return Array.getLength(field.get(instance));
        } catch (IllegalAccessException ignored) {
            ;
        }
        return 0;
    }

    /**
     * If this instance of TestField represents an Array, return the ith item in the array.
     */
    public TestObject getObject(int i) {
        try {
            Object arr = field.get(instance);
            Object item = Array.get(arr, i); // The penguin
            return new TestObject(item);
        } catch (IllegalAccessException ignored) {
            ;
        }
        return null;
    }

    /**
     * If this instance of TestField represents a single object, return the value of that object
     */
    public TestObject getObject() {
        try {
            return new TestObject(field.get(instance));
        } catch (IllegalAccessException ignored) {
            ;
        }
        return null;
    }
}
