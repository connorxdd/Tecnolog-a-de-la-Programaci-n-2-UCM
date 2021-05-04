package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;
import tp.p2.Printer.DebugPrinter;
import tp.p2.Printer.GamePrinter;
import tp.p2.Printer.ReleasePrinter;

public class PrintModeCommand extends Command {
	private boolean noPrint;

	public PrintModeCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
		noPrint=false;
	}
	
	public PrintModeCommand() {
		super("[P]rintMode","<PrintMode>","change print mode [Release|Debug]");
	}
	
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}
	
	@Override
	public void execute(Game game, Controller controller) {
		controller.setNoPrint(noPrint);
		if(!noPrint) {
			GamePrinter gamePrinter=new ReleasePrinter(Game.FILAS, Game.COLUMNAS, game, game.getScmanager());
			controller.setGamePrinter(gamePrinter);
			game.setGamePrinter(gamePrinter);
		}
		else {
			GamePrinter gamePrinter=new DebugPrinter(Game.FILAS, Game.COLUMNAS, game, game.getScmanager());
			controller.setGamePrinter(gamePrinter);
			game.setGamePrinter(gamePrinter);
		}
	}
	
	public Command parse(String[] commandWords, Controller controller){
		if(commandWords.length == 2){
			String option = commandWords[0].toUpperCase();
			String option2 = commandWords[1].toUpperCase();
			if(condicion(option, option2)){
				if(option2.equals("DEBUG") || option2.equals("D")){
					noPrint=true;
				}
				else{
					noPrint=false;
				}
				return this;
			}
			else return null;
		}
		return null;
	}
	
	private boolean condicion(String option, String option2){
		boolean uno = (option.equals("P") || option.equals("PRINTMODE"));
		boolean dos = (option2.equals("RELEASE") || option2.equals("R") || option2.equals("DEBUG") ||  option2.equals("D"));
		return uno && dos;
	}

}
