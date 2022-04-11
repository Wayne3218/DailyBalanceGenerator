package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionPage that = (TransactionPage) o;
        return Objects.equals(totalCount, that.totalCount) && Objects.equals(page, that.page) && Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, page, transactions);
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
