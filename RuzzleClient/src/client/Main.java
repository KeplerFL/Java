package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main {
public static Client client=null;
static JLabel des, punti, parola, add;
static JButton chiudi;
static JCheckBox pp;
static JFrame finestra;
static String ip = "localhost";
static String HOST = "localhost";
static String nick = "mattia";
static Button [][] mat= new Button[4][4];
static JPanel Area;
static String ris, last = "";
static boolean press = false;
static int punteggio = 0;
static int id;

static int lastx, lasty = 125;

public static void main(String[] args){
	finestra = new JFrame();
	des = new JLabel("Chat: ");
	punti = new JLabel("0");
	parola = new JLabel("");
	add = new JLabel("");
	chiudi = new JButton("Chiudi");
	pp = new JCheckBox("Sempre in primo piano");
	
	finestra.setVisible(true);
	finestra.setSize(300, 420);
	finestra.setLocation(100, 100);
	finestra.setLayout(null);
	finestra.setResizable(false); //impedisco che la finestra venga ridimensionata
	finestra.setTitle("Chat Client Graziani");
	
	finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quando viene chiusa la finestraprincipale il programma deve terminarsi!
	
	des.setVisible(true);
	des.setSize(150, 20);
	des.setLocation(15, 20);
	finestra.add(des);
	
	punti.setVisible(true);
	punti.setSize(150, 20);
	punti.setLocation(80, 20);
	finestra.add(punti);
	
	Area = new JPanel();
	Area.setLocation(new Point(30, 50));
	Area.setSize(new Dimension(460, 460));
	Area.setVisible(true);
	Area.setLayout(null);
	finestra.add(Area);
	char k ='a';
	 for(int r=0;r<4;r++)
     {
    	 for(int c=0;c<4;c++)
    	 {
    		 mat[r][c]=new Button();
    		 mat[r][c].setSize(50, 50);
    		 mat[r][c].setLocation(c*65, r*65);
    		 mat[r][c].setText(Character.toString(k));
    		 mat[r][c].setVisible(true);
    		 mat[r][c].setBackground(Color.white);
    		 mat[r][c].setBorderPainted(true);
    		 mat[r][c].addMouseMotionListener(mat[r][c]);
    		 mat[r][c].x = r;
    		 mat[r][c].y = c;
    		 Area.add(mat[r][c]);
    		 mat[r][c].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(press == false)
					{
						Area.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
						press = true;
					}
					else
					{
						press = false;
						Area.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						System.out.println("stringa:  " + ris);
						Main.lastx = Main.lasty = 125;
						try {
							Client.sendMessage("controllo�" + ris);
						} catch (IOException e) 			{e.printStackTrace();}
						last = ris;
						ris = "";
						reset();
					}
					
				}
			});
    		 k++;
    	 }
     }
	 
	parola.setVisible(true);
	parola.setSize(150, 20);
	parola.setLocation(80, 330);
	finestra.add(parola);
	
	add.setVisible(true);
	add.setSize(150, 20);
	add.setLocation(200, 330);
	finestra.add(add);
	
	
	pp.setVisible(true);
	pp.setSize(200, 20);
	pp.setLocation(115, 20);
	pp.setToolTipText("Set the chat frame above every other frame.");
	finestra.add(pp);
	
	String h = JOptionPane.showInputDialog(null, "Inserisci l'indirizzo ip del tuo compagno: ", "IP", 1);
	nick = JOptionPane.showInputDialog(null, "Inserisci il nickname: ", "Nickname", 3);
	if(h != null)
	HOST = h;
	
	pp.addItemListener(new ItemListener() {
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		
		if (source == pp) 
			finestra.setAlwaysOnTop(true);
		if(e.getStateChange() == ItemEvent.DESELECTED)
			finestra.setAlwaysOnTop(false);
		}
	});
	
	chiudi.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.exit(1);
	}
	});
	
	client= new Client();
	try 
	{
		client.start(HOST, 7000);
	}
	catch (IOException e){};
	
	finestra.addWindowListener(new WindowListener() {
		
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowClosing(WindowEvent e) {
			try {
				Client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	});
}



	public static void reset()
	{
		 for(int r=0;r<4;r++)
	     {
	    	 for(int c=0;c<4;c++)
	    	 {
	    		 mat[r][c].pressed = false;
	    	 }
	     }
	}
	
	public static boolean IsInRangeM(int curx, int cury)
	{
		if(curx < lastx-1 || curx > lastx+1)
		{
			return false;
		}
		else if(cury < lasty-1 || cury > lasty+1)
			return false;
		else 
		{
			return true;
		}
	}	
}