package com.om.jss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Utility {
	public static String preprocessFile(String filePath) throws IOException {
		String processedFilePath = filePath.substring(0,
				filePath.lastIndexOf('.'))
				+ "_processed.txt";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		BufferedWriter bw = new BufferedWriter(new FileWriter(processedFilePath));
		String str;
		StringBuffer sb = new StringBuffer();
		while ((str = br.readLine()) != null) {
			sb.append(str.toLowerCase());
		}
		bw.write(sb.toString());
		br.close();
		bw.close();
		return processedFilePath;
	}

	public static Double performOM(String filePath) throws IOException{
		String pathToSWN = "resources/synset_rated.txt";
		SentiWordNetDemo sentiWordNet = new SentiWordNetDemo(pathToSWN);
		StanfordPosTaggerDemo tagger = new StanfordPosTaggerDemo();
		List<List<String>> doc = StanfordTokenizerDemo.getSentences(filePath);
		Double docScore = 0.0;
		int numberOfSentences = doc.size();
		for (List<String> sentence : doc) {
			int numberOfWords = sentence.size();
			String taggedString = tagger.tagString(sentence.toString());
			String simpleString = tagger.convertToSimpleTags(taggedString);
			System.out.println(sentence.toString());
			String[] tokens = simpleString.split("\\s+");
			Double sentenceScore = 0.0;
			for (String token : tokens) {
				Double score = sentiWordNet.extract(token);
				sentenceScore += score;
				//System.out.println(token + " ->" + score);
			}
			//sentenceScore/=numberOfWords;
			System.out.println("sentence score " + sentenceScore+"\n");
			docScore += sentenceScore;
		}
		docScore/=numberOfSentences;
		System.out.println("doc score " + docScore);
		return docScore;
	}
}