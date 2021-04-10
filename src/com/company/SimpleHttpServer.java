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
		try {
			int port = 8080;
			ServerSocket serverSocket = new ServerSocket(port);

			while (true) {
				// Wait for a client to connect. The method will block;
				// when it returns the socket will be connected to the client
				Socket client = serverSocket.accept();

				// Get input and output streams to talk to the client
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream());

				String httpResponse = new HttpResponse().getResponse("index.html");
				out.print(httpResponse);

				out.close(); // Flush and close the output stream
				in.close(); // Close the input stream
				client.close(); // Close the socket itself
			} // Now loop again, waiting for the next connection
		}
		catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java HttpMirror <port>");
		}
	}
}
