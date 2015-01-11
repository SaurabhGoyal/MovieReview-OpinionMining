package com.om.jss;

import java.io.IOException;

public class TestClass {

	public static void main(String[] args) throws IOException {
		String filePath = "resources/file.txt";
		Double score = Utility.performOM(filePath);
		System.out.print("==== your thinking is ");
		if (score < 0.25)
			System.out.println("Negative");
		else if (score < 0.5)
			System.out.println("Neutral");
		else if (score < 0.75)
			System.out.println("jolly");
		else
			System.out.println("'No one can depress you'");
	}
}
