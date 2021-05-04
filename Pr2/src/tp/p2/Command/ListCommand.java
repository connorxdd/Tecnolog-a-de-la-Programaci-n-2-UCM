package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;
import tp.p2.ControllerAndManager.PlantsFactory;

public class ListCommand extends NoParamsCommand {
	
	public ListCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}
	
	public ListCommand() {
		super("[L]ist","<List>","print the list of available plants.");
	}
	
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}

	@Override
	public void execute(Game game, Controller controller) {
		controller.setNoPrintGameState();
		System.out.print(PlantsFactory.listOfAvilablePlants());
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		if(commandWords.length == 1){
			String option = commandWords[0].toUpperCase();
			if(option.equals("L") || option.equals("LIST")){
				return this;
			}
			else return null;
		}
		return null;
	}

}
