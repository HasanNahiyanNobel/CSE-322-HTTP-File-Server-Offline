package com.company;

public class Main {
	public static void main(String[] args) {
		HtmlFileExplorer htmlFileExplorer = new HtmlFileExplorer();

		System.out.println(htmlFileExplorer.getHtmlString("root"));
	}
}
