package OpenDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class OpenDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int FELDSIZE = 15;
	private final boolean DEBUG = false;

	private JButton open;
	private JComboBox<String> csvfiles;
	private JLabel text;
	private int result = -1;

	private char[][] map = new char[FELDSIZE][FELDSIZE];

	// TODO import string from file and parse it into a 2d char array

	public OpenDialog(java.awt.Frame parent) {
		super(parent, true);
		setLayout(null);
		this.setBackground(new Color(0,0,128));
		setBounds(0, 0, 300, 150);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Öffnen");

		setButtonsLables();
		setComboBox();
	}

	public char[][] getMap() {
		return map;
	}

	public int showDialog() {
		setVisible(true);
		return result;
	}

	private void setComboBox() {

		// TODO clean code

		// Files in active directory
		File aktVerz = new File(".");
		String[] files = aktVerz.list();

		// Only .csv files in new Array
		for (int i = 0; i < files.length; i++)
			if (!isCsv(files[i]))
				files[i] = null;

		// How many .csv files there are
		int ncsv = 0;
		for (String i : files)
			if (i != null)
				ncsv++;

		// If there are any, fill them into an array (csvs)
		if (ncsv > 0) {
			String[] csvs = new String[ncsv];
			int n = 0;
			for (int i = 0; i < files.length; i++)
				if (files[i] != null) {
					csvs[n] = files[i];
					n++;
				}

			csvfiles = new JComboBox<String>(csvs);
			csvfiles.setBounds(10, 50, 150, 30);

			this.getContentPane().add(csvfiles);
		} else
			JOptionPane.showMessageDialog(null, "Keine Maps gefunden", "Fehler", 1);

	}

	private boolean isCsv(String name) {
		boolean ret = true;
		String csv = ".csv";

		// Filename shorter than 4 chars, can't be .csv
		String end;
		if (name.length() > 4)
			end = name.substring(name.length() - 4, name.length());
		else
			return false;

		// If last 4 chars are .csv return = true else false
		for (int i = 0; i < 4 && ret; i++)
			if (csv.charAt(i) != end.charAt(i))
				ret = false;

		return ret;
	}

	private void setButtonsLables() {
		open = new JButton("Open");
		open.setBounds(180, 50, 100, 30);
		open.addActionListener(new OpenFileButt());

		this.getContentPane().add(open);

		text = new JLabel("Datei auswählen");
		text.setBounds(10, 20, 200, 30);

		this.getContentPane().add(text);
	}

	public class OpenFileButt implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			BufferedReader br = null;
			FileReader fr = null;
			String input = null;

			// br = new BufferedReader(new FileReader(FILENAME));
			try {
				fr = new FileReader(csvfiles.getSelectedItem().toString());
				br = new BufferedReader(fr);

				input = br.readLine();

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String[] chars = input.split(";");

			if (DEBUG) {
				for (int i = 0; i < chars.length; i++) {
					System.out.print(chars[i] + " ");
					if ((i + 1) % 15 == 0 && i > 0)
						System.out.println();
				}
				System.out.println();
			}

			// Chars in csv equals the size of the map
			if (chars.length == FELDSIZE * FELDSIZE) {
				for (int i = 0; i < FELDSIZE * FELDSIZE; i++) {
					map[i / FELDSIZE][i % FELDSIZE] = chars[i].charAt(0);
				}

				if (DEBUG) {
					for (int i = 0; i < FELDSIZE; i++) {
						for (int j = 0; j < FELDSIZE; j++) {
							System.out.print(map[i][j] + " ");
						}
						System.out.println();
					}
				}

			} else
				JOptionPane.showMessageDialog(null, "Datei ist keine Pacman Map", "Laden fehlgeschlagen", 1);

			result = 0;

			setVisible(false);
			dispose();
		}
	}
}
