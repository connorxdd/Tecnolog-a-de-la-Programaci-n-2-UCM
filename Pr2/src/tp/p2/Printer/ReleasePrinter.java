package tp.p2.Printer;

import tp.p2.ControllerAndManager.Game;
import tp.p2.ControllerAndManager.SunManager;
import tp.p2.Objects.GameObject;
import tp.p2.Objects.Plant;
import tp.p2.Objects.Sun;
import tp.p2.Objects.Zombie;

public class ReleasePrinter extends BoardPrinter{
	
	
	//protected String [][] tablero;
	
	public ReleasePrinter(int fila, int columna, Game game, SunManager sunMan) {
		super(fila, columna, game, sunMan);
	}
	
	public void encodeGame(Game game) {
		GameObject auxiliar = null;
		GameObject solAuxiliar = null;
		for (int fila = 0; fila < game.getLimiteFilas(); fila++) {
			hacerRayas(1);
			System.out.println();
			for (int columna = 0; columna < game.getLimiteColumnas(); columna++) {
				System.out.print(vDelimiter);
				auxiliar = esPlanta(fila, columna);
				if(auxiliar != null){
					solAuxiliar = haySol(fila, columna);
					
					if(solAuxiliar!= null)
						System.out.print(auxiliar.dibujarElemento() + solAuxiliar.getNombre());
					else
						System.out.print(auxiliar.dibujarElemento() + " ");	
				}
				
				else{
					auxiliar = esZombie(fila, columna);
					solAuxiliar = haySol(fila, columna);
					if(auxiliar != null){
						if(solAuxiliar!= null)
							System.out.print(auxiliar.dibujarElemento() + solAuxiliar.getNombre());
						else
							System.out.print(auxiliar.dibujarElemento() + " ");	
					}
					else {
						if(solAuxiliar!= null)
							System.out.print("     " + solAuxiliar.getNombre());
						else
							System.out.print("      ");
					}
				}
			}
			System.out.println(vDelimiter);
		}
		hacerRayas(1);
		System.out.println();
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
	
	public void hacerRayas (int num){
		for(int i = 0; i <= cellSize*(cellSize + 1); i++){
			System.out.print(hDelimiter);
		}
	}

	public Sun haySol(int x, int y) {
		Sun solecito = null;
		if(!game.getScmanager().isPositionEmpty(x, y)) {
			solecito = new Sun(null, 5, "Sol suelto por el mapa");
		}
		return solecito;
	}

	@Override
	public void imprimirDatos() {
		System.out.println("Number of cycles: " + this.game.getCiclos());
		System.out.println("Sun coins: " + this.game.getScmanager().getSolesDePartida());
		System.out.println("Zombies remaining: " + this.game.getZombiesRestantes());
		System.out.println("Level: " + this.game.getLvl());
		System.out.println("Seed: " + this.game.getSeed());
		
	}
}
