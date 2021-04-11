package com.company;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread {
	Socket socket;
	int port;
	String rootDirectoryPath;
	final String LOG_FILE = "log.txt";
	final boolean DO_WE_NEED_TO_SCAN_HTTP_HEADERS = true;

	public Worker (Socket socket, int port, String rootDirectoryPath) {
		this.socket = socket;
		this.port = port;
		this.rootDirectoryPath = rootDirectoryPath;
	}

	public void run() {
		try {
			//String localHostIP = serverSocket.getInetAddress().getLocalHost().toString().split("/")[1];
			while (true) {
				String method = "";
				String path = "";
				String httpVersion = "";
				String header = "";

				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String startLine = bufferedReader.readLine();

				if (startLine!=null) {
					BufferedWriter logFileWriter = new BufferedWriter(new FileWriter(LOG_FILE,true));
					logFileWriter.write(startLine + "\n");

					String[] startLineContents = startLine.split(" ");
					method = startLineContents[0];
					path = startLineContents[1].substring(1); // Substring taken to remove the prefix slash from path.
					httpVersion = startLineContents[2];

					if (DO_WE_NEED_TO_SCAN_HTTP_HEADERS) {
						for (String headerLine=bufferedReader.readLine(); headerLine!=null && headerLine.length()>0; headerLine=bufferedReader.readLine()) {
							header += headerLine;
							logFileWriter.write(headerLine + "\n");
						}
						logFileWriter.write("\n"); // Just an extra line
						logFileWriter.close();
					}
				}
				else {
					break; // Get out of this loop and handle stuffs on a new thread.
				}

				String httpResponse = new HttpResponse(rootDirectoryPath).getResponse(path);
				File file = new File(rootDirectoryPath + "/" + path);

				if (!file.exists() || file.isDirectory()) {
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
					FileInputStream fileInputStream = new FileInputStream(file);
					byte[] buffer = new byte[1024];

					while (fileInputStream.read(buffer) > 0) {
						dataOutputStream.write(buffer);
					}
					dataOutputStream.flush();
					fileInputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
