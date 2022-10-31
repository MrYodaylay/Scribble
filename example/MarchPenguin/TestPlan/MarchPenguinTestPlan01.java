import scribble.api.TestField;
import scribble.test.TestPlan;

public class MarchPenguinTestPlan01 extends TestPlan {

    public void run() {

        Scribble().forAll(s -> {

            message("Hello World from Test Plan!");

            s.runFor(120);

            s.onAfterSetup("There are 5 penguins", () -> {
                TestField arr = s.findField("colony");
                return arr.isArray() && arr.length() == 5;
            });



            s.onAfterSetup("There are 7 penguins", () -> {
                TestField arr = s.findField("colony");
                return arr.isArray() && arr.length() == 7;
            });


        });
    }
}






