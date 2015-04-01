package com.om.jss;

import java.io.IOException;

public class TestClass {

	public static void main(String[] args) throws IOException {
		String filePath = "resources/file.txt";
		filePath = Utility.preprocessFile(filePath);
		double score = Utility.scoreSum(filePath);
		System.out.println(String.format("scoreSum : %f",score));
		score = Utility.scoreAvgTotal(filePath);
		System.out.println(String.format("scoreAvgTotal : %f",score));
		score = Utility.scoreAvgRespective(filePath);
		System.out.println(String.format("scoreAvgRespective : %f",score));
		score = Utility.scoreAvgAdjective(filePath);
		System.out.println(String.format("scoreAvgAdjective : %f",score));
		score = Utility.scoreSumAdjective(filePath);
		System.out.println(String.format("scoreSumAdjective : %f",score));
		//System.out.print("==== your thinking is "+Utility.analyseScore(score));
		//System.out.println(new Lemmatizer().lemmatize(Utility.getString(filePath)));
	}
}
