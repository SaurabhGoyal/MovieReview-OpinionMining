package com.om.jss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataSetClassifier {

	private Utility utility;
	private int[] tp, tn, fp, fn;
	private int nom = 6, nor;

	public DataSetClassifier() throws IOException {
		utility = new Utility();

	}

	public void classify(String directoryPath, int type) throws IOException {
		if (type < 0) {
			tn = new int[nom];
			fp = new int[nom];
		} else {
			tp = new int[nom];
			fn = new int[nom];
		}
		File folder = new File(directoryPath);
		File[] files = folder.listFiles();
		nor = files.length;
		for (int i = 0; i < nor; i++) {
			double[] scores = utility.analyseFile(files[i].getPath());
			for (int j = 0; j < scores.length; j++) {
				double score = scores[j];
				if (type > 0) {
					if (score > 0) {
						tp[j]++;
						//System.out.println(String.format("%d : true +ve\n", j));
					} else {
						fn[j]++;
						//System.out.println(String.format("%d : true -ve\n", j));
					}
				} else {
					if (score > 0) {
						fp[j]++;
						//System.out.println(String.format("%d : false -ve\n", j));
					} else {
						tn[j]++;
						//System.out.println(String.format("%d : false +ve\n", j));
					}
				}
			}

		}
	}

	public static void main(String[] args) throws IOException {
		String posReviewsPath = "reviews/txt_sentoken/pos";
		String negReviewsPath = "reviews/txt_sentoken/neg";
		DataSetClassifier dsc = new DataSetClassifier();
		dsc.classify(posReviewsPath, 1);
		dsc.classify(negReviewsPath, -1);
		String resultFile = "reviews/results/result.txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile));
		for(int i=0;i<dsc.nom;i++){
			bw.write(String.format("method : %d\n",i));
			bw.write(String.format("tp : %d, fn : %d, tn : %d, fp : %d\n",dsc.tp[i], dsc.fn[i], dsc.tn[i], dsc.fp[i]));
		}
		bw.close();
	}

}
