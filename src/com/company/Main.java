package com.company;

public class Main {
	public static void main(String[] args) {
		HttpResponse httpResponse = new HttpResponse();

		String path = "/index.html";
		path = path.substring(1);

		System.out.println(httpResponse.getResponse(path));
	}
}
