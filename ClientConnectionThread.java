package towerPower;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import javax.swing.DefaultListModel;

/**
 * Dieser Thread sucht nach neuen Servern
 * @author Tim & Niko
 * @version 0.2
 */

public class ClientConnectionThread extends Thread{

	private String host = "";
	private boolean isRunning = true;
	private MulticastSocket socket;
	private DefaultListModel<String> model;
	
	/**
	 * Der Client tritt hier einer Multicast Gruppe bei
	 * @param client Client des Spielers
	 * @param model ListModel des ServerListDialogs
	 * @throws IOException
	 */
	
	public ClientConnectionThread(GameClient client, DefaultListModel<String> model) throws IOException {
		socket = new MulticastSocket(4446);
		InetAddress group = InetAddress.getByName("224.0.1.1");
		socket.joinGroup(group);
		this.model = model;
		
		
	}
	
	/**
	 * Übergibt den empfangenen Host
	 * @return
	 */
	
	public String pushHost(){
		return host;
	}
	
	/**
	 * Dient zum resetten des Host-Strings
	 */
	
	public void resetHost(){
		host = "";
	}
	
	/**
	 * Beendet den Thread, sobald er nicht mehr benötigt wird
	 */
	
	public void stopThread(){
		isRunning = false;
		socket.close();
	}

	/**
	 * Sucht nach verfügbaren Hosts und fügt die IPs hinzu
	 */
	
	@Override
	public void run() {
		while(isRunning){
			
			DatagramPacket packet;
			byte[] buf = new byte[256];
			packet = new DatagramPacket(buf,buf.length);
			
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String answer = new String(packet.getData());
			host = answer;
			model.addElement(host);
		}
		
	}
	
}
