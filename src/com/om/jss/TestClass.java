package com.om.jss;

import java.io.IOException;

public class TestClass {

	public static void main(String[] args) throws IOException {
		String filePath = "resources/file.txt";
		filePath = Utility.preprocessFile(filePath);
		double score = Utility.performOM(filePath);
		System.out.print("==== your thinking is "+Utility.analyseScore(score));
		//System.out.println(new Lemmatizer().lemmatize(Utility.getString(filePath)));
	}
}
