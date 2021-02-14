package EditPanel;

import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.swing.*;

import OpenDialog.OpenDialog;

// TODO Autoscroll in matrix?

public class EditPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FELDSIZE = 15;
	private final boolean DEBUG = false;

	private boolean hasPlayer = false;

	// g = ghost(max 4) w = wall p = player(max 1) c = coins b = big coin n =
	// null(air)
	private char[][] charMatrix = new char[FELDSIZE][FELDSIZE];
	private char objChar;

	private MatrixButton[][] feldMatrix = new MatrixButton[FELDSIZE][FELDSIZE];
	private OptionButton[] options = new OptionButton[6];

	private JTextField mapName = new JTextField();
	private JButton save = new JButton();
	private JButton open = new JButton();

	public EditPanel() {
		super();

		setLayout(null);
		setBounds(0, 0, 400, 400);
		setBackground(new Color(71, 75, 150));

		// GUI
		setSaveOpenStuff();
		setMatrix();
		setOptions();

		// Initialize charMatrix with 'n'
		setCharMatrix();
	}

	// ######################### ACTIONLISTENER #######################

	private class OptionButt implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Set all option Buttons grey
			for (int i = 0; i < options.length; i++)
				options[i].setBackground(Color.DARK_GRAY);

			// Set the clicked one to red and objChar to the char of the pressed Button
			if (e.getSource() instanceof OptionButton) {
				OptionButton source = (OptionButton) e.getSource();
				source.setBackground(new Color(34, 139, 34));
				objChar = source.getC();
			}
		}
	}

	private class MatrixButt implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Set the char in charMatrix to the objChar (and change color)
			if (e.getSource() instanceof MatrixButton) {
				MatrixButton source = (MatrixButton) e.getSource();

				// Set current char in the position of the pressed Button
				if (!(hasPlayer && objChar == 'p'))
					charMatrix[source.getXpos()][source.getYpos()] = objChar;
				
				// Color the pressed Button a color for the selected Object
				String path = "/EditPanel/Sprites/";
				String[] images = { path + "Pac20.png", path + "GhostRed20.png", path + "BlueWall20.png",
						path + "Coin20.png", path + "CoinBig20.png", path + "Nichts.png" };

				Image img = null;
				switch (objChar) {
				case 'p':
					if (!hasPlayer) {
						try {
							img = ImageIO.read(getClass().getResource(images[0]));
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						source.setIcon(new ImageIcon(img));
						hasPlayer = true;
					} else {
						options[0].setBackground(Color.red);
						;
					}
					break;
				case 'g':
					try {
						img = ImageIO.read(getClass().getResource(images[1]));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					source.setIcon(new ImageIcon(img));
					break;
				case 'w':
					try {
						img = ImageIO.read(getClass().getResource(images[2]));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					source.setIcon(new ImageIcon(img));
					break;
				case 'c':
					try {
						img = ImageIO.read(getClass().getResource(images[3]));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					source.setIcon(new ImageIcon(img));
					break;
				case 'b':
					try {
						img = ImageIO.read(getClass().getResource(images[4]));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					source.setIcon(new ImageIcon(img));
					break;
				default:
					source.setBackground(Color.DARK_GRAY);
					source.setIcon(null);
					if (charMatrix[source.getXpos()][source.getYpos()] == 'p') {
						hasPlayer = false;
					}

					break;
				}
			}
		}
	}

	private class SaveButt implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String dateiname = mapName.getText();

			if (dateiname.length() > 0)
				exportMap();
			else
				JOptionPane.showMessageDialog(null, "Geben Sie einen Dateinamen an", "Dateinamen", 1);
		}
	}

	private class OpenButt implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OpenDialog openFrame = new OpenDialog(null);
			if (openFrame.showDialog() == 0) {
				charMatrix = openFrame.getMap();

				hasPlayer = false;

				for (int i = 0; i < FELDSIZE; i++) {
					for (int j = 0; j < FELDSIZE; j++) {

						// Update Buttons and color for the selected Object
						String path = "/EditPanel/Sprites/";
						String[] images = { path + "Pac20.png", path + "GhostRed20.png", path + "BlueWall20.png",
								path + "Coin20.png", path + "CoinBig20.png", path + "Nichts.png" };

						Image img = null;
						switch (charMatrix[j][i]) {
						case 'p':
							hasPlayer = true;
							try {
								img = ImageIO.read(getClass().getResource(images[0]));
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							feldMatrix[i][j].setIcon(new ImageIcon(img));
							break;
						case 'g':
							try {
								img = ImageIO.read(getClass().getResource(images[1]));
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							feldMatrix[i][j].setIcon(new ImageIcon(img));
							break;
						case 'w':
							try {
								img = ImageIO.read(getClass().getResource(images[2]));
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							feldMatrix[i][j].setIcon(new ImageIcon(img));
							break;
						case 'c':
							try {
								img = ImageIO.read(getClass().getResource(images[3]));
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							feldMatrix[i][j].setIcon(new ImageIcon(img));
							break;
						case 'b':
							try {
								img = ImageIO.read(getClass().getResource(images[4]));
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							feldMatrix[i][j].setIcon(new ImageIcon(img));
							break;
						default:
							feldMatrix[i][j].setBackground(Color.DARK_GRAY);
							feldMatrix[i][j].setIcon(null);
							break;
						}
					}
				}
			}
		}
	}

	// ############################ METHODS ###########################

	private void setCharMatrix() {
		for (int i = 0; i < FELDSIZE; i++)
			for (int j = 0; j < FELDSIZE; j++)
				charMatrix[i][j] = 'n';
	}

	private void exportMap() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(mapName.getText() + ".csv", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println(charMtoCSV());
		writer.close();
	}

	public String charMtoCSV() {
		String ret = "";
		for (int i = 0; i < FELDSIZE; i++)
			for (int j = 0; j < FELDSIZE; j++)
				ret += charMatrix[j][i] + ";";
		return ret;
	}

	// ########################### GUI SETUP ##########################

	private void setSaveOpenStuff() {
		mapName = new JTextField();
		mapName.setBounds(10, 30, 300, 30);
		mapName.setBackground(new Color(108, 209, 227));

		this.add(mapName);

		save = new JButton("Save");
		save.setBounds(320, 30, 70, 30);
		save.setBackground(new Color(115, 115, 115));
		save.setForeground(new Color(108, 209, 227));
		save.setMargin(new Insets(0, 0, 0, 0));
		save.addActionListener(new SaveButt());

		this.add(save);

		open = new JButton("Open");
		open.setBounds(200, 5, 190, 20);
		open.setBackground(new Color(115, 115, 115));
		open.setForeground(new Color(108, 209, 227));
		open.setMargin(new Insets(0, 0, 0, 0));
		open.addActionListener(new OpenButt());

		this.add(open);
	}

	private void setMatrix() {
		for (int i = 0; i < FELDSIZE; i++) {
			for (int j = 0; j < FELDSIZE; j++) {
				MatrixButton current = new MatrixButton(i, j);

				current.setBounds(10 + 20 * i, 63 + 20 * j, 20, 20);
				current.setBackground(Color.darkGray);
				current.setForeground(Color.WHITE);
				current.addActionListener(new MatrixButt());

				this.add(current);
				feldMatrix[i][j] = current;
			}
		}

	}

	private void setOptions() {
		char[] items = { 'p', 'g', 'w', 'c', 'b', 'n' };

		String path = "/EditPanel/Sprites/";
		String[] images = { path + "Pac50.png", path + "GhostRed50.png", path + "BlueWall50.png", path + "Coin50.png",
				path + "CoinBig50.png", path + "Nichts.png" };

		for (int i = 0; i < 6; i++) {
			if (DEBUG)
				System.out.println(images[i]);
			options[i] = new OptionButton(items[i]);
			options[i].setBounds(320, 63 + 50 * i, 50, 50);
			options[i].addActionListener(new OptionButt());

			// Icon of Option button
			Image img = null;
			try {
				img = ImageIO.read(getClass().getResource(images[i]));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(images[i] + " nicht gefunden");
			}
			options[i].setIcon(new ImageIcon(img));

			this.add(options[i]);
		}
		// Select first OptionButton
		options[0].setBackground(new Color(34, 139, 34));
		this.objChar = options[0].getC();
	}
}