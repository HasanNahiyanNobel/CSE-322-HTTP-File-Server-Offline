package com.company;

public class ClientWorker extends Thread {
	String filePath;

	ClientWorker (String filePath) {
		this.filePath = filePath;
	}

	public void run () {
		System.out.println("Hihi" + filePath);
	}
}
