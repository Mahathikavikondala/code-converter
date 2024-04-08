import java.util.List;

public class translator2 {
    private static List<List<String>> tokenList;

    public static void main(String[] args) {
        tokenList = lex_analyzer.getArray();
        translateJavaToPython();
    }

    public static void translateJavaToPython() {
        StringBuilder pythonCode = new StringBuilder();
        int indentationLevel = 0;

        for (List<String> line : tokenList) {
            for (String token : line) {
                switch (token) {
                    case "{":
                        pythonCode.append("\n");
                        addIndentation(pythonCode, indentationLevel);
                        pythonCode.append(":");
                        indentationLevel++;
                        break;
                    case "}":
                        pythonCode.append("\n");
                        indentationLevel--;
                        addIndentation(pythonCode, indentationLevel);
                        break;
                    case "for":
                        pythonCode.append("\n");
                        addIndentation(pythonCode, indentationLevel);
                        pythonCode.append("for ");
                        break;
                    case "while":
                        pythonCode.append("\n");
                        addIndentation(pythonCode, indentationLevel);
                        pythonCode.append("while ");
                        break;
                    // Add more cases to handle other Java constructs as needed
                    default:
                        pythonCode.append(token).append(" ");
                        break;
                }
            }
            pythonCode.append("\n");
        }

        System.out.println(pythonCode.toString());
    }

    private static void addIndentation(StringBuilder stringBuilder, int indentationLevel) {
        for (int i = 0; i < indentationLevel; i++) {
            stringBuilder.append("\t");
        }
    }
}