package towerPower;

import java.io.IOException;

import javax.swing.DefaultListModel;

/**
 * Klasse zum Erstellen des Clients
 * 
 * @author Tim & Niko
 * @version 0.1
 */

public class ClientCreator implements Runnable {

	private ServerListDialog list;
	private DefaultListModel<String> model;

	/**
	 * Hier werden der ListDialog und das ListModel übergeben, welche der Client
	 * benötigt
	 * 
	 * @param list ServerListDialog
	 * @param model ListModel des Dialogs
	 * @throws IOException
	 */

	public ClientCreator(ServerListDialog list, DefaultListModel<String> model) throws IOException {
		this.list = list;
		this.model = model;
	}

	@Override
	public void run() {
		GameClient client;

		try {
			client = new GameClient(list, model);
			client.start();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}

}
