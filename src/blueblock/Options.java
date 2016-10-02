package blueblock;

import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Options implements PropertyChangeListener, ActionListener, ItemListener {
	private int MaxX = 56;
	private int MaxY = 56;
	private int Player = 4;
	private int powerUps = 10;
	private boolean mouse = true;
	private boolean kill = true;
	final Menu menu;
	JFrame options;
	JLabel tHeight, tWidth;
	JButton reset, save, abort;

	JFormattedTextField eHeight, eWidth, PowerUpNumber;
	JCheckBox Mouse, Kill;
	Choice PlayerKill, PowerUps;
	final ImageIcon BlueBlock = new ImageIcon("src/img/blblockbut.png");
	final ImageIcon RedBlock = new ImageIcon("src/img/rdblockbut.png");

	public Options(Menu menu) {
		this.menu = menu;
		LoadSettings();
		System.out.println(MaxX);
		options = new JFrame("Einstellungen");
		options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		options.setBounds(300, 300, 450, 370);
		options.setLayout(null);

		tHeight = new JLabel("Höhe:");
		options.add(tHeight);
		tHeight.setFont(new Font("Chiller", 2, 30));
		tHeight.setBounds(100, 10, 60, 40);

		eHeight = new JFormattedTextField(int.class);
		options.add(eHeight);
		eHeight.setValue(MaxY);
		eHeight.setFont(new Font("Chiller", 0, 30));
		eHeight.setBounds(160, 10, 45, 40);
		eHeight.addPropertyChangeListener(this);

		tWidth = new JLabel("Width:");
		options.add(tWidth);
		tWidth.setFont(new Font("Chiller", 2, 30));
		tWidth.setBounds(215, 10, 70, 40);

		eWidth = new JFormattedTextField(int.class);
		options.add(eWidth);
		eWidth.setValue(MaxX);
		eWidth.setFont(new Font("Chiller", 0, 30));
		eWidth.setBounds(285, 10, 45, 40);
		eWidth.addPropertyChangeListener(this);

		JLabel PlayerText = new JLabel("Spieler:");
		options.add(PlayerText);
		PlayerText.setFont(new Font("Chiller", 1, 30));
		PlayerText.setBounds(20, 60, 90, 40);

		PlayerKill = new Choice();
		for (int i = 1; i < 5; i++)
			PlayerKill.add(i + "");
		options.add(PlayerKill);
		PlayerKill.setBounds(110, 60, 80, 40);
		PlayerKill.setFont(new Font("Arial", 1, 30));
		PlayerKill.select(PlayerKill + "");
		PlayerKill.addItemListener(this);

		JLabel TPowerUps = new JLabel("Power-Ups:");
		options.add(TPowerUps);
		TPowerUps.setFont(new Font("Chiller", 1, 30));
		TPowerUps.setBounds(200, 60, 130, 40);

		PowerUps = new Choice();
		for (int i = 0; i < 11; i++)
			PowerUps.add(i + "");
		options.add(PowerUps);
		PowerUps.select(powerUps + "");
		PowerUps.setFont(new Font("Chiller", 1, 30));
		PowerUps.setBounds(340, 60, 60, 40);

		Mouse = new JCheckBox(BlueBlock);
		Mouse.setSelectedIcon(RedBlock);
		Mouse.setSelected(mouse);
		Mouse.setText("Maus Aktivieren");
		Mouse.setFont(new Font("Chiller", 1, 30));
		Mouse.setToolTipText("Displays whether the mouse should be disabled in games or not.");
		Mouse.setBounds(70, 120, 240, 60);
		Mouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mouse)
					mouse = false;
				else
					mouse = true;
			}
		});
		options.add(Mouse);

		Kill = new JCheckBox(BlueBlock);
		Kill.setSelectedIcon(RedBlock);
		Kill.setSelected(kill);
		Kill.setText("Player-Kill aktivieren");
		Kill.setFont(new Font("Chiller", 1, 30));
		Kill.setToolTipText("Displays wether players should kill each other or not.");
		Kill.setBounds(70, 190, 310, 60);
		Kill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (kill)
					kill = false;
				else
					kill = true;
			}
		});
		options.add(Kill);

		save = new JButton("Speichern");
		save.addActionListener(this);

		reset = new JButton("Zurücksetzen");
		reset.addActionListener(this);

		abort = new JButton("Abbrechen");
		abort.addActionListener(this);

		options.add(save);
		options.add(reset);
		options.add(abort);

		save.setBounds(10, 270, 120, 40);
		save.setFont(new Font("Harrington", 1, 15));
		reset.setBounds(140, 270, 150, 40);
		reset.setFont(new Font("Harrington", 1, 17));
		abort.setBounds(300, 270, 120, 40);
		abort.setFont(new Font("Harrington", 1, 15));

	}

	public void onStart(boolean onStart) {
		options.setVisible(onStart);
		LoadSettings();
		eHeight.setText(MaxY + "");
		eWidth.setText(MaxX + "");
		PlayerKill.select(PlayerKill + "");
		PowerUps.select(powerUps + "");
	}

	private void SaveSettings() {
		PrintWriter pWriter = null;
		int Y = Integer.parseInt(eHeight.getText());
		int X = Integer.parseInt(eWidth.getText());
		int player = Integer.parseInt(PlayerKill.getSelectedItem());
		int PUs = Integer.parseInt(PowerUps.getSelectedItem());
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter("settings.txt")));
			if (Y > 56)
				Y = 56;
			if (X > 56)
				X = 56;
			if (Y < 6) {
				PUs = 2;
				Y = 6;
			}
			if (X < 6) {
				PUs = 2;
				X = 6;
			}
			pWriter.println("Höhe: " + Y);
			pWriter.println("Breite: " + X);
			pWriter.println("Spieler: " + player);
			pWriter.println("PowerUps: " + PUs);
			pWriter.println("Mouse: " + mouse);
			pWriter.println("Kill: " + kill);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (pWriter != null) {
				pWriter.flush();
				pWriter.close();
			}
		}
	}

	public void LoadSettings() {
		// File f = new File("settings.txt");
		BufferedReader br;

		try {
			FileReader fr = new FileReader("settings.txt");
			br = new BufferedReader(fr);

			int Values[] = new int[4];
			String names[] = { "Höhe: ", "Breite: ", "Spieler: ", "PowerUps: ", "Mouse: ", "Kill: " };

			for (int i = 0; i < Values.length; i++) {
				Values[i] = Integer.parseInt(br.readLine().substring(names[i].length()));
				System.out.println(Values[i]);
			}
			String Mouse = br.readLine().substring(names[4].length());
			System.out.println(Mouse);
			if (Mouse.equals("true"))
				mouse = true;
			else
				mouse = false;
			String Kill = br.readLine().substring(names[5].length());
			System.out.println(Kill);
			if (Kill.equals("true"))
				kill = true;
			else
				kill = false;

			MaxY = Values[0];
			MaxX = Values[1];
			Player = Values[2];
			powerUps = Values[3];

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
	}

	@Override
	public void itemStateChanged(ItemEvent IE) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(reset)) {
			eHeight.setValue(16);
			eWidth.setValue(16);
			PlayerKill.select("4 Spieler");
			PowerUps.select("0");
			Mouse.setSelected(true);
			mouse = true;
			Kill.setSelected(true);
			kill = true;
			return;
		} else if (e.getSource().equals(save)) {
			SaveSettings();
		}
		onStart(false);
		menu.onStart(true);
	}

	public int Player() {
		return Player;
	}

	public int PowerUps() {
		return powerUps;
	}

	public int X() {
		return MaxX;
	}

	public int Y() {
		return MaxY;
	}

	public boolean HasMouse() {
		return mouse;
	}

	public boolean PlayerKill() {
		return kill;
	}
}
