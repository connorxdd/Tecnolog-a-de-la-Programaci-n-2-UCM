package tp.p2.ControllerAndManager;

import java.util.Scanner;

import tp.p2.Command.Command;
import tp.p2.Command.CommandParser;
import tp.p2.Printer.GamePrinter;

public class Controller {
	
	private static final String unknownCommandMsg ="Command invalid";
	private Game game;
	private Scanner scanner;
	private boolean noPrint = false;
	private boolean setNoPrintGameState=true;
	private GamePrinter gamePrinter;
	
	
	public void setGamePrinter(GamePrinter gamePrinter) {
		this.gamePrinter = gamePrinter;
	}

	public Controller(String[] args, Game game) {
		this.game = game;
		this.gamePrinter = game.getGamePrinter();
		this.scanner = new Scanner(System.in);
	}
	
	//Ejecuta el juego, y el comando que se le introduce. Si no es correcto se imprime un error.
	public void run(){
		while (!game.isFinalizado()) {
			if(this.setNoPrintGameState){
				printGame();
			}
			this.setNoPrintGameState=true;
			Command command = userCommand();
			if (command != null) {
			command.execute(game, this);
			}
			else {
			System.out.print(unknownCommandMsg);
			System.out.println();
			setNoPrintGameState();
			}
		}
	}
	public void setNoPrintGameState() {
		this.setNoPrintGameState=false;
		
	}
	
	public void printGame() {
		gamePrinter.imprimirDatos();
		if(!noPrint) {
			gamePrinter.encodeGame(game);
			}
		else{
			gamePrinter.encodeGame(game);
		}
		
	}
	
	public void setNoPrint(boolean noPrint){
		this.noPrint=noPrint;
	}

	public Game getGame() {
		return game;
	}
	
	public Command userCommand(){
		System.out.println();
		System.out.print("Command :>");
		String commandoActual = scanner.nextLine().toUpperCase().trim();
		String[] words = commandoActual.split("\\s+");
		return CommandParser.parseCommand(words, this);
	}
	
	public void setGame(Game game) {
		this.game = game;
	}

}
