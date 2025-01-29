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
@Feature("Эндпоинт /api/duck/action/properties")
public class DuckPropertiesTest extends DuckActionsClient {

    @Test(description = "Получение параметров уточки из материала rubber и с нечетным Id")
    @CitrusTest
    @Flaky
    public void successfulGetPropertiesWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "1234567");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'rubber', 'quack', 'ACTIVE');");

        duckProperties(runner, "${duckId}");
        validateResponseString(runner, "{" + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "rubber" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }

    @Test(description = "Получение параметров уточки из материала wood и с четным Id")
    @CitrusTest
    @Flaky
    public void successfulGetPropertiesWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "1234568");

        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner, "INSERT INTO DUCK (id, color, height, material, sound, wings_state)\n" +
                "VALUES (${duckId}, 'yellow', 0.15, 'wood', 'quack', 'ACTIVE');");

        duckProperties(runner, "${duckId}");
        validateResponseString(runner, "{" + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "wood" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }
}
