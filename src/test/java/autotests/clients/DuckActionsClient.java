package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends BaseTest {

    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }

    public void duckFly(TestCaseRunner runner, String id){
        sendGetRequest(runner, duckService,
                "/api/duck/action/fly", "id", id);
    }

}
