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

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
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

    public void validateResponseCreate(TestCaseRunner runner, String messageBody) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ClassPathResource(messageBody)));
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

    public void validateResponseStringWithUpdateSound(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http().client(duckService)
                        .receive()
                        .response(HttpStatus.BAD_REQUEST)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(responseMessage));
    }

    protected void sendGetRequestWithQueryParam(TestCaseRunner runner, HttpClient URL, String path, String queName, String queValue) {
        runner.$(http()
                .client(URL)
                .send()
                .get(path)
                .queryParam(queName, queValue));
    }

    protected void sendGetRequest(TestCaseRunner runner, HttpClient URL, String path) {
        runner.$(http()
                .client(URL)
                .send()
                .get(path));
    }

    protected void sendPostRequest(TestCaseRunner runner, HttpClient URL, String path, Object body) {
        runner.$(
                http().client(URL)
                        .send()
                        .post(path)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    protected void sendDeleteRequest(TestCaseRunner runner, HttpClient URL, String path){
        runner.$(
                http().client(URL)
                        .send()
                        .delete(path));
    }

    protected void sendPutRequest(TestCaseRunner runner, HttpClient URL, String path){
        runner.$(
                http().client(URL)
                        .send()
                        .put(path));
    }

}
