package base;

import java.net.Socket;
import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Database {
	
	private static String BASE_DELETE = "DELETE FROM creature_loot_template WHERE entry IN (";
	private static String BASE_INSERT = "INSERT INTO creature_loot_template (";
	private static boolean edited_d	 = false;
	private static int FIELDS = Interface.FIELDS;
	
	public static String WriteInsert()
	{
		String ret = "";
		
		ret += BASE_INSERT;
		
		for(int k = 0; k < Interface.rows; k++)
		{
			JTextField[] t = Interface.ta.get(k*FIELDS);
			
			if(k == 0)
			{
				for(int i = 0; i< FIELDS; i++)
					if(Interface.columns[i].isSelected())
					{
						if(i != 0) ret += ", ";
						ret += Interface.TEXTS[i];
					}
				ret += ") VALUES \r\n";
			}
			
			if(k != 0)
				ret += ", \r\n";
			ret += "(";
			for(int i = 0; i< FIELDS; i++)
				if(Interface.columns[i].isSelected())
				{
					if(i != 0) ret += ", ";
					ret += t[i].getText();
				}
			ret += ")";
		}

		ret += ";";
	
		return ret;
	}
	
	private static boolean existInArray(int[] array, int val)
	{
		for(int i = 0; i < array.length; i++)
			if(array[i] == val)
				return true;
		return false;
	}
	public static String WriteDelete()
	{
		String ret = "";
		int[] ids = new int[Interface.rows];
		
		if(Interface.delete == true)
		{
			ret += BASE_DELETE;
			for(int k = 0; k < Interface.rows; k++)
			{
				System.out.println(k);
				JTextField[] t = Interface.ta.get(k*FIELDS);
				int id = Integer.parseInt(t[0].getText());
				if(existInArray(ids, id) == false)
				{
					if(k != 0)
						ret += ", ";
					ret += id;
				}
			}

			ret += ");\r\n";
		}
		return ret;
	}
}
