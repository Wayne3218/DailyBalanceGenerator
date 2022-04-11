package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Ledger")
    private String ledger;
    @JsonProperty("Amount")
    private BigDecimal amount;
    @JsonProperty("Company")
    private String company;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLedger() {
        return ledger;
    }

    public void setLedger(String ledger) {
        this.ledger = ledger;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(date, that.date) && Objects.equals(ledger, that.ledger) && Objects.equals(amount, that.amount) && Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, ledger, amount, company);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", ledger='" + ledger + '\'' +
                ", amount=" + amount +
                ", company='" + company + '\'' +
                '}';
    }
}
