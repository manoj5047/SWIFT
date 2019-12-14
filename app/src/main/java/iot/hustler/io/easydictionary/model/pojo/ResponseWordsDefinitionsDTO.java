package iot.hustler.io.easydictionary.model.pojo;

import java.util.List;
import java.io.Serializable;

public class ResponseWordsDefinitionsDTO implements Serializable {
	private String word;
	private List<ResultsDTO> results;
	private SyllablesDTO syllables;
	private PronunciationDTO pronunciation;
	private Object frequency;

	public void setWord(String word){
		this.word = word;
	}

	public String getWord(){
		return word;
	}

	public void setResults(List<ResultsDTO> results){
		this.results = results;
	}

	public List<ResultsDTO> getResults(){
		return results;
	}

	public void setSyllables(SyllablesDTO syllables){
		this.syllables = syllables;
	}

	public SyllablesDTO getSyllables(){
		return syllables;
	}

	public void setPronunciation(PronunciationDTO pronunciation){
		this.pronunciation = pronunciation;
	}

	public PronunciationDTO getPronunciation(){
		return pronunciation;
	}

	public void setFrequency(Object frequency){
		this.frequency = frequency;
	}

	public Object getFrequency(){
		return frequency;
	}

	@Override
 	public String toString(){
		return 
			"ResponseWordsDefinitionsDTO{" +
			"word = '" + word + '\'' + 
			",results = '" + results + '\'' + 
			",syllables = '" + syllables + '\'' + 
			",pronunciation = '" + pronunciation + '\'' + 
			",frequency = '" + frequency + '\'' + 
			"}";
		}
}