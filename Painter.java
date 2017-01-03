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
	 * @param player1  der Player1 wird übergeben um die Methoden für die Koordinaten
	 * und die Größe aufzurufen
	 * @param player2 der Player2 wird übergeben um die Methoden für die Koordinaten
	 * und die Größe aufzurufen
	 * @param maxX hier wird die größe auf der X Achse der zu Zeichnenden Fläche
	 * übergeben
	 * @param maxY hier wird die größe auf der Y Achse der zu Zeichnenden Fläche
	 * übergeben
	 * @param level das Level wird übergeben um die einzelnen Blöcke des Levels
	 * zeichnen zu können
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
		 * Hier wird zu erst der Hintergrund schwarz gefärbt.
		 */
		// Levl zeichnen
		g.setColor(Color.black);
		g.fillRect(0, 0, maxX, maxY);

		/**
		 * Hier werden in einer Schleife alle Blöcke durchgegangen und
		 * gezeichnet
		 */
		// Level Elemente
		g.setColor(Color.white);
		for (int i = 0; i < level.getBlöcke().length; i++) {
			g.fillRect(level.getBlöcke()[i].getBlockX(), level.getBlöcke()[i].getBlockY(),
					level.getBlöcke()[i].blockWidth, level.getBlöcke()[i].blockHeight);
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
