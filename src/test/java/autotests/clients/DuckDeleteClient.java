package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

public class DuckDeleteClient extends BaseTest {

    @Autowired
    protected SingleConnectionDataSource testDb;

    @Step("Эндпоинт для удаления уточки")
    public void deleteDuck(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner,
                duckService,
                "/api/duck/delete?id=" + id);
    }

    protected void validateDuckInDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement("SELECT COUNT(*) AS count FROM DUCK WHERE ID=" + id)
                .validate("count", "0"));
    }
}
