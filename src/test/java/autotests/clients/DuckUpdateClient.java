package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckUpdateClient extends BaseTest {

    @Step("Эндпоинт для обновления свойств уточки")
    public void updateDuck(TestCaseRunner runner, String id, String color, Double height,
                           String material, String sound, String wingsState) {
        sendPutRequest(runner,
                duckService,
                "/api/duck/update?"
                        + "id=" + id
                        + "&color=" + color
                        + "&height=" + height
                        + "&material=" + material
                        + "&sound=" + sound
                        + "&wingsState=" + wingsState);
    }
}