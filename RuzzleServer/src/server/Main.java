package server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

public class Main {

	private static JFrame f;
	public static JTextArea a;
	public static String l;
	private static JScrollPane c;
	private static JTextField b;
	private static Server s;
	private static JButton d;
	private static String arr[];
	private static Thread t;
	public static String classifica;
	
	public static void main(String[] args) 
	{
		f = new JFrame("Server");
		f.setVisible(true);
		f.setSize(500, 360);
		f.setLocation(100, 100);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.black);
		f.setAlwaysOnTop(true);
		
		a = new JTextArea();
		a.setVisible(true);
		a.setSize(f.getWidth(), f.getHeight()-60);
		a.setLocation(0, 0);
		a.setEditable(false);
		a.setBackground(Color.black);
		a.setForeground(Color.white);
		DefaultCaret caret = (DefaultCaret)a.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		b = new JTextField();
		b.setVisible(true);
		b.setSize(f.getWidth()-110, 20);
		b.setLocation(5, f.getHeight()-65);
		b.setBackground(Color.black);
		b.setForeground(Color.white);
		f.add(b);
		
		c = new JScrollPane(a);
		c.setVisible(true);
		c.setSize(f.getWidth() - 20, f.getHeight()-70);
		c.setLocation(1, 1);
		c.setBackground(Color.black);
		c.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		c.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		c.setAutoscrolls(true);
		f.add(c);
		
		d = new JButton("INVIA");
		d.setVisible(true);
		d.setSize(90, 20);
		d.setLocation(f.getWidth()-105, f.getHeight()-65);
		f.add(d);
		
		f.repaint();
		
		f.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent arg0) {}
			
			@Override
			public void componentResized(ComponentEvent arg0) {
				a.setSize(f.getWidth(), f.getHeight()-60);
				b.setSize(f.getWidth()-110, 20);
				b.setLocation(5, f.getHeight()-65);
				c.setSize(f.getWidth()-20, f.getHeight()-70);
				d.setLocation(f.getWidth()-100, f.getHeight()-65);
			}
			
			@Override
			public void componentMoved(ComponentEvent arg0) {}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {}
		});
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				console();
			}
		});
		
		d.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				console();
			}
		});
		
		Thread t = new Thread(new restart());
		t.start();
		
		s = new Server();
		s.start(6677);
		
		
	}
	
	public static void help()
	{
		debug("usage of console:");
		debug("\n");
		debug("-system");
		debug("--shutdown: close the server process, can set a delay writing it in seconds after \"shutdown\"");
		debug("--top: Set the \"always on top state, arguments: true-false\"");
		debug("\n");
		debug("-list");
		debug("--client: show a list of all clients connected since the server session started");
		debug("-broadcast: Send broadcast message, evrything after the word \"BROADCAST\" without a space");
	}
	
	public static void console()
	{
		String st = b.getText();
		b.setText(null);
		arr = st.split("\\ ");
		if(arr[0].contains("he"))
		{
			help();
		}
		else if(arr[0].equalsIgnoreCase("system"))
		{
			if(arr[1].equalsIgnoreCase("shutdown"))
			{
				if(arr.length == 2)
					System.exit(1);
				else if(arr[2].equalsIgnoreCase("cancel"))
					{
						run.ok = false;
						debug("Shutdown cancelled");
					}
				else
				{
					t = new Thread(new run(Integer.parseInt(arr[2])));
					t.start();
					debug("shutdown started. Server will stop in " + arr[2] + "  seconds");
				}
			}
			else if(arr[1].contains("top"))
			{
				if(arr[2].equalsIgnoreCase("TRUE") || arr[2].equalsIgnoreCase("ON") || arr[2].equalsIgnoreCase("1"))
					{
						f.setAlwaysOnTop(true);
						debug("Frame always on top");
					}
				else if(arr[2].equalsIgnoreCase("FALSE") || arr[2].equalsIgnoreCase("OFF") || arr[2].equalsIgnoreCase("0"))
					{
						f.setAlwaysOnTop(false);
						debug("Frame no longer always on top");
					}
				else
					help();
			}
			else if(arr[0].equalsIgnoreCase("kick"))
			{
				Iterator i = s.clients.iterator();
				while(i.hasNext())
				{
					Socket s = (Socket) i.next();
					if(arr[1].equalsIgnoreCase(s.getInetAddress().toString()))
					{
						try {
							s.getInputStream().close();
							s.getOutputStream().close();
							s.close();
						} catch (IOException e) {}
					}
				}
			}
			else
				help();
		}
		else if(arr[0].equalsIgnoreCase("broad") || arr[0].equalsIgnoreCase("bcast") || arr[0].equalsIgnoreCase("broadcast"))
			s.broadcastMessage(arr[1]);
		else if(arr[0].equalsIgnoreCase("list"))
			{
				if(arr[1].equalsIgnoreCase("client"))
				{
					s.ListClients();
				}
			}
		else
			help();
	}
	public static void debug(String s)
	{
		a.append(s+"\n");
	}
	
	public static void broad(String st)
	{
		s.broadcastMessage(st);
	}

}

class run implements Runnable{

	int secondi;
	static boolean ok = true;
	
	public run(int s)
	{
		secondi = s;
	}
	
	@Override
	public void run() 
	{
		try 
		{
			Thread.sleep(secondi * 1000);
		} 
		catch (InterruptedException e) 		{e.printStackTrace();}
		if(ok)
			System.exit(0);
	}
	
}

class restart implements Runnable
{
	/*******************************************************
	 * le azioni da intraprendere sono: invio griglia, stop gioco, invio nuova griglia dopo circa 20 secondi
	 ********************************************************/
	public int action = 1;
	

	@Override
	public void run() {
		while(true)
		{
			if(action == 1)
			{
				Dizionario d = new Dizionario();
				String parole = d.crea();
				Main.broad("griglia$"+parole);
				Main.debug("nuova griglia: " + parole);
				try {
					Thread.sleep(2*5*1000);
				} catch (InterruptedException e)	 { Main.debug("Errore di sleep");}
				action = 2;
			}
			else if(action == 2)
			{
				Main.broad("end$Partita finita, 20 secondi alla prossima griglia!");
				try {
					Thread.sleep(20*1000);
				} catch (InterruptedException e) 	{}
				action = 1;
			}
		}
		
	}
	
}