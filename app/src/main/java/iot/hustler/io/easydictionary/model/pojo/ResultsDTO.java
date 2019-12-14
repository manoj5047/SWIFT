package iot.hustler.io.easydictionary.model.pojo;

import java.util.List;
import java.io.Serializable;

public class ResultsDTO implements Serializable {
	private String definition;
	private String partOfSpeech;
	private List<String> synonyms;
	private List<String> antonyms;
	private List<String> attribute;
	private List<String> typeOf;
	private List<String> hasTypes;
	private List<String> examples;

	public List<String> getAntonyms() {
		return antonyms;
	}

	public void setAntonyms(List<String> antonyms) {
		this.antonyms = antonyms;
	}

	public void setDefinition(String definition){
		this.definition = definition;
	}

	public String getDefinition(){
		return definition;
	}

	public void setPartOfSpeech(String partOfSpeech){
		this.partOfSpeech = partOfSpeech;
	}

	public String getPartOfSpeech(){
		return partOfSpeech;
	}

	public void setSynonyms(List<String> synonyms){
		this.synonyms = synonyms;
	}

	public List<String> getSynonyms(){
		return synonyms;
	}

	public void setAttribute(List<String> attribute){
		this.attribute = attribute;
	}

	public List<String> getAttribute(){
		return attribute;
	}

	public void setTypeOf(List<String> typeOf){
		this.typeOf = typeOf;
	}

	public List<String> getTypeOf(){
		return typeOf;
	}

	public void setHasTypes(List<String> hasTypes){
		this.hasTypes = hasTypes;
	}

	public List<String> getHasTypes(){
		return hasTypes;
	}

	public void setExamples(List<String> examples){
		this.examples = examples;
	}

	public List<String> getExamples(){
		return examples;
	}

	@Override
 	public String toString(){
		return 
			"ResultsDTO{" + 
			"definition = '" + definition + '\'' + 
			",partOfSpeech = '" + partOfSpeech + '\'' + 
			",synonyms = '" + synonyms + '\'' + 
			",attribute = '" + attribute + '\'' + 
			",typeOf = '" + typeOf + '\'' + 
			",hasTypes = '" + hasTypes + '\'' + 
			",examples = '" + examples + '\'' + 
			"}";
		}
}