package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestApiService {
    private final static int SUCCESS = 200;

    public static String get(String urlString) throws IOException {
        StringBuilder responseData = new StringBuilder();

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == SUCCESS) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bufferedReader.readLine();

            while (line != null) {
                responseData.append(line.trim());   // trim is needed for extra white spaces
                line = bufferedReader.readLine();
            }
        }

        return responseData.toString();
    }
}
