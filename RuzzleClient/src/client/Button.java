package client;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

public class Button extends JButton implements MouseMotionListener {

	boolean pressed = false;
	int x;
	int y;
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e2) {
		if(Main.press)
			if(this.pressed == false)
				{
				//if(Main.IsInRangeM(x, x))
				int cx = e2.getComponent().getX();
				int cy = e2.getComponent().getY();
				
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
					Main.ris += this.getText();
					System.out.println("mouse moved!" + this.getText());
					this.pressed = true;
					}
				else
				{
					Main.press = false;
					Main.ris = "";
					Main.Area.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					Main.lastx = cx;
					Main.lasty = cy;
				}
						
				}
			
	}
	

}
