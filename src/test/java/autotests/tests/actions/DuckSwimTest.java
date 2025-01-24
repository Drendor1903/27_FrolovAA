package autotests.tests.actions;

import autotests.clients.DuckSwimClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTest extends DuckSwimClient {

    @Test(description = "Проверка может ли уточка с существующим Id плавать")
    @CitrusTest
    public void successfulSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");

        saveDuckId(runner);

        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I'm swimming \"\n" + "}");
    }

    @Test(description = "Проверка может ли уточка с не существующим Id плавать")
    @CitrusTest
    public void unsuccessfulSwimWithNotExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "0");

        validateResponseWithNotExistingId(runner);
    }
}
