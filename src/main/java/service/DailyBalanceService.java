package service;

import model.FlagTransaction;
import model.Transaction;
import model.TransactionPage;
import java.math.BigDecimal;
import java.util.*;

public class DailyBalanceService {

    // Constants
    private static final int MAX_ENTRY_PER_PAGE = 10;
    private static final String SPACE = " ";
    private static final String TAB = "    ";
    private static final String JSON_FORMAT = ".json";
    private static final String SLASH = "/";
    private static final String NEW_LINE = "\n";
    private static final String SERVICE_NOT_AVAILABLE_TEXT = "Service Temporarily Unavailable";

    private final String baseURL;
    private int numPages;
    private final TreeMap<String, BigDecimal> dateBalanceMap = new TreeMap<>();
    private final HashMap<String, HashSet<Integer>> duplicationDetectionMap = new HashMap<>();
    private final List<FlagTransaction> flagTransactions = new ArrayList<>();
    private final List<DailyBalanceServiceError> errors = new ArrayList<>();


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

    /**
     * run this service
     */
    public void run() {
        int currentPage = 1;
        if (this.numPages == -1) {
            TransactionPage transactionPage = fetchDataToObject(String.valueOf(currentPage));
            updateBalanceMap(transactionPage);
            currentPage++;
        }

        for (; currentPage <= this.numPages; currentPage++) {
            TransactionPage transactionPage = fetchDataToObject(String.valueOf(currentPage));
            updateBalanceMap(transactionPage);
        }
    }

    /**
     * run the process of fetch data, map data and update balance map for one page of transactions
     * @param currentPage String representation of current page to process
     */
    private TransactionPage fetchDataToObject(String currentPage) {
        String fetchUrl = this.baseURL + SLASH + currentPage + JSON_FORMAT;
        TransactionPage transactionPage = null;
        try {
            String data = RestApiService.get(fetchUrl);

            if (data.isEmpty() == false) {
                transactionPage = DataService.mapJsonStringToTransactionPage(data);

                if (this.numPages == -1) {
                    updateNumPages(transactionPage.getTotalCount());
                }
            }
        } catch (FetchDataException e) {
            this.errors.add(new DailyBalanceServiceError("Fetch Data Error", e.getUrlUsed()));
        } catch (MapDataToObjectException e) {
            this.errors.add(new DailyBalanceServiceError("Map Data Error", ""));
        }

        return transactionPage;
    }

    /**
     * update service's balances map with list of transactions collected
     * @param transactionPage TransactionPage object
     */
    private void updateBalanceMap(TransactionPage transactionPage) {
        if (transactionPage != null) {
            for (Transaction entry : transactionPage.getTransactions()) {
                checkDuplicatedTransaction(entry);

                BigDecimal currentBalance = new BigDecimal(0);
                if (dateBalanceMap.containsKey(entry.getDate())) {
                    currentBalance = dateBalanceMap.get(entry.getDate());
                }
                currentBalance = currentBalance.add(entry.getAmount());
                dateBalanceMap.put(entry.getDate(), currentBalance);
            }
        }
    }

    /**
     * check for duplicated transactions
     * @param transaction Transaction object to check against
     */
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

    /**
     * update total number of transaction pages with calculation
     * @param totalCount String representation of total count field from API data
     */
    private void updateNumPages(String totalCount) {
        this.numPages = Integer.parseInt(totalCount) / MAX_ENTRY_PER_PAGE + 1;
    }

    /**
     * generate the text of daily running balances collected
     * @return String representation of all daily balances
     */
    private String generateDailyBalanceText() {
        StringBuilder stringBuilder = new StringBuilder();
        int lineNumber = 1;
        int numOfEntries = this.dateBalanceMap.size();

        for (Map.Entry<String, BigDecimal> entry : this.dateBalanceMap.entrySet()) {
            stringBuilder.append(entry.getKey()).append(TAB).append(entry.getValue());
            if (lineNumber < numOfEntries) {
                stringBuilder.append(NEW_LINE);
            }
            lineNumber++;
        }

        return stringBuilder.toString();
    }

    /**
     * generate report text
     * @return a String representation of the report
     */
    private String generateReportText() {
        String reportString = "Errors:" + SPACE +
                this.errors +
                NEW_LINE +
                "Flag Transactions:" + SPACE +
                this.flagTransactions +
                NEW_LINE;

        return reportString;
    }

    /**
     * construct daily running balances collected, with the option to display report
     * @param generateReport Boolean to print report or not
     * @return String representation of balances, with the option of the report
     */
    public String display(boolean generateReport) {
        StringBuilder stringBuilder = new StringBuilder();

        if (generateReport) {
            stringBuilder.append(generateReportText());
        } else if (this.errors.isEmpty() == false) {
            return SERVICE_NOT_AVAILABLE_TEXT;
        }

        stringBuilder.append(generateDailyBalanceText());
        return stringBuilder.toString();
    }
}
