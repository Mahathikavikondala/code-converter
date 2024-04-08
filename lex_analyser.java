import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class lex_analyzer {
    private static List<List<String>> finalList = new ArrayList<>();

    public static void main(String[] args) {
        mainFunction();
        List<List<String>> convertedCode = getArray();
        for (List<String> line : convertedCode) {
            for (String word : line) {
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }

    public static void mainFunction() {
        List<String> finalArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("fileToParse.java"))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> result = lexLine(line);
                finalList.add(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (List<String> line : finalList) {
            for (String word : line) {
                finalArray.add(word);
            }
        }
        removeTabs(finalArray);
    }

    public static List<String> lexLine(String s) {
        List<String> arr = new ArrayList<>();
        int start = 0;
        String[] stuff = { " ", ",", "\"", "(", ")", "{", "}", ";", "!", "[", "]", "<=", "==", ">=",
                "!=", "++", "--", "-=", "+=", "=", "+", "-", ">", "<", ":" };

        for (int x = 0; x < s.length(); x++) {
            if (x < s.length() - 1) {
                if (s.charAt(x) == '/' && s.charAt(x + 1) == '/') {
                    arr.add(s.substring(start));
                    break;
                }
            }

            if (contains(stuff, s.substring(x, x + 2))) {
                arr.add(s.substring(start, x));
                arr.add(s.substring(x, x + 2));
                start = x + 2;
                continue;
            }

            if (contains(stuff, s.substring(x, x + 1))) {
                if (x > 0 && contains(stuff, s.substring(x - 1, x + 1))) {
                    continue;
                } else {
                    arr.add(s.substring(start, x));
                    arr.add(s.substring(x, x + 1));
                    start = x + 1;
                    continue;
                }
            }

            if (x == s.length() - 1) {
                arr.add(s.substring(start));
            }
        }

        while (arr.contains("")) {
            arr.remove("");
        }

        return arr;
    }

    public static void removeTabs(List<String> finalArray) {
        // Implementation of removeTabs is already provided in the previous code.
        // No changes are required in this method.
        // ...
        int indentationLevel = 0;
        for (int index1 = 0; index1 < finalList.size(); index1++) {
            List<String> line = finalList.get(index1);
            for (int index2 = 0; index2 < line.size(); index2++) {
                String word = line.get(index2);
                int tabsCount = 0;
                while (word.startsWith("\t")) {
                    tabsCount++;
                    word = word.substring(1);
                }
                indentationLevel += tabsCount;
                line.set(index2, word);
                while (word.contains("\t")) {
                    int tabIndex = word.indexOf("\t");
                    if (tabIndex == 0) {
                        word = word.substring(tabIndex + 1);
                    } else {
                        word = word.substring(0, tabIndex) + word.substring(tabIndex + 1);
                    }
                }
                line.set(index2, word);
            }
            line.removeIf(String::isEmpty);
            if (line.contains("{")) {
                indentationLevel++;
            } else if (line.contains("}")) {
                indentationLevel--;
            }
            for (int i = 0; i < indentationLevel; i++) {
                line.add(0, "\t");
            }
        }
    }

    public static List<List<String>> getArray() {
        return finalList;
    }

    public static boolean contains(String[] array, String target) {
        for (String element : array) {
            if (element.equals(target)) {
                return true;
            }
        }
        return false;
    }
}