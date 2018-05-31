package iot.hustler.io.EasyDictionary.model.pojo;


import java.util.ArrayList;

public class GramaticalExample {
    private ArrayList<Example> examples;

    public ArrayList<Example> getExamples() {
        return this.examples;
    }

    public void setExamples(ArrayList<Example> examples) {
        this.examples = examples;
    }

    private String pattern;

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}