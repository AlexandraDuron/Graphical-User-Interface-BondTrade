import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*  The view is responsible for the objects that are in charge of visual representation of the model. */

public class View {

	//Attributes
	private JButton buttonOpen, buttonQuit; 
	protected static JTextField textField, textField_1;
	protected static JFrame frame;
	protected static JPanel panel2 = new JPanel();
	private JPanel panel = new JPanel(); 

	public void ViewBonTrade() { 
		// Initializing components
		frame=new JFrame();
		buttonOpen = new JButton("OPEN"); 
		buttonQuit = new JButton("Quit"); 
		//Set colors
		panel.setBackground(Color.red); 
		panel2.setPreferredSize(new Dimension(640, 50));
		panel2.setBackground(Color.red); 
		//make a Text Field and print a default text
		textField = new JTextField ("           <Name of file>           ");
		textField_1 = new JTextField ();	
		//Add components to panels
		panel.add(buttonOpen);
		panel.add(textField);
		panel.add(buttonQuit);
		// Panels are set by regions
		frame.getContentPane().add(BorderLayout.NORTH, panel); 
		frame.getContentPane().add(BorderLayout.SOUTH, panel2);
		//Set frame features
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1800, 950);
		frame.setTitle("Bond Trade");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	// Action Listeners
	void openFileListener(ActionListener listenForOpenFileButton){
		buttonOpen.addActionListener(listenForOpenFileButton);
	}
	void quitListener(ActionListener listenForExitFileButton){
		buttonQuit.addActionListener(listenForExitFileButton);
	}

}
