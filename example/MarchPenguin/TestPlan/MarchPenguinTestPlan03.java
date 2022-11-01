import scribble.api.TestField;
import scribble.api.TestObject;
import scribble.test.TestPlan;

public class MarchPenguinTestPlan03 extends TestPlan {

    public void run() {

        Scribble().forAll(s -> {

            message("Hello World from Test Plan!");

            s.runFor(240);

            s.onAfterSetup("There are 5 penguins", () -> {
                TestField arr = s.findField("colony");
                return arr.isArray() && arr.length() == 5;
            });



            s.onAfterSetup("There are 7 penguins", () -> {
                TestField arr = s.findField("colony");
                return arr.isArray() && arr.length() == 7;
            });



            s.onBeforeDraw(24, () -> {
                TestField colony = s.findField("colony");

                if (colony.isArray()) {
                    TestObject penguin = colony.getObject(0);
                    TestField xField = penguin.findField("x");
                    TestField yField = penguin.findField("y");

                    Float x = (Float) xField.getObject().value();
                    Float y = (Float) yField.getObject().value();

                    s.mouseDown(Math.round(x), Math.round(y));
                    s.mouseDrag(200, 200);
                }
            });

            s.onAfterDraw(24, "Penguin can be dragged", () -> {
                boolean result = false;

                TestField colony = s.findField("colony");
                if (colony.isArray()) {
                    TestObject penguin = colony.getObject(0);
                    TestField xField = penguin.findField("x");
                    TestField yField = penguin.findField("y");

                    Float x = (Float) xField.getObject().value();
                    Float y = (Float) yField.getObject().value();

                    result = Math.sqrt(Math.pow(x - 200, 2) + Math.pow(y - 200, 2)) < 1;
                }
                return result;
            });

            s.onBeforeDraw(120, s::mouseUp);

        });
    }
}






