package towerPower;

import java.awt.Rectangle;

/**
 * 
 * @author Tim & Niko
 * @version 1.0
 * In dieser Klasse sind alle Information und Methoden der Spielfigur von Player1 enthalten.
 * Dazu gehören Variablen, die die Position und Größe des Spielers und seines Pfeils festhalten.
 * Und auch welche die speichern ob er den Pfeil bei sich hat oder ob der Pfeil in einer Wand steckt.
 * 
 * Es gibt zu jeder Variable die für andere Teile des Spiels wichtig sind eine Methode um diese auszugeben.
 * Es gibt auch Methoden die die Aktionen des Spielers ins Spiel übertragen.
 */
public class Player1 {

	/**
	 * In diesen Variablen werden die standart Spawn-Koordinaten gespeichert
	 */
	private int spawnx;
	private int spawny;
	
	/**
	 * Die Größe auf der X und Y Achse, also die Breite und Höhe
	 */
	private int sizeX = 20;
	private int sizeY = 20;
	
	/**
	 * In diesen Variablen wird die Größe des Spielfeldes gespeichert
	 */
	private int maxY;
	private int maxX;
	
	/**
	 * In diesen Variablen werden die aktuellen Koordinaten des Spielers gespeichert
	 */
	private int xCoordinate;
	private int yCoordinate;
	
	/**
	 * In dieser Variable wird die Geschwindigkeit des Pfeils gespeichert.
	 */
	private int arrowSpeed = 0;
	
	/**
	 * Die vordefiniert Fallgeschwindigkeit
	 */
	private int fallSpeed = 5;

	/**
	 * Diese Variablen geben die maximale Laufgeschwindigkeit nach links und rechts vor.
	 */
	private int walkSpeedLeft = -2;
	private int walkSpeedRight = 2;
	
	/**
	 * Dieses boolean gibt an ob der Pfeil bei einem Treffer zu einem Punkt
	 * führt
	 */
	public boolean deadly = false;

	/**
	 * In diesen Variablen werden die Koordinaten des Pfeils gespeichert
	 */
	private int arrowXCoordinate = xCoordinate;
	private int arrowYCoordinate = yCoordinate + 8;
	
	/**
	 * In grounded wird festgehalten ob der Spieler kontakt zum Boden hat
	 */
	private boolean grounded;
	
	/**
	 * In jump wird gespeichert ob der Spieler die Springen-Taste gedrückt hat,
	 * Ist so lange auf true bis der Sprung durchgeführt wurde.
	 * Die einzelnen Stufen des Sprungs werden in sprungStufe festgehalten.
	 */
	private boolean jump;
	private int sprungStufe;
	
	/**
	 * Mit move und moveRight werden die eingaben auf der Tastatur übergeben.
	 * Wenn move und moveRight true sind wurde nach Rechts gedrückt
	 * Wenn nur move true ist nach links und wenn beides false ist wird die Figur angehalten
	 */
	private boolean moveRight;
	private boolean move;
	
	/**
	 * In hasArrow wird gespeichert ob der Spieler den Pfeil hat und schießen könnte
	 */
	private boolean hasArrow = true;
	
	/**
	 * In shot wird gespeichert ob der Spieler geschossen hat.
	 * Wenn der Pfeil wieder aufgesammelt wird wird es wieder auf false gesetzt.
	 */
	private boolean shot;
	
	/**
	 * In arrowAtWall wird gespeichert ob der Pfeil mit einer oder einem Block
	 * zusammen gestoßen ist
	 */
	private boolean arrowAtWall;

	private Level level;
	private Game game;
	
	/**
	 * 
	 * @param game die Hauptklasse des Spiels in der alles zusmmen läuft, bei einem Restart wird eine Methode aus Game aufgerufen
	 * @param maxX die größte X Koordinate des Spielfeldes
	 * @param maxY die größte Y Koordinate des Spielfeldes
	 * @param level die Klasse die die Level-Bausteine enthält, diese wird zur Kollisions abfrage benötigt
	 * @param spawnx die Spawn X Koordinate die je nachdem ob man Player 1 oder Player 2 ist unterschiedlich ist
	 * @param spawny die Spawn Y Koordinate
	 */
	public Player1(Game game, int maxX, int maxY, Level level, int spawnx, int spawny) {
		this.spawnx = spawnx;
		this.spawny = spawny;
		this.game = game;
		this.maxX = maxX;
		this.maxY = maxY;
		this.level = level;
		
		xCoordinate = spawnx;
		yCoordinate = spawny;
		
	}
	
	/**
	 * 
	 * @return gibt die aktuelle X Koordiante aus
	 * Diese Methode wird vom Painter aufgerufen um zu berechnen wo auf der X Achse sich der Spieler momentan befindet
	 */
	public int getxCoordinate() {

		if (moveRight && move) {
			checkBlockCollisionWalkRight();
			xCoordinate += walkSpeedRight;
			if (xCoordinate > maxX - sizeX) {
				xCoordinate = maxX - sizeX;
			}
		} else if (move) {
			checkBlockCollisionWalkLeft();
			xCoordinate += walkSpeedLeft;
			if (xCoordinate < 0) {
				xCoordinate = 0;
			}
		}
		if (!shot) {
			arrowXCoordinate = xCoordinate + 2;
		}
		return xCoordinate;
	}
	
	/**
	 * 
	 * @return gibt die Größe der Spielfigur auf der X Achse zurück, also die Breite der Figur
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * 
	 * @return gibt die Größe der Spielfigur auf der Y Achse zurück, also die Höhe der Figur
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * 
	 * @return gibt die aktuelle Y Koordiante aus
	 * Diese Methode wird vom Painter aufgerufen um zu berechnen wo auf der y Achse sich der Spieler momentan befindet
	 * Wenn über die Input Klasse Jump auf true gesetzt wurde wird in den nächsten durchläufen dieser Methode ein
	 * Sprung ausgeführt der mehrere Stufen durchläuft.
	 * 
	 * Es wird auch überprüft ob ein Block über oder unter dem Spieler ist um das Fallen oder das Springen zu unterbrechen.
	 */
	public int getyCoordinate() {
		if (checkHeadCollision()) {
			jump = false;
			sprungStufe = 0;
		}
		if (jump) {
			if (sprungStufe == 0) {
				yCoordinate -= 40;
				sprungStufe++;
			} else if (sprungStufe == 1) {
				yCoordinate -= 30;
				sprungStufe++;

			} else if (sprungStufe == 2) {
				yCoordinate -= 20;
				sprungStufe++;

			} else if (sprungStufe == 3) {
				yCoordinate -= 10;
				sprungStufe++;

			} else {
				yCoordinate -= 5;
				sprungStufe = 0;
				jump = false;

			}

		} else if (checkBlockCollisionFall()) {
			setOnBlock();
		} else if (yCoordinate <= maxY - sizeY) {

			yCoordinate += fallSpeed;
		}

		if (!shot) {
			arrowYCoordinate = yCoordinate + 8;
		}
		return yCoordinate;
	}
	
	/**
	 * 
	 * @param jump
	 * Diese Methode wird von der Input Klasse aufgerufen um das Springen zu aktivieren
	 */
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	/**
	 * 
	 * @param moveRight
	 * Diese Methode wird von der Input Klasse aufgerufen um eine Bewegung nach Rechts zu starten oder zu beenden
	 */
	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}
	/**
	 * 
	 * @param moveRight
	 * Diese Methode wird von der Input Klasse aufgerufen um eine Bewegung zu starten, wenn moveRight false ist
	 * wird die Bewegung nach Links ausgeführt
	 */
	public void setMove(boolean move) {
		this.move = move;
	}
	
	/**
	 * 
	 * @return gibt zurück ob der Spieler momentan den Pfeil hat
	 */
	public boolean hasArrow() {
		return hasArrow;
	}
	
	/**
	 * 
	 * @return gibt zurück ob der Pfeil momentan zu einem Punkt führt falls er den Gegner trifft oder nicht.
	 */
	public boolean isDeadly() {
		return deadly;
	}
	
	/**
	 * 
	 * @return gibt zurück ob die Figur momentan Kontakt zum Boden oder einem Block hat
	 * Diese methode wird von Input1 aufgerufen, wenn sie false zurück gibt kann man nicht springen.
	 */
	public boolean checkGrounded() {
		if (yCoordinate >= maxY - sizeY || xCoordinate == 0 || xCoordinate == maxX - sizeX) {
			grounded = true;
		} else if (checkBlockCollisionJump()) {
			grounded = true;
		} else {
			grounded = false;
		}
		if (yCoordinate <= sizeY) {
			grounded = false;
		}
		return grounded;
	}
	
	/**
	 * 
	 * @return gibt zurück ob die Füße einen Block berühren, dies ist wichtig für die checkGrounded() Methode
	 * für 3 Blöcke wird hier eine Ausnahme gemacht weil man sonst zwischen einem der beiden und einem anderen Block stecken bleibt.
	 * 
	 */
	public boolean checkBlockCollisionJump() {
		for (int i = 0; i < level.getBlöcke().length; i++) {
			if (level.getBlöcke()[i].getRect().intersects(getRectPlayerFeet())) {
				if(i == 1 || i ==4 || i == 25){
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Diese Methode überprüft ob beim gehen nach Rechts ein Block getroffen wird um dann die Bewegung in diese Richtung zu blockieren
	 */
	public void checkBlockCollisionWalkRight() {
		for (int i = 0; i < level.getBlöcke().length; i++) {
			if (level.getBlöcke()[i].getRect().intersects(getRectPlayerBody())) {
				if (level.getBlöcke()[i].getBlockX() > xCoordinate) {
					walkSpeedRight = 0;
					break;
				}
			}
			walkSpeedRight = 2;
		}
	}
	/**
	 * Diese Methode überprüft ob beim gehen nach Links ein Block getroffen wird um dann die Bewegung in diese Richtung zu blockieren
	 */
	public void checkBlockCollisionWalkLeft() {
		for (int i = 0; i < level.getBlöcke().length; i++) {
			if (level.getBlöcke()[i].getRect().intersects(getRectPlayerBody())) {
				if (level.getBlöcke()[i].getBlockX() < xCoordinate) {
					walkSpeedLeft = 0;
					break;
				}
			}
			walkSpeedLeft = -2;
		}

	}
	/**
	 * 
	 * @return gibt zurück ob wir auf einen Block gefallen sind und dadurch ein intersect ausgelöst wurde.
	 * für 3 Blöcke wird hier eine Ausnahme gemacht weil man sonst zwischen einem der beiden und einem anderen Block stecken bleibt.
	 */
	public boolean checkBlockCollisionFall() {
		for (int i = 0; i < level.getBlöcke().length; i++) {
			if (level.getBlöcke()[i].getRect().intersects(getRectPlayerFeet())) {
				if(i == 1 || i ==4 || i == 25){
					grounded = false;
					return false;
				}
				grounded = true;
				return true;
			}
		}
		grounded = false;
		return false;
	}
	
	/**
	 * 
	 * @return gibt zurück ob wir mit dem Kopf gegen einen Block gesprungen sind um dann die Bewegung nach oben zu stoppen
	 */
	public boolean checkHeadCollision() {
		for (int i = 0; i < level.getBlöcke().length; i++) {
			if (level.getBlöcke()[i].getRect().intersects(getRectPlayerHead())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Durch diese Methode wird der Spieler wenn er nahe am oberen Rand des Blockes ist auf den Block gesetzt um
	 * es dem Spieler leichter zu machen auf die Blöcke zu kommen.
	 * für 3 Blöcke wird hier eine Ausnahme gemacht weil man sonst zwischen einem der beiden und einem anderen Block stecken bleibt.
	 */
	public void setOnBlock() {
		for (int i = 0; i < level.getBlöcke().length; i++) {
			if (level.getBlöcke()[i].getRect().intersects(getRectPlayerFeet())) {
				if(i == 1 || i ==4 || i == 25){
					break;
				}
				if (level.getBlöcke()[i].blockY - 10 > yCoordinate) {
					yCoordinate = level.getBlöcke()[i].blockY - sizeY + 1;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param enemyFeet die Füße des Player 2, wenn eine Kollision mit den Füßen des
	 * Player2 statt findet wird über die restart Methode von Game das Spiel neugestartet und der
	 * Player2 bekommt einen Punkt
	 */
	public void checkJumpedOn(Rectangle enemyFeet) {
		if (getRectPlayerHead().intersects(enemyFeet)) {
			game.restart(1);
		}

	}
	
	/**
	 * 
	 * @return gibt true zurück wenn der Pfeil einen Block trifft um in dann anzuhalten.
	 * bei false fliegt er weiter
	 */
	public boolean arrowAtLevelBlock() {
		for (int i = 0; i < level.getBlöcke().length; i++) {
			if (level.getBlöcke()[i].getRect().intersects(getRectArrow())) {
				deadly = false;
				return true;
				
			}
		}

		return false;
	}
	
	/**
	 * Durch diese Methode wird der Pfeil in die richtung in die man zu letzt gegangen ist abgeschossen.
	 * Es wird auch die Variable deadly auf true gesetzt um den Pfeil "scharf" zu machen.
	 */
	public void shotArrow() {
		if (hasArrow) {
			if (moveRight) {
				arrowSpeed = +5;
			} else {
				arrowSpeed = -5;
			}
			shot = true;
			deadly = true;
		}
	}
	
	/**
	 * 
	 * @return gibt die X Koordinaten des Pfeils an den Painter weiter
	 * hier wird berechnet wo der Pfeil momentan ist, und überprüft ob er eine Wand oder einen Block getroffen hat und 
	 * deshalb gestoppt wird
	 */
	public int getArrowXCoordinate() {
		
		if (arrowAtLevelBlock() && shot) {
			arrowAtWall = true;
		}
		else if (arrowXCoordinate >= maxX - 16) {
			arrowXCoordinate = maxX - 16;
			arrowAtWall = true;
		}

		else if (arrowXCoordinate <= 0) {
			arrowXCoordinate = 0;
			arrowAtWall = true;
		} 

		if (arrowAtWall) {
			deadly = false;
			arrowSpeed = 0;
		}
		
		arrowXCoordinate += arrowSpeed;
		return arrowXCoordinate;
	}
	
	/**
	 * 
	 * @return gibt die Y Koordinate des Pfeils aus
	 */
	public int getArrowYCoordinate() {
		return arrowYCoordinate;
	}

	/**
	 * 
	 * @return gibt ein Rectangle zurück um die Kollision mit dem Level zu überprüfen
	 */
	public Rectangle getRectArrow() {

		return new Rectangle(arrowXCoordinate, arrowYCoordinate, 15, 1);

	}
	
	/**
	 * 
	 * @return gibt ein Rectangle zurück um die Kollision mit dem Gegner zu überrpüfen
	 * wenn der Pfeil in einer Wand steckt oder eine Geschwindigkeit von 0 hat was der Fall ist
	 * wenn der spieler in noch bei sich trägt wird ein Rectangle außerhalb des Levels erstellt um eine
	 * Kollision zu vermeiden
	 */
	public Rectangle getRectArrowEnemy() {

		if (arrowAtWall || arrowSpeed == 0) {
			return new Rectangle(-15, -15, 0, 0);

		} else {
			return new Rectangle(arrowXCoordinate, arrowYCoordinate, 15, 3);
		}

	}
	
	/**
	 * 
	 * @return gibt das Rectangle des Player1 zurück
	 */
	public Rectangle getRectPlayer() {
		return new Rectangle(xCoordinate, yCoordinate, sizeX, sizeY);
	}

	/**
	 * 
	 * @return gibt den oberen mittigen Teil des Player1 als Rectangle aus, dies ist Quasi der Collider für den Kopf
	 */
	public Rectangle getRectPlayerHead() {
		return new Rectangle(xCoordinate + 6, yCoordinate, 8, 5);
	}

	/**
	 * 
	 * @return gibt den unteren mittleren Teil des Player1 als Rectangle aus, dies ist Quasi der Collider für die Füße
	 */
	public Rectangle getRectPlayerFeet() {
		return new Rectangle(xCoordinate + 1, yCoordinate + 19, sizeX - 1, 3);
	}
	
	/**
	 * 
	 * @return gibt den mittleren teil des Player1 als Rectangle  aus, dies ist Quasi der Collider für den Bauch.
	 */
	public Rectangle getRectPlayerBody() {
		return new Rectangle(xCoordinate, yCoordinate + 5, sizeX, 5);
	}

	/**
	 * In dieser Methode wird überprüft ob der Pfeil vom Spieler aufgehoben wird.
	 * Diese Methode wird im Timer der Game Klasse aufgerufen.
	 */
	public void checkArrowPickUp() {
		if ((xCoordinate - arrowXCoordinate) <= -22 || (xCoordinate - arrowXCoordinate) >= 16) {
			hasArrow = false;
		}
		if (!hasArrow) {
			if (getRectPlayer().intersects(getRectArrow())) {
				arrowSpeed = 0;
				hasArrow = true;
				arrowAtWall = false;
				shot = false;
				deadly = false;
			}

		}
	}
	
	/**
	 * 
	 * @param feindPfeil der Pfeil des Player2
	 * Sollte der Spieler vom Pfeil des Player2 getroffen werden wird das Spiel neu gestartet und der Player2 bekommt einen Punkt
	 */
	public void checkCollisionArrow(Rectangle feindPfeil) {
		if (getRectPlayer().intersects(feindPfeil)) {
			game.restart(1);
		}
	}
	
	/**
	 * Bei einem restart durch Game wird diese Methode aufgerufen um alles wieder auf die Start Werte zu setzen
	 */
	public void reset() {
		xCoordinate = spawnx;
		yCoordinate = spawny;
		arrowSpeed = 0;
		hasArrow = true;
		shot = false;
		arrowAtWall = false;
		deadly = false;
	}
}
