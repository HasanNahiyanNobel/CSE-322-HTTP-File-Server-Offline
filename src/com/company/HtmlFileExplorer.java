package com.company;

import java.io.File;

public class HtmlFileExplorer {
	String path;
	String htmlStringBeforeContents;
	String contents;
	String htmlStringAfterContents;

	HtmlFileExplorer () {
		htmlStringBeforeContents = "<!DOCTYPE html>\n<html>\n<body>\n";
		contents = "";
		htmlStringAfterContents = "</body>\n</html>";
	}

	String getHtmlString (String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			contents = "This is not a directory!";
		}
		else {
			for (final File fileEntry : file.listFiles()) {
				contents += fileEntry.getName() + "\n";
			}
		}
		return htmlStringBeforeContents + contents + htmlStringAfterContents;
	}
}
