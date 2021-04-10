package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Concept from: http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm
 */
public class SimpleHttpServer {
	public static void main (String[] args) {
		try {
			int port = 8080;
			ServerSocket serverSocket = new ServerSocket(port);

			while (true) {
				System.out.println("Waiting for a request...");
				Socket socket = serverSocket.accept();
				System.out.println("Request received.");

				String path = "index.html";

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream());

				String httpResponse = new HttpResponse().getResponse(path);
				out.print(httpResponse);

				out.close();
				in.close();
				socket.close();
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
