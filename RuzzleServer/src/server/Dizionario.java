package server;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Dizionario {

	private long l, dic;
	private RandomAccessFile diz;
	private String fileName = "out.txt";;
	private BufferedReader conf;
	
	public Dizionario()
	{
		try 
		{
			
		    File file = new File(fileName);
		    diz = new RandomAccessFile(file, "rw");
			
			conf =
					new BufferedReader(
						new FileReader("conf.gb"));
			
			l =  Integer.parseInt(conf.readLine());			
						
		} 
			catch (FileNotFoundException e){e.printStackTrace();} 
			catch (NumberFormatException e){e.printStackTrace();} 
			catch (IOException e){e.printStackTrace();}
	}
	
	public boolean DDizionario()
	{
		try
		{
			diz.close();
			conf.close();
		}
		catch (Exception e) {e.printStackTrace(); return false;}
		return true;
		
	}
	
	public boolean cerca(String s)
	{
		int top, last, middle;
		top = 0;
		last = (int) l;
		middle = (int) (l/2);
		if(s.length() == 16)
			s += "\n";
		else
			while(s.length() != 16)
				s += " ";
		try {
			int r = 4238;
			diz.seek(0);
			String si;
			while(top <= last)
			{
				middle = (top + last) /2;
				diz.seek(middle * 19);
				si = diz.readLine();
				r = si.compareToIgnoreCase(s);
				if(r == 0)	{break;}
				else if(r<0) 	{top = middle + 1;}
				else {last = middle - 1;}			
			}
			
			if(r == 0)
			{
				System.out.println();
				return true;
			}
			else 
				return false;
		} catch (IOException e) {e.printStackTrace();}
		return false;
	}
	
	
	public String crea() 
	{
		Random rand = new Random();
		String s = null;
		try 
		{
			diz.seek(rand.nextInt((int) l)*19);
			s = diz.readLine();
			s = s.trim();
			while(s.length()<=16)
			{
				diz.seek(rand.nextInt((int) l)*19);
				s += diz.readLine().trim();
			}
			s = s.substring(0, 16);
		} catch (IOException e) {e.printStackTrace();}
			return s;
	}
}