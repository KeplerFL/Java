package chatClient;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Client {
	private static DataOutputStream os;
	private BufferedReader is;
	private int porta;
	private Socket socket = null; // crea un oggetto della classe SocketClient
    private boolean isClientConnected; // crea una variabile di tipo Boolean per controllare lo stato di 
    
	public void start(String ipServer,int porta) throws IOException{
		//Connessione della socket con il server
		System.out.println("Tentativo di connessione con server: " + "[" + ipServer + "]" + " su porta: "+ "["+ porta +"]");
		socket = new Socket (ipServer,porta);

		//Stream di Byte da passare al Socket
		os= new DataOutputStream (socket.getOutputStream());//in uscita
		is= new BufferedReader(new InputStreamReader(socket.getInputStream()));//in entrata
		isClientConnected = true;
		Thread t = new Thread(new ParallelClient());
		t.start();
	}

	public static void sendMessage(String strMsg)throws IOException{
		os.writeBytes(strMsg + '\n');
	}

	public String receiveMessage()throws IOException{
		return is.readLine();
	}

	public void close ()throws IOException{
		System.out.println("Chiusura client e  stream");
		os.close();
		is.close();
		socket.close();
		System.exit(0);
	}

	
	public class ParallelClient implements Runnable{
        public void run() { //metodo run

		String s;
		while (isClientConnected)      // ciclo infinito su variabile di controllo isClientConnected
		{
			try 
			{
	           s=is.readLine();
	           System.out.println("ricevuto = " + s);
	           String[] arr = s.split("\\�");
	           for(int i = 0; i<arr.length; i++)
					System.out.println(arr[i] + "\n");
	           Main.chat.append(arr[1] + ":" + arr[2] + "\n");
	           System.out.println("|" + arr[1] + "|");
	           if(Main.finestra.isFocused() != true)
	        	   trillo();
				}
				catch (IOException e) {e.printStackTrace();} 
				catch (LineUnavailableException e) {e.printStackTrace();} 
				catch (UnsupportedAudioFileException e) {e.printStackTrace();} 
				catch (InterruptedException e) {e.printStackTrace();}
		
     }
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
