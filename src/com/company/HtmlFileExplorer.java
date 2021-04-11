package com.company;

import java.io.File;

import static java.lang.System.exit;

public class HtmlFileExplorer {
	String htmlStringBeforeContents;
	String contents;
	String htmlStringAfterContents;

	HtmlFileExplorer () {
		htmlStringBeforeContents = "<!DOCTYPE html><html><body>";
		contents = "";
		htmlStringAfterContents = "</body></html>";
	}

	String getHtmlString (String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			System.out.println("\nSome terrible error occurred.");
			exit(0);
		}
		else {
			for (final File fileEntry : file.listFiles()) {
				int indexOfBackslash = fileEntry.getPath().indexOf('\\');
				String relativePath = fileEntry.getPath().substring(indexOfBackslash+1); // Relative path from root directory
				if (fileEntry.isDirectory()) {
					contents += "<b><a href=\"/" + relativePath + "\">" + fileEntry.getName() + "</a></b><br>";
				}
				else {
					contents += "<a href=\"/" + relativePath + "\" download>" + fileEntry.getName() + "</a><br>";
				}
			}
		}
		return htmlStringBeforeContents + contents + htmlStringAfterContents;
	}
}
