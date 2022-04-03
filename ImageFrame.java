package backend;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImageFrame extends Frame implements ActionListener{
	
	private Panel buttonPanel;
	private Panel imagePanel1;
	private Panel imagePanel2;
	private Button longAxis;
	private Button curve;
	private Button results;
	private ProjectClass project = null;
	private boolean longPressed = false;
	private boolean curvePressed = false;
	
	public ImageFrame(ProjectClass project) {
		
		setTitle("Curve Drawing");
		setLayout(new GridLayout(2,2));
		setVisible(true);
		
		this.buttonPanel = new Panel();
		this.imagePanel1 = new Panel();
		this.imagePanel2 = new Panel();
		this.imagePanel1.setLayout(new FlowLayout());
		this.imagePanel2.setLayout(new FlowLayout());
		
		add(buttonPanel);
		add(new Panel());
		add(imagePanel1);
		add(imagePanel2);
		
		this.longAxis = new Button("Determining Longitudial Axis");
		this.buttonPanel.add(longAxis);
		this.curve = new Button("Drawing Curve");
		this.buttonPanel.add(curve);
		this.results = new Button("Show Results");
		this.buttonPanel.add(results);
		
		this.longAxis.addActionListener(this);
		this.curve.addActionListener(this);
		this.results.addActionListener(this);
		
		
		this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {

                System.exit(0);
            }
        } );
		
		
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == longAxis && this.longPressed == false) {
			this.longPressed = true;
			
		}
		else if(e.getSource() == curve && this.longPressed == true && this.curvePressed == false) {
			this.curvePressed = true;
			
			
		}
		else if(e.getSource() == results && this.longPressed == true && this.curvePressed == true) {
			
			
		}
		
	}
	
	public static void main(String[] args) {
		
		ImageFrame test = new ImageFrame(new ProjectClass(new int[1][1]));
		
	}


	
	
}
