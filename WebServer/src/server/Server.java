package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	private ServerSocket socket;
	private boolean active;
	
	public Server(ServerSocket socket, boolean active)
	{
		this.socket = socket;
		this.active = active;
	}
	
	@Override
	public void run() {
		
		while(active)
		{
			try {
				Socket s = socket.accept();
				Thread t = new Thread(new Connection(s));
				t.start();
			} catch (IOException e) {}
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
