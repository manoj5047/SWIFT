package iot.hustler.io.EasyDictionary.model.pojo;

import java.util.ArrayList;


public class Pronunciation {
    private ArrayList<iot.hustler.io.EasyDictionary.model.model.pojo.Audio> audio;

    public ArrayList<iot.hustler.io.EasyDictionary.model.model.pojo.Audio> getAudio() {
        return this.audio;
    }

    public void setAudio(ArrayList<iot.hustler.io.EasyDictionary.model.model.pojo.Audio> audio) {
        this.audio = audio;
    }

    private String ipa;

    public String getIpa() {
        return this.ipa;
    }

    public void setIpa(String ipa) {
        this.ipa = ipa;
    }
}
