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
@Feature("Эндпоинт /api/duck/action/quack")
public class DuckQuackTest extends DuckActionsClient {

    @Test(description = "Проверка кряканья уточки с корректным нечетным Id")
    @CitrusTest
    public void successfulQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "1234567");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'ACTIVE');");

        duckQuack(runner, "${duckId}", 3, 2);
        validateResponseString(runner, "{\n" + "  \"sound\": \"quack-quack, quack-quack, quack-quack\"\n" + "}");
    }

    @Test(description = "Проверка кряканья уточки с корректным четным Id")
    @CitrusTest
    @Flaky
    public void successfulQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "1234568");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'ACTIVE');");

        duckQuack(runner, "${duckId}", 3, 2);
        validateResponseResource(runner, "duckQuackTest/successfulQuackWithEvenId.json");
    }
}
