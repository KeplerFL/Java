package client;

import java.net.*;
import java.awt.Color;
import java.io.*;

public class Client {
private static DataOutputStream os;
private BufferedReader is;
private int porta;
private Socket socket = null; // crea un oggetto della classe SocketClient
private boolean isClientConnected; // crea una variabile di tipo Boolean per controllare lo stato di

public void start(String ipServer,int porta) throws IOException{
//Connessione della socket con il server
System.out.println("Tentativo di connessione con server: " + "[" + ipServer + "]" + " su porta: "+ "["+
porta +"]");
socket = new Socket (ipServer,porta);

//Stream di Byte da passare al Socket
os= new DataOutputStream (socket.getOutputStream());//in uscita
is= new BufferedReader(new InputStreamReader(socket.getInputStream()));//in entrata
isClientConnected = true;
Thread t = new Thread(new ParallelClient());
t.start();

}

public static void sendMessage(String strMsg)throws IOException{
os.writeBytes(strMsg + '\n');

}

public String receiveMessage()throws IOException{
return is.readLine();
}

public void close ()throws IOException{
System.out.println("Chiusura client e stream");
os.close();
is.close();
socket.close();
System.exit(0);
}

public class ParallelClient implements Runnable{
public void run() { //metodo run

String s;
while (isClientConnected)
// ciclo infinito su variabile di controllo isClientConnected
{try {
s=is.readLine();
System.out.println("ricevuto = " + s);
Main.chat.append(s + "\n");
if(Main.last != s)
{
for(int i =0; i<10; i++) //quando si riceve un messaggio la finestra trema!
{
Main.finestra.setLocation(Main.finestra.getLocationOnScreen().x + 5,
Main.finestra.getLocationOnScreen().y + 5);
Thread.sleep(50);
Main.finestra.setLocation(Main.finestra.getLocationOnScreen().x - 5,
Main.finestra.getLocationOnScreen().y - 5);
Thread.sleep(50);
}
}
}
catch (IOException e) {e.printStackTrace();} catch (InterruptedException e) {
e.printStackTrace();
}
}
}
}
}