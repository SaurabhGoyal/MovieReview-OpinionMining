package com.om.jss;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Utility {

	private Lemmatizer lemmatizer;
	private PosTagger posTagger;
	private Tokenizer tokenizer;

	private String pathToSWN;
	private SentiWordNetDemo sentiWordNet;

	private int nom;

	private String[][] tokenizedFile;

	public Utility() throws IOException {
		init();
		nom = 6;
	}

	private void init() throws IOException {
		lemmatizer = new Lemmatizer();
		posTagger = new PosTagger();
		tokenizer = new Tokenizer();
		pathToSWN = Constants.pathToSWN.getValue();
		sentiWordNet = new SentiWordNetDemo(pathToSWN);
	}

	public double[] analyseFile(String filePath) throws FileNotFoundException {
		double[] scores = new double[nom];
		tokenizeFile(filePath);
		scores[0] = scoreSum();
		scores[1] = scoreAvgTotal();
		scores[2] = scoreSumAdjective();
		scores[3] = scoreAvgAdjective();
		scores[4] = scoreAvgRespective();
		scores[5] = scoreOptimized();
		return scores;
	}

	public void tokenizeFile(String filePath) throws FileNotFoundException {
		List<List<String>> doc = tokenizer.getSentences(filePath);
		tokenizedFile = new String[doc.size()][];
		int i = 0;
		for (List<String> sentence : doc) {
			List<String> lemmatizedString = lemmatizer.lemmatize(sentence
					.toString());
			String taggedString = posTagger.tagString(lemmatizedString
					.toString());
			String simpleString = posTagger.convertToSimpleTags(taggedString);
			tokenizedFile[i++] = simpleString.split("\\s+");
		}
	}

	public int[] scoreToRating(double[] scores) {
		int n = scores.length;
		int[] ratings = new int[n];
		for (int i = 0; i < n; i++) {
			ratings[i] = (int) Math.round(scores[i] * 10);
		}
		return ratings;
	}

	public double scoreSum() {
		double docScore = 0;
		int numberOfSentences = tokenizedFile.length;
		for (String[] tokens : tokenizedFile) {
			double sentenceScore = 0.0;
			for (String token : tokens) {
				double score = sentiWordNet.extract(token);
				sentenceScore += score;
				// System.out.println(token + " ->" + score);
			}
			// sentenceScore/=numberOfWords;
			// System.out.println("sentence score " + sentenceScore + "\n");
			docScore += sentenceScore;
		}
		docScore /= numberOfSentences;
		// System.out.println("doc score " + docScore);
		return docScore;
	}

	public double scoreAvgTotal() {
		double docScore = 0;
		int numberOfSentences = tokenizedFile.length;
		for (String[] tokens : tokenizedFile) {
			int numberOfWords = tokens.length;
			double sentenceScore = 0.0;
			for (String token : tokens) {
				double score = sentiWordNet.extract(token);
				sentenceScore += score;
				// System.out.println(token + " ->" + score);
			}
			sentenceScore /= numberOfWords;
			// System.out.println("sentence score " + sentenceScore + "\n");
			docScore += sentenceScore;
		}
		docScore /= numberOfSentences;
		// System.out.println("doc score " + docScore);
		return docScore;
	}

	public double scoreSumAdjective() {
		double docScore = 0;
		int numberOfSentences = tokenizedFile.length;
		for (String[] tokens : tokenizedFile) {
			double sentenceScore = 0.0;
			for (String token : tokens) {
				if (!token.endsWith("#a"))
					continue;
				double score = sentiWordNet.extract(token);
				sentenceScore += score;
				// System.out.println(token + " ->" + score);
			}
			// sentenceScore/=numberOfWords;
			// System.out.println("sentence score " + sentenceScore + "\n");
			docScore += sentenceScore;
		}
		docScore /= numberOfSentences;
		// System.out.println("doc score " + docScore);
		return docScore;
	}
	
	public double scoreAvgAdjective() {
		double docScore = 0;
		int numberOfSentences = tokenizedFile.length;
		for (String[] tokens : tokenizedFile) {
			double sentenceScore = 0.0;
			int numOfAdjectives = 0;
			for (String token : tokens) {
				if (!token.endsWith("#a"))
					continue;
				numOfAdjectives++;
				double score = sentiWordNet.extract(token);
				sentenceScore += score;
				// System.out.println(token + " ->" + score);
			}
			if (numOfAdjectives > 0)
				sentenceScore /= numOfAdjectives;
			// sentenceScore/=numberOfWords;
			// System.out.println("sentence score " + sentenceScore + "\n");
			docScore += sentenceScore;
		}
		docScore /= numberOfSentences;
		// System.out.println("doc score " + docScore);
		return docScore;
	}

	public double scoreAvgRespective() {
		double docScore = 0;
		int numberOfSentences = tokenizedFile.length;
		for (String[] tokens : tokenizedFile) {
			int numberOfNeg = 0, numberOfPos = 0;
			double negSentence = 0.0, posSentence = 0.0;
			for (String token : tokens) {
				double score = sentiWordNet.extract(token);
				if (score < 0) {
					negSentence += score;
					numberOfNeg++;
				} else {
					posSentence += score;
					numberOfPos++;
				}
			}
			if(numberOfNeg>0) negSentence /= numberOfNeg;
			if(numberOfPos>0) posSentence /= numberOfPos;
			// System.out.println("sentence score " + sentenceScore + "\n");
			docScore += (negSentence + posSentence);
		}
		docScore /= numberOfSentences;
		// System.out.println("doc score " + docScore);
		return docScore;
	}

	public double scoreOptimized() {
		double docScore = 0;
		int numberOfSentences = tokenizedFile.length;
		for (String[] tokens : tokenizedFile) {
			int numberOfSubjectiveWords = 0;
			double sentenceScore = 0.0;
			int numberOfAdv = 0;
			for (String token : tokens) {
				double score = sentiWordNet.extract(token);
				double objScore = 1 - Math.abs(score);
				if (objScore > 0.875)
					continue;
				sentenceScore += score;
				numberOfSubjectiveWords++;
			}
			if (numberOfSubjectiveWords > 0)
				sentenceScore /= numberOfSubjectiveWords;
			docScore += sentenceScore;
		}
		docScore /= numberOfSentences;
		// System.out.println("doc score " + docScore);
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

	public static void main(String[] args) throws Exception {
		Utility u = new Utility();
		double[] scores = u.analyseFile("reviews/test_file.txt");
		int[] ratings = u.scoreToRating(scores);
		for (int i = 0; i < u.nom; i++) {
			System.out.println(String.format("method %d : %f, %d", i,
					scores[i], ratings[i]));
		}
	}
}
