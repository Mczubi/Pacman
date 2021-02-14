package Game;

import Gamemech.GamePanel;
import EditPanel.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.LogManager;

import javax.swing.*;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Pacman extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GamePanel game;
	private Startpanel start;
	private EditPanel edit;
	private Settingspanel settings;
	private Container main;

	public Pacman() {
		super("Pacman");
		setBounds(0, 0, 400, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		
		// startFenster
		main = this.getContentPane();
		main.setLayout(null);
		settings = new Settingspanel();
		start = new Startpanel();
		iniStart();
		main.add(start);
		setVisible(true);

	}

	private void gotoStart() {
		main.removeAll();
		repaint();
		iniStart();
	}

	private void gotoSettings() {
		main.removeAll();
		repaint();
		iniSettings();
	}

	private void gotoEditor() {
		main.removeAll();
		repaint();
		iniEditor();
	}

	private void iniEditor() {
		JButton editor = new JButton("Zum Start Bildschirm");
		editor.setBounds(10, 5, 190, 20);
		editor.setVerticalTextPosition(SwingConstants.CENTER);
		editor.setBackground(new Color(115, 115, 115));
		editor.setForeground(new Color(108, 209, 227));
		editor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gotoStart();
			}
		});
		main.add(editor);
		
		edit = new EditPanel();
		main.add(edit);

	}

	private void gotoGamePanel() {
		getContentPane().removeAll();
		repaint();
		iniGamePanel();
	}

	private void iniGamePanel() {

		JButton back = new JButton("Back");
		back.setBounds(160 , 30 ,	80, 30 );
		back.setVerticalTextPosition(SwingConstants.CENTER);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gotoStart();
				setSize(400, 400);
			}
		});
		main.add(back);
		game = new GamePanel(settings.getTheme(), settings.getBigsize());
		main.add(game);

	}

	private void iniSettings() {
		JButton setti = new JButton();
		setti = new JButton("Back");
		setti.setVerticalTextPosition(SwingConstants.CENTER);
		setti.setForeground(Color.blue);
		main.add(setti);
		setti.setBounds(150, 50, 100, 30);
		setti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gotoStart();
			}
		});
		main.add(settings.getImage());

	}

	private void iniStart() {
		JButton[] sta = new JButton[3];
		for (int i = 0; i < sta.length; i++) {
			sta[i] = new JButton();
			sta[i].setBounds(150, 50 + 50 * i, 100, 30);
			sta[i].setVerticalTextPosition(SwingConstants.CENTER);
			sta[i].setForeground(Color.blue);
			main.add(sta[i]);
		}
		sta[0].setText("Play");
		sta[1].setText("Editor");
		sta[2].setText("Settings");

		// settingsButton
		sta[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gotoGamePanel();

			}

		});
		sta[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gotoEditor();

			}

		});
		sta[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gotoSettings();

			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					GlobalScreen.unregisterNativeHook();
				} catch (NativeHookException ex) {

				}
			}
		});

		main.add(start.getImage());
		repaint();
	}

	/**
	 * Wechselt den Zustand von Starter Bildschirm zu Settings Bildschirm
	 */
	public static void main(String[] args) {
		
		try {
			LogManager.getLogManager().reset();
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println(ex);
		}
		new Pacman();
	}
}
