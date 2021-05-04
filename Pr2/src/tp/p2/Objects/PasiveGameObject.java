package tp.p2.Objects;

import tp.p2.ControllerAndManager.Game;

public abstract class PasiveGameObject extends GameObject{

	public PasiveGameObject(Game game, int maxCiclos, String comportamiento) {
		super(game, maxCiclos, comportamiento);
	}
}
