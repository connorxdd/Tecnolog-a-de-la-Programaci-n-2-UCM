package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;

public class ResetCommand extends NoParamsCommand{

	public ResetCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}
	
	public ResetCommand() {
		super("[R]eset","<Reset>","resets game");
	}
	
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}
	
	@Override
	public void execute(Game game, Controller controller) {
		Game juego = new Game(game.getLvl(), game.getSeed());
		controller.setGamePrinter(juego.getGamePrinter());
		controller.setGame(juego);
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		String part = commandWords[0].toUpperCase();
		if(commandWords.length==1 && (part.equals("R"))||part.equals("RESET")){
			return this;
		}
		return null;
	}

}
