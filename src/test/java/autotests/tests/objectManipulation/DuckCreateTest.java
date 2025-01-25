package autotests.tests.objectManipulation;

import autotests.clients.DuckCreateClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckCreateClient {

    @Test(description = "Создание уточки из резины")
    @CitrusTest
    public void successfulCreateDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
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

        duckProperties(runner, "${duckId}");
        validateResponsePayload(runner, duck);
    }

    @Test(description = "Создание уточки из дерева")
    @CitrusTest
    public void successfulCreateDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
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

        duckProperties(runner, "${duckId}");
        validateResponseResources(runner, "duckCreateTest/validateCreate.json");
    }
}
