package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.ResponseMessage;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckFlyTest extends DuckActionsClient {

    @Test(description = "Проверка полета уточки в состоянии крыльев ACTIVE")
    @CitrusTest
    public void successfulFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, duck);

        saveDuckId(runner);

        duckFly(runner, "${duckId}");
        validateResponseString(runner, "{\n" + "  \"message\": \"I`m flying\"\n" + "}");
    }

    @Test(description = "Проверка полета уточки в состоянии крыльев FIXED")
    @CitrusTest
    public void successfulFlyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);

        saveDuckId(runner);

        duckFly(runner, "${duckId}");
        validateResponseResource(runner, "duckFlyTest/successfulFlyDuck.json");
    }

    @Test(description = "Проверка полета уточки в состоянии крыльев UNDEFINED")
    @CitrusTest
    public void successfulFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.UNDEFINED);

        createDuck(runner, duck);

        saveDuckId(runner);

        ResponseMessage responseMessage = new ResponseMessage().message("Invalid parameter value");

        duckFly(runner, "${duckId}");
        validateResponseWingsUndefined(runner, responseMessage);
    }
}
