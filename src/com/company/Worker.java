package com.company;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread {
	Socket socket;
	int port;
	String rootDirectoryPath;
	boolean doWeNeedToScanHttpHeaders = false;

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

				String[] startLineContents = bufferedReader.readLine().split(" ");
				method = startLineContents[0];
				path = startLineContents[1].substring(1); // Substring taken to remove the prefix slash from path.
				httpVersion = startLineContents[2];

				if (doWeNeedToScanHttpHeaders) {
					for (String headerLine=bufferedReader.readLine(); headerLine!=null && headerLine.length()>0; headerLine=bufferedReader.readLine()) {
						header += headerLine;
					}
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
					System.out.println(httpResponse); //TODO: Remove this debug line.
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
