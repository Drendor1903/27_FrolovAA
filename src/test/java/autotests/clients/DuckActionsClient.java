package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;

public class DuckActionsClient extends BaseTest {

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
