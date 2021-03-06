package blueblock;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class InfoWindow extends JFrame {
	private static final long serialVersionUID = -628078885004570565L;
	private static String BlueBlockAlive, GreenBlockAlive, MagentaBlockAlive, CyanBlockAlive, BlueBlockposition,
			GreenBlockposition, MagentaBlockposition, CyanBlockposition, TextScore;
	private static boolean BlueBlockBool, GreenBlockBool, MagentaBlockBool, CyanBlockBool;
	private static int BlueBlockScore, GreenBlockScore, MagentaBlockScore, CyanBlockScore, MouseScore;
	private int Spieler;
	JFrame InfoFenster;
	JTextArea InfoLog;
	JTextArea Score, BlueBlock, GreenBlock, MagentaBlock, CyanBlock;
	JButton NewGame;
	final ImageIcon New_Game = new ImageIcon("src/img/New Game.png");

	public InfoWindow(int Spieler) {
		this.Spieler = Spieler;
		InfoFenster = new JFrame("Informationen");
		InfoFenster.setLocation(700, 0);
		InfoFenster.setSize(300, 700);
		InfoFenster.setLayout(null);
		InfoFenster.setResizable(false);
		InfoFenster.setFocusable(false);
		InfoLog = new JTextArea();
		InfoLog.setBounds(10, 10, 260, 35);
		InfoLog.setFont(new Font("papyrus", 1, 25));
		InfoLog.setText("          Ereignislog:");
		InfoLog.setSelectionColor(Color.WHITE);
		InfoLog.setEditable(false);
		InfoFenster.add(InfoLog);
		NewGame = new JButton(New_Game);
		NewGame.setBounds(10, 55, 260, 40);
		NewGame.setFont(new Font("papyrus", 1, 25));
		NewGame.setToolTipText(
				"Beendet das laufende Spiel und �ffnet das Men�.\nNach dem Dr�cken des Knopfen eine Aktion auf dem Spielfeld ausf�hren.");
		NewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.EndGame = true;
				Refresh();
			}

		});
		InfoFenster.add(NewGame);
		Score = new JTextArea();
		Score.setBounds(10, 110, 260, 140);
		Score.setFont(new Font("forte", Font.ITALIC, 15));
		Score.setBackground(Color.YELLOW);
		Score.setEditable(false);
		for (int i = 0; i < Spieler; i++) {
			switch (i) {
			case 0:
				TextScore = "\n Blauer Spieler: " + BlueBlockScore;
				break;
			case 1:
				TextScore += "\n Gr�ner Spieler: " + GreenBlockScore;
				break;
			case 2:
				TextScore += "\n Cyaner Spieler: " + CyanBlockScore;
				break;
			case 3:
				TextScore += "\n Magenta Spieler: " + MagentaBlockScore;
				break;
			}
			TextScore += "\n Maus: " + MouseScore;
		}
		Score.setText("PUNKTE:" + TextScore);
		InfoFenster.add(Score);
		BlueBlockBool = true;
		BlueBlock = new JTextArea();
		BlueBlock.setBounds(10, 255, 260, 90);
		BlueBlock.setFont(new Font("forte", Font.ITALIC, 15));
		BlueBlock.setBackground(Color.BLUE);
		BlueBlock.setForeground(Color.WHITE);
		BlueBlock.setEditable(false);
		BlueBlock.setText(BlueBlockAlive + "\n" + BlueBlockposition + "\nKills: 0");
		InfoFenster.add(BlueBlock);
		switch (Spieler) {
		case 4:
			MagentaBlockBool = true;
			MagentaBlock = new JTextArea();
			MagentaBlock.setBounds(10, 540, 260, 90);
			MagentaBlock.setFont(new Font("forte", Font.ITALIC, 15));
			MagentaBlock.setBackground(Color.MAGENTA);
			MagentaBlock.setEditable(false);
			MagentaBlock.setText(MagentaBlockAlive + "\n" + GreenBlockposition + "\nKills: 0");
			InfoFenster.add(MagentaBlock);
		case 3:
			CyanBlockBool = true;
			CyanBlock = new JTextArea();
			CyanBlock.setBounds(10, 445, 260, 90);
			CyanBlock.setFont(new Font("forte", Font.ITALIC, 15));
			CyanBlock.setBackground(Color.CYAN);
			CyanBlock.setEditable(false);
			CyanBlock.setText(CyanBlockAlive + "\n" + MagentaBlockposition + "\nKills: 0");
			InfoFenster.add(CyanBlock);
		case 2:
			GreenBlockBool = true;
			GreenBlock = new JTextArea();
			GreenBlock.setBounds(10, 350, 260, 90);
			GreenBlock.setFont(new Font("forte", Font.ITALIC, 15));
			GreenBlock.setBackground(new Color(10, 220, 10));
			GreenBlock.setEditable(false);
			GreenBlock.setText(GreenBlockAlive + "\n" + CyanBlockposition + "\nKills: 0");
			InfoFenster.add(GreenBlock);
		default:
		}
		BlueBlockposition = "Platz: - ";
		GreenBlockposition = "Platz: - ";
		CyanBlockposition = "Platz: - ";
		MagentaBlockposition = "Platz: - ";
		InfoFenster.repaint();
	}

	public void offen(boolean offen) {
		InfoFenster.setVisible(offen);
		if (Main.EndGame)
			this.dispose();
	}

	public void Refresh() {
		TextScore = "PUNKTE:";
		if (Main.H1.Lives() && Main.H1.Poisoned()) {
			BlueBlockAlive = "Blauer Spieler: Vergiftet";
		} else if (Main.H1.Lives()) {
			BlueBlockAlive = "Blauer Spieler: Lebt";
		} else if (BlueBlockBool) {
			BlueBlockBool = false;
			BlueBlockAlive = "Blauer Spieler: Tod";
			BlueBlockposition = "Platz: " + Spieler;
			Spieler--;
		}
		if (Main.H1.Lives() && Main.H1.Gesch�tzt())
			BlueBlockAlive += " und Ger�stet";
		BlueBlockScore = Main.H1.Kills * 300 + Main.H1.schritte + Main.H1.SuperScore * 50 + Main.H1.PowerUps * 5;
		TextScore += "\n Blauer Spieler: " + BlueBlockScore;
		BlueBlock.setText(BlueBlockAlive + "\n" + BlueBlockposition + "\nKills:" + Main.H1.Kills);
		try {
			if (Main.H2.Lives() && Main.H2.Poisoned()) {
				GreenBlockAlive = "Gr�ner Spieler: Vergiftet";
			} else if (Main.H2.Lives()) {
				GreenBlockAlive = "Gr�ner Spieler: Lebt";
			} else if (GreenBlockBool) {
				GreenBlockBool = false;
				GreenBlockAlive = "Gr�ner Spieler: Tod";
				GreenBlockposition = "Platz: " + Spieler;
				Spieler--;
			}
			if (Main.H2.Lives() && Main.H2.Gesch�tzt())
				GreenBlockAlive += " und Ger�stet";
			GreenBlockScore = Main.H2.Kills * 300 + Main.H2.schritte + Main.H2.SuperScore * 50 + Main.H2.PowerUps * 5;
			TextScore += "\n Gr�ner Spieler: " + GreenBlockScore;
			GreenBlock.setText(GreenBlockAlive + "\n" + GreenBlockposition + "\nKills:" + Main.H2.Kills);
		} catch (Exception E) {
		}
		try {
			if (Main.H3.Lives() && Main.H3.Poisoned()) {
				CyanBlockAlive = "Cyaner Spieler: Vergiftet";
			} else if (Main.H3.Lives()) {
				CyanBlockAlive = "Gr�ner Spieler: Lebt";
			} else if (CyanBlockBool) {
				CyanBlockBool = false;
				CyanBlockAlive = "Cyaner Spieler: Tod";
				CyanBlockposition = "Platz: " + Spieler;
				Spieler--;
			}
			if (Main.H3.Lives() && Main.H3.Gesch�tzt())
				CyanBlockAlive += " und Ger�stet";
			CyanBlockScore = Main.H3.Kills * 300 + Main.H3.schritte + Main.H3.SuperScore * 50 + Main.H3.PowerUps * 5;
			TextScore += "\n Cyaner Spieler: " + CyanBlockScore;
			CyanBlock.setText(CyanBlockAlive + "\n" + CyanBlockposition + "\nKills:" + Main.H3.Kills);
		} catch (Exception E) {
		}
		try {
			if (Main.H4.Lives() && Main.H4.Poisoned()) {
				MagentaBlockAlive = "Magenta Spieler: Vergiftet";
			} else if (Main.H4.Lives()) {
				MagentaBlockAlive = "Magenta Spieler: Lebt";
			} else if (MagentaBlockBool) {
				MagentaBlockBool = false;
				MagentaBlockAlive = "Magenta Spieler: Tod";
				MagentaBlockposition = "Platz: " + Spieler;
				Spieler--;
			}
			if (Main.H4.Lives() && Main.H4.Gesch�tzt())
				MagentaBlockAlive = MagentaBlockAlive + " und Ger�stet";
			MagentaBlockScore = Main.H4.Kills * 300 + Main.H4.schritte + Main.H4.SuperScore * 50 + Main.H4.PowerUps * 5;
			TextScore += "\n Magenta Spieler: " + MagentaBlockScore;
			MagentaBlock.setText(MagentaBlockAlive + "\n" + MagentaBlockposition + "\nKills:" + Main.H4.Kills);
		} catch (Exception E) {
		}
		MouseScore = Main.MouseLava + Main.MouseWall * 2 + Main.MouseS�ure * 3;
		TextScore += "\n Maus: " + MouseScore;
		Score.setText(TextScore);
		InfoFenster.repaint();
		/*
		 * for (int i = 0; i == Main.h.size(); i++) { int Score; String Alive =
		 * ""; String Position = ""; if (Main.h.get(i).Lebt() &&
		 * Main.h.get(i).Poisoned()) { Alive = Main.h.get(i).GetName() +
		 * ": Vergiftet"; } else if (Main.h.get(i).Lebt()) { Alive =
		 * Main.h.get(i).GetName() + ": Lebt"; } else if (!Main.h.get(i).Lebt())
		 * { Alive = Main.h.get(i).GetName() + ": Tod"; Position = "Platz: " +
		 * Spieler; Spieler--; } if (Main.h.get(i).Lebt() &&
		 * Main.h.get(i).Gesch�tzt()) Alive += " und Ger�stet"; Score =
		 * Main.h.get(i).Kills * 200 + Main.h.get(i).schritte +
		 * Main.h.get(i).SuperScore * 50 + Main.h.get(i).PowerUps * 5; TextScore
		 * += "\n" + Main.h.get(i).GetName() + ": " + Score; switch
		 * (Main.h.get(i).Spieler) { case 0: BlueBlock.setText(Alive + "\n" +
		 * Position + "\nKills" + Main.h.get(i).Kills); break; case 1:
		 * GreenBlock.setText(Alive + "\n" + Position + "\nKills" +
		 * Main.h.get(i).Kills); break; case 2: CyanBlock.setText(Alive + "\n" +
		 * Position + "\nKills" + Main.h.get(i).Kills); break; case 3:
		 * MagentaBlock.setText(Alive + "\n" + Position + "\nKills" +
		 * Main.h.get(i).Kills); break; default: } }
		 */
	}

}
