import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/* The controller listens to events triggered by the view, and executes the appropriate reaction to these events.*/

public class Controller {

	//Attributes
	private View theView;
	private Model theModel;
	private List<Double>  Plot_x = new ArrayList<>();
	private List<Double>  Plot_y = new ArrayList<>();
	MyDrawPanel drawPanel = new MyDrawPanel();
	private static Object selected,choose;
	private static int numberYDivisions = 10;
	private static int pointWidth = 4;
	private static double Y_Coordinate, X_Coordinate;
	private double MinScoreP = 0;
	private double MaxScoreP =  20000;
	JPanel axeX = createComboBox(); 
	JPanel axeY = createComboBox_Y(); 

	//Constructor
	public Controller(View  theView, Model theModel) {
		this.theView = theView;
		this.theModel = theModel;
		this.theView.openFileListener(new JFile_Chooser());
		this.theView.quitListener(new Quit());
	}

	//Open file 
	class JFile_Chooser implements ActionListener{
		public void actionPerformed(ActionEvent event) { 
			JFileChooser chooser = new JFileChooser(); 
			// Open current directory
			File currentDirectory = new File(System.getProperty("user.dir")); 
			chooser.setCurrentDirectory(currentDirectory); 
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// Filter .csv files
			FileFilter a= new FileNameExtensionFilter("excel","csv"); 
			chooser.addChoosableFileFilter(a);
			chooser.setFileFilter(a);
			int returnValue = chooser.showOpenDialog(null); 
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();
				// when a file is chosen, the default text in JTextField is replaced by the the file's name.
				View.textField.setText(selectedFile.getName()); 
				// Plot a default graph
				setArrayY(Model.get_DAYS_TO_MATURITY_double());
				setArrayX(Model.get_YIELD_A_double());
				plot();
			}
		}
	}

	//Exit the program 
	class Quit implements ActionListener{ 
		public void actionPerformed(ActionEvent event) {
			System.exit(0);	
		}
	}

	// Graph section
	class MyDrawPanel extends JPanel {
		public  void paintComponent(Graphics g) {
			g.setColor(Color.white);
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			g.setColor(Color.white);
			g.setColor(Color.BLACK);			
			g.drawLine(85, 800, 1400, 800); 
			g.drawLine(85, 5, 85, 800);
			Graphics2D g2 = (Graphics2D) g;

			//Make axe x and numeration for 10 marks.
			//Values for the position and length of the axes are defined by x0,x1,y0,y1. 
			for (int i = 0; i < 10 + 1 ; i++) {
				int x0 = ((i * 1315 ) / (10)) + 85; 
				int x1 = x0;
				int y0 = 800;
				int y1 = y0 - 5;
				g2.drawLine(x0, y0, x1, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((MinScoreP + (MaxScoreP - MinScoreP) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, (y0 + 10) + (metrics.getHeight() / 2) - 3); 
			}

			///Make axe y and numeration for 10 marks.
			//Values for the position and lenght of the axes are defined by x0,x1,y0,y1. 
			for (int i = 0; i < 10; i++) {
				int x0 = 85;
				int x1 = pointWidth + 100 ;
				int y0 = 850 - ((i * (800)) / numberYDivisions + 50);
				int y1 = y0;
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((MinScoreP + (MaxScoreP - MinScoreP) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3); 
				g2.drawLine(x0, y0, x1, y1);
			}

			// Making a Scatter plot
			for(int j = 0; j < Plot_x.size(); j++) {					
				double x1 = Plot_x.get(j);
				double y01 = Plot_y.get(j);
				// x0  defines the distance between points on the axe x.
				//This was calculated dividing the length in pixels of the axe x (1315) over the maximum value that the .cvs file 
				//contains (20,000).
				double x0 = 0.06574777443; 
				//Multiply x0 per the value of the element contained in the array. 
				//Add the distance from the border of the panel to the axe y.
				X_Coordinate = ((x0 * x1 ) + 85)-5;  
				// y0  defines the distance between points on the axe y.
				//This was calculated dividing the length in pixels of the axe y (800) over the maximum value that the .cvs file 
				//contains (20,000).
				double y0 =  0.03999864604;  
				//Multiply y0 per the value of the element contained in the array. 
				//Subtract the distance from the border of the panel to the axe x.
				Y_Coordinate = (800 -(y0 * y01 ))-5; 
				g2.setColor(Color.red);
				Ellipse2D.Double e = new Ellipse2D.Double(X_Coordinate,Y_Coordinate,10,10);
				g2.fill(e);
			}
		}
	}

	//  ComboBox for axe y
	public  JPanel createComboBox_Y() {
		String[] names = new String[]{ "DAYS_TO_MATURITY","YIELD_A", "AMOUNT_CHF"};
		JComboBox comboBox = new JComboBox<String>(names);
		comboBox.setEditable(true);
		JPanel panel = new JPanel();
		panel.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				choose = comboBox.getSelectedItem();
				if (choose.equals("DAYS_TO_MATURITY") ) {
					System.out.println("");
					setArrayY(Model.get_DAYS_TO_MATURITY_double());
					plot();
				} 
				if (choose.equals("YIELD_A")) {
					System.out.println("");
					setArrayY(Model.get_YIELD_A_double());
					plot();
				} 

				if (choose.equals("AMOUNT_CHF")) {
					setArrayY(Model.get_AMOUNT_CHF_double());
					plot();
				} 
			}
		});
		return panel;
	}

	// ComboBox for axe y
	public  JPanel createComboBox() {
		String[] names = new String[]{"YIELD_A", "DAYS_TO_MATURITY", "AMOUNT_CHF"};
		JComboBox comboBox = new JComboBox<String>(names);
		comboBox.setEditable(true);
		JPanel panel = new JPanel();
		panel.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				selected = comboBox.getSelectedItem();
				if (selected.equals("DAYS_TO_MATURITY") ) {
					setArrayX(Model.get_DAYS_TO_MATURITY_double());
					plot();
				} 
				if (selected.equals("YIELD_A")) {
					System.out.println("");
					setArrayX(Model.get_YIELD_A_double());
					plot();
				} 
				if (selected.equals("AMOUNT_CHF")) {
					setArrayX(Model.get_AMOUNT_CHF_double());
					plot();
				}  
			}
		});
		return panel;
	}

	//Repaint graph
	public void plot() {
		View.panel2.add(axeX);
		View.panel2.add(axeY);
		MyDrawPanel drawPanel = new MyDrawPanel();
		View.frame.getContentPane().add(BorderLayout.CENTER,drawPanel); 
		View.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		View.frame.setSize(1800, 950);
		View.frame.setVisible(true);
	}

	//Set values according to what is chosen in the combobox for axe x
	public void setArrayX(List<Double>  Plot_x) {
		this.Plot_x =Plot_x;
	}

	//Set values according to what is chosen in the combobox for axe y
	public void setArrayY(List<Double>  Plot_y) {
		this.Plot_y =Plot_y;
	}

}
