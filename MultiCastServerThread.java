package towerPower;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Klasse zum Senden der IP des Hosts über einen Multicast
 * 
 * @author Tim & Niko
 * @version 0.3
 */

public class MultiCastServerThread extends Thread {

	private boolean isConnected = false;
	private String response = "";
	private DatagramSocket socketData = null;
	private InetAddress host;

	/**
	 * Hier wird ein DatagramSocket auf dem Port 4445 erstellt
	 * 
	 * @param name Name des MultiCasts
	 * @throws IOException
	 */

	public MultiCastServerThread(String name) throws IOException {
		socketData = new DatagramSocket(4445);
	}

	/**
	 * Die IP des Hosts wird ausgelesen und als DatagramPacket an die MulitCast
	 * Gruppe gesendet
	 */

	public void run() {
		while (!isConnected) {

			try {
				byte[] buf = new byte[256];

				host = InetAddress.getLocalHost();
				String hostIP = host.getHostAddress();
				buf = hostIP.getBytes();

				InetAddress group = InetAddress.getByName("224.0.1.1");
				DatagramPacket packet;
				int clientPort = 4446;
				packet = new DatagramPacket(buf, buf.length, group, clientPort);
				socketData.send(packet);

				// Der Multicast wartet hier auf ein Packet, um einen Fehler im
				// Programm zu vermeiden. Ein richtiger Fix wurde nicht
				// rechtzeitig gefunden
				socketData.receive(packet);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		socketData.close();
	}
}
