package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main (String[] args) throws IOException, ClassNotFoundException {
		final String HOST = "localhost";
		final int PORT = 8080;
		final String UPLOAD_COMMAND = "UPLOAD";
		//String filePath = "Specification.pdf"; // TODO: Remove this.

		Socket socket = new Socket(HOST,PORT);

		System.out.println("Connection established");
		System.out.println("Remote port: " + socket.getPort());
		System.out.println("Local port: " + socket.getLocalPort());

		while (true) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.print("Please type request message: ");
				String input = bufferedReader.readLine();
				String command = input.split(" ")[0];
				String filePath = input.split(" ")[1];
				File fileToBeUploaded = new File(filePath);

				if (!command.equals(UPLOAD_COMMAND)) {
					System.out.println("Invalid command \"" + command + "\"\n");
					continue;
				}
				else if (!fileToBeUploaded.exists()) {
					System.out.println("File named \"" + fileToBeUploaded.getName() + "\" does not exist in the given path.\n");
					continue;
				}
				else {
					System.out.println("Uploading " + filePath + "...\n");
				}

				Thread clientWorker = new ClientWorker(input);
				clientWorker.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
