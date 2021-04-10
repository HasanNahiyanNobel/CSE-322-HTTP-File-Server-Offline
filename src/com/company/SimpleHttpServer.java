package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {
	public static void main (String[] args) throws IOException {
		try {
			int port = 8080;
			ServerSocket ss = new ServerSocket(port);

			for (;;) {
				// Wait for a client to connect. The method will block;
				// when it returns the socket will be connected to the client
				Socket client = ss.accept();

				// Get input and output streams to talk to the client
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream());

				/*// Start sending our reply, using the HTTP 1.1 protocol
				out.print("HTTP/1.1 200 \r\n"); // Version & status code
				out.print("Content-Type: text/plain\r\n"); // The type of data
				out.print("Connection: close\r\n"); // Will close stream
				out.print("\r\n"); // End of headers*/

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








		/*ServerSocket welcomeSocket = new ServerSocket(8080);

		while (true) {
			System.out.println("Waiting for connection...");
			Socket socket = welcomeSocket.accept();
			System.out.println("Connection established");
			System.out.println("Remote port: " + socket.getPort());
			System.out.println("Local port: " + socket.getLocalPort());

			// Output buffer and input buffer
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			//ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			String content = "<html>\r\n" +
					"<body>\r\n" +
					"<h1>Hello, World!</h1>\r\n" +
					"</body>\r\n" +
					"</html>";

			String headers = "HTTP/1.1 200 OK\r\n" +
					"Date: Mon, 27 Jul 2009 12:28:53 GMT\r\n" +
					"Server: Apache/2.2.14 (Win32)\r\n" +
					"Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\r\n" +
					"Content-Length: " + content.length() + "\r\n" +
					"Content-Type: text/html\r\n" +
					"Connection: Closed\r\n\r\n";

			// Send message to client
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(
							new BufferedOutputStream(
									socket.getOutputStream())));

			String response = headers + content;
			*//*bufferedWriter.write(headers+content);
			bufferedWriter.flush();
			bufferedWriter.close();*//*
			socket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
		}*/
	}
}
