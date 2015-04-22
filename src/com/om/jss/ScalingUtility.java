package com.om.jss;

import java.io.File;
import java.io.IOException;

public class ScalingUtility {
	
	public static Utility u;
	public static int nom;
	public static double[] min, max;

	public static void analyseFiles(String directoryPath, int size) throws IOException {
		File folder = new File(directoryPath);
		File[] files = folder.listFiles();
		for(int i=0;i<Math.min(size, files.length);i++){
			double[] scores = u.analyseFile(files[i].getPath());
			//System.out.println(String.format("%f",scores[1]));
			for(int j=0;j<nom;j++){
				max[j] = Math.max(max[j], scores[j]);
				min[j] = Math.min(min[j], scores[j]);
			}
		}
	}

	public static void main(String... s) throws IOException {
		u = new Utility();
		nom = 6;
		max = new double[nom];
		min = new double[nom];
		analyseFiles("reviews/txt_sentoken/pos", 1000);
		analyseFiles("reviews/txt_sentoken/neg", 1000);
		for(int i=0;i<nom;i++){
			System.out.println(String.format("Method %d : [%f, %f]",i,min[i],max[i]));
		}
	}

}
