package tp.p2.Printer;

import tp.p2.ControllerAndManager.Game;
import tp.p2.ControllerAndManager.SunManager;
import tp.p2.Objects.GameObject;
import tp.p2.Objects.Plant;
import tp.p2.Objects.Zombie;

public class DebugPrinter extends BoardPrinter{
	
	public DebugPrinter(int fila, int columna, Game game, SunManager sunMan) {
		super(fila, columna, game, sunMan);
	}

	@Override
	public void encodeGame(Game game) {
		GameObject auxiliar = null;
		hacerRayas(this.game.getContadorPlantas() + this.game.getContadorZombies());
		boolean mostrar=false;
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				auxiliar = esPlanta(i, j);
				if(auxiliar != null) {
					mostrar=true;
					int max=auxiliar.getMaxCiclos();
					int cic=auxiliar.getCiclos();
					int resultado=0;
					if(cic>0){
						resultado =( cic % max);
					}
					System.out.print("|" + auxiliar.getNombre() + "[l:" + auxiliar.getResistencia() + ",x:"
							+ auxiliar.getX() + ",y:" + auxiliar.getY() + ",t:" + resultado + "]");				
				}
				
				auxiliar = esZombie(i, j);
				if(auxiliar != null) {
					mostrar=true;
					int max=auxiliar.getMaxCiclos();
					int cic=auxiliar.getCiclos();
					int resultado=0;
					if(cic>0){
						resultado =( cic % max);
					}
					System.out.print("|" + auxiliar.getNombre() + "[l:" + auxiliar.getResistencia() + ",x:"
							+ auxiliar.getX() + ",y:" + auxiliar.getY() + ",t:" + resultado  + "]");
				
				}
			}
		}
		if(mostrar){
			System.out.println("|");
			hacerRayas(this.game.getContadorPlantas() + this.game.getContadorZombies());
			System.out.println();
		}
	
	}
	
	
	public Plant esPlanta(int x, int y) {
		Plant resultado = null;
		int contador = 0;
		while(contador < this.game.getContadorPlantas() && resultado == null) {
			if(this.game.getListPlants()[contador].getX() == x && this.game.getListPlants()[contador].getY() == y) {
				resultado = this.game.getListPlants()[contador];
			}
			contador++;
		}
		return resultado;
	}
	
	
	public Zombie esZombie(int x, int y) {
		Zombie resultado = null;
		int contador = 0;
		while(contador < this.game.getContadorZombies() && resultado == null) {
			if(this.game.getListZombie()[contador].getX() == x && this.game.getListZombie()[contador].getY() == y) {
				resultado = this.game.getListZombie()[contador];
			}
			contador++;
		}
		return resultado;
	}
	public void hacerRayas(int num) {
		if(this.game.getContadorPlantas()+this.game.getContadorZombies()>0){
			for(int i = 0; i < num; i++){
				System.out.print("------------------");
			}
			for(int i=0;i<=this.game.getContadorPlantas()+this.game.getContadorZombies();i++){
				System.out.print("-");
			}
			System.out.println();
		}
	}

	@Override
	public void toRelease(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toDebug(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void imprimirDatos() {
		System.out.println("Number of cycles: " + this.game.getCiclos());
		System.out.println("Sun coins: " + this.game.getScmanager().getSolesDePartida());
		System.out.println("Zombies remaining: " + game.getZombiesRestantes());
		System.out.println("Level: " + game.getLvl());
		System.out.println("Seed: " + game.getSeed());
		
	}
		
}
