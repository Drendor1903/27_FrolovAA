package autotests.tests.controller;

import autotests.clients.DuckCreateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckCreateClient {

    @Test(description = "Создание уточки из резины")
    @CitrusTest
    public void successfulCreateDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

        validateResponse(runner, "{" + "  \"id\": " + "${duckId}" + ","
                + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "rubber" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }

    @Test(description = "Создание уточки из дерева")
    @CitrusTest
    public void successfulCreateDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "wood", "quack", "ACTIVE");

        validateResponse(runner, "{" + "  \"id\": " + "${duckId}" + ","
                + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "wood" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }
}
