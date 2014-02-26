package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

	public static ServerSocket socket;
	public static final int port = 8080;
	public static void main(String[] args) {
		try {
			socket = new ServerSocket(port);
			Thread t = new Thread(new Server(socket, true));
			t.start();
			
		} catch (IOException e) {debug("Cannot create socket on port " + port); }
		
	}
	
	public static void debug(String s)
	{
		System.out.println(s);
	}

}
