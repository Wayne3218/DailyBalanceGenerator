package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.TransactionPage;

public class DataService {
    public static TransactionPage mapJsonStringToTransactionPage(String stringData) throws MapDataToObjectException {
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionPage transactionPage;

        try {
            transactionPage = objectMapper.readValue(stringData, TransactionPage.class);
        } catch (Exception e) {
            throw new MapDataToObjectException();
        }

        return transactionPage;
    }
}
