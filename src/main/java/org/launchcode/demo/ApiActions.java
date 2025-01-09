package org.launchcode.demo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ApiActions {

    // Initialize fields
    private static final String APPLICATION_NAME = "LittleOnlineLibrary/0.5";

    private static String apiKey = "AIzaSyAhvB_h1FV3outRsS40eI6D5ygcIyWbO1I";
    private static final String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=";

    // Constructor
    public ApiActions() {

    }

//Methods

    public static String ApiSearch(String query) throws IOException {
        String urlString = apiUrl + query +"&key="+apiKey;
        URL url = new URL(urlString);

        //Initialize connection
        HttpURLConnection connection = null;
        BufferedReader in = null;
        
    try{
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Check response from server
        int status = connection.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + status);
        }

        // Read the response
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        return content.toString();

    } catch (IOException e) {
        throw new IOException("Error during API request", e);

    } finally {
        // Close resources
        if (in != null) {
            in.close();
        }
        if (connection != null) {
            connection.disconnect();
        }
    }
}

    public static ArrayList<Book> ParseResults(String searchResult) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(searchResult);
        ArrayList<Book> bookInfo = new ArrayList<>();
        JsonNode itemsNode = root.get("items");
        for (JsonNode itemNode : itemsNode) {
            JsonNode volumeInfoNode = itemNode.get("volumeInfo");
            String currentTitle = String.valueOf(volumeInfoNode.get("title"));
            String currentAuthor = String.valueOf(volumeInfoNode.get("author"));
            Book currentBook = new Book(currentTitle, currentAuthor);
            bookInfo.add(currentBook);
        }
        return bookInfo;

    }

}
