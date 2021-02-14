package EditPanel;   
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestEditPanel extends JFrame {
	
	private JButton editButt;
	private JButton exit;
	private JButton back;
	private EditPanel edit;
	
	public TestEditPanel() {
		super();
		setLayout(null);
		setBounds(0, 0, 400, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(71, 75, 150));
		
		editButt = new JButton("Test Editor");
		editButt.setBounds(getWidth() / 2 - 50, 20, 100, 30);
		editButt.setMargin(new Insets(0, 0, 0, 0));
		editButt.addActionListener(new Edit());
		this.getContentPane().add(editButt);
		
		exit = new JButton("Exit");
		exit.setBounds(getWidth() / 2 - 50, 60, 100, 30);
		exit.setMargin(new Insets(0, 0, 0, 0));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		this.getContentPane().add(exit);
		
		this.setLocationRelativeTo(null);
				
		setVisible(true);
	}

	
	public static void main(String[] args) {
		TestEditPanel test = new TestEditPanel();
	}
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	private class Edit implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			getContentPane().removeAll();
			
			back = new JButton("Back");
			back.setBounds(0, 0, 70, 30);
			back.addActionListener(new Back());
			getContentPane().add(back);
			
			edit = new EditPanel();
			getContentPane().add(edit);
			
			repaint();
		}
	}
	
	private class Back implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			getContentPane().removeAll();
			getContentPane().add(editButt);
			getContentPane().add(exit);
			
			repaint();
		}
		
	}
}
