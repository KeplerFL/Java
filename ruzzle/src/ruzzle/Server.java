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
		clients=new ArrayList();
		sock client = new sock();
		serverSocket= new ServerSocket(port);
		while(true){
		client.sock= serverSocket.accept();
		clients.add(client.sock);
		Thread t=new Thread(new ParallelServer (client));
		t.start();
		}
	}

	class ParallelServer implements Runnable{
		DataOutputStream os;
		BufferedReader is;
		sock client;
		public ParallelServer(sock client){
			try{
				is= new BufferedReader(new InputStreamReader(client.sock.getInputStream()));//in entrata
				os= new DataOutputStream (client.sock.getOutputStream());//in uscita
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try
			{
				String strReceived = "a�";
				while((strReceived = is.readLine()) != null)
				{
					System.out.println("sono in: CLASSE PARALLEL_SERVER - Metodo broadcastMessage");
							String[] arr = strReceived.split("\\�");
							Dizionario d = new Dizionario();
							if(d.cerca(arr[0]))
							{
								System.out.println("parola trovata");
								//client.punteggio += (int) arr[0].length() * 0.8;
							}	
							else
								System.out.println("parola sbagliata");
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
	
	public void broadcastMessage(String recMsg, BufferedReader is, DataOutputStream os, Socket client) throws IOException{
		Iterator all=clients.iterator();
		System.out.println("stringa: "+ recMsg);
		for(   ;all.hasNext();     ){
			Socket cl=(Socket)all.next();
				new DataOutputStream(cl.getOutputStream()).writeBytes(recMsg + "\n");
			}
	}

}
