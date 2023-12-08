package de.unistuttgart.iste.sqa.pse.sheet07.homework.painter;

import de.hamstersimulator.objectsfirst.datatypes.Direction;
import de.hamstersimulator.objectsfirst.datatypes.Location;
import de.hamstersimulator.objectsfirst.datatypes.LocationVector;
import de.hamstersimulator.objectsfirst.external.simple.game.SimpleHamsterGame;

/**
 * Describe the purpose of this class here.
 *
 * @author AmoresSchneyinck
 * @version 07122023
 */
public class PainterPauleHamsterGame extends SimpleHamsterGame {

	/**
	 * Creates a new PainterPauleHamsterGame.<br>
	 * Do not modify!
	 */
	public PainterPauleHamsterGame() {
		this.loadTerritoryFromResourceFile("/territories/PainterPauleTerritory.ter");
		this.displayInNewGameWindow();
		game.startGame();
	}

	/**
	 * Paints a line of the spiral.
	 */
	@Override
	protected void run() {
		int grainsToPut = getPaulesGrainCount();
		int requiredGrains = getNumbeOfRequiredGrains();
		if (requiredGrains>grainsToPut) {
			paule.write("I do not have enough grains!");
		}
		/*@
    	@ loop_invariant Paule puts a grain for each already executed loop iteration.
    	@ decreasing grainsToPut
    	@*/
		while (getDistanceToWall() >= 0) {
			while (grainsToPut > 0&&!paule.grainAvailable()) {
				paule.putGrain();
				grainsToPut--;
			}
			turnAndMove();
			if (grainsToPut==0) {
				paule.move();
				turnRight();
				paule.write("Painting complete!");
				break;
			}
		}
	}
	private boolean spiralTurn() {
		Direction turnDic = paule.getDirection();
		int turnRow = paule.getLocation().getRow();
		int turnCol = paule.getLocation().getColumn();

		if (turnDic == Direction.NORTH) {
			Location testingLoc = new Location(turnRow - 2, turnCol);
            return game.getTerritory().getNumberOfGrainsAt(testingLoc) >0;
		} else if (turnDic == Direction.WEST) {
			Location testingLoc = new Location(turnRow, turnCol - 2);
            return game.getTerritory().getNumberOfGrainsAt(testingLoc) >0;
		} else if (turnDic == Direction.EAST) {
			Location testingLoc = new Location(turnRow, turnCol + 2);
            return game.getTerritory().getNumberOfGrainsAt(testingLoc) >0;
		} else if (turnDic == Direction.SOUTH) {
			Location testingLoc = new Location(turnRow + 2, turnCol);
            return game.getTerritory().getNumberOfGrainsAt(testingLoc) > 0;
		} else {
			return false;
		}
    }
	/**
	 *  Makes Paule turn right
	 */
	private void turnRight() {
		for (int i=0; i<3; i++) {
			paule.turnLeft();
		}
	}
	/**
	 * Turns and moves Paule to the next position in the spiral.
	 */
	private void turnAndMove() {
		if (!paule.frontIsClear()||spiralTurn()) {
			turnRight();
		} else {
			paule.move();
		}
	}

	/**
	 * Calculate paules distance to the next wall.
	 * <p>
	 * (Only Works, if the territory is quadratic and has no inner walls)
	 *
	 * @return paule's distance to the next wall.
	 */
	private int getDistanceToWall() {
		int size = game.getTerritory().getTerritorySize().getColumnCount();
		switch (paule.getDirection()) {
			case NORTH: {
				return paule.getLocation().getRow() - 1;
			}
			case EAST: {
				return size - paule.getLocation().getColumn() - 2;
			}
			case SOUTH: {
				return size - paule.getLocation().getRow() - 2;
			}
			case WEST: {
				return paule.getLocation().getColumn() - 1;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + paule.getDirection());
		}
	}

	/**
	 * Calculate the number of grains paule currently hold in his mouth.
	 *
	 * @return number of grains currently in paule's mouth.
	 */
	private int getPaulesGrainCount() {
		int grainCounter = 0;
		/*@
		@ loop_invariant paule put a grain for each already executed loop iteration.
		@ decreasing grains remaining in paules mouth.
		@*/
		while (!paule.mouthEmpty()) {
			paule.putGrain();
			grainCounter++;
		}
		/*@
		@ loop_invariant paule picked a grain for each already executed loop iteration.
		@ decreasing grains remaining on the tile.
		@*/
		while (paule.grainAvailable()) {
			paule.pickGrain();
		}
		return grainCounter;
	}

	/**
	 * Calculate the number of grains required to paint the desired spiral on the current territory.
	 *
	 * @return number of grains required to paint the desired spiral on the current territory.
	 */
	private int getNumbeOfRequiredGrains() {
		final int territorySize = game.getTerritory().getTerritorySize().getColumnCount();
		int lineSize = territorySize - 3;

		int total = lineSize;
		/*@
		@ loop_invariant total is the sum of the previous total plus two times the current lineSize + 2
		@ decreasing lineSize
		@*/
		while (lineSize > 0) {
			total += 2 * lineSize;
			lineSize -= 2;
		}
		return total;
	}
}
