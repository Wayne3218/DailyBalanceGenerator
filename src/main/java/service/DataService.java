package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.TransactionPage;

public class DataService {
    public static TransactionPage mapJsonStringToTransactionPage(String stringData) {
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionPage transactionPage = null;
        try {
            transactionPage = objectMapper.readValue(stringData, TransactionPage.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return transactionPage;
    }
}
