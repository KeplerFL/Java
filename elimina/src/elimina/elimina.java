package elimina;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class elimina {

	/**
	 * @param args
	 * @throws IOException 
	 */
	static char last = '�';
	static char[] v;
	static int i = 0;
	public static void main(String[] args) throws IOException {
		String s;
		BufferedReader reader =
			new BufferedReader(
				new FileReader("DIZ.txt"));
		
		PrintWriter writer =
				new PrintWriter(
					new BufferedWriter(
						new FileWriter("out.txt")));
		PrintWriter conf =
				new PrintWriter(
					new BufferedWriter(
						new FileWriter("conf.gb")));
		writer.write("");
		while( (s = reader.readLine()) != null )
			if(s.length() < 17 && s.length() > 1)
			{
				i++;
				s = s.toUpperCase();
				if(s.length() < 16)
				{
					while(s.length() < 16)
					{
						s.replace("'", "");
						s.replace("-", "");
						s += " ";
					}
				}
				s+= "\n";
				writer.println(s);

			}
		conf.println(i);
		conf.close();
		writer.close();
		reader.close();

	}

}
