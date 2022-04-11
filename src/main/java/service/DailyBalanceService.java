package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.FlagTransaction;
import model.Transaction;
import model.TransactionPage;
import java.math.BigDecimal;
import java.util.*;

import static service.Constants.*;

public class DailyBalanceService {
    private String baseURL;
    private int numPages;
    private final static int MAX_ENTRY_PER_PAGE = 10;
    private TreeMap<String, BigDecimal> dateBalanceMap = new TreeMap<>();
    private HashMap<String, HashSet<Integer>> duplicationDetectionMap = new HashMap<>();
    private List<FlagTransaction> flagTransactions = new ArrayList<>();
    private List<DailyBalanceServiceError> errors = new ArrayList<>();


    public DailyBalanceService() {
        this("https://resttest.bench.co/transactions"); // Default URL when no URL gets passed
    }

    public DailyBalanceService(String baseURL) {
        this(baseURL, -1);
    }

    public DailyBalanceService(String baseURL, int numPages) {
        this.baseURL = baseURL;
        this.numPages = numPages;
    }

    public void run() {
        int currentPage = 1;
        if (this.numPages == -1) {
            runProcess(String.valueOf(currentPage));
            currentPage++;
        }

        for (currentPage = currentPage; currentPage <= this.numPages; currentPage++) {
            runProcess(String.valueOf(currentPage));
        }
    }

    private void runProcess(String currentPage) {
        String fetchUrl = this.baseURL + SLASH + currentPage + JSON_FORMAT;
        try {
            String data = RestApiService.get(fetchUrl);

            if (data.isEmpty() == false) {
                TransactionPage transactionPage = DataService.mapJsonStringToTransactionPage(data);

                if (this.numPages == -1) {
                    updateNumPages(transactionPage.getTotalCount());
                }

                updateBalanceMap(transactionPage.getTransactions());
            }
        } catch (FetchDataException e) {
            this.errors.add(new DailyBalanceServiceError("Fetch Data Error", e.getUrlUsed()));
        } catch (MapDataToObjectException e) {
            this.errors.add(new DailyBalanceServiceError("Map Data Error", ""));
        }
    }

    private void updateBalanceMap(List<Transaction> transactions) {
        for (Transaction entry : transactions) {
            checkDuplicatedTransaction(entry);

            BigDecimal currentBalance = new BigDecimal(0);
            if (dateBalanceMap.containsKey(entry.getDate())) {
                currentBalance = dateBalanceMap.get(entry.getDate());
            }
            currentBalance = currentBalance.add(entry.getAmount());
            dateBalanceMap.put(entry.getDate(), currentBalance);
        }
    }

    private void checkDuplicatedTransaction(Transaction transaction) {
        int hashCode = transaction.hashCode();
        if (this.duplicationDetectionMap.get(transaction.getDate()) == null) {
            this.duplicationDetectionMap.put(transaction.getDate(), new HashSet<>());
            this.duplicationDetectionMap.get(transaction.getDate()).add(hashCode);
        } else if (this.duplicationDetectionMap.get(transaction.getDate()).contains(hashCode)) {
            FlagTransaction flagTransaction = new FlagTransaction(transaction, "Duplication");
            this.flagTransactions.add(flagTransaction);
        } else {
            this.duplicationDetectionMap.get(transaction.getDate()).add(hashCode);
        }
    }

    private void updateNumPages(String totalCount) {
        this.numPages = Integer.parseInt(totalCount) / MAX_ENTRY_PER_PAGE + 1;
    }

    private String displayDailyBalances() {
        StringBuilder stringBuilder = new StringBuilder();
        String newLine = NEW_LINE;
        int lineNumber = 1;
        int numOfEntries = this.dateBalanceMap.size();

        for (Map.Entry<String, BigDecimal> entry : this.dateBalanceMap.entrySet()) {
            if (lineNumber == numOfEntries) {
                newLine = "";
            }
            stringBuilder.append(entry.getKey() + TAB + entry.getValue() + newLine);
            lineNumber++;
        }

        return stringBuilder.toString();
    }

    public String display(boolean generateReport) {
        StringBuilder stringBuilder = new StringBuilder();

        if (generateReport) {
            stringBuilder.append("Errors:" + SPACE);
            stringBuilder.append(this.errors);
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("Flag Transactions:" + SPACE);
            stringBuilder.append(this.flagTransactions);
            stringBuilder.append(NEW_LINE);
        } else if (this.errors.isEmpty() == false) {
            return SERVICE_NOT_AVAILABLE;
        }

        stringBuilder.append(displayDailyBalances());

        return stringBuilder.toString();
    }
}
