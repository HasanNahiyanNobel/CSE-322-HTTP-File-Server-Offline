package com.company;

import java.io.IOException;
import java.net.Socket;

public class Client {
	public static void main (String[] args) throws IOException, ClassNotFoundException {
		final String HOST = "localhost";
		final int PORT = 8080;
		String filePath = "Specification.pdf"; // TODO: Read this from console

		Socket socket = new Socket(HOST,PORT);

		System.out.println("Connection established");
		System.out.println("Remote port: " + socket.getPort());
		System.out.println("Local port: " + socket.getLocalPort());

		while (true) {
			Thread clientWorker = new ClientWorker();
			clientWorker.start();
		}
	}
}
