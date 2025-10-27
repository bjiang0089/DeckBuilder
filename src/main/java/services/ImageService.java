package services;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class ImageService {

    public static HttpRequest cardSearch(String s) {
        HttpRequest getReqest = null;


        try {
            getReqest = HttpRequest.newBuilder()
                    // Search for all cards that have "finale" in its name
                    .uri(new URI(s))
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
     * Send a search for the card image
     * @param client
     * @param req request with the URI to the card to find
     * @param filename place to save the card
     * @return response for the request
     */
    public static HttpResponse<Path> sendSearch(HttpClient client, HttpRequest req, String filename) {
        HttpResponse<Path> response = null;
        File f = new File(filename);
        try {
            response = client.send(req, HttpResponse.BodyHandlers.ofFile(f.toPath()));
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
     * Takes the card name and converts it to a filename in the imgs
     * folder using snake casing
     * @param s card name to use as filename
     * @return path to the imgs folder with the card as name
     */
    public static String imageName(String s) {
        StringBuilder str = new StringBuilder("imgs/");
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                str.append('_');
            } else if (c == '/') {
                continue;
            }else {
                str.append(c);
            }
        }
        str.append(".png");
        return str.toString();
    }

}
