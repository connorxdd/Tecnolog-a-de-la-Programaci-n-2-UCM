package tp.p2.ControllerAndManager;

import java.util.Random;

import tp.p2.Objects.GameObject;
import tp.p2.Objects.Plant;
import tp.p2.Objects.Sun;
import tp.p2.Objects.Zombie;
import tp.p2.Printer.GamePrinter;
import tp.p2.Printer.ReleasePrinter;

public class Game {
	private int ciclos;
	private Random rand;
	public static final int FILAS = 4;
	public static final int COLUMNAS = 8;
	private boolean finalizado;
	private GamePrinter gamePrinter;
	public void setGamePrinter(GamePrinter gamePrinter) {
		this.gamePrinter = gamePrinter;
	}

	private long seed;
	private SunManager smanager;
	private int contadorPlanta;
	private int contadorZombie;
	private ZombieManager zmManager;
	private Level lvl;
	private Zombie []listZombies;
	private Plant []listPlantas;
//-----------------------------------------------------------------
	
	public Level getLvl() {
		return lvl;
	}

	//Constructor
	public Game(Level lvl, long semilla) {
		this.ciclos = 0;
		this.rand = new Random(semilla);
		this.finalizado = false;
		this.contadorPlanta = 0;
		this.contadorZombie = 0;
		this.listPlantas = new Plant[FILAS * COLUMNAS - 1];
		this.listZombies = new Zombie[FILAS * COLUMNAS - 1];
		this.seed=semilla;
		this.smanager= new SunManager(this);
		this.gamePrinter = new ReleasePrinter(FILAS, COLUMNAS, this, this.smanager);
		this.lvl = lvl;
		switch (lvl) {
		case EASY:
			this.zmManager = new ZombieManager(3, this.rand);
			
			break;
		case HARD:
			this.zmManager = new ZombieManager(5, this.rand);
			
			break;
		
		case INSANE: 
			this.zmManager = new ZombieManager(10, this.rand);
			
			break;
		}
	}

	//---------------------------------------------- 
	
	
	//Funcion que devuelve true si los numeros introducidos estan en el rango del tablero de las plantas.
	public boolean plantaEstaDentroTablero(int x, int y){
		if(x<0||x>=FILAS || y<0 || y>=COLUMNAS-1){
			return false;
		}
		return true;
	}
	
	//Funcion que devuelve true si los numeros introducidos estan en el rango del tablero.
	public boolean estaDentroTableroSoles(int x, int y){
		if(x<0||x>=FILAS || y<0 || y>=COLUMNAS){
			return false;
		}
		return true;
	}
	
	
	//Funcion donde se actualiza el teclado y las acciones de las planta y zombies.
	public void update() {
		this.ciclos++;		
		actualizarSolesPlantas();
		if(this.ciclos % 5 == 0) {
			añadirSol();
		}
		ataquePlantas();
		moverZombies();
		ataqueZombie();		
		this.contadorPlanta -= eliminarMuertos(listPlantas, contadorPlanta);
		this.contadorZombie -= eliminarMuertos(listZombies, contadorZombie);
		gananPlantas();
		gananZombies();
	}
	
	//Elimina los gameobject de una lista si estan muertos
	public int eliminarMuertos(GameObject []array, int numero){
		int i=0;
		int contador=numero;
		while(i<contador){
			if(array[i].getResistencia() <= 0){
				for(int j=i;j<contador-1;j++){
					array[j] = array[j+1];
					array[j+1] = null;
				}
				i--;
				contador--;
			}
			i++;
		}
		return numero-contador;
	}
	
	//Se recorre el tablero realizando los ataques de los lanzaguisantes a los zombies.
	private void ataquePlantas(){
		for(int i = 0; i < this.contadorPlanta; i++){
			if(!listPlantas[i].getNombre().equals("S")){
				//Ataque petacereza.
				if(listPlantas[i].getNombre().equals("C")){
					ataquePetacereza(i);
				}
				//Ataque peashooter.
				if(listPlantas[i].getNombre().equals("P")){
					ataquePeashooter(i);
				}
			}
		}
	}
	
	//Ataque Petacereza
	private void ataquePetacereza(int i){
		if((listPlantas[i].getCiclos() % listPlantas[i].getMaxCiclos()) == 0){
			
			for(int j = 0; j < this.contadorZombie; j++){
				if(listPlantas[i].estaEnRadio(listZombies[j].getX(), listZombies[j].getY())){
					listZombies[j].setResistencia(listZombies[j].getResistencia() - listPlantas[i].getDannio());
				}
			}
			listPlantas[i].setResistencia(listPlantas[i].getResistencia());
		}
	}
	
	//Ataque Peashooter
	private void ataquePeashooter(int i){
		boolean encontrado = false;
		int contador = 0;
		
		while(contador < this.contadorZombie && !encontrado) {
			if(listPlantas[i].estaEnRadio(listZombies[contador].getX(), listZombies[contador].getY())){
				listZombies[contador].setResistencia(listZombies[contador].getResistencia()- listPlantas[i].getDannio());
				encontrado = true;
			}
			contador++;
		}
	}
	
	//Se recorre el tablero comprobando si los zombies atacan a las plantas.
	private void ataqueZombie(){
		for(int i = 0; i < this.contadorZombie; i++){
			for(int j = 0; j < this.contadorPlanta; j++){
				if(listZombies[i].getX() == listPlantas[j].getX() && 
						listZombies[i].getY()-1 == listPlantas[j].getY()){
					listPlantas[j].setResistencia(1);
				}
			}
		}
	}
	
	//Accion de mover zombies del tablero
	public void moverZombies(){
		for(int i = 0; i < this.contadorZombie; i++){
			if(!hayAlguien(listZombies[i].getX(), listZombies[i].getY()-1)){
				if(listZombies[i].update()){
				listZombies[i].setY(listZombies[i].getY()-1);
				}
			}
		}
	}
	
	public int getCiclos() {
		return ciclos;
	}
	
	public int getRestantes() {
		return this.zmManager.getRestantes();
	}

	//Funcion que maneja la creacion de los zombies dentro del tablero.
	public void computerAction(){
		int casilla;
		double fre = 0;
		switch(lvl) {
		case EASY: fre=0.1;break;
		case HARD:fre=0.2;break;
		case INSANE: fre=0.3;break;
		}
		casilla = rand.nextInt(FILAS);
		if(!hayAlguien(casilla, COLUMNAS-1)){
			if(zmManager.isZombieAdded(fre,seed)){
				zmManager.setRestantes();
				this.listZombies[this.contadorZombie] = zmManager.tipoDeZombieAdded(this);
				this.listZombies[this.contadorZombie].setX(casilla);
				this.listZombies[this.contadorZombie].setY(COLUMNAS-1);
				this.contadorZombie++;
			}
		}	
	}
	
	//Funcion que anniade la planta con las coordenadas al tablero.
	public void añadir(Plant plant, int x, int y) {
		this.listPlantas[this.contadorPlanta] = plant;
		this.contadorPlanta++;
		this.smanager.cobrarPlanta(plant.getCoste());
		
	}
	
	public int getLimiteFilas() {
		return FILAS;
	}

	public int getLimiteColumnas() {
		return COLUMNAS;
	}
	
	public boolean isFinalizado() {
		return finalizado;
	}
	
	//Funcion que devuelve true si las plantas han ganado y no queda ningun zombie por sacar al campo.
	public void gananPlantas() {
		if (!zmManager.quedanZombies() && this.contadorZombie == 0) {
			this.finalizado = true;
			this.gamePrinter.encodeGame(this);
			System.out.println("Game Over");
			System.out.println("Player Wins!");
		}
	}
	
	//Funcion que devuelve true si algun zombie ha llegado a la cualquier columna 0 del tablero.
	public void gananZombies() {
		for(int i = 0; i < this.contadorZombie; i++) {
			if(this.listZombies[i].getY() == 0) {
				this.finalizado = true;
				this.gamePrinter.encodeGame(this);
				System.out.println("Game Over");
				System.out.println("Zombies win!");
			}
		}
	}

	public void setFinalizado(boolean b) {
		this.finalizado = b;
		
	}
	
	//Saca los soles de las plantas que le toque echar soles
	public void actualizarSolesPlantas() {
		Sun nextSun = null;
		for(int i = 0; i < this.contadorPlanta; i++) {
			if(this.listPlantas[i].update() && this.listPlantas[i].getNombre().equals("S") && smanager.isPositionEmpty(this.listPlantas[i].getX(), this.listPlantas[i].getY())) {
				nextSun = new Sun(this, 5, "Sol suelto por el mapa");
				nextSun.establecerPos(this.listPlantas[i].getX(), this.listPlantas[i].getY());
				smanager.addSun(nextSun, nextSun.getX(), nextSun.getY());
			}
		}
	}

	public long getSeed() {
		return this.seed;
	}
	
	public SunManager getScmanager() {
		return smanager;
	}

	public Plant[] getListPlants() {
		return this.listPlantas;
	}
	
	public Zombie[] getListZombie() {
		return this.listZombies;
	}
	
	public GamePrinter getGamePrinter() {
		return this.gamePrinter;
	}

	public int getContadorPlantas() {
		return this.contadorPlanta;
	}
	public int getContadorZombies() {
		return this.contadorZombie;
	}
	
	public int getZombiesRestantes(){
		return zmManager.getRestantes();
	}
	
	//Añade sol
	public void añadirSol() {
		int x, y;
		boolean añadido = false;
		Sun solActual = new Sun(this, 5, "Sol suelto por el mapa");
		while(!añadido) {
		x = rand.nextInt(4);
		y = rand.nextInt(8);
			if(smanager.isPositionEmpty(x, y)) {
				solActual.establecerPos(x, y);
				smanager.addSun(solActual, x, y);
				añadido = true;
			}
		}
	}
	public void recogerSol(int x, int y) {
		smanager.recogerSol(x, y);
	}
	//Nos indica si en esa posicion hay un objeto o esta vacio
	public boolean hayAlguien(int x, int y){
		boolean ok1=false;
		boolean ok2=false;
		for (int i = 0; i < this.contadorPlanta; i++) {
			if(this.listPlantas[i].getX()==x && this.listPlantas[i].getY()==y){
				ok1=true;
				return true;
			}
		}
		for (int i = 0; i < this.contadorZombie; i++){
			if(this.listZombies[i].getX()==x && this.listZombies[i].getY()==y){
				ok2=true;
				return true;
			}
		}
		return ok1&&ok2;
	}
}
