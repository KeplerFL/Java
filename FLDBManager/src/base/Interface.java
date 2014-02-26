package base;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interface {

	static JFrame f;
	static JLabel[] l;
	static JCheckBox aot, d;
	static JButton b, c;
	static int rows;
	static JCheckBox columns[];
	static String[] TEXTS = {"entry", "item", "ChanceOrQuestChance", "groupid", "mincountOrRef", "maxcount"};
	static int FIELDS = TEXTS.length;
	static boolean delete = false;
	static Result r;
	
	static String Output;
	
	public static ArrayList<JTextField[]> ta;
	
	public static void main(String[] args) {
		// creazione finestra principale
		f = new JFrame("ForgottenLands DB Manager");
		f.setVisible(true);
		f.setSize(80*FIELDS + 185, 360);
		f.setLocation(100, 100);
		f.setLayout(null);
		f.getContentPane().setBackground(Color.GRAY);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.black);
		
		aot = new JCheckBox("Always on Top");
		aot.setVisible(true);
		aot.setLocation(80*FIELDS + 60, 5);
		aot.setSize(500, 15);
		aot.setBackground(Color.GRAY);
		f.add(aot);
		
		d = new JCheckBox("Delete");
		d.setToolTipText("Insert Delete query before insert. WARNING: This will delete every loot for the specified npc");
		d.setVisible(true);
		d.setLocation(80*FIELDS + 60, 25);
		d.setSize(500, 15);
		d.setBackground(Color.GRAY);
		f.add(d);
		
		b = new JButton("Aggiungi una riga");
		b.setSize(80, 20);
		b.setLocation(80*FIELDS + 60, 60);
		b.setVisible(true);
		f.add(b);
		
		c = new JButton("Genera");
		c.setSize(80, 50);
		c.setLocation(80*FIELDS + 60, 90);
		c.setVisible(true);
		f.add(c);
		
		r = new Result();
		
		c.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 			
			{
				Output = Database.WriteDelete() + Database.WriteInsert();
				r.Show(Output);
			}
		});
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 			{createRow();}
		});
		
		aot.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object source = e.getItemSelectable();
				if (source == aot) 								f.setAlwaysOnTop(true);
				if(e.getStateChange() == ItemEvent.DESELECTED)	f.setAlwaysOnTop(false);
			}
		});
		
		d.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object source = e.getItemSelectable();
				if (source == d) 								delete = true;
				if(e.getStateChange() == ItemEvent.DESELECTED)	delete = false;
			}
		});
		
		rows = 0;
		ta =new ArrayList<JTextField[]>();
		l = new JLabel[FIELDS];
		createRow();
		
		for(int i = 0; i < FIELDS; i++)
		{
			l[i] = new JLabel(TEXTS[i]);
			l[i].setLocation((i*80 + 10 * i + 10), 5);
			l[i].setSize(80, 20);
			l[i].setVisible(true);
			f.add(l[i]);
		}
		
		columns = new JCheckBox[FIELDS];
		for(int i = 0; i < FIELDS; i++)
		{
			columns[i] = new JCheckBox();
			columns[i].setToolTipText("Insert Delete query before insert. WARNING: This will delete every loot for the specified npc");
			columns[i].setVisible(true);
			columns[i].setLocation((i*80 + 10 * i + 10), 25);
			columns[i].setSize(20, 15);
			columns[i].setSelected(true);
			if(i >= 3)
				columns[i].setSelected(false);
			else
				columns[i].setEnabled(false);
			columns[i].setBackground(Color.GRAY);
			f.add(columns[i]);
		}
		f.repaint();
	}

	private static void createRow()
	{
		int y = 40;
		JTextField[] temp = new JTextField[FIELDS];
		for(int i = 0; i < FIELDS; i++)
		{
			temp[i] = new JTextField();
			temp[i].setLocation((i*80 + 10 * i + 10), (rows * 30 + y));
			temp[i].setSize(80, 20);
			temp[i].setVisible(true);
			f.add(temp[i]);
			ta.add(temp);
		}
		
		rows++;
		if(rows == 9)
			b.setEnabled(false);
		
		f.repaint();
	}
}
