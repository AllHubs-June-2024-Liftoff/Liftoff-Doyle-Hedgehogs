package org.launchcode.demo;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ApiActions {

    // Initialize fields
    private static final String APPLICATION_NAME = "LittleOnlineLibrary/0.5";

    private static String apiKey = "PlaceHolder API Key";
    private static final String apiUrl = "http://www.google.com/books/feeds/volumes/?q";

    // Constructor
    public ApiActions() {

    }

//Methods

    public static String ApiSearch(String query) throws Exception {
        String urlString = apiUrl + query +"&key="+apiKey;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = in.readLine();
        StringBuilder content = new StringBuilder();

        while ((inputLine) != null) {
            content.append(inputLine);
            inputLine = in.readLine();
        }

        in.close();
        connection.disconnect();
        return content.toString();
    }

    public static ArrayList<Book> ParseResults(String searchResult){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(searchResult);
        ArrayList<Book> bookInfo = new ArrayList();
        JsonNode itemsNode = root.get("items");
        for (JsonNode itemNode : itemsNode) {
            JsonNode volumeInfoNode = root.get("volumeInfo");
            String currentTitle = volumeInfoNode.get("title").asText();
            String currentAuthor = volumeInfoNode.get("author").asText();
            Book currentBook = new Book(currentTitle, currentAuthor);
            bookInfo.add(currentBook);
        }
        return bookInfo;

    }

}
