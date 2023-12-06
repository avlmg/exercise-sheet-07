package de.unistuttgart.iste.sqa.pse.sheet07.presence;

import de.hamstersimulator.objectsfirst.external.simple.game.SimpleHamsterGame;

/**
 * This class is used to solve some easy tasks.
 */
public class SpringCleaningHamsterGame extends SimpleHamsterGame {

	/**
	 * Creates a new ArtemisPauleHamsterGame.<br>
	 * Do not modify!
	 */
	public SpringCleaningHamsterGame() {
		this.loadTerritoryFromResourceFile("/territories/ArtemisPaule.ter");
		this.displayInNewGameWindow();
		game.startGame();
	}

	@Override
	protected void run() {


		for (int i = 1; i <= 5; i++) {
			if (i % 2 == 1) {
				paule.pickGrain();
			}
			paule.move();
		}
		paule.turnLeft();
		paule.turnLeft();
		while (paule.frontIsClear()) {
			paule.move();
		}
	}
}

