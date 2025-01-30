package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    protected void validateResponseString(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    protected void validateResponseResource(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(responseMessage)));
    }

    protected void validateResponseBadRequest(TestCaseRunner runner, Object body) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.BAD_REQUEST)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    protected void validateResponseNotFound(TestCaseRunner runner, String body) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.NOT_FOUND)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(body));
    }

    protected void sendGetRequest(TestCaseRunner runner, HttpClient URL, String path, String queName, String queValue){
        runner.$(http()
                .client(URL)
                .send()
                .get(path)
                .queryParam(queName, queValue));
    }

    protected void duckQuack(TestCaseRunner runner, String id, int repetitionCount, int soundCount) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", Integer.toString(repetitionCount))
                .queryParam("soundCount", Integer.toString(soundCount)));
    }

    protected void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    protected void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }
}
