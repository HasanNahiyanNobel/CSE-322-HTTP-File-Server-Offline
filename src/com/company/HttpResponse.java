package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class HttpResponse {
	String httpVersion;
	int statusCode;
	String statusMessage;
	String headers;
	String htmlString;
	final String indexHtmlPath = "index.html";
	final String errorHtmlPath = "error_404.html";

	public HttpResponse () {
		httpVersion = "HTTP/1.1";
	}

	public String getResponse (String path) {
		path = "root/" + path;
		if (new File(path).exists()) {
			statusCode = 200;
			htmlString = convertHtmlPageToString(indexHtmlPath);
		}
		else {
			statusCode = 404;
			htmlString = convertHtmlPageToString(errorHtmlPath);
		}
		statusMessage = getStatusMessageFromStatusCode(statusCode);

		headers = "Date: " + new Date() + "\r\n" +
				"Content-Type: text/html" + "\r\n" +
				"Content-Length: " + htmlString.length() + "\r\n"+
				"Connection: close";

		String response = httpVersion + " " + statusCode + " " + statusMessage + "\r\n"
				+ headers + "\r\n\r\n"
				+ htmlString;

		return response;
	}

	/**
	 * Concept from: https://stackoverflow.com/a/12035403
	 * @param path Path of the html file
	 * @return The whole html file as a string
	 */
	String convertHtmlPageToString (String path) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String aLine;
			while ((aLine = in.readLine()) != null) {
				stringBuilder.append(aLine + "\r\n");
			}
			in.close();
		} catch (IOException ioException) {
			System.out.println(ioException);
		}
		return stringBuilder.toString();
	}

	String getStatusMessageFromStatusCode (int statusCode) {
		return switch (statusCode) {
			case 200 -> "OK";
			case 400 -> "Bad Request";
			case 403 -> "Forbidden";
			case 404 -> "Not Found";
			default -> "";
		};
	}
}
