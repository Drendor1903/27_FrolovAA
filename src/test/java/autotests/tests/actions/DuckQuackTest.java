package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class DuckQuackTest extends DuckActionsClient {

    @Test(description = "Проверка кряканья уточки с корректным нечетным Id")
    @CitrusTest
    public void successfulQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        AtomicInteger id = new AtomicInteger();

        do {
            createDuck(runner, duck);

            saveDuckId(runner);

            runner.$(action -> id.set(Integer.parseInt(action.getVariable("duckId"))));

        } while (id.get() % 2 == 0);

        duckQuack(runner, "${duckId}", 3, 2);
        validateResponseString(runner, "{\n" + "  \"sound\": \"quack-quack-quack, quack-quack-quack\"\n" + "}");
    }

    @Test(description = "Проверка кряканья уточки с корректным четным Id")
    @CitrusTest
    public void successfulQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, duck);

            saveDuckId(runner);

            runner.$(action -> id.set(Integer.parseInt(action.getVariable("duckId"))));

        } while (id.get() % 2 != 0);

        duckQuack(runner, "${duckId}", 3, 2);
        validateResponseResource(runner, "duckQuackTest/successfulQuackWithEvenId.json");
    }
}
