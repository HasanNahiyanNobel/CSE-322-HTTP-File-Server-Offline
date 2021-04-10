package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

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

				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				for (String aLine=bufferedReader.readLine(); aLine!=null; aLine=bufferedReader.readLine()) {
					System.out.println(aLine);
				}
				exit(0);

				String method = "";
				String path = "";
				String httpVersion = "";
				String header = "";

				Scanner scanner = new Scanner(socket.getInputStream());

				if (scanner.hasNext()) {
					method = scanner.next();
					path = scanner.next();
					path = path.substring(1); // Remove the slash at the beginning of the path
					httpVersion = scanner.next();
					while (scanner.hasNext() && doWeNeedToScanHttpHeaders) {
						header += scanner.next() + "\n";
					}
				}
				System.out.println(path);
				exit(0);

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
