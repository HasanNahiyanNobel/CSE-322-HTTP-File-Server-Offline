package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main (String[] args) throws IOException {
		final int PORT = 8080;
		final String ROOT_DIRECTORY_PATH = "root";
		ServerSocket serverSocket = new ServerSocket(PORT);
		int numberOfThreads = 0;

		while (true) {
			System.out.println("Waiting for connection...");
			Socket socket = serverSocket.accept();
			System.out.println("Connection established.");

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			// Open thread
			Thread worker = new ServerWorker(socket, PORT, ROOT_DIRECTORY_PATH, bufferedReader, printWriter, dataInputStream, dataOutputStream);
			worker.start();
			numberOfThreads++;
			System.out.println("Opened thread #" + numberOfThreads + "\n");
		}
	}
}
