package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckCreateClient extends BaseTest {

    @Step("Эндпоинт для создания уточки")
    public void createDuck(TestCaseRunner runner, Object body) {
        sendPostRequest(runner, duckService, "/api/duck/create", body);
    }

}
