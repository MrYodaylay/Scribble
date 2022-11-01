package scribble.api;

import java.lang.reflect.Field;

public class TestObject {

    Object obj;

    public TestObject(Object obj) {
        this.obj = obj;
    }

    public TestField findField(String name) {
        try {
            Field field = obj.getClass().getField(name);
            return new TestField(obj, field);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public Object value() {
        return obj;
    }

}
