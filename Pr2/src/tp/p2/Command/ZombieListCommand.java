package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;

public class ZombieListCommand extends Command {

	public ZombieListCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}
	
	public ZombieListCommand() {
		super("ZombieList: ","<zombieList>","print the list of zombies.");
	}
	
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}

	@Override
	public void execute(Game game, Controller controller) {
		controller.setNoPrintGameState();
		System.out.println("[Z]ombie comun: speed: 2 Harm: 1 Life: 5");
		System.out.println("[Z]ombie deportista: speed: 1 Harm: 1 Life: 2");
		System.out.print("[Z]ombie caracubo: speed: 4 Harm: 1 Life: 8");
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		if(commandWords.length == 1){
			String option = commandWords[0].toUpperCase();
			if(option.equals("ZOMBIELIST") || option.equals("Z")){
				return this;
			}
			else return null;
		}
		return null;
	}

}
