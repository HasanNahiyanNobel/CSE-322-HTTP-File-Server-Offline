package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	public static void main (String[] args) throws IOException, ClassNotFoundException {
		Socket socket = new Socket("localhost", 8080);
		System.out.println("Connection established");
		System.out.println("Remote port: " + socket.getPort());
		System.out.println("Local port: " + socket.getLocalPort());

		// Buffers
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		while (true) {
			String msg = (String) in.readObject();
			System.out.println(msg);
		}
	}
}
