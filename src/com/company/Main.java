package com.company;

public class Main {
	public static void main(String[] args) {
		HtmlFileExplorer htmlFileExplorer = new HtmlFileExplorer("192.168.0.100", 8080);

		System.out.println(htmlFileExplorer.getHtmlString("root"));
	}
}
