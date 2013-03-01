package chatClient;

public class controllo {
	
	public boolean isConsonant(char c)
	{
		switch(c)
		{
		case 'b':
		case 'c':
		case 'd':
		case 'f':
		case 'g':
		case 'h':
		case 'l':
		case 'm':
		case 'n':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'v':
		case 'z': 
			return true;
		}
		return false;
	}

}
