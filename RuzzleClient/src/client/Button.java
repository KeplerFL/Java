package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Button extends JButton implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	public boolean premuto = false;
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
		if(Main.pressed)
		{
			this.setBorder(BorderFactory.createMatteBorder(
	                2, 2, 2, 2, Color.red));
			if(!this.premuto)
			{
				int cx = e.getComponent().getX();
				int cy = e.getComponent().getY();

				cx = cx / 65;
				cy = cy / 65;
				if(Main.lastx == 125)
					Main.lastx = cx;
				if(Main.lasty == 125)
					Main.lasty = cy;
				if(Main.IsInRangeM(cx, cy))
				{
					Main.lastx = cx;
					Main.lasty = cy;
					Main.sfinale += this.getText();
					premuto = true;
					Main.l.setText(Main.sfinale.substring(4));
				}
				else
				{
					Main.pressed = false;
					Main.sfinale = null;
					Main.f.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					Main.lastx = cx;
					Main.lasty = cy;
					Main.reset();
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent ev) 
	{ 
		Main.pressed = true; 
		Main.sfinale += this.getText(); 
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
		Main.f.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		int cx = ev.getComponent().getX();
		int cy = ev.getComponent().getY();
		cx = cx / 65;
		cy = cy / 65;
		Main.lastx = cx;
		Main.lasty = cy;
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		Main.pressed = false; 
		Main.reset(); 
		Main.send();
		Main.f.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void premuto(){premuto=false;}
	public void pres(){premuto=true;}
}
