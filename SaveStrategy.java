package at.ac.tgm.jertl2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public interface SaveStrategy {
    void save(Rechtschreibtrainer rechtschreibtrainer) throws IOException;

    Rechtschreibtrainer load(File file) throws IOException;
}
