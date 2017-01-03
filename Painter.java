package towerPower;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * @author Tim & Niko
 * @version 1.0 In diser Klasse werden die Einzelnen Komponenenten des Spiels
 *          gezeichnet
 */
public class Painter extends JPanel {

	private Player1 player1;
	private Player2 player2;
	private Level level;
	private int maxY;
	private int maxX;
	private Game game;

	/**
	 * 
	 * @param player1  der Player1 wird �bergeben um die Methoden f�r die Koordinaten
	 * und die Gr��e aufzurufen
	 * @param player2 der Player2 wird �bergeben um die Methoden f�r die Koordinaten
	 * und die Gr��e aufzurufen
	 * @param maxX hier wird die gr��e auf der X Achse der zu Zeichnenden Fl�che
	 * �bergeben
	 * @param maxY hier wird die gr��e auf der Y Achse der zu Zeichnenden Fl�che
	 * �bergeben
	 * @param level das Level wird �bergeben um die einzelnen Bl�cke des Levels
	 * zeichnen zu k�nnen
	 */
	public Painter(Player1 player1, Player2 player2, int maxX, int maxY, Level level, Game game) {
		this.player1 = player1;
		this.player2 = player2;
		this.maxX = maxX;
		this.maxY = maxY;
		this.level = level;
		this.game = game;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		/**
		 * Hier wird zu erst der Hintergrund schwarz gef�rbt.
		 */
		// Levl zeichnen
		g.setColor(Color.black);
		g.fillRect(0, 0, maxX, maxY);

		/**
		 * Hier werden in einer Schleife alle Bl�cke durchgegangen und
		 * gezeichnet
		 */
		// Level Elemente
		g.setColor(Color.white);
		for (int i = 0; i < level.getBl�cke().length; i++) {
			g.fillRect(level.getBl�cke()[i].getBlockX(), level.getBl�cke()[i].getBlockY(),
					level.getBl�cke()[i].blockWidth, level.getBl�cke()[i].blockHeight);
		}

		/**
		 * Hier wird der Player 1 gezeichnet
		 */
		// Player1 zeichnen
		g.setColor(Color.blue);
		g.fillRect(player1.getxCoordinate(), player1.getyCoordinate(), player1.getSizeX(), player1.getSizeY());

		/**
		 * Hier wird der Pfeil des Player1 gezeichnet
		 */
		// Player1 Arrow zeichnen
		g.setColor(Color.gray);
		g.fillRect(player1.getArrowXCoordinate(), player1.getArrowYCoordinate(), 15, 3);

		/**
		 * Hier wird der Player2 gezeichnet
		 */
		// Player2 zeichnen
		g.setColor(Color.red);
		g.fillRect(player2.getxCoordinate(), player2.getyCoordinate(), player2.getSizeX(), player2.getSizeY());

		/**
		 * Hier wird der Pfeil des Player2 gezeichnet
		 */
		// Player2 Arrow zeichnen
		g.setColor(Color.yellow);
		g.fillRect(player2.getArrowXCoordinate(), player2.getArrowYCoordinate(), 15, 3);

		/**
		 * Hier wird der Score in oben in der mitte des Levels gezeichnet
		 */
		g.setColor(Color.BLACK);
		g.drawString(game.getScore(), 380, 15);
	}
}
