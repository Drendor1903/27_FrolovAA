package autotests.tests.controller;

import autotests.clients.DuckControllerClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckUpdateTest extends DuckControllerClient {

    @Test(description = "Изменение цвета и высоты уточки")
    @CitrusTest
    public void successfulUpdateDuckWithColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

        saveDuckId(runner);

        updateDuck(runner, "${duckId}", "gray", 1.11, "rubber", "quack", "ACTIVE");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
    }

    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void successfulUpdateDuckWithColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

        saveDuckId(runner);

        updateDuck(runner, "${duckId}", "gray", 0.15, "rubber", "meow", "ACTIVE");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
    }
}
