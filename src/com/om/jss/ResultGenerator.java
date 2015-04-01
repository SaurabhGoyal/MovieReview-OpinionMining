package com.om.jss;

import java.io.IOException;

public class ResultGenerator {

	public static void generateResults(String filePath) throws IOException {
		for (int i = 1; i <= 10; i++) {
			String filePath1 = filePath + "/" + i + ".txt";
			filePath1 = Utility.preprocessFile(filePath1);
			double scoreSum = Utility.scoreSum(filePath1);
			double scoreAvgTotal = Utility.scoreAvgTotal(filePath1);
			double scoreAvgRespective = Utility.scoreAvgRespective(filePath1);
			double scoreAvgAdjective = Utility.scoreAvgAdjective(filePath1);
			double scoreSumAdjective = Utility.scoreSumAdjective(filePath1);
			System.out.println(String.format("===============%d=================",i));
			System.out.println(String.format("scoreSum : %f",scoreSum));
			System.out.println(String.format("scoreAvgTotal : %f",scoreAvgTotal));
			System.out.println(String.format("scoreAvgRespective : %f",scoreAvgRespective));
			System.out.println(String.format("scoreAvgAdjective : %f",scoreAvgAdjective));
			System.out.println(String.format("scoreSumAdjective : %f",scoreSumAdjective));
		}
	}

	public static void main(String[] args) throws Exception {
		String filePath = "reviews/Interstellar";
		generateResults(filePath);
	}

}
