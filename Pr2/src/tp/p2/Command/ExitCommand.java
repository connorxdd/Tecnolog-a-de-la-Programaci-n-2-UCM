package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;

public class ExitCommand extends NoParamsCommand{

	public ExitCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}
	
	public ExitCommand() {
		super("[E]xit","<exit>","terminate the program.");
	}
	
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}

	@Override
	public void execute(Game game, Controller controller) {
		game.setFinalizado(true);
		controller.setNoPrintGameState();
		System.out.println("Game Over");
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		if(commandWords.length == 1){
			String option = commandWords[0].toUpperCase();
			if(option.equals("EXIT") || option.equals("E")){
				return this;
			}
			else return null;
		}
		return null;
	}

}
