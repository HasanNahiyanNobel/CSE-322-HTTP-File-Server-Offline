package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Concept from: http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm
 */
public class SimpleHttpServer {
	public static void main (String[] args) throws IOException {
		int port = 8080;
		boolean doWeNeedToScanHttpHeaders = false;

		try {
			ServerSocket serverSocket = new ServerSocket(port);

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

				System.out.println(path);

				if (doWeNeedToScanHttpHeaders) {
					for (String headerLine=bufferedReader.readLine(); headerLine!=null && headerLine.length()>0; headerLine=bufferedReader.readLine()) {
						header += headerLine;
					}
				}

				PrintWriter out = new PrintWriter(socket.getOutputStream());
				String httpResponse = new HttpResponse().getResponse(path);
				out.print(httpResponse);

				out.close();
				socket.close();
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
