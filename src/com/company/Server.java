package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main (String[] args) throws IOException, ClassNotFoundException {
		ServerSocket welcomeSocket = new ServerSocket(8080);

		while (true) {
			System.out.println("Waiting for connection...");
			Socket socket = welcomeSocket.accept();
			System.out.println("Connection established.\n");

			// Open thread
			Thread worker = new Worker(socket);
			worker.start();
		}

	}
}
