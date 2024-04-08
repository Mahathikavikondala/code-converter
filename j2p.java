import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaToPythonConverterGUI extends JFrame {

    private JTextArea javaTextArea;
    private JTextArea pythonTextArea;
    private JTextArea resultsTextArea;

    public JavaToPythonConverterGUI() {
        setTitle("JAVA-TO-PYTHON");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        javaTextArea = new JTextArea("Enter ERROR-FREE Java code", 20, 50);
        javaTextArea.setBackground(Color.BLACK);
        javaTextArea.setForeground(Color.WHITE);
        javaTextArea.setCaretColor(Color.WHITE);
        JScrollPane javaScrollPane = new JScrollPane(javaTextArea);

        pythonTextArea = new JTextArea("PYTHON CODE", 20, 50);
        pythonTextArea.setBackground(Color.BLACK);
        pythonTextArea.setForeground(Color.WHITE);
        pythonTextArea.setCaretColor(Color.WHITE);
        JScrollPane pythonScrollPane = new JScrollPane(pythonTextArea);

        resultsTextArea = new JTextArea(20, 100);
        resultsTextArea.setBackground(Color.BLACK);
        resultsTextArea.setForeground(Color.WHITE);
        resultsTextArea.setCaretColor(Color.WHITE);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTextArea);

        JButton openButton = new JButton("OPEN FILE");
        openButton.setBackground(Color.BLACK);
        openButton.setForeground(Color.GREEN);
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileToParse();
            }
        });

        JButton writeButton = new JButton("WRITE FILE");
        writeButton.setBackground(Color.BLACK);
        writeButton.setForeground(Color.GREEN);
        writeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeFile();
            }
        });

        JButton translateButton = new JButton("TRANSLATE");
        translateButton.setBackground(Color.BLACK);
        translateButton.setForeground(Color.GREEN);
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                translateFile();
            }
        });

        JButton runButton = new JButton("RUN");
        runButton.setBackground(Color.BLACK);
        runButton.setForeground(Color.GREEN);
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTransFile();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(openButton);
        buttonPanel.add(writeButton);
        buttonPanel.add(translateButton);
        buttonPanel.add(runButton);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(javaScrollPane, gbc);

        gbc.gridx = 2;
        add(pythonScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(resultsScrollPane, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(buttonPanel, gbc);
    }

    private void openFileToParse() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                javaTextArea.setText(sb.toString());
                resultsTextArea.append("You opened:\n" + selectedFile.getName() + "\n");
                resultsTextArea.append("**********\n");
            } catch (IOException ex) {
                ex.printStackTrace();
                resultsTextArea.append("Error reading file\n");
                resultsTextArea.append("**********\n");
            }
        }
    }

    private void writeFile() {
        String text = javaTextArea.getText();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("fileToParse.java"));
            bw.write(text);
            bw.close();
            resultsTextArea.append("You wrote to: fileToParse.java\n");
            resultsTextArea.append("**********\n");
        } catch (IOException ex) {
            ex.printStackTrace();
            resultsTextArea.append("Error writing file\n");
            resultsTextArea.append("**********\n");
        }
    }

    private void translateFile() {
        pythonTextArea.setText("");
        try {
            // Replace this section with your actual translation logic
            List<String> transarr = new ArrayList<>();
            transarr.add("# Sample Python code");
            transarr.add("print(\"Hello, World!\")");
            BufferedWriter bw = new BufferedWriter(new FileWriter("translatedFile.py"));
            for (String line : transarr) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            resultsTextArea.append("JAVA FILE HAS BEEN TRANSLATED TO PYTHON\n");
            resultsTextArea.append("**********\n");

            // Display translated code in pythonTextArea
            StringBuilder pythonCode = new StringBuilder();
            for (String line : transarr) {
                pythonCode.append(line).append("\n");
            }
            pythonTextArea.setText(pythonCode.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
            resultsTextArea.append("Error translating file\n");
            resultsTextArea.append("**********\n");
        }
    }

    private void runTransFile() {
        // Replace this section with your actual logic to run the Python file
        resultsTextArea.append("Running the translated Python file...\n");
        resultsTextArea.append("Output of translated file is:\n");
        resultsTextArea.append("Hello, World!\n");
        resultsTextArea.append("**********\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JavaToPythonConverterGUI().setVisible(true);
            }
        });
    }
}