package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;

public class HelpCommand extends NoParamsCommand {

	public HelpCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}
	
	public HelpCommand() {
		super("[H]elp","<Help>","print this help message.");
	}
	
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}
	
	@Override
	public void execute(Game game, Controller controller) {
		controller.setNoPrintGameState();
		System.out.println("The available commands are:");
		System.out.print(CommandParser.commandHelp());
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		if(commandWords.length == 1){
			String option = commandWords[0].toUpperCase();
			if(option.equals("H") || option.equals("HELP")){
				return this;
			}
			else return null;
		}
		return null;
	}

}
