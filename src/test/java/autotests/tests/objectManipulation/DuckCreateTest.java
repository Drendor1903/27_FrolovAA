package autotests.tests.objectManipulation;

import autotests.clients.DuckCreateClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/create")
public class DuckCreateTest extends DuckCreateClient {

    Duck duck1 = new Duck()
            .color("yellow")
            .height(0.15)
            .material("rubber")
            .sound("quack")
            .wingsState(WingsState.ACTIVE);

    Duck duck2 = new Duck()
            .color("red")
            .height(0.25)
            .material("rubber")
            .sound("meow")
            .wingsState(WingsState.ACTIVE);

    Duck duck3 = new Duck()
            .color("grey")
            .height(0.11)
            .material("rubber")
            .sound("car")
            .wingsState(WingsState.ACTIVE);

    Duck duck4 = new Duck()
            .color("white")
            .height(0.34)
            .material("rubber")
            .sound("quack")
            .wingsState(WingsState.ACTIVE);

    Duck duck5 = new Duck()
            .color("purple")
            .height(0.73)
            .material("rubber")
            .sound("quack")
            .wingsState(WingsState.ACTIVE);


    @DataProvider(name = "testData")
    public Object[][] DuckProvider() {
        return new Object[][]{
                {duck1, "duckCreateTest/validateCreateYellowDuck.json", null},
                {duck2, "duckCreateTest/validateCreateRedDuck.json", null},
                {duck3, "duckCreateTest/validateCreateGreyDuck.json", null},
                {duck4, "duckCreateTest/validateCreateWhiteDuck.json", null},
                {duck5, "duckCreateTest/validateCreatePurpleDuck.json", null}
        };
    }


    @Test(description = "Создание уточки из резины",
            dataProvider = "testData")
    @CitrusTest
    @CitrusParameters({"payload", "response","runner"})
    public void successfulCreateDuckWithMaterialRubber(Duck payload, String response, @Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(action ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        createDuck(runner, payload);
        validateResponseCreate(runner, response);

        validateDuckInDatabase(runner,
                "${duckId}",
                payload.color(),
                String.valueOf(payload.height()),
                payload.material(),
                payload.sound(),
                String.valueOf(payload.wingsState()));
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
        validateResponseCreate(runner, "duckCreateTest/validateCreateDuck.json");

        validateDuckInDatabase(runner, "${duckId}", "yellow", "0.15", "wood", "quack", "ACTIVE");
    }
}
