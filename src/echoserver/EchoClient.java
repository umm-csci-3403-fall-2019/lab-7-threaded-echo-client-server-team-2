package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		Runnable input = () -> {
			try{
				int read;
				while ((read = System.in.read()) != -1) {
					socketOutputStream.write(read); // Write the input from the stdin to output stream (the server)
				} 

				// Shutdown the socket
				socket.shutdownOutput();

			} catch(IOException e) {
				e.printStackTrace();
			}
			
			
		};

		Runnable output = () -> {
			try{
				int read;
				while ((read = socketInputStream.read()) != -1) {
					System.out.write(read); // Output to stdout the input coming from the server
				} 

				System.out.flush(); // Flush the stdout stream
				
				socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		};

		Thread inputThread = new Thread(input);
		inputThread.start();

		Thread outputThread = new Thread(output);
		outputThread.start();

	}
}