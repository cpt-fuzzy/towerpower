package towerPower;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Tim & Niko
 * @version 1.0 Mit diser Klasse werden die Eingaben des Player1 abgefangen. Je
 *          nachdem welche Taste gedrückt wurde oder welche nicht werden
 *          verschiedene Methoden aufgerufen.
 */
public class Input extends KeyAdapter {

	private Player1 player1;
	private int key;

	/**
	 * 
	 * @param player1 der Player1 wird hier übergeben damit wir seine Methoden
	 * aufgerufen können und die Änderungen auf dem Bildschierm zu
	 * sehen sind
	 */
	public Input(Player1 player1) {
		this.player1 = player1;
	}

	/**
	 * In dieser Methode wird beim Drücken einer Taste überprüft welche es war,
	 * wenn es eine der Richtungstasten war wird der Spieler mit der passenden
	 * Methode in diese Richtung bewegt.
	 */
	public void keyPressed(KeyEvent e) {
		key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			if (player1.checkGrounded()) {
				player1.setJump(true);
			}
		}

		if (key == KeyEvent.VK_A) {
			player1.setMoveRight(false);
			player1.setMove(true);

		} else if (key == KeyEvent.VK_D) {
			player1.setMoveRight(true);
			player1.setMove(true);
		}

		if (key == KeyEvent.VK_SPACE) {
			player1.shotArrow();
		}
	}

	/**
	 * Wenn eine Taste losgelassen wird und diese entweder die Taste für die
	 * Bewegung nach links oder Rechts war wird die Bewegung angehalten.
	 */
	public void keyReleased(KeyEvent e) {
		key = e.getKeyCode();

		if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
			player1.setMove(false);
		}

	}

}
