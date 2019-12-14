package iot.hustler.io.easydictionary.model.pojo;

import java.util.List;
import java.io.Serializable;

public class SyllablesDTO implements Serializable {
	private int count;
	private List<String> list;

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setList(List<String> list){
		this.list = list;
	}

	public List<String> getList(){
		return list;
	}

	@Override
 	public String toString(){
		return 
			"SyllablesDTO{" + 
			"count = '" + count + '\'' + 
			",list = '" + list + '\'' + 
			"}";
		}
}