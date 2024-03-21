package at.ac.tgm.jertl2;

import javax.swing.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Die Klasse beinhaltet Methode zur Erstellung eines Rechtschreibtrainers
 * @author jakob ertl
 * @version 03.10.2023
 */
public class Rechtschreibtrainer {
    private ArrayList<WortPaar> wortPaare;
    private int richtigW;
    private int falschW;

    public ArrayList<WortPaar> getWortPaare() {
        return wortPaare;
    }

    public void setWortPaare(ArrayList<WortPaar> wortPaare) {
        this.wortPaare = wortPaare;
    }

    private String currentWord;
    private String currentUrl;

    private SaveStrategy saveStrategy;

    public int getRichtigW() {
        return richtigW;
    }

    public void setRichtigW(int richtigW) {
        this.richtigW = richtigW;
    }

    public int getFalschW() {
        return falschW;
    }

    public void setFalschW(int falschW) {
        this.falschW = falschW;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public SaveStrategy getSaveStrategy() {
        return saveStrategy;
    }

    public void setSaveStrategy(SaveStrategy saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode getJsonNode() {
        return jsonNode;
    }

    public void setJsonNode(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    public boolean isFalschG() {
        return falschG;
    }

    public void setFalschG(boolean falschG) {
        this.falschG = falschG;
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode jsonNode = objectMapper.readTree(new File("C:\\Users\\jakob\\IdeaProjects\\WortTrainerReloaded\\src\\main\\java\\at\\ac\\tgm\\jertl2\\data.json"));
    private boolean falschG;



    public Rechtschreibtrainer() throws IOException {
        wortPaare = new ArrayList<>();
        saveStrategy = new JSONSave();
        richtigW = 0;
        falschW = 0;
        currentWord = null;
        currentUrl = null;
    }

    private void loadWortpaare() throws IOException {
        Rechtschreibtrainer rechtschreibtrainer = saveStrategy.load(new File("C:\\Users\\jakob\\IdeaProjects\\WortTrainerReloaded\\src\\main\\java\\at\\ac\\tgm\\jertl2\\data.json"));
        this.richtigW = rechtschreibtrainer.getRichtigW();
        this.falschW = rechtschreibtrainer.getFalschW();
        this.wortPaare = rechtschreibtrainer.getWortPaare();
    }

    private void displayOptionPane() throws MalformedURLException, IOException {
        String url = null;
        String wort = null;

        if(!falschG) {
            Random random = new Random();
            int x = random.nextInt(wortPaare.size());
            url = wortPaare.get(x).getUrl();
            wort = wortPaare.get(x).getWort();
            while (wort.equalsIgnoreCase(currentWord)) {
                x = random.nextInt(wortPaare.size());
                url = wortPaare.get(x).getUrl();
                wort = wortPaare.get(x).getWort();
            }
        } else {
            url = currentUrl;
            wort = currentWord;
        }

        // Laden des Bilds von der URL und Skalierung auf maximale Größe
        URL imageURL = new URL(url);
        Image originalImage = ImageIO.read(imageURL);
        Image scaledImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);

        // Text für die Statistik
        String statisticText = "Richtig: " + richtigW + " Falsch: " + falschW;

        // Eingabefeld
        JTextField inputField = new JTextField();

        // OptionPane anzeigen
        Object[] components = {icon, statisticText, "Eingabe:", inputField};
        int option = JOptionPane.showConfirmDialog(null, components, "Eingabeaufforderung", JOptionPane.OK_CANCEL_OPTION);

        // Überprüfen, ob der OK-Button gedrückt wurde
        if (option == JOptionPane.OK_OPTION) {
            String userInput = inputField.getText();
            if (userInput.equalsIgnoreCase(wort)) {
                JOptionPane.showMessageDialog(null, "Richtig!");
                currentWord = wort;
                currentUrl = url;
                falschG = false;
                richtigW++;
            } else {
                JOptionPane.showMessageDialog(null, "Falsch");
                currentWord = wort;
                currentUrl = url;
                falschG = true;
                falschW++;
            }
        }
    }

    private void updateStatisticsInJson() throws IOException {
        saveStrategy.save(this);
    }

    public void runTraining() throws IOException {
        boolean finished = false;

        loadWortpaare();

        do {
            displayOptionPane();
            updateStatisticsInJson();
        } while (!finished);
    }
}