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
 * Warte-Bildschirms des Clients
 * 
 * @author Tim & Niko
 * @version 0.2
 */

public class ClientLobby extends JDialog implements Runnable {

	private String user;
	private String host;

	/**
	 * Beide Namen werden übergeben
	 * 
	 * @param user Name des Clients
	 * @param host  Name des Servers
	 * @param client Client des Users
	 */

	public ClientLobby(String user, String host, GameClient client) {

		this.user = user;
		this.host = host;

	}

	/**
	 * Erstellt den Warte-Bildschirm des Clients
	 */

	@Override
	public void run() {

		setModal(true);

		JTextField hostName = new JTextField(host);
		JTextField userName = new JTextField(user);

		hostName.setEditable(false);
		userName.setEditable(false);

		Box verticalTxt = Box.createVerticalBox();

		verticalTxt.add(hostName);
		verticalTxt.add(userName);

		add(verticalTxt, BorderLayout.CENTER);

		setSize(250, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	public void start() {
		new Thread(this).start();
	}

}
