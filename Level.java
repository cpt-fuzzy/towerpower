package towerPower;

import java.awt.Rectangle;
/**
 * 
 * @author Tim & Niko
 * @version 1.0
 * 
 * Diese Klasse enth�lt die Levelbausteine, also die ganzen Bl�cke die im Level angezeigt werden.
 * Die Einzelnen Bl�cke sind in einem Array gespeichert damit beim Zeichnen und auch bei der Collisionsabfrage 
 * nur das Array durchlaufen werden muss.
 */
public class Level {

	private Block[] bl�cke = new Block[28];
	
	/**
	 * Im Construktor werden die 27 Bl�cke erstellt und in das Array gespeichert.
	 * Die Koordinaten sind aus der Levelvorlage entnommen.
	 */
	public Level() {

		bl�cke[0] = new Block(84, 537, 33, 724);
		bl�cke[1] = new Block(0, 465, 25, 110);
		bl�cke[2] = new Block(222, 490, 48, 170);
		bl�cke[3] = new Block(500, 490, 48, 170);
		bl�cke[4] = new Block(780, 465, 25, 114);
		bl�cke[5] = new Block(0, 440, 25, 166);
		bl�cke[6] = new Block(278, 420, 70, 20);
		bl�cke[7] = new Block(586, 420, 70, 20);
		bl�cke[8] = new Block(724, 440, 25, 170);
		bl�cke[9] = new Block(140, 300, 50, 50);
		bl�cke[10] = new Block(418, 328, 50, 50);
		bl�cke[11] = new Block(700, 300, 50, 50);
		bl�cke[12] = new Block(0, 232, 28, 102);
		bl�cke[13] = new Block(308, 234, 50, 50);
		bl�cke[14] = new Block(529, 234, 50, 50);
		bl�cke[15] = new Block(786, 234, 28, 108);
		bl�cke[16] = new Block(166, 164, 50, 50);
		bl�cke[17] = new Block(370, 166, 26, 150);
		bl�cke[18] = new Block(670, 164, 50, 50);
		bl�cke[19] = new Block(0, 0, 154, 138);
		bl�cke[20] = new Block(224, 94, 22, 84);
		bl�cke[21] = new Block(418, 60, 108, 56);
		bl�cke[22] = new Block(586, 94, 22, 84);
		bl�cke[23] = new Block(756, 0, 154, 138);
		bl�cke[24] = new Block(138, 0, 68, 28);
		bl�cke[25] = new Block(166, 0, 22, 562);
		bl�cke[26] = new Block(728, 0, 68, 28);
		bl�cke[27] = new Block(0, 0, 0, 0);

	}
	
	/**
	 * 
	 * @return gibt das bl�cke Array wieder damit es f�r verschiedene Dinge durchlaufen  werden kann
	 */
	public Block[] getBl�cke() {
		return bl�cke;
	}
	
	/**
	 * 
	 * @author Niko
	 * @version 1.0
	 * Die Klasse Block enth�lt alle Informationen und Methoden die man ben�tigt um ihn im Spiel zu nutzen.
	 * Man kann die Koordinaten und die Gr��e f�r das zeichnen der Bl�cke abfragen und auch das Rectangle f�r
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
		 * @param blockHeight die H�he des Blockes
		 * @param blockWidth die Breite des Blockes
		 * Im Construktor werden diese Information �bergeben und dann in die passenden Variablen gespeichert
		 */
		public Block(int blockX, int blockY, int blockHeight, int blockWidth) {
			this.blockX = blockX;
			this.blockY = blockY;
			this.blockHeight = blockHeight;
			this.blockWidth = blockWidth;
		}
		
		/**
		 * 
		 * @return gibt das Rectangle des Blocks wieder, dies wird f�r die Kollisionsabfrage ben�tigt
		 */
		public Rectangle getRect() {
			return new Rectangle(blockX, blockY, blockWidth, blockHeight);
		}
		
		/**
		 * 
		 * @return gibt die X Coordinate des Blocks zur�ck
		 */
		public int getBlockX() {
			return blockX;
		}
		
		/**
		 * 
		 * @return gibt die y Coordinate des Blocks zur�ck
		 */
		public int getBlockY() {
			return blockY;
		}

		/**
		 * 
		 * @return gibt die H�he des Blocks zur�ck
		 */
		public int getBlockHeight() {
			return blockHeight;
		}
		
		/**
		 * 
		 * @return gibt die Breite des Blocks zur�ck
		 */
		public int getBlockWidth() {
			return blockWidth;
		}
	}

}
