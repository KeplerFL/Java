package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	public static JFrame f;
	public static JLabel l;
	private static JPanel p;
	public static JLabel j;
	public static JLabel k;
	public static JCheckBox pp;
	static Button b[][] = new Button[4][4];
	public static String sfinale;
	public static boolean pressed = false;
	private static Client cl;
	public static String giusta;
	static int lastx, lasty = 125;
	public static int punti = 0;
	//finestra di connessione
	private static JFrame fc;
	private static JTextField ipc, pc;
	private static JButton bc;
	private static JLabel lc;
	
	public static void main(String[] args) 
	{
		f = new JFrame("Ruzzle!");
		f.setVisible(false);
		f.setSize(300, 350);
		f.setLocation(400, 50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);
		
		p = new JPanel();
		p.setVisible(true);
		p.setLocation(20,20);
		p.setSize(280, 270);
		p.setLayout(null);
		f.add(p);
		
		for(int r=0;r<4;r++)
	     {
	    	 for(int c=0;c<4;c++)
	    	 {
	    		 char k = 'a';
	    		 b[r][c]=new Button();
	    		 b[r][c].setSize(50, 50);
	    		 b[r][c].setLocation(c*70, r*70);
	    		 b[r][c].setVisible(true);
	    		 b[r][c].setText(String.valueOf(k));
	    		 b[r][c].setBackground(Color.white);
	    		 b[r][c].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
	    		 b[r][c].addMouseListener(b[r][c]);
	    		 p.add(b[r][c]);
	    	 }
	     }
		
		l = new JLabel();
		l.setVisible(true);
		l.setSize(100, 50);
		l.setLocation(120, 275);
		l.setHorizontalTextPosition(JLabel.CENTER);
		f.add(l);
		
		j = new JLabel();
		j.setVisible(true);
		j.setSize(80, 50);
		j.setLocation(280, 275);
		j.setHorizontalTextPosition(JLabel.CENTER);
		f.add(j);
		
		k = new JLabel();
		k.setVisible(true);
		k.setSize(80, 50);
		k.setLocation(20, 275);
		k.setForeground(Color.orange);
		k.setHorizontalTextPosition(JLabel.CENTER);
		f.add(k);
		
		f.repaint();	
		
		fc = new JFrame("Connessione al server");
		fc.setVisible(true);
		fc.setSize(200, 220);
		fc.setLocation(300, 300);
		fc.setLayout(null);
		fc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ipc = new JTextField();
		ipc.setVisible(true);
		ipc.setLocation(20, 40);
		ipc.setSize(160, 30);
		ipc.setText("localhost");
		fc.add(ipc);
		
		pc = new JTextField();
		pc.setVisible(true);
		pc.setLocation(20, 90);
		pc.setSize(160, 30);
		pc.setText("6677");
		fc.add(pc);
		
		lc = new JLabel("Errore di connessione");
		lc.setVisible(false);
		lc.setSize(200, 20);
		lc.setHorizontalTextPosition(JLabel.CENTER);
		lc.setLocation(20, 5);
		lc.setForeground(Color.red);
		fc.add(lc);
		
		bc = new JButton("Connect");
		bc.setVisible(true);
		bc.setLocation(40, 140);
		bc.setSize(120, 30);
		fc.add(bc);
		fc.repaint();
		bc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fc.setVisible(false);
				cl = new Client();
				try 
				{
					cl.start(ipc.getText(), Integer.parseInt(pc.getText()));
					f.setVisible(true);
				} 
				catch (NumberFormatException e) {fc.setVisible(true); lc.setVisible(true);} 
				catch (UnknownHostException e) 	{fc.setVisible(true); lc.setVisible(true);} 
				catch (IOException e) 			{fc.setVisible(true); lc.setVisible(true);}
			}
		});
	}
	public static void reset()
	{
		for(int r=0;r<4;r++)
	     {
	    	 for(int c=0;c<4;c++)
	    	 {
	    		 b[r][c].premuto();
	    		 b[r][c].setBorder(BorderFactory.createMatteBorder(
	                        1, 1, 1, 1, Color.black));
	    	 }
	     }
	}
	
	public static void usa()
	{
		for(int r=0;r<4;r++)
	     {
	    	 for(int c=0;c<4;c++)
	    	 {
	    		 b[r][c].pres();
	    		
	    	 }
	     }
	}
	
	public static void evidenzia()
	{
		for(int r=0;r<4;r++)
	     {
	    	 for(int c=0;c<4;c++)
	    	 {
	    		 b[r][c].premuto();
	    		 b[r][c].setBorder(BorderFactory.createMatteBorder(
	                        2, 2, 2, 2, Color.red));
	    	 }
	     }
	}
	
	public static boolean send()
	{
		try 
		{
			cl.sendMessage("controllo$"+sfinale.substring(4));
			giusta = sfinale.substring(4);
			sfinale = null;
			return true;
		} catch (IOException e) { return false;} 
	}
	
	public static void setGriglia(String s)
	{
		System.out.println("imposto griglia");
		char[] ch = s.toCharArray();
		int q=0;
		for(int r=0;r<4;r++)
		 {
			 for(int c=0;c<4;c++)
			 {
				Main.b[r][c].setText(Character.toString(ch[q]));
				q++;
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
	
	public void finalize()
	{
		cl.close();
	}
}
