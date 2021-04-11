package com.company;

import java.io.*;
import java.net.Socket;

public class Client {
	public static void main (String[] args) throws IOException, ClassNotFoundException {
		final String HOST = "localhost";
		final int PORT = 8080;
		final String UPLOAD_COMMAND = "UPLOAD";

		Socket socket = new Socket(HOST,PORT);

		System.out.println("Connection established");
		System.out.println("Remote port: " + socket.getPort());
		System.out.println("Local port: " + socket.getLocalPort());

		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

		while (true) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.print("Please type request message: ");
				String[] inputs = bufferedReader.readLine().split(" ");

				if (inputs.length!=2) {
					System.out.println("Invalid number of arguments (expected 2).\n");
					continue;
				}

				String command = inputs[0];
				String filePath = inputs[1];
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

				Thread clientWorker = new ClientWorker(socket, filePath, dataOutputStream);
				clientWorker.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
