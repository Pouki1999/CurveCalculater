package backend;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageCapture {

	public static BufferedImage getScreenShot(int[] pos1, int[] pos2) {
		int width = pos1[0] - pos2[0];
		if (width < 0) {
			width = width*(-1);
		}
		int height = pos1[1] - pos2[1];
		if (height < 0) {
			height = height*(-1);
		}
		System.out.println("height:" + height + " width:" + width);
		
		Rectangle frame = null;
		
		if (pos1[0] < pos2[0] && pos1[1] < pos2[1]) {
			frame = new Rectangle(pos1[0],pos1[1], width, height);
		}
		else if (pos2[0] < pos1[0] && pos2[1] < pos1[1]) {
			frame = new Rectangle(pos2[0],pos2[1], width, height);
		}
		else {
			System.out.println("third option with pt:" + Math.min(pos1[0], pos2[0]) + "," + Math.min(pos1[1], pos2[1]));
			frame = new Rectangle(Math.min(pos1[0], pos2[0]), Math.min(pos1[1], pos2[1]),width, height);
		}
		System.out.println(frame.toString());
		
		BufferedImage capture = null;
		
		try {
	        AffineTransform tx = GraphicsEnvironment.
	                getLocalGraphicsEnvironment().getDefaultScreenDevice().
	                getDefaultConfiguration().getDefaultTransform();
	        frame.x = (int) (frame.x / tx.getScaleX());
	        frame.y = (int) (frame.y / tx.getScaleY());
	        frame.width = (int) (frame.width / tx.getScaleY());
	        frame.height = (int) (frame.height / tx.getScaleY());
	        capture = new Robot().createScreenCapture(frame);
			
	        /*
			new Robot().getPixelColor(200, 300);
			
			List<Image> resolutionVariants = mrImage.getResolutionVariants(); 
			if (resolutionVariants.size() > 1) {
				capture =(BufferedImage) resolutionVariants.get(0);
			}
			else {
				capture =(BufferedImage) resolutionVariants.get(0);
			}
			*/
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return capture;
	}
	
	public static void main(String[] args) {
		int[] pos1 = new int[] {300,400};
		int[] pos2 = new int[] {1595,815};
		BufferedImage test = getScreenShot(pos1, pos2);
		File outputfile = new File("C:\\Users\\gogom\\Documents\\MBEC\\JavaStuff\\image.jpg");
		try {
			ImageIO.write(test, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
