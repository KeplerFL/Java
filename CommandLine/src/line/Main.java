package line;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Main {
	
	private static JFrame f;
	private static JLabel l;
	private static JTextArea c;
	private static JTextArea a;
	private static JButton b;
	private static Object DefaultCommands;

	public static void main(String[] args) {
		f = new JFrame();
		f.setTitle("Command Line");
		f.setSize(500, 400);
		f.setLocation(100, 100);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setLayout(null);
		
		l = new JLabel();
		
		l.setText("Output:");
		f.add(l);
		
		c = new JTextArea();
		c.setBackground(Color.BLACK);
		c.setForeground(Color.WHITE);
		f.add(c);
		
		a = new JTextArea();
		a.setBackground(Color.black);
		a.setForeground(Color.WHITE);
		a.setEditable(false);
		f.add(a);
		
		b = new JButton();
		b.setText("Invia");
		f.add(b);
		
		f.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e)
			{
				Resize();
			}
		});
		
		c.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) 
			{}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Resize();
	}
	
	public static Point GetPosition(String rel, Component panel)
	{
		switch(rel)
		{
		case "TOPLEFT" :
			return new Point(panel.getX(), panel.getY());
		case "TOP":
			return new Point((panel.getX() + panel.getWidth()/2), panel.getY());
		case "TOPRIGHT":
			return new Point((panel.getX() + panel.getWidth()), panel.getY());
		case "RIGHT":
			return new Point((panel.getX() + panel.getWidth()), panel.getY() + panel.getHeight() / 2);
		case "BOTTOMRIGHT":
			return new Point((panel.getX() + panel.getWidth()), panel.getY() + panel.getHeight());
		case "BOTTOM":
			return new Point((panel.getX() + panel.getWidth() / 2 ), panel.getY() + panel.getHeight());
		case "BOTTOMLEFT":
			return new Point(panel.getX(), panel.getY() + panel.getHeight());
		case "LEFT":
			return new Point(panel.getX(), panel.getY() + panel.getHeight() / 2 );
		}
		return new Point(0, 0);
	}
	
	public static void Resize()
	{
		l.setSize(f.getWidth() - 30, 15);
		l.setLocation(5, 5);
		
		a.setSize(f.getWidth() - 30, f.getHeight() - 90);
		a.setLocation(5, GetPosition("BOTTOMLEFT", l).y + 5);
		
		c.setSize(f.getWidth() - 100, 15);
		c.setLocation(5, GetPosition("BOTTOMLEFT", a).y + 5);
		
		b.setSize(a.getWidth() - c.getWidth() - 5, 15);
		b.setLocation(GetPosition("TOPRIGHT", c).x + 5, GetPosition("TOPRIGHT", c).y);

		f.repaint();
	}
	
	public static void loadCommands(String cmd)
	{
		DefaultCommands commands = new DefaultCommands();
		String[] params = cmd.split("\\ ");
		if(params[0].equalsIgnoreCase("help"))
			debug(commands.help());
		else if(params[0].equalsIgnoreCase("network"))
			debug(commands.network());
	}
	
	public static void debug(String msg)
	{
		a.append(msg + "\n");
	}
	public static int contains( String val, String[] array) {
		for(int i = 0; i < array.length; i++)
			if(array[i].equalsIgnoreCase(val))
				return i;
		return -1;
	}

}
