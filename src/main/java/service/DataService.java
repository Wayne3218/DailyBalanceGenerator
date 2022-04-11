package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.TransactionPage;

public class DataService {

    /**
     * map json string to TransactionPage object
     * @param stringData String representation of a json object
     * @return TransactionPage object of the input string
     * @throws MapDataToObjectException
     */
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
