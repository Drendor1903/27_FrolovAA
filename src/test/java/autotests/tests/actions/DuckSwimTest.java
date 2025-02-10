package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
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
@Feature("Эндпоинт /api/duck/action/swim")
public class DuckSwimTest extends DuckActionsClient {

    @Test(description = "Проверка может ли уточка с существующим Id плавать")
    @CitrusTest
    @Flaky
    public void successfulSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(6, true)");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'ACTIVE');");

        duckSwim(runner, "${duckId}");
        validateResponseString(runner, "{\n" + "  \"message\": \"I'm swimming\"\n" + "}");
    }

    @Test(description = "Проверка может ли уточка с не существующим Id плавать")
    @CitrusTest
    public void unsuccessfulSwimWithNotExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "0");

        duckSwim(runner, "${duckId}");

        validateResponseNotFound(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is not found\"\n" + "}");
    }
}
