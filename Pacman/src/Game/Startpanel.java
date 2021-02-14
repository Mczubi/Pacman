package Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class Startpanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImagePanel start;
	private JButton sta = new JButton();

	public Startpanel() {
		setSize(getSize());
		setBounds(0, 0, 400, 400);
		setLayout(null);
		try {
			start = new ImagePanel("/Game/startimg.png");
			start.setLayout(null);
			start.setSize(getSize());

		} catch (IOException e) {

			e.printStackTrace();
		}
		iniButtons();
		setVisible(true);
	}
	private void iniButtons() {
		// bei der 0ten Stelle playButton, 1 ten Stelle editorButton, 2
		// settingsButton, 3 ist ExitButton
		sta = new JButton();
		sta.setBounds(150, 200, 100, 30);
		sta.setVerticalTextPosition(SwingConstants.CENTER);
		sta.setForeground(Color.blue);
		start.add(sta);
		sta.setText("Exit");
		sta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});
	}
	public ImagePanel getImage() {
		return start;
	}

}
