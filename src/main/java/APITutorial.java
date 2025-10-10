import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class APITutorial {

    public static void main(String[] args) {
        Gson gson = new Gson();
        Scanner scan = new Scanner(System.in);

        System.out.println("\n\n\n");
        System.out.println("Enter Scryfall name search");
        System.out.println("Do not use additional search parameters like c, o, mv, etc");
        System.out.print("> ");

        // Construct Scryfall search GET request
        HttpRequest getRequest = nameSearch(scan.nextLine());
        scan.close();

        // Construct the HTTP Client to send the request
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = sendSearch(client, getRequest);

        // Print the name of the cards found
        List<Card> cards = extractData(response, gson);
        if (cards != null) {
            for (Card c : cards) {
                System.out.println(c.getName());
            }
        }

        System.out.println("\n\n\nEnd of program\n\n\n");
    }

    /**
     * Create a search request. Only searches by name
     * Do not use any other search paramaters like mv= or o:
     * @param s the name to search
     * @return HTTP Request searching for the name
     */
    public static HttpRequest nameSearch(String s) {
        HttpRequest getReqest = null;
        StringBuilder str = new StringBuilder(Constants.URL + "/cards/search?q=");
        char[] query = s.toCharArray();

        // Replace all spaces with '+'
        for (char c: query) {
            if (c == ' ') {
                str.append('+');
            } else {
                str.append(c);
            }
        }

        try {
            getReqest = HttpRequest.newBuilder()
                    // Search for all cards that have "finale" in its name
                    .uri(new URI(str.toString()))
                    .header("User-Agent", "DeckBuilder/1.0")
                    .header("Accept", "*/*")
                    .build();
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI address");
            return null;
        }
        return getReqest;
    }

    /**
     * Send a search query as a GET request
     * @param client HTTPClient to send requests
     * @param req the request to send
     * @return The response to the HTTP GET Request
     */
    public static HttpResponse<String> sendSearch(HttpClient client, HttpRequest req) {
        HttpResponse<String> response = null;
        try {
            response = client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.err.println("IO Error Occured");
        } catch (InterruptedException e) {
            System.err.println("Request Interupted");
        } catch (Exception e) {
            System.err.println("Exception Thrown.");
        }

        return response;
    }

    /**
     * Extract the list of cards found in the search
     * @param response HTTP response to parse
     * @param gson GSON object to parse JSON to SearchResponse
     * @return List of Cards or null to indicate an error
     */
    public static List<Card> extractData(HttpResponse<String> response, Gson gson) {
        try {
            System.out.printf("Response Status: %d\n", response.statusCode());
//            System.out.printf("\n\n%s", response.body());
            if (response.statusCode() != 200) {
                return null;
            }
        } catch (Exception e) {
            System.err.println("Unable to get Status Code");
            return null;
        }
        SearchResponse searchResult = null;
        try {
            searchResult = gson.fromJson(response.body(), SearchResponse.class);
            System.out.printf("Cards returned: %d\n\n", searchResult.getData().size());
        } catch (Exception e) {
            System.err.println("Error converting JSon to Search Response");
            return null;
        }

        return searchResult.getData();
    }
}
