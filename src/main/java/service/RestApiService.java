package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static service.Constants.SUCCESS;

public class RestApiService {

    /**
     *  Fetch api data from provided url
     * @param urlString String representation of the url to be fetch
     * @return String representation of the fetched data
     * @throws FetchDataException
     */
    public static String get(String urlString) throws FetchDataException {
        StringBuilder responseData = new StringBuilder();

        try {
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
            } else {
                throw new FetchDataException(urlString);
            }
        } catch (Exception e) {
            throw new FetchDataException(urlString);
        }

        return responseData.toString();
    }
}
