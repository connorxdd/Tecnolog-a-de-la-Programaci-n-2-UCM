package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;

public abstract class NoParamsCommand extends Command{

	public NoParamsCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}

	@Override
	public abstract void execute(Game game, Controller controller);

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		if (commandWords[0].equalsIgnoreCase(this.commandName) && commandWords.length == 1) {
			return this;
		} else {
			return null;
		}
	}
	
}
