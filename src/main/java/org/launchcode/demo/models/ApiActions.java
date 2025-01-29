package org.launchcode.demo.models;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.launchcode.demo.models.Volume;

public class ApiActions {

    // Initialize fields
    private static final String APPLICATION_NAME = "LittleOnlineLibrary/0.5";

    private static String apiKey = "AIzaSyC2jQ3l0bzLQnq5Ow4mHerwHXTpNULIumc";
    private static final String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=";

    // Constructor
    public ApiActions() {

    }

//Methods

    public static String ApiSearch(String query) throws IOException {
        String betterQuery = query.replaceAll("\\s+", "_");
        String urlString = apiUrl + betterQuery +"&key="+apiKey;
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
        // Close all connections
        if (in != null) {
            in.close();
        }
        if (connection != null) {
            connection.disconnect();
        }
    }
}

    public static ArrayList<Volume> ParseResults(String searchResult) throws JsonProcessingException, MalformedURLException {

        ArrayList<Volume> volumeInfo = new ArrayList<>();

        //Create a readable JSON from API spaghetti
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(searchResult);

        //Strip away top layer of JSON to reach important innards
        JsonNode itemsNode = jsonNode.get("items");

        //Iterate through itemNode to strip out the needed info
        for (JsonNode itemNode : itemsNode) {
            String currentId = String.valueOf(itemNode.get("id")).replaceAll("\"", "");
            JsonNode volumeInfoNode = itemNode.get("volumeInfo");
            String currentTitle =  String.valueOf(volumeInfoNode.get("title"));
            String currentAuthors = String.valueOf(volumeInfoNode.get("authors"));
            String currentDescription = String.valueOf(volumeInfoNode.get("subtitle"));
            JsonNode volumeImageNode = volumeInfoNode.get("imageLinks");
            String currentThumbnail = String.valueOf(volumeImageNode.get("thumbnail"));
        //Remove double quotes and square brackets added by API
            String tidyTitle = currentTitle.replaceAll("\"", "");
            String tidyAuthors = currentAuthors.replaceAll("\"", "").replaceAll("\\[","").replaceAll("\\]","");
            String tidyDescription = currentDescription.replaceAll("\"", "");
            String tidyThumbnail = currentThumbnail.replaceAll("\"", "");
        //Constructor
            Volume currentVolume = new Volume(currentId, tidyAuthors, tidyTitle, tidyDescription, tidyThumbnail);
            volumeInfo.add(currentVolume);
        }
        return volumeInfo;

    }

}

//TO DO Clean up thumbnail, author, and title by removing the API's double quotes



