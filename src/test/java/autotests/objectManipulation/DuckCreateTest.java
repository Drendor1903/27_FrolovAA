package autotests.objectManipulation;

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

public class DuckCreateTest extends TestNGCitrusSpringSupport {

    @Test(description = "Создание уточки из резины")
    @CitrusTest
    public void successfulCreateDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

        saveDuck(runner);

        duckProperties(runner, "${duckId}");

        validateResponse(runner, "{" + "  \"color\": \"" + "${duckColor}" + "\","
                + "  \"height\": " + "${duckHeight}" + ","
                + "  \"material\": \"" + "${duckMaterial}" + "\","
                + "  \"sound\": \"" + "${duckSound}" + "\","
                + "  \"wingsState\": \"" + "${duckWingsState}"
                + "\"" + "}");
    }

    @Test(description = "Создание уточки из дерева")
    @CitrusTest
    public void successfulCreateDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "wood", "quack", "ACTIVE");

        saveDuck(runner);

        duckProperties(runner, "${duckId}");

        validateResponse(runner, "{" + "  \"color\": \"" + "${duckColor}" + "\","
                + "  \"height\": " + "${duckHeight}" + ","
                + "  \"material\": \"" + "${duckMaterial}" + "\","
                + "  \"sound\": \"" + "${duckSound}" + "\","
                + "  \"wingsState\": \"" + "${duckWingsState}"
                + "\"" + "}");
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

    public void saveDuck(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .extract(fromBody().expression("$.color", "duckColor"))
                .extract(fromBody().expression("$.height", "duckHeight"))
                .extract(fromBody().expression("$.material", "duckMaterial"))
                .extract(fromBody().expression("$.sound", "duckSound"))
                .extract(fromBody().expression("$.wingsState", "duckWingsState")));
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }
}
