package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Concept from: http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm
 */
public class SimpleHttpServer {
	public static void main (String[] args) throws IOException {
		int port = 8080;
		String rootDirectoryPath = "root";
		boolean doWeNeedToScanHttpHeaders = false;

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			//String localHostIP = serverSocket.getInetAddress().getLocalHost().toString().split("/")[1];

			while (true) {
				System.out.println("Waiting for a request...");
				Socket socket = serverSocket.accept();
				System.out.println("Request received.");

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

				if (httpResponse!=null) {
					// Write the html page
					PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
					printWriter.print(httpResponse);
					printWriter.close();
				}
				else {
					// Write the file
					File file = new File(rootDirectoryPath + "/" + path);
					DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
					FileInputStream fileInputStream = new FileInputStream(file);
					byte[] buffer = new byte[1024];

					while (fileInputStream.read(buffer) > 0) {
						dataOutputStream.write(buffer);
					}

					fileInputStream.close();
					dataOutputStream.close();
				}

				socket.close();
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
