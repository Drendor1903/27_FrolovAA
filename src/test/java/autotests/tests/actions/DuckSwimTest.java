package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTest extends DuckActionsClient {

    @Test(description = "Проверка может ли уточка с существующим Id плавать")
    @CitrusTest
    public void successfulSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);

        saveDuckId(runner);

        duckSwim(runner, "${duckId}");
        validateResponseString(runner, "{\n" + "  \"message\": \"I'm swimming \"\n" + "}");
    }

    @Test(description = "Проверка может ли уточка с не существующим Id плавать")
    @CitrusTest
    public void unsuccessfulSwimWithNotExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "0");

        validateResponseWithNotExistingId(runner);
    }
}
