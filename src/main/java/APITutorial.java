import com.google.gson.Gson;
import models.Card;
import services.Constants;
import services.ImageService;
import services.SearchService;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Tutorial used for using Gson and making API calls
 * https://youtu.be/9oq7Y8n1t00?si=tG_Xm60enM1xv55N
 */
public class APITutorial {
    private static final Gson gson = new Gson();

    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        getCardByName();

        System.out.println("\n\n\nEnd of program\n\n\n");
    }

    /**
     * Tests the search functionality of
     */
    private static List<Card> searchRoute() {
        Constants.initializeEncodings();
        Scanner scan = new Scanner(System.in);

        System.out.println("\n\n\n");
        System.out.println("Enter Scryfall search. Can use advanced search syntax.");
        System.out.print("> ");

        // Construct Scryfall search GET request
        HttpRequest getRequest = SearchService.cardSearch(scan.nextLine());
        scan.close();

        // Construct the HTTP Client to send the request
        HttpResponse<String> response = SearchService.sendSearch(client, getRequest);

        // Print the name of the cards found
        List<Card> cards = SearchService.extractData(response, gson);
        if (cards != null) {
            for (Card c : cards) {
                System.out.println(c.getName());
            }
        }
        return cards;
    }

    /**
     * Prompts the user for a search. Gets and saves the first card image from the results
     * to the imgs/ directory.
     */
    private static void getCardByName() {
        List<Card> cards = searchRoute();
        Card card = cards.get(0);

        // Get the normal image of a card
        HttpRequest request = ImageService.cardSearch(card.getImage_uris().getPng());
        String filename = ImageService.imageName(card.getName());
        HttpResponse<Path> response = ImageService.sendSearch(client, request, filename);
        System.out.println(response.toString());
    }

}
