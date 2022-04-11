package model;

public class FlagTransaction {
    private Transaction transaction;
    private String reason;

    public FlagTransaction() {
    }

    public FlagTransaction(Transaction transaction, String reason) {
        this.reason = reason;
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "FlagTransaction{" +
                "transaction=" + transaction +
                ", reason='" + reason + '\'' +
                '}';
    }
}
