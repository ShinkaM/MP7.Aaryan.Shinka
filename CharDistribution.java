import java.io.File;
import java.net.URI;
import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * A class that determines the character distribution in string.
 * <p>
 * Analyzing character counts is a straightforward way to break any simple substitution cipher.
 * Given a large enough corpus, text in natural languages like English have extremely similar
 * distributions of characters. You can convince yourself of that using the main method below, which
 * compares Rent and Hamlet.
 * <p>
 * By computing the distribution of the characters in a ciphertext, a crytographer can begin the
 * process of breaking simple encryption schemes. (Letter frequency also has something to do with
 * the per-letter scores in Scrabble.)
 * <p>
 * The provided code is incomplete. Modify it so that it works properly and passes the unit tests in
 * <code>CharacterDistributionTest.java</code>.
 *
 * @see <a href="https://cs125.cs.illinois.edu/MP/3/">MP3 Documentation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Letter_frequency">Letter frequency</a>
 */
public class CharDistribution {

    /*
     * Take a single string argument. Return an array of doubles such that array[0] through
     * array[25] are the fractions of characters for 'a-z' and array[26] through array[51] are the
     * fractions for 'A-Z'. Other characters should not be included in the array or the count.
     * You'll want to review the ASCII character table carefully. Call this function
     * computeDistribution.
     */
    /** amount of indexes to shift; aka the length of the english alphabet.
     */
    public static final int TWENTY_SIX = 26;
    /**
     *
     * @param line this is the input, rent and hamlet.
     * @return this is the letter distribution for line.
     */
    public static double[] computeDistribution(final String line) {
        double[] distribution = new double[TWENTY_SIX * 2];
        char[] lAlphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        char[] cAlphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        String letter2nd = "";
        String letter1st = "";
//plit out escape characters
        if (line == null) {
            for (int t = 0; t < distribution.length; t++) {
                distribution[t] += 0;
            }
        }
        for (int h = 0; h < line.length(); h++) {
            if (line.charAt(h) >= 'A' && line.charAt(h) <= 'Z') {
                letter2nd += line.charAt(h);
                } else {
                    if (line.charAt(h) >= 'a' && line.charAt(h) <= 'z') {
                        letter1st += line.charAt(h);
                    }
                }
        }

        for (int q = 0; q < letter2nd.length(); q++) {
            for (int t = 0; t < cAlphabet.length; t++) {
            if (letter2nd.charAt(q) == (cAlphabet[t])) {
                distribution[t + TWENTY_SIX] += 1;
            }
            }
        }
        for (int g = 0; g < letter1st.length(); g++) {
            for (int f = 0; f < lAlphabet.length; f++) {
                if (letter1st.charAt(g) == (lAlphabet[f])) {
                    distribution[f] += 1;
                }
            }

        }
       double bothStrings = letter2nd.length() + letter1st.length();
       for (int k = 0; k < distribution.length; k++) {
           if (bothStrings != 0) {
            distribution[k] *= (1 / bothStrings);
        } else {
            distribution[k] /= 1;
        }
       }
        return distribution;
    }
    /** Text of Rent. Used for interactive testing. */
    private static final String RENT_FILE = "Rent.txt";

    /** Text of Hamlet. Used for interactive testing. */
    private static final String HAMLET_FILE = "Hamlet.txt";

    /**
     * Compare the character distributions in Rent.txt and Hamlet.txt.
     * <p>
     * You are free to review this function, but should not modify it. Note that this function is
     * not tested by the test suite, as it is purely to aid your own interactive testing.
     *
     * @param unused unused input arguments
     */
    public static void main(final String[] unused) {

        String rentText, hamletText;
        try {
            String rentPath = CharDistribution.class.getClassLoader()
                    .getResource(RENT_FILE).getFile();
            rentPath = new URI(rentPath).getPath();
            File rentFile = new File(rentPath);
            Scanner rentScanner = new Scanner(rentFile, "UTF-8");
            rentText = rentScanner.useDelimiter("\\A").next();
            rentScanner.close();

            String hamletPath = CharDistribution.class.getClassLoader()
                    .getResource(HAMLET_FILE).getFile();
            hamletPath = new URI(hamletPath).getPath();
            File hamletFile = new File(hamletPath);
            Scanner hamletScanner = new Scanner(hamletFile, "UTF-8");
            hamletText = hamletScanner.useDelimiter("\\A").next();
            hamletScanner.close();
        } catch (Exception e) {
            throw new InvalidParameterException("Bad file path" + e);
        }

        double[] rentArray = computeDistribution(rentText);
        double[] hamletArray = computeDistribution(hamletText);
        if (rentArray.length != hamletArray.length) {
            throw new RuntimeException("Arrays should have the same length");
        }

        String charactersInOrder = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (charactersInOrder.length() != rentArray.length) {
            throw new RuntimeException("Wrong number of characters in returned array");
        }

        System.out.printf("%-10s%-10s%-10s\n", "Char", "Rent", "Hamlet");
        System.out.println("------------------------------");
        for (int characterIndex = 0; characterIndex < rentArray.length; characterIndex++) {
            System.out.printf("%-10s%-10.2f%-10.2f\n",
                    charactersInOrder.substring(characterIndex, characterIndex + 1),
                    rentArray[characterIndex], hamletArray[characterIndex]);

        }
    }
}
