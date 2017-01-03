package towerPower;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * 
 * @author Tim & Niko
 * @version 1.0
 * 
 *          Mit dieser Klasse wird das Spiel ansich erstellt. Hier laufen die
 *          informationen über die Player zusammen, das Fenster in dem man
 *          spielt wird erstellt und der Painter wird von hier angesteuert.
 */
public class Game extends JFrame {

	private Player1 player1;
	private Player2 player2;

	private int punktePlayer1 = 0;
	private int punktePlayer2 = 0;

	int playerNumber;

	private String hostName;
	private String clientName;

	private GameSenderReceiver senderReceiver;

	/**
	 * 
	 * @param player der Wert des Spielers 1 = Server und  2 = Client
	 * @param ipAddress die IP-Adresse des anderen Spielers, diese wird benötigt um die Packets zu versenden
	 * @param port der Port über den gespielt wird
	 * @param hostName der vom Host eingegebene Name, wird für den Score benötigt
	 * @param clientName der vom Client eingegebene Name, wird für den Score benötigt
	 * 
	 * beim erstellen eines neuen Games wird überprüft ob man der
	 * Server oder der Client ist. Als Server ist man Player 1 und
	 * als Client immer Player2, das ist für die Startpunkte und auch
	 *  den Score wichtig.
	 */
	public Game(int player, InetAddress ipAddress, int port, String hostName, String clientName) {
		Level level = new Level();

		this.hostName = hostName;
		this.clientName = clientName;

		if (player == 1) {
			player1 = new Player1(Game.this, 893, 565, level, 40, 530);
			this.playerNumber = player;
		} else {
			player1 = new Player1(Game.this, 893, 565, level, 840, 530);
			this.playerNumber = player;
		}
		player2 = new Player2(Game.this);

		/**
		 * Hier wird ein neuer GameSenderReceiver erstellt und auch ein Thread
		 * davon erstellt. Über diese Klasse läuft die Kommunikation mit dem
		 * anderen Computer und die Koordinaten der Spieler werden ausgetauscht.
		 */
		senderReceiver = new GameSenderReceiver(this, ipAddress, port);
		Thread senderReceiverServerThread = new Thread(senderReceiver);
		senderReceiverServerThread.start();

		Painter painter = new Painter(player1, player2, 893, 570, level, this);

		/**
		 * In dem Timer wird immer überprüft ob es eine Kollsion mit dem
		 * Gegnerischen Pfeil oder seinen Füßen gab weil dies die beiden Events
		 * für ein Spiel neustart sind. Es wird auch überprüft ob der eigene
		 * Pfeil aufgehoben wird. Es wird auch immer das Level und die Player
		 * und deren Pfeile neu gezeichnet.
		 * 
		 * Zudem wird die sendData()-Methode aus dem SenderReceiver aufgerufen
		 * und mit den aktuellen Koordinaten des lokalen Spielers und auch mit
		 * dem Zustand seines Pfeils befüllt um diese an den entfernten PC zu
		 * senden.
		 */
		Timer timer = new Timer(15, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				painter.repaint();

				player1.checkArrowPickUp();
				player1.checkCollisionArrow(player2.getRectArrowEnemy());
				player1.checkJumpedOn(player2.getRectPlayerFeet());

				senderReceiver.sendData(player1.getxCoordinate(), player1.getyCoordinate(),
						player1.getArrowXCoordinate(), player1.getArrowYCoordinate(), player1.isDeadly());

				player2.checkCollisionArrow(player1.getRectArrowEnemy());
				player2.checkJumpedOn(player1.getRectPlayerFeet());

			}
		});

		add(painter);
		addKeyListener(new Input(player1));
		// addKeyListener(new Input3(player3));

		timer.start();

		setSize(900, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("TowerPower");
	}

	/**
	 * 
	 * @param playerXCoordinate
	 *            die X Koordinate des anderen Player1
	 * @param playerYCoordinate
	 *            die Y Koordinate des anderen Player1
	 * @param arrowXCoordinate
	 *            die X Koordinate des Pfeils des Anderen Spielers
	 * @param arrowYCoordinate
	 *            die Y Koordinate des Pfeils des Anderen Spielers
	 * @param deadly
	 *            mit diesem boolean wird überprüft ob der Pfeil des Player2
	 *            momentan "scharf" ist. Also ob es bei einer Kollsion zu einem
	 *            Tod kommt.
	 * 
	 *            Mit dieser Methode wird der lokale Player2 mit den Koordinaten
	 *            des anderen Player1 befüllt damit er sich genau so wie dieser
	 *            verhält
	 */
	public void setPlayer2(int playerXCoordinate, int playerYCoordinate, int arrowXCoordinate, int arrowYCoordinate,
			boolean deadly) {
		player2.setxCoordinate(playerXCoordinate);
		player2.setyCoordinate(playerYCoordinate);
		player2.setArrowXCoordinate(arrowXCoordinate);
		player2.setArrowYCoordinate(arrowYCoordinate);
		player2.setDeadly(deadly);
	}

	/**
	 * 
	 * @param getroffenerPlayer
	 *            über diesen int wird mitgegeben welche Spieler getroffen
	 *            wurde, auf grund dessen wird der Score für den jeweils anderen
	 *            erhöht. Dann wird das Spiel neugestartet.
	 */
	public void restart(int getroffenerPlayer) {
		if (playerNumber == 1) {
			if (getroffenerPlayer == 1) {
				punktePlayer2++;
			} else {
				punktePlayer1++;
			}
		} else {
			if (getroffenerPlayer == 1) {
				punktePlayer1++;
			} else {
				punktePlayer2++;
			}
		}
		player1.reset();
		player2.reset();
	}

	/**
	 * 
	 * @return diese Methode gibt den Score als String mit den zugehörigen Namen
	 *         aus damit er im vom Painter im Level angezeigt werden kann.
	 */
	public String getScore() {
		return new String(hostName + ": " + punktePlayer1 + "   " + clientName + ": " + punktePlayer2);
	}

	/**
	 * 
	 * @author Niko & Tim
	 * @version 1.0
	 *
	 *          Diese Klasse ist für das Senden und Empfangen der Packete mit
	 *          den Spielerinformationen zwischen den beiden Spielern zuständig.
	 *          Es wird ein DatagramSocket erstellt über das dann die
	 *          Kommunkikation läuft.
	 */
	public class GameSenderReceiver implements Runnable {

		private byte[] data;
		private DatagramSocket socket;
		private Game game;
		private InetAddress ipAddress;
		private int port;

		/**
		 * 
		 * @param game
		 *            hier wird das Spiel mitgegeben damit die Methode um den
		 *            Player2 zu steuern aufgerufen werden kann
		 * @param ipAddress
		 *            hier wird die IP-Adresse des anderen Spielers übergeben
		 *            damit man die Packete richtig adressieren kann
		 * @param port
		 *            hier wird der Port über den die Kommunikation lief
		 *            mitgegeben.
		 */
		public GameSenderReceiver(Game game, InetAddress ipAddress, int port) {
			this.game = game;
			this.ipAddress = ipAddress;
			this.port = port;
			try {
				this.socket = new DatagramSocket(4447);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * In der run Methode die immer wieder durchlaufen wird wird ein
		 * DatagramPacket erstellt und dann wartet das Socket darauf die Füllung
		 * für das Packet vom entfernten PC zu bekommen. Wenn ein Packet
		 * angekommen ist wird die useData-Methode aufgerufen um die Daten
		 * auszuwerten.
		 */
		@Override
		public void run() {

			while (true) {
				byte[] data = new byte[1024];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
				useData(packet.getData());
			}
		}

		/**
		 * 
		 * @param data
		 *            die daten die über das DatagramSocket empfangen wurden.
		 *            das byte-Array enthält einen String der durch die Methode
		 *            readData() ausgelesen und dann durch .split in ein String
		 *            Array geschrieben wird damit man die empfangenen Daten
		 *            nutzen kann. Nach dem Zerlegen der Informationen werden
		 *            diese mit der setPlayer- Methode an den Player2
		 *            übertragen.
		 */
		public void useData(byte[] data) {
			this.data = data;
			String[] dataArray = readData(data).split(",");
			game.setPlayer2(Integer.parseInt(dataArray[0]), Integer.parseInt(dataArray[1]),
					Integer.parseInt(dataArray[2]), Integer.parseInt(dataArray[3]), Boolean.parseBoolean(dataArray[4]));

		}

		/**
		 * 
		 * @param data
		 *            hier wird das Empfangene byte-Array übergeben damit es
		 *            zerschnitten und in einen String umgewandelt werden kann.
		 * @return gibt die erhaltenen Spielerinformationen als String zurück.
		 */
		public String readData(byte[] data) {
			String playerInformation = new String(data).trim();
			return playerInformation;
		}

		/**
		 * 
		 * @param playerXCoordinate
		 *            Die X-Koordinate des lokalen Spielers
		 * @param playerYCoordinate
		 *            Die Y-Koordinate des lokalen Spielers
		 * @param arrowXCoordinate
		 *            Die X-Koordinate des Pfeils lokalen Spielers
		 * @param arrowYCoordinate
		 *            Die Y-Koordinate des Pfeils lokalen Spielers
		 * @param deadly
		 *            Das boolean über das Festgelegt wird ob der Pfeil des
		 *            lokalen Spielers bei einem Treffer zu einem Punkt führen
		 *            könnte.
		 * 
		 *            Diese Methode erstellt ein Packet mit allen Informationen
		 *            über den lokalen Spieler1 und verpackt sie in ein
		 *            DatagramPacket. Das Packet wird dann an den entfernten PC
		 *            gesendet wo es dann wieder auseinender geschnitten und in
		 *            die richtigen Datentypen umgewandelt wird.
		 */
		public void sendData(int playerXCoordinate, int playerYCoordinate, int arrowXCoordinate, int arrowYCoordinate,
				boolean deadly) {

			String playerInformation = playerXCoordinate + "," + playerYCoordinate + "," + arrowXCoordinate + ","
					+ arrowYCoordinate + "," + deadly;

			byte[] playerData = new byte[1024];
			playerData = playerInformation.getBytes();
			DatagramPacket packet = new DatagramPacket(playerData, playerData.length, ipAddress, port);

			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
