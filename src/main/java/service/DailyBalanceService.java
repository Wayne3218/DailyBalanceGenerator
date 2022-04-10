package service;

import model.Transaction;
import model.TransactionPage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class DailyBalanceService {
    public static final String SPACE = " ";
    public static final String JSON_FORMAT = ".json";
    public static final String SLASH = "/";
    private String baseURL;
    private int numPages;
    private final static int MAX_ENTRY_PER_PAGE = 10;
    private TreeMap<String, BigDecimal> dateBalanceMap;

    public DailyBalanceService() {
        this("https://resttest.bench.co/transactions");
    }

    public DailyBalanceService(String baseURL) {
        this(baseURL, -1);
    }

    public DailyBalanceService(String baseURL, int numPages) {
        this.baseURL = baseURL;
        this.numPages = numPages;
        this.dateBalanceMap = new TreeMap<>();
    }

    public void run() {
        System.out.println("running, numPages=" + this.numPages);
        int currentPage = 1;
        if (this.numPages == -1) {
            runProcess(String.valueOf(currentPage));
        }

        for (currentPage = 2; currentPage <= this.numPages; currentPage++) {
            runProcess(String.valueOf(currentPage));
        }

        displayResult();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBalanceMap(List<Transaction> transactions) {
        for (Transaction entry : transactions) {
            BigDecimal currentBalance = new BigDecimal(0);
            if (dateBalanceMap.containsKey(entry.getDate())) {
                currentBalance = dateBalanceMap.get(entry.getDate());
            }
            currentBalance = currentBalance.add(entry.getAmount());
            dateBalanceMap.put(entry.getDate(), currentBalance);
        }
    }

    private void updateNumPages(String totalCount) {
        this.numPages = Integer.valueOf(totalCount) / MAX_ENTRY_PER_PAGE + 1;
    }


    private void displayResult() {
        for (Map.Entry<String, BigDecimal> entry : this.dateBalanceMap.entrySet()) {
            System.out.println(entry.getKey() + SPACE + entry.getValue());
        }
    }
}
