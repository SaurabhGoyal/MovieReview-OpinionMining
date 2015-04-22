package com.om.jss;

import java.io.IOException;

public class TestClass {

	public void generateResults(String filePath) throws IOException {
		Utility utility = new Utility();
		for (int i = 1; i <= 10; i++) {
			String filePath1 = filePath + "/" + i + ".txt";
			double scores[] = utility.analyseFile(filePath1);
			System.out.println(String.format(
					"===============%d=================", i));
			System.out.println(String.format("scoreSum : %f", scores[0]));
			System.out.println(String.format("scoreAvgTotal : %f", scores[1]));
			System.out.println(String.format("scoreAvgRespective : %f",
					scores[2]));
			System.out.println(String.format("scoreAvgAdjective : %f",
					scores[3]));
			System.out.println(String.format("scoreSumAdjective : %f",
					scores[4]));
		}
	}

	public static void main(String[] args) throws Exception {
		String filePath = "reviews/Interstellar";
		new TestClass().generateResults(filePath);
	}

}
