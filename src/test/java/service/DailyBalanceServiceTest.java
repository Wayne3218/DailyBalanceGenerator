package service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DailyBalanceServiceTest {
    private DailyBalanceService service;
    private DailyBalanceService serviceWithError;
    private String expectedBalanceDisplay = "2013-12-19    19953.31\n" +
            "2013-12-20    -2179.85\n" +
            "2013-12-21    -17.98\n" +
            "2013-12-22    -110.71";

    private String expectedGenerateReportOn = "Errors: []\n" +
            "Flag Transactions: []\n" +
            "2013-12-19    19953.31\n" +
            "2013-12-20    -2179.85\n" +
            "2013-12-21    -17.98\n" +
            "2013-12-22    -110.71";

    private String expectedServiceWithErrorReportFlagOn = "Errors: [DailyBalanceServiceError{errorType='Fetch Data Error', errorMessage='Random url that won't work/1.json'}]\n" +
            "Flag Transactions: []\n";

    @BeforeAll
    void setup() {
        service = new DailyBalanceService("https://resttest.bench.co/transactions", 1);
        service.run();
        serviceWithError = new DailyBalanceService("Random url that won't work");
        serviceWithError.run();
    }

    @Test
    void display() {
        String actual = service.display(false);
        assertEquals(expectedBalanceDisplay, actual);
    }

    @Test
    void displayWithReport() {
        String actual = service.display(true);
        assertEquals(expectedGenerateReportOn, actual);
    }

    @Test
    void displayWithErrorReportFlagOff() {
        String actual = serviceWithError.display(false);
        assertEquals("Service Temporarily Unavailable", actual);
    }

    @Test
    void displayWithErrorReportFlagOn() {
        String actual = serviceWithError.display(true);
        assertEquals(expectedServiceWithErrorReportFlagOn, actual);
    }
}