package ruzzle;

import java.awt.Color;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFrame;

import ruzzle.Server;
public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	static Server server;
	static int port =7000;
	private static JFrame finestra;
	
	public static void main(String[] args) throws FileNotFoundException {
		server =new Server();
		finestra = new JFrame();
		
		finestra.setVisible(true);
		finestra.setSize(300, 420);
		finestra.setLocation(100, 100);
		finestra.setLayout(null);
		finestra.setResizable(false);  //impedisco che la finestra venga ridimensionata 
		finestra.setTitle("Chat Client Graziani");
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quando viene chiusa la finestra principale il programma deve terminarsi!
		
		while(true)
		{
			try {
				System.out.println("ciaociao");
				server.start(port);
				}
			catch (IOException e){System.out.println("vaffanculo, non riesco ada avviarmi");}
		}
		
	}

}
