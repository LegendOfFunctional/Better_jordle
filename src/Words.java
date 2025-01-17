import java.nio.file.Path;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Words {
    private final Map<Integer, List<String>> wordsByLength = new HashMap<>();
    private final Path wordsFile = Paths.get("src/resources/words.txt");
    public Character[] wordChar;

    public Words() {
        loadWords();
    }

    public void setWord(Integer length) {
        Random rand = new Random();
        List<String> words = wordsByLength.get(length);
        String word = words.get(rand.nextInt(words.size()));
        wordChar = parseWord(word.toLowerCase());
    }

    private Character[] parseWord(String word) {
        Character[] res = new Character[word.length()];
        for (int i = 0; i < word.length(); i++) {
            res[i] = word.charAt(i);
        }
        return res;
    }

    private void putWord(String word) {
        if (!wordsByLength.containsKey(word.length())) {
            wordsByLength.put(word.length(), new ArrayList<>());
        }
        List<String> words = wordsByLength.get(word.length());
        words.add(word);
    }

    private void processLine(String line) {
        Arrays.stream(line.split(" ")).forEach(this::putWord);
    }

    private void loadWords() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(wordsFile);
        } catch (IOException exception) {
            System.err.println("No words file found.");
        }
        for (String line : lines) {
            processLine(line);
        }
    }

    private void promptNewGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to start a new game? (yes/no)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            // Start a new game
            System.out.println("Starting a new game...");
            setWord(5); // Change the length as per your requirement
        } else {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }
}
