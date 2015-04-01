package com.om.jss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IMDBReviewsParser {

	public static void parseReviews(String title, int max) throws IOException {
		int total = 0;
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(
				"reviews/" + title + ".txt")));
		while (total < max) {
			Document doc = Jsoup.connect(
					"http://www.imdb.com/title/" + title + "/reviews?start="
							+ total).get();
			Element content = doc.getElementById("tn15content");
			Elements images = content.getElementsByTag("img");
			Elements para = content.getElementsByTag("p");
			for(int i=0;i<para.size();i++){
				String p = para.get(i).text();
				String rating = images.get(i).attr("alt");
				System.out.println(String.format("p: %s ||| rating : %s",p.substring(0, Math.min(50, p.length())),rating));
				total++;
			}
		}

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		parseReviews("tt2310332", 20);
	}

}
