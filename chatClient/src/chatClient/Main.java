package chatClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	static JLabel des;
	static JTextField input;
	static JTextArea chat;
	static JButton chiudi;
	static JButton file;
	private static JScrollPane sbrText;
	static JCheckBox pp;
	static JFrame finestra;
	static String ip = "localhost";
	static String HOST = "localhost";
	static String nick = "mattia";
	static String last ="";
	static int i;
	static bottone [][] mat= new bottone[4][4];
	
	
	public static void main(String[] args){
		finestra = new JFrame();
		des = new JLabel("Chat: ");
		input = new JTextField(/*"Aspetta che il tuo amico risponda!"*/);
		chat = new JTextArea();
		chiudi = new JButton("Chiudi");
		pp = new JCheckBox("Sempre in primo piano");
		
		finestra.setVisible(true);
		finestra.setSize(300, 420);
		finestra.setLocation(100, 100);
		finestra.setLayout(null);
		finestra.setResizable(false);  //impedisco che la finestra venga ridimensionata 
		finestra.setTitle("Chat Client Graziani");
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quando viene chiusa la finestra principale il programma deve terminarsi!
		
		des.setVisible(true);
		des.setSize(150, 20);
		des.setLocation(15, 20);
		finestra.add(des);
		
		
		JPanel Area = new JPanel();
		 for(int r=0;r<4;r++)
         {
        	 for(int c=0;c<4;c++)
        	 {
        		 mat[r][c]=new bottone();
        		 mat[r][c].setSize(50, 50);
        		 mat[r][c].setLocation(30+c*52, 50+r*52);
        		 mat[r][c].setVisible(true);
        		 mat[r][c].setBackground(Color.white);
        		 mat[r][c].setBorderPainted(true);
        		 finestra.add(mat[r][c]);
        		// mat[r][c].addActionListener(bLis);
        	 }
        	 
         }
        
		/*sbrText = new JScrollPane(chat);   //scrollpane 
		sbrText.setVisible(true);
		sbrText.setSize(250, 250);
		sbrText.setLocation(15, 40);
		sbrText.setBackground(Color.white);
		sbrText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sbrText.setAutoscrolls(true);
		
		chat.setVisible(true);
		chat.setSize(232, 250);
		chat.setLocation(0, 0);
		chat.setEditable(false);
		chat.setWrapStyleWord(true);
		chat.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret)chat.getCaret();   //impostazioni di autoscroll
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		finestra.add(sbrText);*/
			
		input.setVisible(true);
		input.setSize(250, 30);
		input.setLocation(15, 300);
		finestra.add(input);
		
		chiudi.setVisible(true);
		chiudi.setSize(200, 30);
		chiudi.setLocation(45, 335);
		finestra.add(chiudi);
		
		pp.setVisible(true);
		pp.setSize(200, 20);
		pp.setLocation(115, 20);
		pp.setToolTipText("Set the chat frame above every other frame.");
		finestra.add(pp);
		
		String h = JOptionPane.showInputDialog(null, "Inserisci l'indirizzo ip del server: ", "IP", 1);
		nick = JOptionPane.showInputDialog(null, "Inserisci il nickname: ", "Nickname", 3);
		if(h != null)
			HOST = h;
		
		pp.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				 Object source = e.getItemSelectable();

			    if (source == pp) {
			    	finestra.setAlwaysOnTop(true);
			    }
			    if(e.getStateChange() == ItemEvent.DESELECTED)
			    	finestra.setAlwaysOnTop(false);
			}
		});
		
		input.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				chat.setForeground(Color.blue);
				try {
					Client.sendMessage( input.getText() );
				} catch (IOException e1) {
					chat.append("ERROR IN SENDING MESSAGE!");
				}
				input.setText(null);				
			}
		});
		
		chiudi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(1);
				
			}
		});
		
		client= new Client();
		try {
			client.start(HOST, 7000);
		}
		catch (IOException e) 
		{};
	}
}
