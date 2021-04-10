package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class HttpResponse {
	String httpVersion;
	String statusCode;
	String statusMessage;
	String headers;
	String html;

	public HttpResponse () {
		httpVersion = "HTTP/1.1";
		statusCode = "";
		statusMessage = "";
		headers = "";
		html = "";
	}

	public String getResponse (String path) {
		statusCode = "200"; // TODO: Change this as needed.
		statusMessage = "OK"; // TODO: Change this as needed.

		headers += "Date: " + new Date().toString() + "\n";
		headers += "Server: Nobel PC\n";
		headers += convertHtmlPageToString(path);


		String response = httpVersion + " " + statusCode + " " + statusMessage + "\n" + headers;

		return response;
	}

	/**
	 * From https://stackoverflow.com/a/12035403
	 * @param path Path of the html file
	 * @return The whole html file as a string
	 */
	String convertHtmlPageToString (String path) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String str;
			while ((str = in.readLine()) != null) {
				stringBuilder.append(str);
			}
			in.close();
		} catch (IOException ioException) {
			System.out.println(ioException);
		}
		return stringBuilder.toString();
	}
}
