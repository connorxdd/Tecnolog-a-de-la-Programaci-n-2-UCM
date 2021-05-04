package tp.p2.ControllerAndManager;

import tp.p2.ControllerAndManager.Controller;
import tp.p2.ControllerAndManager.Game;


public class PlantasVsZombies {
	
	static public void main(String []args){
		Level lvl = null;
		long seed;
		if(args.length>0){
			lvl = eligeNivel(args[0].toUpperCase());
			try {
			seed = Integer.parseInt(args[1]);
			}
			catch (Exception e) {
				System.out.println("Semilla mal introducida, se ha introducido la semilla 0 por defecto");
				seed=0;
			}
		}
		else{
			System.out.println("No se ha introducido ningun argumento. Nivel Easy y semilla 0 por defecto");
			seed=0;
			lvl=Level.EASY;
		}
		
		Game game = new Game(lvl, seed);
		Controller controlador = new Controller(args, game);
		controlador.run();
	
	}
		
	private static Level eligeNivel(String arg){
		Level lvl;
		switch(arg){ 
		
		case "EASY": {
			lvl = Level.EASY;
			break;
			}
		case "HARD":{
			lvl= Level.HARD;
			break;
			}
		case "INSANE": {
			lvl= Level.INSANE;
			break;
			}
		default:
			System.out.println("No se ha introducido un nivel correcto. Se ha puesto modo EASY por defecto");
			lvl= Level.EASY;
		}
		
		return lvl;
	}

}