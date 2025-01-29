package autotests.tests.objectManipulation;

import autotests.clients.DuckUpdateClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckUpdateTest extends DuckUpdateClient {

    @Test(description = "Обновление данных цвета и размера уточки")
    @CitrusTest
    public void successfulUpdateDuckWithColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, duck);

        saveDuckId(runner);

        duck.color("gray").height(0.11);

        updateDuck(runner, "${duckId}", duck);
        validateResponseString(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");

        duckProperties(runner, "${duckId}");
        validateResponsePayload(runner, duck);
    }

    @Test(description = "Обновление данных цвета и звука уточки")
    @CitrusTest
    public void successfulUpdateDuckWithColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, duck);

        saveDuckId(runner);

        duck.color("gray").sound("meow");

        updateDuck(runner, "${duckId}", duck);
        validateResponseString(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");

        duckProperties(runner, "${duckId}");
        validateResponseResources(runner, "duckUpdateTest/validateUpdateDuck.json");
    }
}
