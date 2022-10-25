import scribble.test.TestPlan;

import java.lang.reflect.Array;

public class MarchPenguinTestPlan extends TestPlan {

    public void run() {

        Scribble().forAll(s -> {

            message("Hello World from Test Plan!");

            s.runFor(250);

            s.onBeforeDraw(17, () -> {
                Object array = s.findField("ArrayType Penguin");
                return Array.getLength(array) == 7;
            });

        });
    }
}






