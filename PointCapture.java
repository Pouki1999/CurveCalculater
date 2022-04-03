package backend;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class PointCapture implements NativeMouseInputListener{
	
	private int[][] activeList;
	int currentPos = 0;
	int[][] finalList = null;
	
	public PointCapture(int [][] pts) {
		try {
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		this.activeList = pts;
		GlobalScreen.addNativeMouseListener(this);
		//System.out.println("things are running!");
	}	
	
	public int get_current() {
		return this.currentPos;
	}
	
	public void nativeMouseClicked(NativeMouseEvent e) {
		//System.out.println("Mouse Pressed: " + " at " + e.getX() + ", " + e.getY());
		this.activeList[currentPos] = new int[]{e.getX(), e.getY()};
		this.currentPos++;
		System.out.println(currentPos);
		if(this.activeList.length == this.currentPos) {
			this.finalList = this.activeList;
			this.activeList = null;
			for(int i=0;i<this.finalList.length;i++) {
				System.out.println(this.finalList[i][0]+","+this.finalList[i][1]);
			}
		
			//System.out.println("Job Done");
			GlobalScreen.removeNativeMouseListener(this);
			
		}
	}
	public void nativeMousePressed(NativeMouseEvent e) {
	}
	public void nativeMouseReleased(NativeMouseEvent e) {
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
	}

	public static void main(String[] args) {
		/*
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		*/

		// Construct the example object.
		PointCapture example = new PointCapture(new int[10][2]);

		
	}
	

}
