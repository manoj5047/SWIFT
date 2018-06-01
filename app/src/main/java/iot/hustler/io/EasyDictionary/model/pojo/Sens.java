package iot.hustler.io.EasyDictionary.model.pojo;

import java.util.ArrayList;

public class Sens {
    private ArrayList<String> definition;

    public ArrayList<String> getDefinition() {
        return this.definition;
    }

    public void setDefinition(ArrayList<String> definition) {
        this.definition = definition;
    }

    private ArrayList<GramaticalExample> gramatical_examples;

    public ArrayList<GramaticalExample> getGramaticalExamples() {
        return this.gramatical_examples;
    }

    public void setGramaticalExamples(ArrayList<GramaticalExample> gramatical_examples) {
        this.gramatical_examples = gramatical_examples;
    }
}
