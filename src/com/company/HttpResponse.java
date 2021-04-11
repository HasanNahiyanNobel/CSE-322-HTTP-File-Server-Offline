package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public class HttpResponse {
	String rootDirectoryPath;
	String httpVersion;
	int statusCode;
	String statusMessage;
	String headers;
	String mimeType;
	String contentDisposition;
	long contentLength;
	String htmlString;
	final String indexHtmlPath = "index.html";
	final String errorHtmlPath = "error404.html";
	final String errorHtmlString = "<!DOCTYPE html><html><body style=\"text-align: center; margin-bottom: 1em\">" +
			"<h1>Error 404</h1>" +
			"<p>Page not found.</p>" +
			"</body></html>";

	public HttpResponse (String rootDirectoryPath) {
		this.rootDirectoryPath = rootDirectoryPath;
		httpVersion = "HTTP/1.1";
	}

	public String getResponse (String path) {
		path = rootDirectoryPath + "/" + path;
		File file = new File(path);

		if (file.exists()) {
			statusCode = 200;
			if (file.isDirectory()) {
				mimeType = "text/html";
				htmlString = new HtmlFileExplorer().getHtmlString(path);
				contentDisposition = "inline";
				contentLength = htmlString.length();
			}
			else {
				try {
					mimeType = Files.probeContentType(file.toPath());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				htmlString = ""; // A file will be sent
				contentDisposition = "attachment";
				contentLength = file.length();
			}
		}
		else {
			statusCode = 404;
			mimeType = "text/html";
			htmlString = errorHtmlString;
			contentDisposition = "inline";
			contentLength = htmlString.length();
			System.out.println("Error 404 for file path: " + file.getPath());
		}

		statusMessage = getStatusMessageFromStatusCode(statusCode);

		headers = "Date: " + new Date() + "\r\n" +
				"Content-Type: " + mimeType + "\r\n" +
				"Content-Disposition: " + contentDisposition + "\r\n" +
				"Content-Length: " + contentLength + "\r\n"+
				"Connection: open";

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
