package tp.p2.Command;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;
import tp.p2.ControllerAndManager.PlantsFactory;
import tp.p2.Objects.Plant;


public class AddCommand extends Command{
	private Plant plant;
	private UpdateCommand update;
	int x;
	int y;
	public AddCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText, commandInfo, helpInfo);		
	}
	
	public AddCommand() {
		super("[A]dd","<add> <plant> <x> <y>","add flower.");
		this.update=new UpdateCommand();
	}
	
	public String helpText(){
		return " " + commandText + ": " + this.helpText;
	}
	
	@Override
	public void execute(Game game, Controller controller) {
		update.execute(game, controller);
		this.plant.setGame(game);
		this.plant.setX(x);
		this.plant.setY(y);
		game.añadir(this.plant, x, y);
		game.computerAction();
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		try {
			String option=inicial(commandWords[1]);
			if(condicionComando(option, commandWords, controller)){
				if(condicionJuego(commandWords, controller)){
					this.x = Integer.parseInt(commandWords[2]);
					this.y = Integer.parseInt(commandWords[3]);
					return this;
				}
				else{
					return null;
				}
			}
			else{
				return null;
			}
		}
		catch (Exception e){
			return null;
		}
	}
	
	private boolean condicionComando(String option, String[] commandWords, Controller controller ){
		boolean uno=!option.equals(null);
		boolean dos = (commandWords[0].toUpperCase().equals("A") || commandWords[0].toUpperCase().equals("ADD"));
		boolean tres = commandWords.length==4;
		boolean cuatro = controller.getGame().plantaEstaDentroTablero(Integer.parseInt(commandWords[2]),Integer.parseInt(commandWords[3]));
		return uno&&dos&&tres&&cuatro;
	}
	
	private boolean condicionJuego(String[] commandWords, Controller controller){
		boolean uno = !controller.getGame().hayAlguien(Integer.parseInt(commandWords[2]), Integer.parseInt(commandWords[3]));
		boolean dos = controller.getGame().getScmanager().getSolesDePartida() >= this.plant.getCoste();
		return uno&&dos;
	}
	
	private String inicial(String planta){
		if(planta.equals("S")||planta.equals("SUNFLOWER")){
			this.plant = PlantsFactory.getPlant(planta);
			return "S";
		}
		if(planta.equals("P") || planta.equals("PEASHOOTER")){
			this.plant = PlantsFactory.getPlant(planta);
			return "P";
		}
		if(planta.equals("C") || planta.equals("PETACEREZA")){
			this.plant = PlantsFactory.getPlant(planta);
			return "C";
		}
		if(planta.equals("N") || planta.equals("NUEZ")){
			this.plant = PlantsFactory.getPlant(planta);
			return "N";
		}
		return null;
	}

}
