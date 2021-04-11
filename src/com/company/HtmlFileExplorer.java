package com.company;

import java.io.File;

public class HtmlFileExplorer {
	String path;
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
			contents = "This is not a directory!";
		}
		else {
			for (final File fileEntry : file.listFiles()) {
				if (!fileEntry.isDirectory()) {
					contents += fileEntry.getName() + "<br>";
				}
				else {
					int indexOfBackslash = fileEntry.getPath().indexOf('\\');
					String relativePath = fileEntry.getPath().substring(indexOfBackslash+1); // Relative path from root directory
					contents += "<a href=\"/"+ relativePath + "\">" + fileEntry.getName() + "</a><br>";
				}
			}
		}
		return htmlStringBeforeContents + contents + htmlStringAfterContents;
	}
}
