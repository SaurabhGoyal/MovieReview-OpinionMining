package com.om.jss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RTReviewFetcher {

	public static void generateReviews(int movieId) throws IOException,
			ParseException {
		URL url = new URL(
				"http://api.rottentomatoes.com/api/public/v1.0/movies/"
						+ movieId + "/reviews.json?apikey="
						+ Constants.RTAPI.getValue());
		URLConnection conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		BufferedWriter out = new BufferedWriter(new FileWriter(new File("resources/" + movieId + "_reviews.txt")));
		StringBuilder sb = new StringBuilder("");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(in);
		JSONArray arr = (JSONArray) obj.get("reviews");
		Iterator<JSONObject> it = arr.iterator();
		while (it.hasNext()) {
			String review = (String) it.next().get("quote");
			sb.append(review).append("\n");
		}
		out.write(sb.toString());
		in.close();
		out.close();
	}

	public static void main(String[] args) throws Exception {
		int interstellar = 771351912;
		generateReviews(interstellar);
	}

}
