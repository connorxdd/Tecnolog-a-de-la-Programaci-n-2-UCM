package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;

public abstract class Command {
		protected String helpText;
		protected String commandText;
		protected final String commandName;
		
		
		public Command(String commandText, String commandInfo, String helpInfo){
		this.commandText = commandText;
		helpText = helpInfo;
		String[] commandInfoWords = commandInfo.split("\\s+");
		commandName = commandInfoWords[0];
		}
		
		//Ejecuta el comando correspondiente.
		public abstract void execute(Game game, Controller controller);
		
		//Parsea la informacion de cada comando.
		public abstract Command parse(String[] commandWords, Controller controller);
		
		//Devuelve la ayuda del comando.
		public abstract String helpText();
}
