package com.company;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Worker extends Thread {
	Socket socket;

	public Worker (Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());

			while (true) {
				Scanner scanner = new Scanner(socket.getInputStream()); // From https://stackoverflow.com/a/35446009

				if (scanner.hasNext()) {
					String method = scanner.next();
					String path = scanner.next();
					String httpVersion = scanner.next();
					System.out.println("Method: " + method + ", Path: " + path);
					System.out.println();
				}

				Thread.sleep(1000);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
