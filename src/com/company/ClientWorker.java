package com.company;

public class ClientWorker extends Thread {
	String filePath;

	ClientWorker () {

	}

	public void run () {
		System.out.println("Something is happening in thread.");
	}
}
