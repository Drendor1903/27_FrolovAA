package autotests.actions;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckSwimTest extends TestNGCitrusSpringSupport {

    @Test(description = "Проверка может ли уточка с существующим Id плавать")
    @CitrusTest
    public void successfulSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");

        saveDuckId(runner);

        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I'm swimming\"\n" + "}");
    }

    @Test(description = "Проверка может ли уточка с не существующим Id плавать")
    @CitrusTest
    public void unsuccessfulSwimWithNotExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "0");

        validateResponseWithNotExistingId(runner);
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    public void validateResponseWithNotExistingId(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.NOT_FOUND));
    }

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http().client("http://localhost:2222")
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" + "  \"color\": \"" + color + "\",\n"
                                + "  \"height\": " + height + ",\n"
                                + "  \"material\": \"" + material + "\",\n"
                                + "  \"sound\": \"" + sound + "\",\n"
                                + "  \"wingsState\": \"" + wingsState
                                + "\"\n" + "}"));
    }

    public void saveDuckId(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }
}
