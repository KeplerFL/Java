package text;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

public class Window {

	public static JFrame f;
	public static JMenuBar mb;
	public static JTextArea testo;
	public static JScrollPane c;
	private static String path;
	private static FileManager file;
	
	public static String[] itemsString = {"New", "Open", "STOP", "Close", "Save"};
	public static JMenuItem[] items = new JMenuItem[itemsString.length];
	
	public static String[] editString = {"Copy", "Paste"};
	public static JMenuItem[] edits = new JMenuItem[editString.length];
	
	public static JLabel elabel;
	public static JButton nbutton;
	public static JButton obutton;
	
	protected static String defTitle = "Mattia Graziani Text Editor";
	
	public static void main(String[] args) {
		f = new JFrame();
		f.setSize(500, 500);
		f.setLocation(100, 100);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);
		f.setTitle(defTitle);
				
		nbutton = new JButton("Nuovo File");
		nbutton.setSize(150, 40);
		nbutton.setLocation(10, 40);
		nbutton.setVisible(true);
		f.add(nbutton);
		nbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newfile();
			}
		});
		
		obutton = new JButton("Apri File");
		obutton.setSize(150, 40);
		obutton.setLocation(170, 40);
		obutton.setVisible(true);
		f.add(obutton);
		obutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		
		elabel = new JLabel("Nessunn File aperto!");
		elabel.setVisible(true);
		elabel.setSize(f.getWidth(), 15);
		elabel.setLocation(5, 20);
		f.add(elabel);
		
		mb = new JMenuBar();
		JMenu filemenu = new JMenu("File");
		filemenu.add(new JSeparator());
		
		for(int i = 0; i < itemsString.length; i++)	{
			if(itemsString[i].equals("STOP"))
				filemenu.add(new JSeparator());
			else {
				items[i] = new JMenuItem(itemsString[i]);
				filemenu.add(items[i]);
			}
		}
		
		JMenu editmenu = new JMenu("Edit");
		for(int i = 0; i < editString.length; i++)	{
			if(editString[i].equals("STOP"))
				editmenu.add(new JSeparator());
			else {
				edits[i] = new JMenuItem(editString[i]);
				editmenu.add(edits[i]);
			}
		}
		
		testo = new JTextArea();
		testo.setVisible(true);
		testo.setSize(f.getWidth(), f.getHeight()-60);
		testo.setLocation(0, 0);
		
		DefaultCaret caret = (DefaultCaret)testo.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		c = new JScrollPane(testo);
		c.setVisible(true);
		c.setSize(f.getWidth() - 18, f.getHeight()-60);
		c.setLocation(1, 1);
		c.setBackground(Color.black);
		c.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		c.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		c.setAutoscrolls(true);
		c.setVisible(false);
		f.add(c);
		
		f.setJMenuBar(mb);
		mb.add(filemenu);
		mb.add(editmenu);
		
		edits[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(testo.getSelectedText());
				clipboard.setContents(strSel, null);
			}
		});
		
		edits[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				try {
					testo.append((String) clipboard.getData(DataFlavor.stringFlavor));
				}
				catch (UnsupportedFlavorException e1) {}
				catch (IOException e1) {}
			}
		});
		
		items[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				open();
			}
		});
		
		items[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newfile();
			}
		});
		
		items[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(file != null)
					file.WriteFile(testo.getText());
				
			}
		});
		
		items[3].setEnabled(false);
		items[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				file = null;
				c.setVisible(false);
				obutton.setVisible(true);
				nbutton.setVisible(true);
				elabel.setVisible(true);
				f.setTitle(defTitle);
				items[3].setEnabled(false);
				items[0].setEnabled(true);
				items[1].setEnabled(true);
			}
		});
		
		f.revalidate();
		f.repaint();
	}
	
	public static void open()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = new FileManager(null, fileChooser.getSelectedFile());
			c.setVisible(true);
			nbutton.setVisible(false);
			obutton.setVisible(false);
			elabel.setVisible(false);
			testo.setText(file.ReadFile());
			f.setTitle(file.getPath());
			items[3].setEnabled(true);
			items[0].setEnabled(false);
			items[1].setEnabled(false);
		}
	}
	
	public static void newfile()
	{
		String s = JOptionPane.showInputDialog(null, "Inserisci il nome del file: ", "File Name", JOptionPane.QUESTION_MESSAGE);
		if(s == null)
			return;
		
		Window.path = s + ".txt";
		file = new FileManager(Window.path, null);
		file.WriteFile("");
		c.setVisible(true);
		nbutton.setVisible(false);
		obutton.setVisible(false);
		elabel.setVisible(false);
		testo.setText("");
		f.setTitle(file.getPath());
		items[3].setEnabled(true);
		items[0].setEnabled(false);
		items[1].setEnabled(false);
	}

}
