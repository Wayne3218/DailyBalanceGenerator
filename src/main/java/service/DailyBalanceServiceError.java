package service;

public class DailyBalanceServiceError {
    private String errorType;
    private String errorMessage;

    public DailyBalanceServiceError(String errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "DailyBalanceServiceError{" +
                "errorType='" + errorType + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
