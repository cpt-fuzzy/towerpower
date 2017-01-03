package towerPower;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Tim & Niko
 * @version 0.2
 */

public class LobbyDialog extends JDialog {

	private JTextField player2Txt;
	private String clientName;
	private String ownerName;
	private boolean startGame = false;

	/**
	 * Hier wird die GUI des LobbyDialogs erstellt
	 * 
	 * @param title Titel des Dialogs
	 * @param ownerName Name des Hosts
	 */

	public LobbyDialog(String ownerName) {
		this.ownerName = ownerName;

		GameServer server = new GameServer(this);
		Thread serverThread = new Thread(server);
		serverThread.start();

		JButton backBtn = new JButton("Zurück");
		JButton startBtn = new JButton("Start");

		JTextField player1Txt = new JTextField(ownerName);
		player2Txt = new JTextField("Warte auf Spieler 2");

		JPanel btnPanel = new JPanel();

		Box playerBox = Box.createVerticalBox();

		player1Txt.setEditable(false);
		player2Txt.setEditable(false);

		playerBox.add(player1Txt);
		playerBox.add(player2Txt);

		btnPanel.add(backBtn, BorderLayout.WEST);
		btnPanel.add(startBtn, BorderLayout.EAST);

		add(playerBox, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);

		setSize(250, 200);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Lobby");

		// Spiel wird gestartet
		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				startGame = true;
				new Game(1, server.getSocket().getInetAddress(), 4447, ownerName, clientName);
			}
		});

		// Schließt den Dialog und führt zum GameSetup zurück
		backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}

	/**
	 * Gibt den Namen des Hosts wieder
	 * 
	 * @return
	 */

	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * Methode um den Spielernamen des Clients zu beschreiben
	 * 
	 * @param user
	 */

	public void setPlayer2Txt(String user) {
		clientName = user;
		player2Txt.setText(clientName);
	}

	/**
	 * Checkt ob ein Spiel gestartet wurde
	 * 
	 * @return
	 */

	public boolean gameStarted() {
		return startGame;
	}
}
