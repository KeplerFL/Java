package chatClient;

import javax.swing.JButton;

public class bottone extends JButton {
	
	int num;
	int molt;
	@SuppressWarnings("static-access")
	public void setText(char c) {
		String s = null;
		s.valueOf(c);
		super.setText(s);
		
	}

}
