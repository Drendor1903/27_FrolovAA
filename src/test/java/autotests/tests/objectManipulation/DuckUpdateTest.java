package autotests.tests.objectManipulation;

import autotests.clients.DuckUpdateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckUpdateTest extends DuckUpdateClient {

    @Test(description = "Обновление данных цвета и размера уточки")
    @CitrusTest
    public void successfulUpdateDuckWithColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

        saveDuckId(runner);

        updateDuck(runner, "${duckId}", "gray", 0.11, "rubber", "quack", "ACTIVE");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");

        duckProperties(runner, "${duckId}");
        validateResponse(runner, "{" + "  \"color\": \"" + "gray" + "\","
                + "  \"height\": " + 0.11 + ","
                + "  \"material\": \"" + "rubber" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }

    @Test(description = "Обновление данных цвета и звука уточки")
    @CitrusTest
    public void successfulUpdateDuckWithColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

        saveDuckId(runner);

        updateDuck(runner, "${duckId}", "gray", 0.15, "rubber", "meow", "ACTIVE");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");

        duckProperties(runner, "${duckId}");
        validateResponse(runner, "{" + "  \"color\": \"" + "gray" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "rubber" + "\","
                + "  \"sound\": \"" + "meow" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }
}
