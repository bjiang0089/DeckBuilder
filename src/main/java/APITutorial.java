import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class APITutorial {

    public static void main(String[] args) {
        Gson gson = new Gson();

        // Construct Scryfall search GET request
        HttpRequest getReqest = null;
        try {
            getReqest = HttpRequest.newBuilder()
                    // Search for all cards that have "finale" in its name
                    .uri(new URI(Constants.URL + "/cards/search?q=finale"))
                    .header("User-Agent", "DeckBuilder/1.0")
                    .header("Accept", "*/*")
                    .build();
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI address");
        }

        // Construct the HTTP Client to send the request
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(getReqest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.err.println("IO Error Occured");
        } catch (InterruptedException e) {
            System.err.println("Request Interupted");
        } catch (Exception e) {
            System.err.println("Exception Thrown.");
        }

        System.out.println("Http Response Received");
        try {
            System.out.printf("Response Status: %d\n", response.statusCode());
//            System.out.printf("\n\n%s", response.body());
        } catch (Exception e) {
            System.err.println("Unable to get Status Code");
        }
        SearchResponse searchResult = null;
        try {
            searchResult = gson.fromJson(response.body(), SearchResponse.class);
            System.out.printf("Cards returned: %d\n", searchResult.getData().size());
        } catch (Exception e) {
            System.err.println("Error converting JSon to Search Response");
        }

        List<Card> cards = searchResult.getData();
        for (Card c : cards) {
            System.out.println(c.getName());
        }

        System.out.println("\n\n\nEnd of program\n\n\n");
    }
}
