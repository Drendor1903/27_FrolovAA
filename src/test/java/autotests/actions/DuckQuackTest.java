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

public class DuckQuackTest extends TestNGCitrusSpringSupport {

    @Test(description = "Проверка кряканья уточки с корректным нечетным Id")
    @CitrusTest
    public void successfulQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        AtomicInteger id = new AtomicInteger();

        do {
            createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

            saveDuckId(runner);

            runner.$(action -> id.set(Integer.parseInt(action.getVariable("duckId"))));

        } while (id.get() % 2 == 0);

        duckQuack(runner, "${duckId}", 3, 2);
        validateResponse(runner, "{\n" + "  \"sound\": \"quack-quack-quack, quack-quack-quack\"\n" + "}");
    }

    @Test(description = "Проверка кряканья уточки с корректным четным Id")
    @CitrusTest
    public void successfulQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");

            saveDuckId(runner);

            runner.$(action -> id.set(Integer.parseInt(action.getVariable("duckId"))));

        } while (id.get() % 2 != 0);

        duckQuack(runner, "${duckId}", 3, 2);
        validateResponse(runner, "{\n" + "  \"sound\": \"quack-quack-quack, quack-quack-quack\"\n" + "}");
    }

    public void duckQuack(TestCaseRunner runner, String id, int repetitionCount, int soundCount) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", Integer.toString(repetitionCount))
                .queryParam("soundCount", Integer.toString(soundCount)));
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

    public void saveDuckId(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }
}
