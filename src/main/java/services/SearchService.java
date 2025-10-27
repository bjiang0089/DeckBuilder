package services;

import com.google.gson.Gson;
import models.Card;
import models.SearchResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class SearchService {

    /**
     * Create a search request. Allows for full Scryfall searches, including parameters.
     * Does not support ordering the search. Defaults to ordering by name.
     * @param s the search parameters
     * @return HTTP Request searching for the name
     */
    public static HttpRequest cardSearch(String s) {
        HttpRequest getReqest = null;
        StringBuilder uri = new StringBuilder(Constants.URL + "/cards/search?q=");
        uri.append(Constants.percentEncode(s));

        try {
            getReqest = HttpRequest.newBuilder()
                    // Search for all cards that have "finale" in its name
                    .uri(new URI(uri.toString()))
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
     * @param gson GSON object to parse JSON to models.SearchResponse
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
