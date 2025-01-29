package autotests.tests.objectManipulation;

import autotests.clients.DuckCreateClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/create")
public class DuckCreateTest extends DuckCreateClient {

    @Test(description = "Создание уточки из резины")
    @CitrusTest
    public void successfulCreateDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, duck);
        validateResponseCreateString(runner, "{" + "  \"id\": " + "${duckId}" + ","
                + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "rubber" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");

        validateDuckInDatabase(runner, "${duckId}", "yellow", "0.15", "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Создание уточки из дерева")
    @CitrusTest
    public void successfulCreateDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, duck);
        validateResponseCreateString(runner, "{" + "  \"id\": " + "${duckId}" + ","
                + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "wood" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");

        validateDuckInDatabase(runner, "${duckId}", "yellow", "0.15", "wood", "quack", "ACTIVE");
    }
}
