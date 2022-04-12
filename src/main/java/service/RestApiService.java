package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestApiService {
    private static final int SUCCESS = 200;
    private static final String GET = "GET";

    /**
     *  Fetch api data from provided url
     * @param urlString String representation of the url to be fetched
     * @return String representation of the fetched data
     * @throws FetchDataException Exception when fetch api fails or returns non-200
     */
    public static String get(String urlString) throws FetchDataException {
        StringBuilder responseData = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET);

            if (connection.getResponseCode() == SUCCESS) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = bufferedReader.readLine();

                while (line != null) {
                    responseData.append(line.trim());   // trim is needed for extra white spaces
                    line = bufferedReader.readLine();
                }
            } else {
                throw new FetchDataException(urlString);
            }
        } catch (Exception e) {
            throw new FetchDataException(urlString);
        }

        return responseData.toString();
    }
}
