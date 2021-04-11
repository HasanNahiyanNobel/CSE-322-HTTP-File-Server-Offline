package com.company;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread {
	Socket socket;
	int port;
	String rootDirectoryPath;
	final String LOG_FILE = "log.txt";

	public Worker (Socket socket, int port, String rootDirectoryPath) {
		this.socket = socket;
		this.port = port;
		this.rootDirectoryPath = rootDirectoryPath;
	}

	public void run() {
		try {
			//String localHostIP = serverSocket.getInetAddress().getLocalHost().toString().split("/")[1];
			while (true) {
				String path = "";
				StringBuilder headers = new StringBuilder();
				String request = "";

				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String startLine = bufferedReader.readLine();

				if (startLine!=null) {
					String[] startLineContents = startLine.split(" ");
					path = startLineContents[1].substring(1); // Substring taken to remove the prefix slash from path.

					for (String headerLine=bufferedReader.readLine(); headerLine!=null && headerLine.length()>0; headerLine=bufferedReader.readLine()) {
						headers.append(headerLine).append("\n");
					}

					request = startLine + "\n" + headers;
				}
				else {
					break; // Get out of this loop and handle stuffs on a new thread.
				}

				String httpResponse = new HttpResponse(rootDirectoryPath).getResponse(path);

				BufferedWriter logFileWriter = new BufferedWriter(new FileWriter(LOG_FILE,true));
				logFileWriter.write("REQUEST:\n" + request + "\nRESPONSE:\n" + httpResponse + "\n\n\n\n");
				logFileWriter.close();

				File requestedFile = new File(rootDirectoryPath + "/" + path);

				if (!requestedFile.exists() || requestedFile.isDirectory()) {
					// Write http headers and html page
					PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
					printWriter.print(httpResponse);
					printWriter.flush();
				}
				else {
					// Write http headers
					PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
					printWriter.print(httpResponse);
					printWriter.flush();

					// Write the file
					DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
					FileInputStream fileInputStream = new FileInputStream(requestedFile);
					byte[] buffer = new byte[1024];
					while (fileInputStream.read(buffer) > 0) dataOutputStream.write(buffer);

					dataOutputStream.flush();
					fileInputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
