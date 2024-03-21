package at.ac.tgm.jertl2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JSONSave implements SaveStrategy {

    @Override
    public void save(Rechtschreibtrainer rechtschreibtrainer) throws IOException {
        ObjectNode statNode = (ObjectNode) rechtschreibtrainer.getJsonNode().path("Statistik");
        int richtigW = rechtschreibtrainer.getRichtigW();
        statNode.put("richtig", richtigW);
        int falschW = rechtschreibtrainer.getFalschW();
        statNode.put("falsch", falschW);
        rechtschreibtrainer.getObjectMapper().writeValue(new File("C:\\Users\\jakob\\IdeaProjects\\WortTrainerReloaded\\src\\main\\java\\at\\ac\\tgm\\jertl2\\data.json"), rechtschreibtrainer.getJsonNode());
    }

    @Override
    public Rechtschreibtrainer load(File file) throws IOException {
        Rechtschreibtrainer rechtschreibtrainer = new Rechtschreibtrainer();
        ArrayList<WortPaar> wortPaare = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(file);
        JsonNode wortpaareNode = jsonNode.path("Wortpaare");
        for (int i = 0; i < wortpaareNode.size(); i++) {
            JsonNode randomWortpaarNode = wortpaareNode.get(i);
            String wort = randomWortpaarNode.path(randomWortpaarNode.fieldNames().next()).get("Wort").asText();
            String url = randomWortpaarNode.path(randomWortpaarNode.fieldNames().next()).get("URL").asText();
            wortPaare.add(new WortPaar(wort, url));
        }
        rechtschreibtrainer.setWortPaare(wortPaare);
        int[] werte = new int[2];
        JsonNode statistikNode = jsonNode.path("Statistik");
        werte[0] = statistikNode.get("richtig").asInt();
        werte [1] = statistikNode.get("falsch").asInt();
        rechtschreibtrainer.setFalschW(werte[1]);
        rechtschreibtrainer.setRichtigW(werte[0]);
        return rechtschreibtrainer;
    }
}
