package towerPower;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.DefaultListModel;

/**
 * Client des Spiels. Kommuniziert mit dem Server.
 * 
 * @author Tim & Niko
 * @version 0.7
 */

public class GameClient extends Thread {

	private String host = "";
	private String user = "";
	private boolean hostStarted = false;

	/**
	 * Hier sucht der Client nach verfügbaren Servern. Wird ein Server gefunden,
	 * wird dieser in die JList des ServerListDialogs gespeichert Nach
	 * Verbindung zum Host wird gewartet, bis der Host das Spiel startet.
	 * 
	 * @param list ServerListDialog
	 * @param model ListModel des Dialogs
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public GameClient(ServerListDialog list, DefaultListModel<String> model) throws IOException, InterruptedException {

		String ips[] = new String[5];
		int counter = 0;

		ClientConnectionThread connect = new ClientConnectionThread(this, model);
		connect.start();

		// Solange nach Verbindungen suchen, bis einem Server gejoint wird.
		while (!list.isQueued()) {
			sleep(50);
			if (!connect.pushHost().equals("")) {
				switch (counter) {
				case 0:
					ips[0] = connect.pushHost();
					counter++;
					connect.resetHost();
					break;
				case 1:
					ips[1] = connect.pushHost();
					counter++;
					connect.resetHost();
					break;
				case 2:
					ips[2] = connect.pushHost();
					counter++;
					connect.resetHost();
					break;
				case 3:
					ips[3] = connect.pushHost();
					counter++;
					connect.resetHost();
					break;
				case 4:
					ips[4] = connect.pushHost();
					counter++;
					connect.resetHost();
					break;
				default:
					break;
				}
			}
		}

		// Ein neues Socket wird mit der ausgewählten IP erstellt

		Scanner input = new Scanner(System.in);
		Socket socket = new Socket(InetAddress.getByName(ips[list.getSelected()]), 4447);
		String selectedIP = ips[list.getSelected()];

		Scanner socketInput = new Scanner(socket.getInputStream());
		PrintStream print = new PrintStream(socket.getOutputStream());

		user = list.getName();
		print.println(user);

		host = socketInput.nextLine();

		ClientLobby lobby = new ClientLobby(user, host, this);
		lobby.start();

		while (socketInput.hasNext()) {
			if (socketInput.nextBoolean() == true) {
				break;
			}
		}

		list.dispose();
		lobby.dispose();

		// Ein neues Spiel wird erstellt
		InetAddress ipAddress = InetAddress.getByName(selectedIP);
		Game game = new Game(2, ipAddress, 4447, host, user);

		socket.close();
		input.close();
		socketInput.close();
	}

}
