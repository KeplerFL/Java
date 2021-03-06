package text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	
	private String path;
	private File file = null;
	
	public FileManager(String path, File f)
	{
		try {
			
			this.path = path;
			if(f == null)
				file = new File(this.path);
			else
			{
				file = f;
				this.path = this.file.getPath();
			}
			
			if (!file.exists()) {
				file.createNewFile();		
			}
		} catch (IOException e)		{}
	}
	
	public boolean WriteFile(String content)
	{
		try {
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			System.out.println("Scrittura file completata: " + content);
			return true;
 
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("resource")
	public String ReadFile()
	{
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(this.path));
			
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			 
			while((line =bufferedReader.readLine())!=null) {
				stringBuffer.append(line).append("\n");
			}
			
			System.out.println("lettura completata");
			return stringBuffer.toString();
		} 
		catch (FileNotFoundException e)		{} 
		catch (IOException e)				{}
		
		return null;
	}
	
	public String getPath()
	{
		return file.getPath();
	}
}
