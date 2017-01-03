package towerPower;

import java.awt.Rectangle;
/**
 * 
 * @author Tim & Niko
 * @version 1.0
 * 
 * Diese Klasse enthält die Levelbausteine, also die ganzen Blöcke die im Level angezeigt werden.
 * Die Einzelnen Blöcke sind in einem Array gespeichert damit beim Zeichnen und auch bei der Collisionsabfrage 
 * nur das Array durchlaufen werden muss.
 */
public class Level {

	private Block[] blöcke = new Block[28];
	
	/**
	 * Im Construktor werden die 27 Blöcke erstellt und in das Array gespeichert.
	 * Die Koordinaten sind aus der Levelvorlage entnommen.
	 */
	public Level() {

		blöcke[0] = new Block(84, 537, 33, 724);
		blöcke[1] = new Block(0, 465, 25, 110);
		blöcke[2] = new Block(222, 490, 48, 170);
		blöcke[3] = new Block(500, 490, 48, 170);
		blöcke[4] = new Block(780, 465, 25, 114);
		blöcke[5] = new Block(0, 440, 25, 166);
		blöcke[6] = new Block(278, 420, 70, 20);
		blöcke[7] = new Block(586, 420, 70, 20);
		blöcke[8] = new Block(724, 440, 25, 170);
		blöcke[9] = new Block(140, 300, 50, 50);
		blöcke[10] = new Block(418, 328, 50, 50);
		blöcke[11] = new Block(700, 300, 50, 50);
		blöcke[12] = new Block(0, 232, 28, 102);
		blöcke[13] = new Block(308, 234, 50, 50);
		blöcke[14] = new Block(529, 234, 50, 50);
		blöcke[15] = new Block(786, 234, 28, 108);
		blöcke[16] = new Block(166, 164, 50, 50);
		blöcke[17] = new Block(370, 166, 26, 150);
		blöcke[18] = new Block(670, 164, 50, 50);
		blöcke[19] = new Block(0, 0, 154, 138);
		blöcke[20] = new Block(224, 94, 22, 84);
		blöcke[21] = new Block(418, 60, 108, 56);
		blöcke[22] = new Block(586, 94, 22, 84);
		blöcke[23] = new Block(756, 0, 154, 138);
		blöcke[24] = new Block(138, 0, 68, 28);
		blöcke[25] = new Block(166, 0, 22, 562);
		blöcke[26] = new Block(728, 0, 68, 28);
		blöcke[27] = new Block(0, 0, 0, 0);

	}
	
	/**
	 * 
	 * @return gibt das blöcke Array wieder damit es für verschiedene Dinge durchlaufen  werden kann
	 */
	public Block[] getBlöcke() {
		return blöcke;
	}
	
	/**
	 * 
	 * @author Niko
	 * @version 1.0
	 * Die Klasse Block enthält alle Informationen und Methoden die man benötigt um ihn im Spiel zu nutzen.
	 * Man kann die Koordinaten und die Größe für das zeichnen der Blöcke abfragen und auch das Rectangle für
	 * die Collisions abfrage bekommt man hier.
	 */
	public class Block {
		int blockX;
		int blockY;
		int blockHeight;
		int blockWidth;
		
		/**
		 * 
		 * @param blockX die X Coordinate des Blockes
		 * @param blockY die Y Coordinate des Blockes
		 * @param blockHeight die Höhe des Blockes
		 * @param blockWidth die Breite des Blockes
		 * Im Construktor werden diese Information übergeben und dann in die passenden Variablen gespeichert
		 */
		public Block(int blockX, int blockY, int blockHeight, int blockWidth) {
			this.blockX = blockX;
			this.blockY = blockY;
			this.blockHeight = blockHeight;
			this.blockWidth = blockWidth;
		}
		
		/**
		 * 
		 * @return gibt das Rectangle des Blocks wieder, dies wird für die Kollisionsabfrage benötigt
		 */
		public Rectangle getRect() {
			return new Rectangle(blockX, blockY, blockWidth, blockHeight);
		}
		
		/**
		 * 
		 * @return gibt die X Coordinate des Blocks zurück
		 */
		public int getBlockX() {
			return blockX;
		}
		
		/**
		 * 
		 * @return gibt die y Coordinate des Blocks zurück
		 */
		public int getBlockY() {
			return blockY;
		}

		/**
		 * 
		 * @return gibt die Höhe des Blocks zurück
		 */
		public int getBlockHeight() {
			return blockHeight;
		}
		
		/**
		 * 
		 * @return gibt die Breite des Blocks zurück
		 */
		public int getBlockWidth() {
			return blockWidth;
		}
	}

}
