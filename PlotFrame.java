package backend;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;


public class PlotFrame {
	
	public PlotFrame(ProjectClass project) {
		
		final Display display = new Display();

		try {
			Shell shell = new Shell(display);
			
			Label label = new Label(shell, SWT.WRAP);
			
			
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
				System.exit(0);
		}
		
	}
	public static void main(String[] args) {
		int[][] coords = new int[][]{new int[]{100,100}, new int[]{800,800}};
		ProjectClass proj = new ProjectClass(coords);
		proj.initImages();
		proj.initImages();
		PlotFrame plot = new PlotFrame(proj);
	}
}
