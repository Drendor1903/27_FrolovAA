package autotests.actions;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesTest extends TestNGCitrusSpringSupport {

    @Test(description = "Получение параметров уточки из материала - rubber")
    @CitrusTest
    public void successfulGetPropertiesWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
        AtomicInteger id = new AtomicInteger();
        while (true){
            createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
            runner.$(http().client("http://localhost:2222")
                    .receive()
                    .response(HttpStatus.OK)
                    .message()
                    .extract(fromBody().expression("$.id", "duckId")));

            runner.$(action -> id.set(Integer.parseInt(action.getVariable("duckId"))));

            if (id.get() % 2 != 0) {
                break;
            }

        }

        duckProperties(runner, "${duckId}");
        validateResponse(runner, "{" + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "rubber" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }

    @Test(description = "Получение параметров уточки из материала - wood")
    @CitrusTest
    public void successfulGetPropertiesWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        AtomicInteger id = new AtomicInteger();
        while (true){
            createDuck(runner, "yellow", 0.15, "wood", "quack", "ACTIVE");
            runner.$(http().client("http://localhost:2222")
                    .receive()
                    .response(HttpStatus.OK)
                    .message()
                    .extract(fromBody().expression("$.id", "duckId")));

            runner.$(action -> id.set(Integer.parseInt(action.getVariable("duckId"))));

            if (id.get() % 2 == 0) {
                break;
            }

        }
        duckProperties(runner, "${duckId}");
        validateResponse(runner, "{" + "  \"color\": \"" + "yellow" + "\","
                + "  \"height\": " + 0.15 + ","
                + "  \"material\": \"" + "wood" + "\","
                + "  \"sound\": \"" + "quack" + "\","
                + "  \"wingsState\": \"" + "ACTIVE"
                + "\"" + "}");
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
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
}
