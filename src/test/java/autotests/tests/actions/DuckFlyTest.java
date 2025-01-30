package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.ResponseMessage;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/fly")
public class DuckFlyTest extends DuckActionsClient {

    @Test(description = "Проверка полета уточки в состоянии крыльев ACTIVE")
    @CitrusTest
    @Flaky
    public void successfulFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(6, true)");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'ACTIVE');");

        duckFly(runner, "${duckId}");
        validateResponseString(runner, "{\n" + "  \"message\": \"I’m flying\"\n" + "}");
    }

    @Test(description = "Проверка полета уточки в состоянии крыльев FIXED")
    @CitrusTest
    @Flaky
    public void successfulFlyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(6, true)");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'FIXED');");

        duckFly(runner, "${duckId}");
        validateResponseResource(runner, "duckFlyTest/successfulFlyDuck.json");
    }

    @Test(description = "Проверка полета уточки в состоянии крыльев UNDEFINED")
    @CitrusTest
    @Flaky()
    public void successfulFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(6, true)");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'UNDEFINED');");

        ResponseMessage responseMessage = new ResponseMessage().message("Wings are not detected");

        duckFly(runner, "${duckId}");
        validateResponseWingsUndefined(runner, responseMessage);
    }
}
