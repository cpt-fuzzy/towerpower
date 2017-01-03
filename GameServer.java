package towerPower;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Server des Spiels
 * 
 * @author Tim & Niko
 * @version 0.4
 */

public class GameServer implements Runnable {

	private LobbyDialog lobby;
	private String user;
	private Socket socket;

	/**
	 * 
	 * @param lobby LobbyDialog des Hosts
	 */

	public GameServer(LobbyDialog lobby) {
		this.lobby = lobby;
	}

	/**
	 * Hier wird ein neuer MultiCastServerThread erstellt Auﬂerdem wird ein
	 * neuer ServerSocket erstellt und auf das Socket des Clients gewartet
	 * Danach werden die Namen der beiden User ausgetauscht
	 */

	@Override
	public void run() {
		try {
			new MultiCastServerThread("MultiCast").start();

			ServerSocket server = new ServerSocket(4447);
			socket = server.accept();

			Scanner input = new Scanner(socket.getInputStream());
			PrintStream print = new PrintStream(socket.getOutputStream());

			user = input.nextLine();
			print.println(lobby.getOwnerName());

			lobby.setPlayer2Txt(user);

			while (!lobby.gameStarted()) {
				print.println(lobby.gameStarted());
			}

			server.close();
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Socket getSocket() {
		return socket;
	}

}
