package iot.hustler.io.EasyDictionary.model.pojo;

import java.util.ArrayList;

public class Result {
    private ArrayList<String> datasets;

    public ArrayList<String> getDatasets() {
        return this.datasets;
    }

    public void setDatasets(ArrayList<String> datasets) {
        this.datasets = datasets;
    }

    private String headword;

    public String getHeadword() {
        return this.headword;
    }

    public void setHeadword(String headword) {
        this.headword = headword;
    }

    private int homnum;

    public int getHomnum() {
        return this.homnum;
    }

    public void setHomnum(int homnum) {
        this.homnum = homnum;
    }

    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String part_of_speech;

    public String getPartOfSpeech() {
        return this.part_of_speech;
    }

    public void setPartOfSpeech(String part_of_speech) {
        this.part_of_speech = part_of_speech;
    }

    private ArrayList<Pronunciation> pronunciations;

    public ArrayList<Pronunciation> getPronunciations() {
        return this.pronunciations;
    }

    public void setPronunciations(ArrayList<Pronunciation> pronunciations) {
        this.pronunciations = pronunciations;
    }

    private ArrayList<Sens> senses;

    public ArrayList<Sens> getSenses() {
        return this.senses;
    }

    public void setSenses(ArrayList<Sens> senses) {
        this.senses = senses;
    }

    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
