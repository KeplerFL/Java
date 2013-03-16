package ruzzle;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	static Server server;
	static int port =7000;
	private static JFrame finestra;
	static int punteggio =0;
	static JLabel punti;
	
	public static void main(String[] args) throws FileNotFoundException {
		server =new Server();
		finestra = new JFrame();
		punti = new JLabel("0");
		
		finestra.setVisible(true);
		finestra.setSize(300, 420);
		finestra.setLocation(100, 100);
		finestra.setLayout(null);
		finestra.setResizable(false);  //impedisco che la finestra venga ridimensionata 
		finestra.setTitle("Chat Client Graziani");
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quando viene chiusa la finestra principale il programma deve terminarsi!
		
		punti.setVisible(true);
		punti.setSize(300, 50);
		punti.setLocation(20, 20);
		finestra.add(punti);
		while(true)
		{
			try	{ server.start(port); }
			catch (IOException e){JOptionPane.showMessageDialog(null,
				    "Avvio fallito!!",
				    "Errore Server!",
				    JOptionPane.ERROR_MESSAGE);}
		}
		
	}

}
