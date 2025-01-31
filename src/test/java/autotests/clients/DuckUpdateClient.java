package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

public class DuckUpdateClient extends BaseTest {

    @Autowired
    protected SingleConnectionDataSource testDb;

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

    protected void validateDuckInDatabase(TestCaseRunner runner, String id, String color, Double height,
                                          String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", color)
                .validate("HEIGHT", String.valueOf(height))
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }

    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }
}
