import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class WordCounter extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JLabel wordCountLabel;
    private JButton openFileButton;
    private JButton countButton;

    public WordCounter() {
        setTitle("File Word Counter- Saurabh Jadhav");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        wordCountLabel = new JLabel("Word Count: 0");
        openFileButton = new JButton("Open File");
        countButton = new JButton("Count Words");

        openFileButton.addActionListener(this);
        countButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openFileButton);
        buttonPanel.add(countButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel infoPanel = new JPanel();
        infoPanel.add(wordCountLabel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(infoPanel, BorderLayout.SOUTH);
    }

    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private int countWords(String[] words) {
        int count = 0;
        for (String word : words) {
            if (!word.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openFileButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    String fileContent = readFile(filePath);
                    textArea.setText(fileContent);
                    updateWordCount(fileContent);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error reading the file.");
                }
            }
        } else if (e.getSource() == countButton) {
            String content = textArea.getText();
            String[] words = content.split("\\s+|\\p{Punct}");
            int wordCount = countWords(words);
            wordCountLabel.setText("Word Count: " + wordCount);
        }
    }

    private void updateWordCount(String content) {
        String[] words = content.split("\\s+|\\p{Punct}");
        int wordCount = countWords(words);
        wordCountLabel.setText("Word Count: " + wordCount);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordCounter wordCounterGUI = new WordCounter();
            wordCounterGUI.setVisible(true);
        });
    }
}

