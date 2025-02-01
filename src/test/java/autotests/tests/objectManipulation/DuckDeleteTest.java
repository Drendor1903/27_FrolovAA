package autotests.tests.objectManipulation;

import autotests.clients.DuckDeleteClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/delete")
public class DuckDeleteTest extends DuckDeleteClient {

    @Test(description = "Удаление уточки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "citrus:randomNumber(6, true)");

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'ACTIVE');");


        deleteDuck(runner, "${duckId}");
        validateResponseString(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");

        validateDuckInDatabase(runner, "${duckId}");
    }
}