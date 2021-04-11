package com.company;

import java.io.*;
import java.net.Socket;

public class ClientWorker extends Thread {
	Socket socket;
	String filePath;
	DataOutputStream dataOutputStream;

	ClientWorker (Socket socket, String filePath, DataOutputStream dataOutputStream) {
		this.socket = socket;
		this.filePath = filePath;
		this.dataOutputStream = dataOutputStream;
	}

	public void run () {
		try {
			// Write file name
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.print(new File(filePath).getName() + "\n");
			printWriter.flush();
			// Write the file
			//DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
