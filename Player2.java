package towerPower;

import java.awt.Rectangle;

/**
 * 
 * @author Tim & Niko
 * @version 1.0
 * 
 *          Diese Klasse ist quasi eine leere Hülle des Player1. Hier werden die
 *          Informationen die vom anderen Spieler kommen gespeichert. Sie werden
 *          dann wieder ausgelesen um die Bewegungen des anderen Spielers nach
 *          zu machen und das Spielen übers Netzwerk zu ermöglichen.
 */
public class Player2 {

	/**
	 * Die vordefinierten Spawn koordinaten des Player2 damit es nicht zu Anfang
	 * zu ungewollten kollisionen kommt
	 */
	private int spawnx = 10;
	private int spawny = 10;

	/**
	 * Die Größe auf der X und Y Achse, also die Breite und Höhe
	 */
	private int sizeX = 20;
	private int sizeY = 20;

	/**
	 * Dieses boolean gibt an ob der Pfeil bei einem Treffer zu einem Punkt
	 * führt
	 */
	private boolean deadly = false;

	/**
	 * Die aktuellen X und Y Koordinaten des Spielers
	 */
	private int xCoordinate = spawnx;
	private int yCoordinate = spawny;

	/**
	 * Die aktuellen X und Y Koordinaten des Pfeils
	 */
	private int arrowXCoordinate = xCoordinate;
	private int arrowYCoordinate = yCoordinate + 8;

	private Game game;

	/**
	 * 
	 * @param game
	 *            im Construktor wird nur das Game an sich übergeben um methoden
	 *            aus Game aufrufen zu können
	 */
	public Player2(Game game) {
		this.game = game;

	}

	/**
	 * 
	 * @return gibt die X Koordinate des Player2 zurück
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * 
	 * @param xCoordinate
	 *            hier wird die X Kooridnate des Player 2 beschrieben, die
	 *            Koordinate kommt dann über das Netzwerk vom anderen Spieler
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * 
	 * @return gibt die Göße auf der X Achse / die Breite der Spielfigur zurück
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * 
	 * @return gibt die Göße auf der Y Achse / die Höhe der Spielfigur zurück
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * 
	 * @return gibt die Y Koordinate des Player 2 wieder
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * 
	 * @param yCoordinate
	 *            hier wird die Y Kooridnate des Player 2 beschrieben, die
	 *            Koordinate kommt dann über das Netzwerk vom anderen Spieler
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 *
	 * @param enemyFeet
	 *            die Füße des Player1 Wenn man auf den Player 2 springt wird
	 *            das Spiel neu gestartet und der Player 1 bekommt einen Punkt
	 */
	public void checkJumpedOn(Rectangle enemyFeet) {
		if (getRectPlayerHead().intersects(enemyFeet)) {
			game.restart(2);
		}
	}

	/**
	 * 
	 * @return gibt die X Koordinate des Pfeils wieder
	 */
	public int getArrowXCoordinate() {
		return arrowXCoordinate;
	}

	/**
	 * 
	 * @param arrowXCoordinate
	 *            hier wird die X Kooridnate des Pfeils beschrieben, die
	 *            Koordinate kommt dann über das Netzwerk vom anderen Spieler.
	 */
	public void setArrowXCoordinate(int arrowXCoordinate) {
		this.arrowXCoordinate = arrowXCoordinate;
	}

	/**
	 * 
	 * @return gibt die y Koordinate des Pfeils wieder
	 */
	public int getArrowYCoordinate() {
		return arrowYCoordinate;
	}

	/**
	 * 
	 * @param arrowYCoordinate
	 *            hier wird die Y Kooridnate des Pfeils beschrieben, die
	 *            Koordinate kommt dann über das Netzwerk vom anderen Spieler.
	 */
	public void setArrowYCoordinate(int arrowYCoordinate) {
		this.arrowYCoordinate = arrowYCoordinate;
	}

	/**
	 * 
	 * @return gibt das Rectangle des Pfeils für die Collsions abfrage mit der
	 *         Umgebung aus
	 */
	public Rectangle getRectArrow() {
		return new Rectangle(arrowXCoordinate, arrowYCoordinate, 15, 1);
	}

	/**
	 * 
	 * @param deadly,  wenn deadly auf True gesetzt wird und dann der Player1 vom
	 *            	   Pfeil von Player2 getroffen wird zählt das als Treffer. Der
	 *           	   Wert kommt auch hier über das Netzwerk vom Player2.
	 */
	public void setDeadly(boolean deadly) {
		this.deadly = deadly;
	}

	/**
	 * 
	 * @return gibt das Rectangle für die Kollsionsabfrage mit dem Player1
	 *         wieder. Wenn deadly gesetzt ist ist es das Rectangle was man auch
	 *         sieht, wenn nicht wird ein Rectangle außerhalb des Spiels erzeugt
	 *         um ein Intersect mit dem Player1 zu vermeiden
	 */
	public Rectangle getRectArrowEnemy() {

		if (deadly) {
			return new Rectangle(arrowXCoordinate, arrowYCoordinate, 15, 3);
		} else {
			return new Rectangle(-15, -15, 0, 0);
		}

	}

	/**
	 * 
	 * @return gibt das Rectangle des Player2 aus.
	 */
	public Rectangle getRectPlayer() {
		return new Rectangle(xCoordinate, yCoordinate, sizeX, sizeY);
	}

	/**
	 * 
	 * @return gibt den oberen mittigen Teil des Player2 als Rectangle aus, dies
	 *         ist Quasi der Collider für den Kopf
	 */
	public Rectangle getRectPlayerHead() {
		return new Rectangle(xCoordinate + 6, yCoordinate, 8, 5);
	}

	/**
	 * 
	 * @return gibt den unteren mittleren Teil des Player2 als Rectangle aus,
	 *         dies ist Quasi der Collider für die Füße
	 */
	public Rectangle getRectPlayerFeet() {
		return new Rectangle(xCoordinate + 1, yCoordinate + 19, sizeX - 1, 3);
	}

	/**
	 * 
	 * @return gibt den mittleren teil des Player2 als Rectangle aus, dies ist
	 *         Quasi der Collider für den Bauch.
	 */
	public Rectangle getRectPlayerBody() {
		return new Rectangle(xCoordinate, yCoordinate + 5, sizeX, 5);
	}

	/**
	 * 
	 * @param feindPfeil
	 *            wenn Player2 vom Pfeil Rectangle von Player1 getroffen wird
	 *            wird das Spiel neu gestartet und der Player1 bekommt einen
	 *            Punkt
	 */
	public void checkCollisionArrow(Rectangle feindPfeil) {
		if (getRectPlayer().intersects(feindPfeil)) {
			game.restart(2);
		}
	}

	/**
	 * Bei einem neustart werden die Koordinaten des Player2 wieder auf den
	 * Anfang gesetzt
	 */
	public void reset() {
		xCoordinate = spawnx;
		yCoordinate = spawny;
	}
}
