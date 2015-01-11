package com.om.jss;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

public class Tokenizer {

	public static void getTokens(String filePath) throws FileNotFoundException {
		PTBTokenizer<CoreLabel> ptbTokenizer = new PTBTokenizer<CoreLabel>(
				new FileReader(filePath), new CoreLabelTokenFactory(), "");
		List<String> tokens = new ArrayList<String>();
		for (CoreLabel label; ptbTokenizer.hasNext();) {
			label = ptbTokenizer.next();
			tokens.add(label.originalText());
			//System.out.println(label.originalText());
		}
	}

	public static List<List<String>> getSentences(String filePath)
			throws FileNotFoundException {
		DocumentPreprocessor dp = new DocumentPreprocessor(filePath);
		List<List<String>> processedText = new ArrayList<List<String>>();
		for (List sentence : dp) {
			processedText.add(sentence);
			//System.out.println(sentence);
		}
		return processedText;
	}

	public static void main(String[] args) throws FileNotFoundException {
		String filePath = "resources/file.txt";
		getTokens(filePath);
	}

}
