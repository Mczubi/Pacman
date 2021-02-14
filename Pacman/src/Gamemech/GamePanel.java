package Gamemech;

import OpenDialog.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GamePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static boolean secret;
	static JLabel highscore;
	static JLabel life;
	private JButton play;
	private JButton open;
	private Gamefield gamefield;
	private char theme;
	private char[][] map = {
			{ 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
					'w' },
			{ 'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c',
					'w' },
			{ 'w', 'b', 'w', 'c', 'c', 'w', 'w', 'w', 'w', 'w', 'c', 'c', 'w', 'b',
					'w' },
			{ 'w', 'c', 'w', 'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w', 'w', 'c',
					'w' },
			{ 'w', 'c', 'w', 'c', 'c', 'c', 'w', 'n', 'w', 'c', 'c', 'c', 'w', 'c',
					'w' },
			{ 'w', 'c', 'w', 'c', 'w', 'w', 'w', 'n', 'w', 'w', 'w', 'c', 'w', 'c',
					'w' },
			{ 'w', 'c', 'c', 'c', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'c', 'c', 'c',
					'w' },
			{ 'w', 'c', 'w', 'w', 'n', 'w', 'g', 'g', 'g', 'w', 'n', 'w', 'w', 'c',
					'w' },
			{ 'w', 'c', 'w', 'n', 'n', 'w', 'w', 'w', 'w', 'w', 'n', 'n', 'w', 'c',
					'w' },
			{ 'w', 'c', 'w', 'n', 'n', 'n', 'n', 'p', 'n', 'n', 'n', 'n', 'w', 'c',
					'w' },
			{ 'w', 'c', 'c', 'c', 'c', 'w', 'w', 'w', 'w', 'w', 'c', 'c', 'c', 'c',
					'w' },
			{ 'w', 'c', 'c', 'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w', 'c', 'c',
					'w' },
			{ 'w', 'b', 'w', 'w', 'w', 'w', 'c', 'w', 'c', 'w', 'w', 'w', 'w', 'b',
					'w' },
			{ 'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c',
					'w' },
			{ 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
					'w' } };

	public GamePanel(char c, boolean b) {
		GamePanel.secret = b;
		setSize(getSize());
		setBounds(0, 0, 400, 400);
		setLayout(null);
		this.theme = c;
		if (theme == 'c') {
			setBackground(new Color(25, 25, 112));
		} else
			if (theme == 'l' && !secret) {
				setBackground(new Color(184, 134, 11));
			} else {
				setBackground(new Color(205, 0, 0));
			}
		play = new JButton("Play");
		play.setBounds(80, 30, 80, 30);
		play.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				gameIni();
			}
		});
		open = new JButton("Open");
		open.setBounds(240, 30, 80, 30);
		open.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				OpenDialog openFrame = new OpenDialog(null);
				if (openFrame.showDialog() == 0) {
					map = openFrame.getMap();
					gameIni();
				}
			}
		});
		this.add(open);
		this.add(play);
		setVisible(true);
	}

	private void gameIni() {
		jLabelini();
		gotoGamefield();
	}

	private void gotoGamefield() {
		if (gamefield != null)
			this.remove(gamefield);
		repaint();
		gamefield = new Gamefield(theme, map, secret);
		this.add(gamefield);
		getRootPane().repaint();
	}

	private void jLabelini() {
		if (life != null && highscore != null) {
			this.remove(life);
			this.remove(highscore);
		}
		highscore = new JLabel("Highscore:");
		highscore.setBounds(80, 70, 120, 30);
		if (theme == 'c') {
			highscore.setForeground(Color.lightGray);
		} else {
			highscore.setForeground(Color.black);
		}

		this.add(highscore);
		// Schwonz zu kloan für a
		life = new JLabel("Leben:");
		life.setBounds(250, 70, 100, 30);
		if (theme == 'c') {
			life.setForeground(Color.lightGray);
		} else {
			life.setForeground(Color.BLACK);
		}
		this.add(life);

	}

	public static void newHighscore(int i) {
		highscore.setText("Highscore: " + i);
	}

	public static void newLifeammount(int i) {
		life.setText("Leben: " + i);
	}

}
