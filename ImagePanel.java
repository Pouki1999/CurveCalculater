package backend;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;



public class ImagePanel extends Panel {
	
	private BufferedImage image;
	
	public ImagePanel(BufferedImage image) {
		this.image = image;
		//this.paint();
	}
	
	public void paint(Graphics g) {
		
		super.paint(g);
		g.drawImage(this.image,0, 0, this);
	}
	
	

}
