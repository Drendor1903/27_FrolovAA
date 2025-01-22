package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class DuckPropertiesTest extends DuckActionsClient {

    @Test(description = "Получение параметров уточки из материала - rubber")
    @CitrusTest
    public void successfulGetPropertiesWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
        AtomicInteger id = new AtomicInteger();
        while (true) {
            createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

            saveDuckId(runner);

            runner.$(action -> id.set(Integer.parseInt(action.getVariable("duckId"))));

            if (id.get() % 2 != 0) {
                break;
            }

        }

        duckProperties(runner, "${duckId}");
        validateResponse(runner, "{" + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "rubber" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }

    @Test(description = "Получение параметров уточки из материала - wood")
    @CitrusTest
    public void successfulGetPropertiesWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        AtomicInteger id = new AtomicInteger();
        while (true) {
            createDuck(runner, "yellow", 0.15, "wood", "quack", "ACTIVE");

            saveDuckId(runner);

            runner.$(action -> id.set(Integer.parseInt(action.getVariable("duckId"))));

            if (id.get() % 2 == 0) {
                break;
            }

        }
        duckProperties(runner, "${duckId}");
        validateResponse(runner, "{" + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "wood" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }
}
