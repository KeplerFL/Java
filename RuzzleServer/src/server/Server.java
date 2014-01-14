package server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class Server
{
	
	public static ArrayList<Socket> clients;
	
	public void start(int port)
	{
		debug("trying to start server process...");
		try {
			clients=new ArrayList<Socket>();
			ServerSocket sock = new ServerSocket(port);
			debug("Server started. Waiting for connections");
			while(true)
			{
				Socket s = sock.accept();
				debug("Client connected. Waiting for new connections");
				clients.add(s);
				Thread t = new Thread(new ParallelServer(s));
				t.start();
			}
		} catch (IOException e) 					{e.printStackTrace();}
	}
	public void sendMessage(String msg,Socket cl)
	{
		try {
			new DataOutputStream(cl.getOutputStream()).writeBytes(msg+"\n");
		} catch (IOException e) {				e.printStackTrace();
		}
	}
	public void broadcastMessage(String msg)
	{
		debug("Sending broadcast message: " + msg);
		Iterator<Socket> i = clients.iterator();
		while(i.hasNext())
		{
			Socket s = (Socket)i.next();
			sendMessage(msg, s);
		}
	}
	public class ParallelServer implements Runnable
	{
		public BufferedReader is;
		public DataOutputStream os;
		Socket client;
		public ArrayList<String> ind;
		
		public ParallelServer(Socket cl)
		{
			try
			{
				ind = new ArrayList<String>();
				client = cl;
				os = new DataOutputStream(cl.getOutputStream());
				is = new BufferedReader(new InputStreamReader(cl.getInputStream()));
			} catch (IOException e) 				{e.printStackTrace();}
		}

		@Override
		public void run()
		{
			Dizionario d = new Dizionario();
			Main.broad("griglia$****************");
			String s;
			try {
				while(true)
				{
					s = is.readLine();
					String arr[] = s.split("\\$");
					if(arr[0].equalsIgnoreCase("controllo"))
						{
							if(d.cerca(arr[1]))
							{
								if(valida(arr[1]))
								{
									ind.add(arr[1]);
									sendMessage("stringa$ok", client);
								}
								else
									sendMessage("stringa$used", client);
								
							}
							else
							{
								sendMessage("stringa$no", client);
							}
								
						}						
				}
			} catch (IOException e) 				{}
		}
		
		public boolean valida(String parola)
		{
			Iterator<String> i = ind.iterator();
			String s;
			while(i.hasNext())
			{
				s = (String) i.next();
				if(s.equalsIgnoreCase(parola))
					return false;
			}
			return true;
		}
	}
	public void debug(String s)
	{
		Main.a.append(s + "\n");
		Main.l = s;
	}
	
	public void ListClients()
	{
		int j = 1;
		Iterator<Socket> i = clients.iterator();
		while(i.hasNext())
		{
			Socket s = (Socket)i.next();
			debug("socket: " + j + " ip: " + s.getInetAddress().toString() + "connected: " + s.isClosed());
		}
	}
}
