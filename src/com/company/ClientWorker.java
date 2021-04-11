package com.company;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientWorker extends Thread {
	Socket socket;
	String filePath;

	ClientWorker (Socket socket, String filePath) {
		this.socket = socket;
		this.filePath = filePath;
	}

	public void run () {
		try {
			// Write the file
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			FileInputStream fileInputStream = new FileInputStream(filePath);
			byte[] buffer = new byte[1024];
			while (fileInputStream.read(buffer) > 0) dataOutputStream.write(buffer);

			dataOutputStream.flush();
			fileInputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
