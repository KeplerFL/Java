package ruzzle;

//import dei packages necessari
import java.net.*;
import java.util.*;
import java.awt.Color;
import java.io.*;

public class Server {
	private ServerSocket serverSocket;
	ArrayList clients;
	boolean isConnected = true;

	public void start(int port) throws IOException{
		System.out.println("cm asdjklfna");
		clients=new ArrayList();
		sock client = new sock();
		serverSocket= new ServerSocket(port);
		while(true)
		{
			System.out.println("jdskf");
			client.sock= serverSocket.accept();
			clients.add(client.sock);
			Thread t=new Thread(new ParallelServer (client));
			t.start();
		}
	}

	class ParallelServer implements Runnable
	{
		DataOutputStream os;
		BufferedReader is;
		sock client;
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
				System.out.println("ciao");
				String strReceived = "a�";
				Dizionario d = new Dizionario();
				System.out.println("cacca");
				String s = d.crea();
				broadcastMessage("griglia�"+s,is, os, client.sock);
				while((strReceived = is.readLine()) != null)
				{
					System.out.println("sono in: CLASSE PARALLEL_SERVER - Metodo broadcastMessage");
							String[] arr = strReceived.split("\\�");
							if(arr[0].equals("controllo"))
							{
								if(d.cerca(arr[1]))
								{
									System.out.println("parola trovata");
									os.writeBytes("punti�" + arr[1].length()*1.5);
								}	
								else
									System.out.println("parola sbagliata");
							}
							
							for(int i = 0; i<arr.length; i++)
								System.out.println(arr[i] + "\n");
						
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
				try {
					is.close();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
				
			}
		}
	
	public void broadcastMessage(String recMsg, BufferedReader is, DataOutputStream os, Socket client) throws IOException
	{
		Iterator all=clients.iterator();
		System.out.println("stringa: "+ recMsg);
		for(   ;all.hasNext();     )
		{
			Socket cl=(Socket)all.next();
				new DataOutputStream(cl.getOutputStream()).writeBytes(recMsg + "\n");
		}
	}

}
