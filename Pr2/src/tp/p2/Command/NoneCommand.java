package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;
	
public class NoneCommand extends NoParamsCommand{
	private UpdateCommand update;
	public NoneCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);
	}
	
	public NoneCommand() {
		super("[N]one","<none>","skips cycle");
		this.update=new UpdateCommand();
	}
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}

	@Override
	public void execute(Game game, Controller controller) {
		update.execute(game, controller);
		game.computerAction();
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		if(commandWords.length <= 1){
			String option = commandWords[0].toUpperCase();
			if(option.equals("N")||option.equals("NONE") || option.isEmpty()){
				return this;
			}
			else return null;
		}
		return null;
	}

}
