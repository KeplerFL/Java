package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Client {
private static DataOutputStream os;
private static BufferedReader is;
private int porta;
private static Socket socket = null; // crea un oggetto della classe SocketClient
private boolean isClientConnected; // crea una variabile di tipo Boolean per controllare lo stato di

public void start(String ipServer,int porta) throws IOException{
	//Connessione della socket con il server
	socket = new Socket (ipServer,porta);
	
	//Stream di Byte da passare al Socket
	os= new DataOutputStream (socket.getOutputStream());//in uscita
	is= new BufferedReader(new InputStreamReader(socket.getInputStream()));//in entrata
	isClientConnected = true;
	Thread t = new Thread(new ParallelClient());
	t.start();
	
	}
	
	public static void sendMessage(String strMsg)throws IOException
	{
		os.writeBytes(strMsg + '\n');
	}
	
	public String receiveMessage()throws IOException
	{
		return is.readLine();
	}
	
	public static void close ()throws IOException
	{
		os.writeBytes("chiusura�"+Main.id);
		os.close();
		is.close();
		socket.close();
		System.exit(0);
	}
	
	public class ParallelClient implements Runnable
	{
		public void run()  //metodo run
		{
			String s;
			while (isClientConnected)
				// ciclo infinito su variabile di controllo isClientConnected
				{
				try 
				{
					s=is.readLine();					
					String[] arr = s.split("\\�");
					if(arr[0].equals("griglia"))
					{
						char[] ch = arr[1].toCharArray();
						int q=0;
						 for(int r=0;r<4;r++)
					     {
					    	 for(int c=0;c<4;c++)
					    	 {
					    		Main.mat[r][c].setText(Character.toString(ch[q]));
					    		q++;
					    	 }
					     }
					}
					else if(arr[0].equals("stringa"))
					{
						if(arr[1].equals("ok"))
						{
							Main.punteggio += Main.last.length()*1.5;
							Main.parola.setText(Main.last);
							Main.add.setText(Integer.toString((int) (Main.last.length()*1.5)));
							Main.punti.setText(Integer.toString(Main.punteggio));
						}
						else
						{
							
						}
					}
					if(Main.finestra.isFocused() == false)
						trillo();
				}
				catch (IOException e) 						{e.printStackTrace();} 
				catch (InterruptedException e) 				{e.printStackTrace();} 
				catch (LineUnavailableException e) 			{e.printStackTrace();}
				catch (UnsupportedAudioFileException e) 	{e.printStackTrace();}
			}
		}
		
		public void trillo() throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException
		{
			Main.finestra.toFront();
	 	   File url = new File("trillo.wav");
	 	   AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	 	   Clip clip = AudioSystem.getClip();
	 	   clip.open(audioIn);
	 	   clip.start(); 
	 	   
	 			  
	        for(int i =0; i<10; i++)  //quando si riceve un messaggio la finestra trema!
	        {
	     	   Main.finestra.setLocation(Main.finestra.getLocationOnScreen().x + 5, Main.finestra.getLocationOnScreen().y + 5);
	     	   Thread.sleep(50);
	            Main.finestra.setLocation(Main.finestra.getLocationOnScreen().x - 5, Main.finestra.getLocationOnScreen().y - 5);
	            Thread.sleep(50);
	        }
		}
	}
}