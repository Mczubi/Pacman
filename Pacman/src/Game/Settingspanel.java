package Game;

import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Settingspanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImagePanel settings;
	private JButton[] setti = new JButton[3];
	private boolean musicpressed = false;
	private MP3Audio russia;
	private char theme = 'c';
	private boolean bigsize = false;

	public Settingspanel() {
		setSize(getSize());
		setBounds(0, 0, 400, 400);
		setLayout(null);
//		try {
//			russia = new MP3Audio("/Game/russia.wav");
//		} catch (Exception e) {
//		}
		iniButtons();
		setVisible(true);
	}

	private void iniButtons() {

		try {
			settings = new ImagePanel("/Game/settingsimg.png");
			settings.setLayout(null);
			settings.setSize(getSize());
		} catch (IOException e) {

			e.printStackTrace();
		}

		for (int i = 0; i < setti.length; i++) {
			setti[i] = new JButton();
			setti[i].setVerticalTextPosition(SwingConstants.CENTER);
			setti[i].setForeground(Color.blue);
			settings.add(setti[i]);
		}
		setti[0].setBounds(150, 150, 100, 30);
		setti[1].setBounds(150, 100, 100, 30);
		setti[2].setBounds(150, 200, 100, 30);
		setti[0].setText("Music");
		setti[1].setText("Theme");
		setti[2].setText("Perhaps?");
		if (theme != 'c') {
			setti[1].setForeground(Color.LIGHT_GRAY);
		} else {
			setti[1].setForeground(Color.blue);
		}
		setti[0].addActionListener(new ActionListener()

		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (!musicpressed) {
						setBackground(Color.red);
						try {
							russia = new MP3Audio("/Game/russia.mp3");
						} catch (Exception e) {
						}
						russia.start();
						musicpressed = true;
					} else {
						russia.interrupt();
						musicpressed = false;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		setti[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if (theme == 'c') {
					theme = 'l';
					setti[1].setForeground(Color.LIGHT_GRAY);
				} else {
					theme = 'c';
					setti[1].setForeground(Color.blue);
				}
			}
		});
		setti[2].addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {

				bigsize = theme == 'l' && musicpressed;
				

			}
		});

	}

	public boolean getBigsize() {
		return bigsize;
	}

	public char getTheme() {
		return theme;
	}

	public void setTheme(char theme) {
		this.theme = theme;
	}

	public ImagePanel getImage() {
		return settings;
	}

}
