package com.om.jss;

public enum Constants {
	
	RTAPI ("pb8mft5vbvufusmu9una6cf7"),
	pathToSWN ("resources/synset_rated.txt");
	
	private String value;

	private Constants(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

}
