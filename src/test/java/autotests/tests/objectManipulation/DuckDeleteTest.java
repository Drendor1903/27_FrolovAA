package autotests.tests.objectManipulation;

import autotests.clients.DuckDeleteClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckDeleteTest extends DuckDeleteClient {

    @Test(description = "Удаление уточки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

        saveDuckId(runner);

        deleteDuck(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");
        validateDeleteDuck(runner);
    }
}