package ruzzle;

//import dei packages necessari
import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
	private ServerSocket serverSocket;
	@SuppressWarnings("rawtypes")
	ArrayList clients;
	int cont;
	boolean isConnected = true;
	boolean isempty = true;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void start(int port) throws IOException{
		clients=new ArrayList();
		sock client = new sock();
		serverSocket= new ServerSocket(port);
		while(true)
		{
			client.sock = serverSocket.accept();
			if(isempty)
			{
				isempty = false;
				Timer timer = new Timer("Printer");
				MyTask t = new MyTask();
				timer.schedule(t, 0, 2*60*1000);
			}
			clients.add(client);
			client.id = cont;
			cont ++;
			Thread t=new Thread(new ParallelServer (client));
			t.start();
		}
	}

	class ParallelServer implements Runnable
	{
		DataOutputStream os;
		BufferedReader is;
		sock client;
		int punteggio = 0;
		
		public ParallelServer(sock cliente){
			try{
				client = cliente;
				is= new BufferedReader(new InputStreamReader(client.sock.getInputStream()));//in entrata
				os= new DataOutputStream (client.sock.getOutputStream());//in uscita
			}catch(IOException e)				{e.printStackTrace();}
		}
		
		@Override
		public void run() {
			try
			{
				String strReceived = "a�";
				Dizionario d = new Dizionario();
				sendMessage("id�"+client.id, client.sock);
				while((strReceived = is.readLine()) != null)
				{
							String[] arr = strReceived.split("\\�");
							if(arr[0].equals("controllo"))
							{
								if(d.cerca(arr[1]))
								{
									sendMessage("stringa�ok", client.sock);
								}	
								else
								{
									sendMessage("stringa�errore", client.sock);
								}
									
							}
							else if(arr[0].equals("chiusura"))
							{
								@SuppressWarnings("rawtypes")
								Iterator all=clients.iterator();
								for(   ;all.hasNext();     )
								{
									sock cl = (sock) all.next();
									if(cl.id == Integer.parseInt(arr[1]))
									{
										all.remove();
									}
								}
							}
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
				
			}
		}
	
	public void broadcastMessage(String recMsg) throws IOException
	{
		@SuppressWarnings("rawtypes")
		Iterator all=clients.iterator();
		System.out.println("stringa: "+ recMsg);
		for(   ;all.hasNext();     )
		{
			sock cl=(sock)all.next();
				new DataOutputStream(cl.sock.getOutputStream()).writeBytes(recMsg + "\n");
		}
	}
	
	public void sendMessage(String s, Socket cl)
	{
		try 
		{
			new DataOutputStream(cl.getOutputStream()).writeBytes(s + "\n");
		} 
		catch (IOException e) 								{e.printStackTrace();}
	}
	
	class MyTask extends TimerTask {
	 
	    public void run()
	    {
			Dizionario d = new Dizionario();
			String s = d.crea();
			try {
				broadcastMessage("griglia�" + s);
			} catch (IOException e)							{e.printStackTrace();}
			d.DDizionario();
	    }
	}

}
