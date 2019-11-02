package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
	
	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 6013; 
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

		// Use number of CPU threads for thread pool
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		while (true) {
			Socket socket = serverSocket.accept();
			executor.submit(() -> {
				try {
					InputStream input = socket.getInputStream();
					OutputStream output = socket.getOutputStream(); 
					
					int read; // The byte we will be reading

					// While there is still input to read
					while ((read = input.read()) != -1)
						output.write(read); // Write the input from the client back to the client
					
					output.flush(); // Flush the output stream

					// Shutdown and close the connection to the client
					socket.shutdownOutput();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
			});
			
		}
	}
}