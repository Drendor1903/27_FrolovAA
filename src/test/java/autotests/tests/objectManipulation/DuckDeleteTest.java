package autotests.tests.objectManipulation;

import autotests.clients.DuckDeleteClient;
import autotests.payloads.Duck;
import autotests.payloads.ResponseMessage;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckDeleteTest extends DuckDeleteClient {

    @Test(description = "Удаление уточки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        //Валидация через String
        createDuck(runner, duck);

        saveDuckId(runner);

        deleteDuck(runner, "${duckId}");
        validateResponseString(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");
        validateDeleteDuck(runner);

        //Валидация через Resources
        createDuck(runner, duck);

        saveDuckId(runner);

        deleteDuck(runner, "${duckId}");
        validateResponseResources(runner, "duckDeleteTest/successfulDeleteDuck.json");

        //Валидация через Payload
        ResponseMessage responseMessage = new ResponseMessage().message("Duck is deleted");

        createDuck(runner, duck);

        saveDuckId(runner);

        deleteDuck(runner, "${duckId}");
        validateResponsePayload(runner, responseMessage);
    }
}