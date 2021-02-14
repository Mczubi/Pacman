

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class PacmanGUI extends JFrame 
{
	
	private JPanel container;
	
	private JPanel gamePanel;
	
	private JPanel menuPanel;
	
	private JButton doIt;
	
	private String currentPanel;
	
	public PacmanGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(0, 0, 600, 400);
		setLocationRelativeTo(null);
		
		currentPanel = "menuPanel";
		
		CardLayout layout = new CardLayout();
		container = new JPanel();
		container.setBounds(0, 0, getWidth(), getHeight());
		
		JLabel lblGame = new JLabel("Game yeaaahhh!");
		lblGame.setBounds(10, 10, 100, 25);
		
		gamePanel = new JPanel();
		gamePanel.setBounds(0, 0, container.getWidth(), container.getHeight());
		gamePanel.setBackground(Color.red);
		gamePanel.add(lblGame);
		
		JLabel lblMenu = new JLabel("Menu uaaahh!");
	  lblMenu.setBounds(10, 40, 100, 25);
		
		menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, container.getWidth(), container.getHeight());
		menuPanel.setBackground(Color.green);
		menuPanel.add(lblMenu);
		
		layout.addLayoutComponent(gamePanel, "gamePanel");
		layout.addLayoutComponent(menuPanel, "menuPanel");
		
		doIt = new JButton("Just do it!");
		doIt.setBounds(10, 10, 100, 25);
		doIt.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
					CardLayout layout = (CardLayout)container.getLayout();
					currentPanel = currentPanel.equals("menuPanel") ? "gamePanel" : "menuPanel";
					
					layout.show(container, currentPanel);
					layout.invalidateLayout(container);
			}
		});
		container.setLayout(layout);
		layout.show(container, currentPanel);
		
		getContentPane().add(doIt);
		getContentPane().add(menuPanel);
		getContentPane().add(gamePanel);
		getContentPane().add(container);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		try {
			java.awt.Image i = ImageIO.read(getClass().getResource("/Game/settingsimg.png").openStream());
			g.drawImage(i, 0, 0, null);
		} catch (IOException ex) {
			
		}
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			System.err.println(ex);
		}
		PacmanGUI pacman = new PacmanGUI();
	}

	
}
