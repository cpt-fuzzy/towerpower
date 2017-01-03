package towerPower;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Setup Fenster des Spiels. Hier kann zwischen Client und Server ausgewählt
 * werden
 * 
 * @author Tim & Niko
 * @version 0.1
 */

public class GameSetup extends JFrame {

	private boolean isReadyP1 = false;

	private JTextField nameP1;

	/**
	 * Konstruktor eines Setup-Objekts
	 * 
	 * @param title Titel des Frames
	 */

	public GameSetup(String title) {
		// Einstellungen des Frames
		super(title);

		nameP1 = new JTextField("Enter Name", 10);
		JComboBox<String> serverSelP1 = new JComboBox<>();
		JButton start = new JButton("Start Game");

		serverSelP1.addItem("Host Server");
		serverSelP1.addItem("Join Server");

		start.setEnabled(false);

		JPanel txt = new JPanel();
		JPanel btns = new JPanel();

		txt.add(nameP1);
		add(txt, BorderLayout.NORTH);

		btns.add(serverSelP1);
		btns.add(start);
		add(btns, BorderLayout.SOUTH);

		// siehe private class TextFocus unten
		TextFocus fl = new TextFocus();
		nameP1.addFocusListener(fl);

		// KeyListener, um den Namen zu bestätigen
		nameP1.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					nameP1.setEditable(false);
					start.setEnabled(true);
					nameP1.transferFocus();
					nameP1.removeFocusListener(fl);
				}
			}
		});

		// Entscheidet, welcher Dialog aufgerufen wird anhand der Auswahl in der
		// Combobox
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (serverSelP1.getSelectedItem() == serverSelP1.getItemAt(1)) {

					try {
						new ServerListDialog((JFrame) getParent(), nameP1.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				else if (serverSelP1.getSelectedItem() == serverSelP1.getItemAt(0)) {
					new LobbyDialog(nameP1.getText());
				}
			}
		});

		setSize(250, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public boolean isReadyP1() {
		return isReadyP1;
	}

	public JTextField getNameP1() {
		return nameP1;
	}

	/**
	 * Ändert den Text des Feldes je nachdem, ob der Fokus gesetzt ist, oder
	 * nicht
	 * 
	 * @author Tim & Niko
	 * @version 0.1
	 */

	private class TextFocus implements FocusListener {

		// Dient zum verändern des TextField, je nach dem, ob das Field den
		// Fokus hat oder nicht
		@Override
		public void focusGained(FocusEvent e) {
			if (nameP1.hasFocus()) {
				nameP1.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (nameP1.hasFocus() && nameP1.equals("")) {
				nameP1.setText("Enter Name");
			}
		}

	}

}