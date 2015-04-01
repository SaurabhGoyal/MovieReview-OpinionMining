package com.om.jss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Utility {

	public static String getString(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuffer sb = new StringBuffer();
		String str;
		while((str = br.readLine())!=null){
			sb.append(str+"\n");
		}
		br.close();
		return sb.toString();
	}

	public static String preprocessFile(String filePath) throws IOException {
		String processedFilePath = filePath.substring(0,
				filePath.lastIndexOf('.'))
				+ "_processed.txt";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(processedFilePath));
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

	public static double scoreSum(String filePath) throws IOException {
		String pathToSWN = Constants.pathToSWN.getValue();
		SentiWordNetDemo sentiWordNet = new SentiWordNetDemo(pathToSWN);
		Lemmatizer lemmatizer = new Lemmatizer();
		PosTagger tagger = new PosTagger();
		List<List<String>> doc = Tokenizer.getSentences(filePath);
		double docScore = 0.0;
		int numberOfSentences = doc.size();
		for (List<String> sentence : doc) {
			//int numberOfWords = sentence.size();
			List<String> lemmatizedString = lemmatizer.lemmatize(sentence.toString());
			String taggedString = tagger.tagString(lemmatizedString.toString());
			String simpleString = tagger.convertToSimpleTags(taggedString);
			String[] tokens = simpleString.split("\\s+");
			//System.out.println(simpleString.toString());
			double sentenceScore = 0.0;
			for (String token : tokens) {
				double score = sentiWordNet.extract(token);
				sentenceScore += score;
				// System.out.println(token + " ->" + score);
			}
			// sentenceScore/=numberOfWords;
			//System.out.println("sentence score " + sentenceScore + "\n");
			docScore += sentenceScore;
		}
		docScore /= numberOfSentences;
		//System.out.println("doc score " + docScore);
		return docScore;
	}
	
	public static double scoreAvgTotal(String filePath) throws IOException {
		String pathToSWN = Constants.pathToSWN.getValue();
		SentiWordNetDemo sentiWordNet = new SentiWordNetDemo(pathToSWN);
		Lemmatizer lemmatizer = new Lemmatizer();
		PosTagger tagger = new PosTagger();
		List<List<String>> doc = Tokenizer.getSentences(filePath);
		double docScore = 0.0;
		int numberOfSentences = doc.size();
		for (List<String> sentence : doc) {
			int numberOfWords = sentence.size();
			List<String> lemmatizedString = lemmatizer.lemmatize(sentence.toString());
			String taggedString = tagger.tagString(lemmatizedString.toString());
			String simpleString = tagger.convertToSimpleTags(taggedString);
			//System.out.println(lemmatizedString.toString());
			String[] tokens = simpleString.split("\\s+");
			double sentenceScore = 0.0;
			for (String token : tokens) {
				double score = sentiWordNet.extract(token);
				sentenceScore += score;
				// System.out.println(token + " ->" + score);
			}
			sentenceScore/=numberOfWords;
			//System.out.println("sentence score " + sentenceScore + "\n");
			docScore += sentenceScore;
		}
		docScore /= numberOfSentences;
		//System.out.println("doc score " + docScore);
		return docScore;
	}
	
	public static double scoreAvgRespective(String filePath) throws IOException {
		String pathToSWN = Constants.pathToSWN.getValue();
		SentiWordNetDemo sentiWordNet = new SentiWordNetDemo(pathToSWN);
		Lemmatizer lemmatizer = new Lemmatizer();
		PosTagger tagger = new PosTagger();
		List<List<String>> doc = Tokenizer.getSentences(filePath);
		double docScore = 0.0;
		int numberOfSentences = doc.size();
		for (List<String> sentence : doc) {
			int numberOfNeg = 0, numberOfPos = 0;
			double negSentence = 0.0, posSentence = 0.0;
			List<String> lemmatizedString = lemmatizer.lemmatize(sentence.toString());
			String taggedString = tagger.tagString(lemmatizedString.toString());
			String simpleString = tagger.convertToSimpleTags(taggedString);
			//System.out.println(lemmatizedString.toString());
			String[] tokens = simpleString.split("\\s+");
			for (String token : tokens) {
				double score = sentiWordNet.extract(token);
				if(score<0){
					negSentence += score;
					numberOfNeg++;
				}
				else{
					posSentence += score;
					numberOfPos++;
				}
			}
			negSentence /= numberOfNeg;
			posSentence /= numberOfPos;
			//System.out.println("sentence score " + sentenceScore + "\n");
			docScore += (negSentence+posSentence);
		}
		docScore /= numberOfSentences;
		//System.out.println("doc score " + docScore);
		return docScore;
	}
	
	public static double scoreSumAdjective(String filePath) throws IOException {
		String pathToSWN = Constants.pathToSWN.getValue();
		SentiWordNetDemo sentiWordNet = new SentiWordNetDemo(pathToSWN);
		Lemmatizer lemmatizer = new Lemmatizer();
		PosTagger tagger = new PosTagger();
		List<List<String>> doc = Tokenizer.getSentences(filePath);
		double docScore = 0.0;
		int numberOfSentences = doc.size();
		for (List<String> sentence : doc) {
			//int numberOfWords = sentence.size();
			List<String> lemmatizedString = lemmatizer.lemmatize(sentence.toString());
			String taggedString = tagger.tagString(lemmatizedString.toString());
			String simpleString = tagger.convertToSimpleTags(taggedString);
			String[] tokens = simpleString.split("\\s+");
			//System.out.println(simpleString.toString());
			double sentenceScore = 0.0;
			for (String token : tokens) {
				if(!token.endsWith("#a")) continue;
				double score = sentiWordNet.extract(token);
				sentenceScore += score;
				// System.out.println(token + " ->" + score);
			}
			// sentenceScore/=numberOfWords;
			//System.out.println("sentence score " + sentenceScore + "\n");
			docScore += sentenceScore;
		}
		docScore /= numberOfSentences;
		//System.out.println("doc score " + docScore);
		return docScore;
	}
	
	public static double scoreAvgAdjective(String filePath) throws IOException {
		String pathToSWN = Constants.pathToSWN.getValue();
		SentiWordNetDemo sentiWordNet = new SentiWordNetDemo(pathToSWN);
		Lemmatizer lemmatizer = new Lemmatizer();
		PosTagger tagger = new PosTagger();
		List<List<String>> doc = Tokenizer.getSentences(filePath);
		double docScore = 0.0;
		int numberOfSentences = doc.size();
		for (List<String> sentence : doc) {
			int numberOfNeg = 0, numberOfPos = 0;
			double negSentence = 0.0, posSentence = 0.0;
			List<String> lemmatizedString = lemmatizer.lemmatize(sentence.toString());
			String taggedString = tagger.tagString(lemmatizedString.toString());
			String simpleString = tagger.convertToSimpleTags(taggedString);
			String[] tokens = simpleString.split("\\s+");
			//System.out.println(simpleString.toString());
			for (String token : tokens) {
				if(!token.endsWith("#a")) continue;
				double score = sentiWordNet.extract(token);
				if(score<0){
					negSentence += score;
					numberOfNeg++;
				}
				else{
					posSentence += score;
					numberOfPos++;
				}
			}
			negSentence /= numberOfNeg;
			posSentence /= numberOfPos;
			//System.out.println("sentence score " + sentenceScore + "\n");
			docScore += (negSentence+posSentence);
		}
		docScore /= numberOfSentences;
		//System.out.println("doc score " + docScore);
		return docScore;
	}

	public static String analyseScore(double score) {
		if (score < -0.5)
			return "strongly negative";
		if (score < -0.1)
			return "negative";
		if (score < 0.1)
			return "neutral";
		if (score < 0.5)
			return "positive";
		return "strongly positive";
	}
}
