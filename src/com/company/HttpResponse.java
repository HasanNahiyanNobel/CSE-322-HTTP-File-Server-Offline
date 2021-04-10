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
	String html;
	final String errorHtmlString = "<!DOCTYPE html>\n" +
			"<html>\n" +
			"<body>\n" +
			"<h1>Error 404</h1>\n" +
			"<p>Page not found.</p>\n" +
			"</body>\n" +
			"</html>\n";

	public HttpResponse () {
		httpVersion = "HTTP/1.1";
		statusCode = 0;
		statusMessage = "";
		headers = "";
		html = "";
	}

	public String getResponse (String path) {
		if (new File(path).exists()) {
			statusCode = 200;
			html = convertHtmlPageToString(path);
		}
		else {
			statusCode = 404;
			html = errorHtmlString;
		}
		statusMessage = getStatusMessageFromStatusCode(statusCode);

		headers = "Date: " + new Date() + "\r\n" +
				"Content-Type: text/html" + "\r\n" +
				"Content-Length: " + html.length() + "\r\n"+
				"Connection: close";

		String response = httpVersion + " " + statusCode + " " + statusMessage + "\r\n"
				+ headers + "\r\n\r\n"
				+ html;

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
			BufferedReader in = new BufferedReader(new FileReader(path)); // TODO: Change this to path
			String str;
			while ((str = in.readLine()) != null) {
				stringBuilder.append(str + "\r\n");
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
