package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

public class Main {
public static Client client=null;
static JLabel des;
static JTextField input;
static JTextArea chat;
static JButton chiudi;
static JButton file;
private static JScrollPane sbrText;
static JCheckBox pp;
static JFrame finestra;
static String ip = "localhost";
static String HOST = "localhost";
static String nick = "mattia";
static String last ="";

public static void main(String[] args){
finestra = new JFrame();
des = new JLabel("Chat: ");
input = new JTextField(/*"Aspetta che il tuo amico risponda!"*/);
chat = new JTextArea();
chiudi = new JButton("Chiudi");
pp = new JCheckBox("Sempre in primo piano");

finestra.setVisible(true);
finestra.setSize(300, 420);
finestra.setLocation(100, 100);
finestra.setLayout(null);
finestra.setResizable(false); //impedisco che la finestra venga ridimensionata
finestra.setTitle("Chat Client Graziani");

finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quando viene chiusa la finestraprincipale il programma deve terminarsi!

des.setVisible(true);
des.setSize(150, 20);
des.setLocation(15, 20);
finestra.add(des);

sbrText = new JScrollPane(chat); //scrollpane
sbrText.setVisible(true);
sbrText.setSize(250, 250);
sbrText.setLocation(15, 40);
sbrText.setBackground(Color.white);
sbrText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
sbrText.setAutoscrolls(true);

chat.setVisible(true);
chat.setSize(232, 250);
chat.setLocation(0, 0);
chat.setEditable(false);
chat.setWrapStyleWord(true);
chat.setLineWrap(true);
DefaultCaret caret = (DefaultCaret)chat.getCaret(); //impostazioni di autoscroll
caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

finestra.add(sbrText);

input.setVisible(true);
input.setSize(250, 30);
input.setLocation(15, 300);
finestra.add(input);

chiudi.setVisible(true);
chiudi.setSize(200, 30);
chiudi.setLocation(45, 335);
finestra.add(chiudi);

pp.setVisible(true);
pp.setSize(200, 20);
pp.setLocation(115, 20);
pp.setToolTipText("Set the chat frame above every other frame.");
finestra.add(pp);

String h = JOptionPane.showInputDialog(null, "Inserisci l'indirizzo ip del tuo compagno: ", "IP", 1);
nick = JOptionPane.showInputDialog(null, "Inserisci il nickname: ", "Nickname", 3);
if(h != null)
HOST = h;

pp.addItemListener(new ItemListener() {

@Override
public void itemStateChanged(ItemEvent e) {
Object source = e.getItemSelectable();

if (source == pp) {
finestra.setAlwaysOnTop(true);
}
if(e.getStateChange() == ItemEvent.DESELECTED)
finestra.setAlwaysOnTop(false);

}

});

input.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {

chat.setForeground(Color.blue);
if(sbrText.isMaximumSizeSet()== true)
{
System.out.println("arrivato alla fine");
sbrText.setPreferredSize(new
Dimension(sbrText.getPreferredSize().width, sbrText.getPreferredSize().height+150));
}
try {
last=nick + ":" + input.getText();
Client.sendMessage(nick + ":" + input.getText());
} catch (IOException e1) {
chat.append("ERROR IN SENDING MESSAGE!");
}
input.setText(null);
}
});

chiudi.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {
System.exit(1);
}

});

client= new Client();
try {
client.start(HOST, 7000);
}
catch (IOException e)
{};

}

}