package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				String input = bufferedReader.readLine();
				Thread clientWorker = new ClientWorker(input);
				clientWorker.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
