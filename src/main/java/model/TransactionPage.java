package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionPage {
    @JsonProperty("totalCount")
    private String totalCount;
    @JsonProperty("page")
    private String page;
    @JsonProperty("transactions")
    private List<Transaction> transactions;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "totalCount='" + totalCount + '\'' +
                ", page='" + page + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
