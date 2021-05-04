package tp.p2.Command;


import tp.p2.Command.AddCommand;
import tp.p2.Command.ExitCommand;
import tp.p2.Command.HelpCommand;
import tp.p2.Command.ListCommand;
import tp.p2.Command.ResetCommand;

import tp.p2.ControllerAndManager.Controller;

public class CommandParser{

	private static Command [] availableCommands = {
		new ExitCommand(),
		new ResetCommand(),
		new HelpCommand(),
		new NoneCommand(),
		new ListCommand(),
		new AddCommand(),
		new CatchSunCommand(),
		new PrintModeCommand(),
		new ZombieListCommand()
	};

	public CommandParser() {}
	
	public static Command parseCommand(String[] words, Controller controller) {
		Command orden = null;
		for (Command op: availableCommands){
			orden = op.parse(words,controller);
			if (orden != null) return orden;
		}
		return null;
	}
	
	public static String commandHelp(){
		String ayuda="";
		for (Command op: availableCommands){
			ayuda += op.helpText()+"\n";
		}
		return ayuda;
	}
}
