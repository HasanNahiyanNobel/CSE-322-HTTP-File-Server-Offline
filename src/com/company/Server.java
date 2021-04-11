package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main (String[] args) throws IOException {
		final int PORT = 8080;
		final String ROOT_DIRECTORY_PATH = "root";
		ServerSocket serverSocket = new ServerSocket(PORT);

		while (true) {
			System.out.println("Waiting for connection...");
			Socket socket = serverSocket.accept();
			System.out.println("Connection established.\n");

			// Open thread
			Thread worker = new Worker(socket, PORT, ROOT_DIRECTORY_PATH);
			worker.start();
		}
	}
}
