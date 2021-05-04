package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;

public class CatchSunCommand extends Command{

	int x;
	int y;
	private UpdateCommand update;
	
	public CatchSunCommand() {
		super("[C]atch ", "catch x y", "catch a sun");
		this.update=new UpdateCommand();
	}

	@Override
	public void execute(Game game, Controller controller) {
		update.execute(game, controller);
		game.recogerSol(x, y);
		game.computerAction();		
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		try {
			if(condicionComando(Integer.parseInt(commandWords[1]), Integer.parseInt(commandWords[2]), commandWords, controller)) {
				this.x = Integer.parseInt(commandWords[1]);
				this.y = Integer.parseInt(commandWords[2]);
				return this;
			}
			else 
				return null;
		} 
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public String helpText() {
		return " " + commandText + ": " + this.helpText;
	}
	
	private boolean condicionComando(int x, int y, String[] commandWords, Controller controller){
		boolean uno=commandWords.length==3;
		boolean dos = controller.getGame().estaDentroTableroSoles(Integer.parseInt(commandWords[1]),Integer.parseInt(commandWords[2]));
		boolean tres = (commandWords[0].toUpperCase().equals("C") || commandWords[0].toUpperCase().equals("CATCH"));
		return uno&&dos&&tres;
	}

}
