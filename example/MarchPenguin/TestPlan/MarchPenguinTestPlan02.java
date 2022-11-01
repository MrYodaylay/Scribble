import scribble.api.TestField;
import scribble.test.TestPlan;

public class MarchPenguinTestPlan02 extends TestPlan {

    public void run() {

        Scribble().forAll(s -> {

            message("Hello World from Test Plan!");

            s.setVisibility(false);
            s.runFor(2);

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






