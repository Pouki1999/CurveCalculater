package backend;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;

public class CaptureFrame2 {
	
	private ProjectClass project;
	private PointCapture listener;
	private int[][] coords;
	private boolean ptPressed = false;
	private boolean firstPressed = false;
	private boolean secondPressed = false;
	private Button ptCapture;
	private Button firstImage;
	private Button secondImage;
	
	public CaptureFrame2() {
		
		final Display display = new Display();
		
		try {
			Shell shell = new Shell(display);
			
			shell.setLayout(new FillLayout());
			shell.setSize(600,300);
			
			this.ptCapture = new Button(shell, SWT.PUSH);
			this.ptCapture.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("Pt Capture Selected beuh");
				}
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Pt Capture Selected");
					if (ptPressed == false) {
						ptPressed = true;
						listener = new PointCapture(new int[2][2]);
					}
				}
			});
			this.ptCapture.setText("Points for screenshots");
			
			this.firstImage = new Button(shell, SWT.PUSH);
			this.firstImage.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("First Image Selected beuh");
				}
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("First Image Selected");
					if (ptPressed == true && firstPressed == false && listener.finalList != null) {
						firstPressed = true;
						coords = listener.finalList;
						project = new ProjectClass(coords);
						project.initImages();
					}	
				}
			});
			this.firstImage.setText("Take first image");
			this.secondImage = new Button(shell, SWT.PUSH);
			this.secondImage.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("Second Image Selected beuh");
				}
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Second image Selected");
					if (firstPressed == true && secondPressed == false) {
						secondPressed = true;
						project.initImages();
						/*
						File outputfile1 = new File("C:\\Users\\gogom\\Documents\\MBEC\\JavaStuff\\image1.jpg");
						try {
							ImageIO.write(project.get_image1(), "jpg", outputfile1);
						} catch (IOException ev) {
							// TODO Auto-generated catch block
							ev.printStackTrace();
						}
						File outputfile2 = new File("C:\\Users\\gogom\\Documents\\MBEC\\JavaStuff\\image2.jpg");
						try {
							ImageIO.write(project.get_image2(), "jpg", outputfile2);
						} catch (IOException ev) {
							// TODO Auto-generated catch block
							ev.printStackTrace();
						}
						*/
						display.dispose();
						ImageFrame2 imgFrame = new ImageFrame2(project);
					}
				}
			});
			this.secondImage.setText("Take second image");
	
			
			try {
				shell.open();
				while(!shell.isDisposed()) {
					if(!display.readAndDispatch()) {
						display.sleep();
					}
				}
			} finally {
				if (!shell.isDisposed()) {
					shell.dispose();
				}
			}
		} finally {		
		display.dispose();
		}
	}
	
	public static void main(String[] args) {
		CaptureFrame2 cap = new CaptureFrame2();
		
	}
	
	
}
