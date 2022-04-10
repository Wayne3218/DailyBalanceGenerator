package service;

import model.Transaction;
import model.TransactionPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataServiceTest {

    private TransactionPage expected;

    private String actualJsonDataSuccess = "{\n" +
            "    \"totalCount\": 1,\n" +
            "    \"page\": 1,\n" +
            "    \"transactions\": [{\n" +
            "        \"Date\": \"2013-12-22\",\n" +
            "        \"Ledger\": \"Phone & Internet Expense\",\n" +
            "        \"Amount\": \"-110.71\",\n" +
            "        \"Company\": \"SHAW CABLESYSTEMS CALGARY AB\"\n" +
            "    }]\n" +
            "}";

    private String getActualJsonDataFailure = "{\n" +
            "    \"totalCount\": 1,\n" +
            "    \"page\": 1,\n" +
            "    \"transactions\": [{\n" +
            "        \"Date\": \"2013-12-22\",\n" +
            "        \"Ledger\": \"Phone & Internet Expense\",\n" +
            "        \"Amount\": \"100\",\n" +
            "        \"Company\": \"SHAW CABLESYSTEMS CALGARY AB\"\n" +
            "    }]\n" +
            "}";

    private String extraFieldJsonData = "{\n" +
            "    \"totalCount\": 1,\n" +
            "    \"page\": 1,\n" +
            "    \"transactions\": [{\n" +
            "        \"Date\": \"2013-12-22\",\n" +
            "        \"Ledger\": \"Phone & Internet Expense\",\n" +
            "        \"Amount\": \"-110.71\",\n" +
            "        \"Extra1\": \"extra message\",\n" +
            "        \"Extra2\": \"extra message again\",\n" +
            "        \"Company\": \"SHAW CABLESYSTEMS CALGARY AB\"\n" +
            "    }]\n" +
            "}";

    @BeforeAll
    public void setup() {
        expected = new TransactionPage();
        expected.setPage("1");
        expected.setTotalCount("1");
        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setAmount(new BigDecimal("-110.71"));
        expectedTransaction.setCompany("SHAW CABLESYSTEMS CALGARY AB");
        expectedTransaction.setDate("2013-12-22");
        expectedTransaction.setLedger("Phone & Internet Expense");
        List<Transaction> transactionList = Arrays.asList(new Transaction[]{expectedTransaction});
        expected.setTransactions(transactionList);
    }

    @org.junit.jupiter.api.Test
    void mapJsonStringToTransactionPageSuccess() {
        TransactionPage actual = DataService.mapJsonStringToTransactionPage(this.actualJsonDataSuccess);
        assertEquals(this.expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void mapJsonStringToTransactionPageFailure() {
        TransactionPage actual = DataService.mapJsonStringToTransactionPage(this.getActualJsonDataFailure);
        assertNotEquals(this.expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void mapJsonStringToTransactionPageExtraFields() {
        TransactionPage actual = DataService.mapJsonStringToTransactionPage(this.extraFieldJsonData);
        assertEquals(this.expected.toString(), actual.toString());
    }
}