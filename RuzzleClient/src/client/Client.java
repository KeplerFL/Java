package client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client
{
	public static DataOutputStream os;
	public static BufferedReader is;
	public static boolean ClienteConnesso;
	public static int porta;
	public static Socket socket=null;
	
	public void start(String ip,int porta) throws UnknownHostException, IOException
	{
		socket=new Socket(ip,porta);
		is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		os=new DataOutputStream(socket.getOutputStream());
		ClienteConnesso = true;
			
		Thread t = new Thread(new ClientParallelo());
		t.start();
	}
	
	public void close()
	{
		try {
			os.close();
			is.close();
			socket.close();
		} catch (IOException e) 				{e.printStackTrace(); ClienteConnesso = false;}
		
	}
	public void sendMessage(String s) throws IOException
	{
			os.writeBytes(s + "\n");
	}

	public class ClientParallelo implements Runnable
	{
		@Override
		public void run() {
			String s;
			try 
			{
				while(ClienteConnesso)
				{
					s = is.readLine();
					String arr[] = s.split("\\$");
					if(arr[0].equals("griglia"))
					{
						Main.punti = 0;
						Main.j.setText("0");
						
						char[] ch = arr[1].toCharArray();
						int q=0;
						 for(int r=0;r<4;r++)
					     {
					    	 for(int c=0;c<4;c++)
					    	 {
					    		Main.b[r][c].setText(Character.toString(ch[q]));
					    		q++;
					    	 }
					     }
						 Thread t = new Thread(new evidenzia());
						 t.start();
					}
					else if(arr[0].equalsIgnoreCase("stringa"))
					{
						if(arr[1].equalsIgnoreCase("ok"))
						{
							Main.l.setText(Main.giusta);
							Main.l.setForeground(Color.black);
							Main.k.setText(Integer.toString(Main.giusta.length()));
							Main.punti += Main.giusta.length();
							Main.j.setText(Integer.toString(Main.punti));
						}
						else if(arr[1].equalsIgnoreCase("no"))
						{
							Main.k.setText(Integer.toString(0));
							Main.l.setForeground(Color.red);
							Main.l.setText(Main.giusta.toUpperCase());
						}
						else if(arr[1].equalsIgnoreCase("used"))
						{
							Main.k.setText(Integer.toString(0));
							Main.l.setForeground(Color.black);
							Main.l.setText(Main.giusta.toUpperCase());
						}
					}
					else if(arr[0].equalsIgnoreCase("end"))
					{
						Main.usa();
						JOptionPane.showMessageDialog(null, arr[1]);
					}
					else if(arr[0].equalsIgnoreCase("givemepoints"))
						sendMessage(Integer.toString(Main.punti));
				}
			} catch (IOException e) 			{e.printStackTrace();}
			
		}
	}
}

class evidenzia implements Runnable
{

	@Override
	public void run() {
		Main.evidenzia();
		try {Thread.sleep(1500);} catch (InterruptedException e) {}
		Main.reset();
	}
	
}
