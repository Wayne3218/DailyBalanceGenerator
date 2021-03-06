package service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestApiServiceTest {
    private final String successURL = "https://resttest.bench.co/transactions/1.json";
    private final String failureURL = "https://resttest.bench.co/transactions/2.json";
    private final String expectedSuccessData = "{\"totalCount\": 38,\"page\": 1,\"transactions\": [{\"Date\": \"2013-12-22\",\"Ledger\": \"Phone & Internet Expense\",\"Amount\": \"-110.71\",\"Company\": \"SHAW CABLESYSTEMS CALGARY AB\"}, {\"Date\": \"2013-12-21\",\"Ledger\": \"Travel Expense, Nonlocal\",\"Amount\": \"-8.1\",\"Company\": \"BLACK TOP CABS VANCOUVER BC\"}, {\"Date\": \"2013-12-21\",\"Ledger\": \"Business Meals & Entertainment Expense\",\"Amount\": \"-9.88\",\"Company\": \"GUILT & CO. VANCOUVER BC\"}, {\"Date\": \"2013-12-20\",\"Ledger\": \"Travel Expense, Nonlocal\",\"Amount\": \"-7.6\",\"Company\": \"VANCOUVER TAXI VANCOUVER BC\"}, {\"Date\": \"2013-12-20\",\"Ledger\": \"Business Meals & Entertainment Expense\",\"Amount\": \"-120\",\"Company\": \"COMMODORE LANES & BILL VANCOUVER BC\"}, {\"Date\": \"2013-12-20\",\"Ledger\": \"Business Meals & Entertainment Expense\",\"Amount\": \"-177.5\",\"Company\": \"COMMODORE LANES & BILL VANCOUVER BC\"}, {\"Date\": \"2013-12-20\",\"Ledger\": \"Equipment Expense\",\"Amount\": \"-1874.75\",\"Company\": \"NINJA STAR WORLD VANCOUVER BC\"}, {\"Date\": \"2013-12-19\",\"Ledger\": \"\",\"Amount\": \"20000\",\"Company\": \"PAYMENT - THANK YOU / PAIEMENT - MERCI\"}, {\"Date\": \"2013-12-19\",\"Ledger\": \"Web Hosting & Services Expense\",\"Amount\": \"-10.99\",\"Company\": \"DROPBOX xxxxxx8396 CA 9.99 USD @ xx1001\"}, {\"Date\": \"2013-12-19\",\"Ledger\": \"Business Meals & Entertainment Expense\",\"Amount\": \"-35.7\",\"Company\": \"NESTERS MARKET #x0064 VANCOUVER BC\"}]}";

    @Test
    void getSuccess() throws FetchDataException{
        String actual = RestApiService.get(successURL);
        assertEquals(expectedSuccessData, actual);
    }

    @Test
    void getFailure() throws FetchDataException {
        String actual = RestApiService.get(failureURL);
        assertNotEquals(expectedSuccessData, actual);
    }

    @Test
    void getException() {
        assertThrows(FetchDataException.class, () -> RestApiService.get("RandomStringHere"));
    }
}