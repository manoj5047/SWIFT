package iot.hustler.io.easydictionary.model.pojo;

import java.io.Serializable;

public class PronunciationDTO implements Serializable {
	private String all;

	public void setAll(String all){
		this.all = all;
	}

	public String getAll(){
		return all;
	}

	@Override
 	public String toString(){
		return 
			"PronunciationDTO{" + 
			"all = '" + all + '\'' + 
			"}";
		}
}