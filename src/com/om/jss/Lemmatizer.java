package com.om.jss;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Lemmatizer {
	
	protected StanfordCoreNLP pipeline;

	public Lemmatizer() {
		Properties props;
		props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		this.pipeline = new StanfordCoreNLP(props);
	}

	public List<String> lemmatize(String documentText) {
		List<String> lemmas = new LinkedList<String>();
		Annotation document = new Annotation(documentText);
		this.pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				lemmas.add(token.get(LemmaAnnotation.class));
			}
		}
		return lemmas;
	}

	public static void main(String[] args) {
		System.out.println("Starting Stanford Lemmatizer");
		String text = "How could you be seeing into my eyes like open doors? \n"
				+ "You led me down into my core where I've became so numb \n"
				+ "You were bringing me to life.";
		Lemmatizer slem = new Lemmatizer();
		System.out.println(slem.lemmatize(text));
	}

}
