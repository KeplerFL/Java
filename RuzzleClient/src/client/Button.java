package client;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

public class Button extends JButton implements MouseMotionListener {

	boolean pressed = false;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//Main.Area.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void mouseMoved(MouseEvent e2) {
		if(this.pressed == false)
			{
				Main.ris += this.getText();
				System.out.println("mouse moved!" + this.getText());
				this.pressed = true;
			}
			
	}
	

}
