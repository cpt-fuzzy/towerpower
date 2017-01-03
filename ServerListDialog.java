package towerPower;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Tim & Niko
 * @version 0.1
 */

public class ServerListDialog extends JDialog {
	private String name;
	private boolean isQueued = false;

	DefaultListModel<String> model = new DefaultListModel<>();
	JList<String> list = new JList<>(model);
	JScrollPane scrollPane = new JScrollPane(list);

	/**
	 * Konstruktor eines ServerList-Dialogs, zum auswählen eines Servers
	 * 
	 * @param parent Parent-Frame des Dialogs, hier: GameSetup
	 * @throws IOException
	 */

	public ServerListDialog(JFrame parent, String user) throws IOException {
		// Einstellungen des Dialogs
		super(parent, "Choose your server", true);
		name = user;

		setTitle("Choose your server");

		JButton back = new JButton("Back");
		JButton join = new JButton("Join");

		list.setFixedCellWidth(200);

		JPanel listPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		Thread client = new Thread(new ClientCreator(this, model));
		client.start();

		listPanel.add(scrollPane);

		buttonPanel.add(join);
		buttonPanel.add(back);

		add(listPanel, BorderLayout.WEST);
		add(buttonPanel, BorderLayout.SOUTH);

		// Hier wird dem Host gejoint
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isQueued = true;
			}
		});

		// Schließt den Dialog und führt zum GameSetup zurück
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setSize(250, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Gibt den Clientnamen wieder
	 */

	@Override
	public String getName() {
		return name;
	}

	/**
	 * False, solange der Client keinem Server joint
	 * 
	 * @return
	 */

	public boolean isQueued() {
		return isQueued;
	}

	/**
	 * Gibt die ausgewählte IP in der JList wieder
	 * 
	 * @return
	 */

	public int getSelected() {
		return list.getSelectedIndex();
	}

}
