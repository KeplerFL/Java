package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection implements Runnable {

	private BufferedReader is;
	private DataOutputStream os;
	private Socket client;
	private boolean active;
	
	public Connection(Socket client)
	{
		this.client = client;
		try {
			this.is = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.os = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) { Main.debug("Error while creating socket's comunication methods");}
		setActive(true);
		Main.debug("Socket created! IP:" + client.getRemoteSocketAddress().toString());
	}
	

	@Override
	public void run() {
		String s;
		while(isActive())
		{
			try {
				s = is.readLine();
				if(s == null)
					throw new IOException();
				if(s.contains("GET /"))
				{
					String[] doc = s.split("/");
					Main.debug(doc[doc.length -1]);
				}
				Main.debug(s);
			} catch (IOException e) {setActive(false); Main.debug("Socket failed to read new line");}
		}
	}

	
	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

}
