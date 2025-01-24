package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckDeleteClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(
                http().client("http://localhost:2222")
                        .send()
                        .delete("/api/duck/delete")
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

    public void validateDeleteDuck(TestCaseRunner runner) {
        AtomicInteger duckId = new AtomicInteger();
        runner.$(action -> duckId.set(Integer.parseInt(action.getVariable("duckId"))));

        getAllDucksIds(runner);
        saveAllDucksIds(runner);

        AtomicReference<String> ducksIds = new AtomicReference<>();
        runner.$(action -> ducksIds.set(action.getVariable("${ducksIds}")));

        ArrayList<Integer> ducksIdsList = extractDucksIds(ducksIds);

        for (Integer id : ducksIdsList) {
            if (id == duckId.get()) {
                throw new AssertionError("Duck with id= " + duckId + " was found.");
            }
        }
    }

    public void getAllDucksIds(TestCaseRunner runner) {
        runner.$(
                http().client("http://localhost:2222")
                        .send()
                        .get("/api/duck/getAllIds"));
    }

    public void saveAllDucksIds(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$", "ducksIds")));
    }

    public ArrayList<Integer> extractDucksIds(AtomicReference<String> string) {

        ArrayList<Integer> ducksIds = new ArrayList<>();

        String content = string.get().replace("[", "").replace("]", "");

        String[] numbers = content.split(",\\s*");

        for (String number : numbers) {
            ducksIds.add(Integer.parseInt(number.trim()));
        }

        return ducksIds;
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
