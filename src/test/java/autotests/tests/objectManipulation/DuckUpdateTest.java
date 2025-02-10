package autotests.tests.objectManipulation;

import autotests.clients.DuckUpdateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/update")
public class DuckUpdateTest extends DuckUpdateClient {

    @Test(description = "Обновление данных цвета и размера уточки")
    @CitrusTest
    public void successfulUpdateDuckWithColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(6, true)");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'ACTIVE');");

        updateDuck(runner, "${duckId}", "gray", 0.11, "rubber", "quack", "ACTIVE");
        validateResponseString(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");

        validateDuckInDatabase(runner, "${duckId}", "gray", 0.11, "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Обновление данных цвета и звука уточки")
    @CitrusTest
    public void successfulUpdateDuckWithColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(6, true)");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'ACTIVE');");

        updateDuck(runner, "${duckId}", "gray", 0.15, "rubber", "meow", "ACTIVE");
        validateResponseStringWithUpdateSound(runner, "{\n" + "  \"message\": \"Invalid parameter value\"\n" + "}");

        validateDuckInDatabase(runner, "${duckId}", "gray", 0.15, "rubber", "meow", "ACTIVE");
    }
}
