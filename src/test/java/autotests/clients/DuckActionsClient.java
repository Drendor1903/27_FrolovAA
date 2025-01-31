package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;

public class DuckActionsClient extends BaseTest {

    @Autowired
    protected SingleConnectionDataSource testDb;

    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }

    public void duckFly(TestCaseRunner runner, String id) {
        sendGetRequestWithQueryParam(runner, duckService,
                "/api/duck/action/fly", "id", id);
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        sendGetRequestWithQueryParam(runner, duckService,
                "/api/duck/action/swim", "id", id);
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        sendGetRequestWithQueryParam(runner, duckService,
                "/api/duck/action/properties", "id", id);
    }

    public void duckQuack(TestCaseRunner runner, String id, int repetitionCount, int soundCount) {
        sendGetRequest(runner, duckService,
                "/api/duck/action/quack?"
                        + "id=" + id
                        + "&repetitionCount=" + repetitionCount
                        + "&soundCount=" + soundCount);
    }

}
