package services;

import java.util.HashMap;

public class Constants {

    /** Scryfall API link */
    public static final String URL = "https://api.scryfall.com";

    /**
     * Scryfall requires all search queries be percent encoded
     * Below are the encodings for special characters
     */
    public static HashMap<Character, String> ENCODINGS = new HashMap<Character, String>();

    /**
     *
     */
    public static final String SEARCH_PARAMS = "(o|c|color|id|identity|t|type)";
    /**
     * All the values that can come after a search parameter to indicate how
     * the search should be performed for the given search parameter
     *
     * Ex. id>g (All cards that has green in its color identity)
     * o:"target creature" (all cards that have the words "target creature" in
     *      that exact order in its rules text
     */
    public static final String SEARCH_COMPARITORS = "(>|<|>=|<=|!=|:)";

    /**
     * Initialize the map of special characters to their percent encodings
     * https://en.wikipedia.org/wiki/Percent-encoding
     */
    public static void initializeEncodings() {
        ENCODINGS.put('!', "%21");
        ENCODINGS.put('#', "%23");
        ENCODINGS.put('$', "%24");
        ENCODINGS.put('%', "%25");
        ENCODINGS.put('&', "%26");
        ENCODINGS.put('\'', "%27");
        ENCODINGS.put('(', "%28");
        ENCODINGS.put(')', "%29");

        ENCODINGS.put('*', "%2A");
        ENCODINGS.put('+', "%2B");
        ENCODINGS.put(',', "%2C");
        ENCODINGS.put('/', "%2F");

        ENCODINGS.put(':', "%3A");
        ENCODINGS.put(';', "%3B");
        ENCODINGS.put('<', "%3C");
        ENCODINGS.put('=', "%3D");
        ENCODINGS.put('>', "%3E");
        ENCODINGS.put('?', "%3F");

        ENCODINGS.put('@', "%40");
        ENCODINGS.put('[', "%5B");
        ENCODINGS.put(']', "%5D");
    }

    /**
     * Scryfall requires that all search requests be
     * sent with percent encodings for special characters.
     *
     * Encodes the string using percent encoding values for special characters.
     *
     * @param s the string to encode
     * @return percent encoded string
     */
    public static String percentEncode(String s) {
        char[] vals = s.toCharArray(); // Break string into its characters
        StringBuilder str = new StringBuilder(); // Build up the encoded string

        // Assemble the encoded string character by character
        for (char c: vals) {
            if (c == ' ') {
                str.append('+');
            } else if (ENCODINGS.get(c) == null) {
                str.append(c);
            } else {
                str.append(ENCODINGS.get(c));
            }
        }

        // Return the encoded string
        return str.toString();
    }
}
