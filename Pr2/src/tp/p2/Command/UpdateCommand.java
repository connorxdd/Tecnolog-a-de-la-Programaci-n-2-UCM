package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;

public class UpdateCommand extends NoParamsCommand{

	public UpdateCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}
	
	public UpdateCommand() {
		super("Update","","Update the game");
	}
	
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}

	@Override
	public void execute(Game game, Controller controller) {
		game.update();
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		return null;
	}

}
