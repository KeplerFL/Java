package line;

public class DefaultCommands {
	
	public String[] flevel = {
			"help",
			"network",
			"broadcast",
	};
	
	public String help()
	{
		return "Available Commands \n"
			+	"	help -- Show this message \n"
			+	"	network -- show network settings \n"
			+	"	broadcast -- broadcast message to all connected";
	}
	
	public String network()
	{
		return "ok";
	}

}
