package base;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Result {
	
	private static JFrame f;
	private static JTextArea a;
	private static JScrollPane c;
	
	public Result()
	{
		f = new JFrame("Query Output");
		f.setSize(600, 100);
		f.setLocation(100, 100);
		f.setVisible(false);
		f.setLayout(null);
		
		a = new JTextArea();
		a.setSize(f.getWidth()-30, f.getHeight()-60);
		a.setLocation(10, 10);
		a.setVisible(true);
		
		c = new JScrollPane(a);
		c.setVisible(true);
		c.setSize(f.getWidth()-30, f.getHeight()-60);
		c.setLocation(10, 10);
		c.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		c.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		c.setAutoscrolls(true);
		f.add(c);
	}
	
	public void Show(String s)
	{
		a.setText(s);
		f.setVisible(true);
		a.setEditable(false);
		repaint();
	}
	
	public void repaint()
	{
		f.setSize(f.getWidth(), a.getLineCount()*20 + 100);
		a.setSize(f.getWidth()-30, f.getHeight()-60);
		c.setSize(f.getWidth()-30, f.getHeight()-60);
		f.repaint();
	}
}
