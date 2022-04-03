package backend;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math.*;

import com.opencsv.CSVWriter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;


public class ImageFrame2 {
	
	private ProjectClass project;
	private int [][] longPts = null;
	private int [] center1 = null;
	private int [] center2 = null;
	private int [][] measurePts = null;
	private ArrayList<int[]> curvePts = null;
	private int longCount = 0;
	private int measureCount =0;
	private boolean longDone = false;
	private boolean curveDone = false;
	private boolean exportDone = false;
	private Button longButton;
	private Button zoomButton;
	private Button measureButton;
	private Button gridButton;
	private Button curveButton;
	private Button resultsButton;
	private Button plotButton;
	private Button exportButton;
	private Button newWindowButton;
	private boolean longPressed = false;
	private boolean measurePressed = false;
	private boolean gridPressed = false;
	private boolean curvePressed = false;
	private boolean resultsPressed = false;
	private Label result;
	private Label instructions;
	private Text inputText;
	
	
	
	public ImageFrame2(final ProjectClass project) {
		
		final ImageFrame2 obj = this;
		this.project = project;
		final Display display = new Display();
		
		try {
			Shell shell = new Shell(display);
			shell.setMaximized(true);
			
			GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			//layout.makeColumnsEqualWidth = true;
			shell.setLayout(layout);
			
			Rectangle rect = shell.getClientArea();
			System.out.println(rect.height);
			
			Group buttons = new Group(shell, SWT.NULL);
			GridLayout butLayout = new GridLayout();
			butLayout.makeColumnsEqualWidth = true;
			butLayout.numColumns = 1;
			buttons.setLayout(butLayout);
			
			GridData butData = new GridData();
			butData.horizontalSpan = 1;
			butData.verticalAlignment = GridData.FILL;
			butData.horizontalAlignment = GridData.FILL;
			//butData.grabExcessVerticalSpace = true;
			buttons.setLayoutData(butData);
			
			double scalling = (rect.height*0.9)/this.project.get_image1().getHeight(); 
			final ImageLabel imagedisp1 = new ImageLabel(display, shell, scalling, this.project.get_image1(), 1);
			final ImageLabel imagedisp2 = new ImageLabel(display, shell, scalling, this.project.get_image2(), 2);
			
			this.result = new Label(shell, SWT.WRAP);
			this.result.setText("This is where the result will be shown");
			
			GridData resultData = new GridData();
			resultData.horizontalSpan = 1;
			resultData.horizontalAlignment = GridData.FILL;
			resultData.verticalAlignment = GridData.FILL;
			result.setLayoutData(resultData);
			
			this.instructions = new Label(shell, SWT.WRAP);
			this.instructions.setText("Start by pressing on [Select Longitudial Axis] and use the left image to identify it.");
			
			GridData instructionData = new GridData();
			instructionData.horizontalSpan = 2;
			instructionData.horizontalAlignment = GridData.FILL;
			instructionData.verticalAlignment = GridData.FILL;
			instructionData.grabExcessVerticalSpace = true;
			instructions.setLayoutData(instructionData);
			
			this.inputText = new Text(shell, SWT.SINGLE | SWT.BORDER);
			this.inputText.setText("Write the name of the patient here");
			
			/*
			System.out.println(this.project.get_image1().getHeight() + " , " + scalling);
			BufferedImage image1 = resize(this.project.get_image1(), scalling);
			BufferedImage image2 = resize(this.project.get_image2(), scalling);
			final ImageData proto1 = convertToSWT(image1);
			final ImageData proto2 = convertToSWT(image2);
			Image img1 = new Image(display , proto1);
			Image img2 = new Image(display , proto2);
			final Label imagedisp1 = new Label(shell, SWT.WRAP);
			final Label imagedisp2 = new Label(shell, SWT.WRAP);
			imagedisp1.setImage(img1);
			imagedisp2.setImage(img2);
		
			*/
			
			GridData imageData1 = new GridData();
			imageData1.verticalAlignment = GridData.FILL;
			imageData1.horizontalSpan = 1;
			imageData1.grabExcessHorizontalSpace = true;
			imagedisp1.getLabel().setLayoutData(imageData1);
			
			GridData imageData2 = new GridData();
			imageData2.verticalAlignment = GridData.FILL;
			imageData2.horizontalSpan = 1;
			imageData2.grabExcessHorizontalSpace = true;
			imagedisp2.getLabel().setLayoutData(imageData2);
			
			GridData ButtonData = new GridData();
			ButtonData.verticalAlignment = GridData.FILL;
			ButtonData.horizontalAlignment = GridData.FILL;
			ButtonData.horizontalSpan = 1;
			ButtonData.grabExcessVerticalSpace = true;
			ButtonData.grabExcessHorizontalSpace = true;
			
			this.longButton = new Button(buttons, SWT.PUSH);
			this.longButton.setText("Select Longitudial Axis");
			this.longButton.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("Long Axis pressed beuh");
				}
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Longitudial Axis pressed");
					if (!longPressed && !measurePressed) {
						longPressed = true;
						System.out.println("Capturing pts for long axis");
						longPts = new int[4][2];
						obj.instructions.setText("After selecting the axis, press on [Erase Longitudial Axis] to erase the selection you just made, or press on [Select a distance representing 1 cm] and use the right image to select the scale.");
						obj.longButton.setText("Erase Longitudial Axis");
					}
					
					else if (longPressed && !measurePressed) {
						System.out.println("Clearing pts for long axis and restart");
						longPressed = false;
						longCount = 0;
						longPts = null;
						longDone = false;
						imagedisp1.reInit();
						imagedisp2.reInit();
						obj.project.erase_data1();
						obj.instructions.setText("Press again on [Select Longitudial Axis] to start over with the selection of the axis on the left image.");
						obj.longButton.setText("Select Longitudial Axis");
						gridPressed = false;
					}	
				}
			});
			longButton.setLayoutData(ButtonData);
			
			this.zoomButton = new Button(buttons, SWT.PUSH);
			this.zoomButton.setText("Zoom");
			this.zoomButton.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("Zoom pressed beuh");
				}
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Zoom pressed");
				}
			});
			
			zoomButton.setLayoutData(ButtonData);
			
			this.measureButton = new Button(buttons, SWT.PUSH);
			this.measureButton.setText("Select a distance representing 1 cm");
			this.measureButton.addSelectionListener(new SelectionListener( ) {
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("Measure pressed beuh");
				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("Measure pressed");
					if (longPressed && !measurePressed && !curvePressed) {
						System.out.println("Capturing pts for measure");
						//This is when the matrix is computed and initialized in the project object
						LongAxisCalc.get_matrix(longPts, obj.project);
						obj.measurePressed = true;
						obj.measurePts = new int[2][2];
						obj.instructions.setText("After selecting a distance of 1 cm, press [Erase Scale] to erase the selection you just made, or press on [Select the curve of interest] and use the right image to select the curve.");
						obj.measureButton.setText("Erase Scale");
					}
					else if (longPressed && longDone && measurePressed && !curvePressed) {
						System.out.println("Clearing pts for ratio estimation");
						obj.measurePressed = false;
						obj.project.erase_data2();
						obj.measurePts = null;
						obj.measureCount = 0;
						imagedisp1.reInit();
						imagedisp2.reInit();
						imagedisp1.drawLine(obj.center1, obj.center2, 1);
						imagedisp2.drawLine(obj.center1, obj.center2, 2);
						for (int i=0; i<4; i++) {
							imagedisp1.drawPoint(longPts[i][0], longPts[i][1], 1);
						}
						obj.measureButton.setText("Select a distance representing 1 cm");
						obj.instructions.setText("Press [Select a distance representing 1 cm] again to restart the selection of the scale, or press on [Erase Longitudial Axis] to erase it as well.");
						if (gridPressed) {
							imagedisp2.drawGrid(center1, center2, 20);
						}
					}
				}});
			
			measureButton.setLayoutData(ButtonData);
			
			this.gridButton = new Button(buttons, SWT.PUSH);
			this.gridButton.setText("Display Grid");
			this.gridButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("Grid pressed beuh");
				}
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Grid pressed");
					if (!gridPressed && longPressed) {
						if (center1 != null && center2 != null) {
							imagedisp2.drawGrid(obj.center1, obj.center2, 20);
							gridPressed = true;
						}
					}
					else {
						imagedisp2.reInit();
						gridPressed = false;
						if (center1 != null && center2 != null) {
							imagedisp2.drawLine(obj.center1, obj.center2, 2);
							if (measurePts != null) {
								imagedisp2.drawLine(obj.measurePts[0], obj.measurePts[1], 1);
								imagedisp2.drawPoint(measurePts[0][0], measurePts[0][1], 1);
								imagedisp2.drawPoint(measurePts[1][0], measurePts[1][1], 1);
								if (obj.curvePts != null) {
									for (int i=0; i < obj.curvePts.size(); i++) {
										imagedisp2.drawPoint(obj.curvePts.get(i)[0], obj.curvePts.get(i)[1], 1);
									}
								}
							}
							
						}
						
					}
				}
			});
			gridButton.setLayoutData(ButtonData);
			
			this.curveButton = new Button(buttons, SWT.PUSH);
			this.curveButton.setText("Select the Curve of Interest");
			this.curveButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("Curve pressed beuh");
				}
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Curve pressed");
					if (measurePressed && !curvePressed && measureCount == 2) {
						curvePressed = true;
						System.out.println("Capturing pts for curve");
						obj.project.compute_and_set_ratio(obj.measurePts[0], obj.measurePts[1]);
						curvePts = new ArrayList<int[]>();
						obj.curveButton.setText("Erase the Curve");
						obj.instructions.setText("After selecting the curve, press on [Erase the Curve] to erase the selection you just made, or press on [Show Results] to get the estimated curve. NOTE: When selecting the curve, the point that will be positioned at the origine must be selected last. Also, the point that will be positioned the furthest from the origin must be at the position where the longitudial axis crosses the curve.");
					}
					else if (longPressed && curvePressed && measureCount == 2) {
						System.out.println("Clearing pts for curve and restart");
						curvePressed = false;
						curveDone = false;
						resultsPressed = false;
						imagedisp2.reInit();
						imagedisp2.drawLine(obj.center1, obj.center2, 2);
						imagedisp2.drawLine(obj.measurePts[0], obj.measurePts[1], 1);
						curvePts = null;
						obj.project.erase_data3();
						obj.result.setText("This is where the result will be shown");
						obj.curveButton.setText("Select the Curve of Interest");
						obj.instructions.setText("Press again on [Select the Curve of Interest] to select a new curve on the right image, or press on [Erase Scale] to erase the selected scale as well.");
						if (gridPressed) {
							imagedisp2.drawGrid(obj.center1, obj.center2, 20);
						}
						
					}
				}
			});
			
			curveButton.setLayoutData(ButtonData);
			
			this.resultsButton = new Button(buttons, SWT.PUSH);
			this.resultsButton.setText("Show Results");
			this.resultsButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("Results pressed beuh");
				}
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Results pressed");
					if (curvePressed) {
						curveDone = true;
						resultsPressed = true;
						int[][] newCurvePts = new int[curvePts.size()][2];
						for (int i=0; i < curvePts.size(); i++) {
							newCurvePts[i][0] = curvePts.get(i)[0];
							newCurvePts[i][1] = curvePts.get(i)[1];
						}
						obj.project.set_curve(Curve.pts_transf(newCurvePts, obj.project.get_matrix(), obj.project));
						Curve.setCurve(obj.project);
						result.setText((Math.round(obj.project.get_a()*100.0)/100.0) + "x^" + (Math.round(obj.project.get_n()*100.0)/100.0));
						//imagedisp2.drawCurve(curvePts.get(curvePts.size()-1)[0], curvePts.get(curvePts.size()-1)[1], 100, 3, obj.project.get_a(), obj.project.get_n(), obj.project.get_direction(), obj.project.get_scale(), obj.project.get_matrix());
						obj.instructions.setText("You are done! You have an estimation for the curve. If you are not satisfied, you can erase the curve by pressing on [Select the curve of interest].");
					}
				}
			});
		
			resultsButton.setLayoutData(ButtonData);
			
			this.plotButton = new Button(buttons, SWT.PUSH);
			this.plotButton.setText("Show plot");
			this.plotButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("Plot pressed beuh");
				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("Plot pressed");
				}
				
			});
			
			this.plotButton.setLayoutData(ButtonData);
			
			this.exportButton = new Button(buttons, SWT.PUSH);
			this.exportButton.setText("Export curve to file");
			this.exportButton.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("Export pressed beuh");
				}
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Export pressed");
					if (!exportDone && curveDone) {
						try {
							File file = new File("CurveInfos.csv");
							boolean flag = file.createNewFile();
							if (flag) {
								FileWriter outputFile = new FileWriter(file);
								CSVWriter writer = new CSVWriter(outputFile);
								String[] header = { "Name of patient", "a", "n"};
								writer.writeNext(header);
								String[] newLine = { obj.inputText.getText(), Double.toString((Math.round(obj.project.get_a()*100.0))/100.0), Double.toString((Math.round(obj.project.get_n()*100.0))/100.0)};
								writer.writeNext(newLine);
								writer.close();
							}
							else {
								FileWriter outputFile = new FileWriter(file, true);
								CSVWriter writer = new CSVWriter(outputFile);
								String[] newLine = { obj.inputText.getText(), Double.toString((Math.round(obj.project.get_a()*100.0))/100.0), Double.toString((Math.round(obj.project.get_n()*100.0))/100.0)};
								writer.writeNext(newLine);
								writer.close();
							}
						}
						catch (IOException excep) {
							excep.printStackTrace();	
						}
							
						
						obj.exportButton.setText("Exported Succesfully");
						exportDone = true;
					}
				}
			});

			exportButton.setLayoutData(ButtonData);
			
			this.newWindowButton = new Button(buttons, SWT.PUSH);
			this.newWindowButton.setText("Start new project");
			this.newWindowButton.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("New Window pressed beuh");
				}
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("New Window pressed");
					display.dispose();
					CaptureFrame2 newCapture = new CaptureFrame2();
				}
			});
			
			newWindowButton.setLayoutData(ButtonData);
			
			/*
			GC gc1 = new GC(img1);
			GC gc2 = new GC(img2);
			Rectangle bounds1 = img1.getBounds();
			Rectangle bounds2 = img2.getBounds();
			gc1.drawLine(0, 0, bounds1.width, bounds1.height);
			gc2.drawLine(0, bounds2.height, bounds1.width, 0);
			gc1.dispose();
			gc2.dispose();
			*/
			
			//System.out.println("We got here 4");

			
			imagedisp1.getLabel().addMouseListener(new MouseListener() {

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseDown(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if (longPts != null && longCount < 4 && arg0.button == 1) {
						System.out.println(arg0.x + "," + arg0.y);
						longPts[longCount][0] = arg0.x;
						longPts[longCount][1] = arg0.y;
						longCount++;
						imagedisp1.drawPoint(arg0.x,arg0.y, 1);
						
						if (longCount == 4) {
							//center1 = LongAxisCalc.find_center(longPts[0], longPts[1]);
							//center2 = LongAxisCalc.find_center(longPts[2], longPts[3]);
							longDone = true;
							obj.center1 = LongAxisCalc.find_center(longPts[0], longPts[1]);
							obj.center2 = LongAxisCalc.find_center(longPts[2], longPts[3]);
							imagedisp1.drawLine(center1, center2, 1);
							imagedisp2.drawLine(center1, center2, 2);
							//imagedisp2.drawLine(center1, center2);
						}
						//imagedisp1.redraw();
					}
				}

				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
				} 
			});
			imagedisp2.getLabel().addMouseListener(new MouseListener() {

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if (obj.measurePts != null && measureCount < 2 && arg0.button == 1) {
						measurePts[measureCount][0] = arg0.x;
						measurePts[measureCount][1] = arg0.y;
						System.out.println(arg0.x + "," + arg0.y);
						System.out.println(measurePts[measureCount][0] + "," + measurePts[measureCount][1]);
						imagedisp2.drawPoint(measurePts[measureCount][0], measurePts[measureCount][1], 1);
						measureCount++;
						if (measureCount == 2) {
							imagedisp2.drawLine(measurePts[0], measurePts[1], 1);
						}
					}
					if (curvePts != null && !curveDone && arg0.button == 1) {
						System.out.println(arg0.x + "," + arg0.y);
						int[] pt = new int[2];
						pt[0] = arg0.x;
						pt[1] = arg0.y;
						curvePts.add(pt);
						//imagedisp2.redraw();
						imagedisp2.drawPoint(arg0.x,arg0.y, 1);
					}
					if (curvePts != null && curvePts.size()>0 && !curveDone && arg0.button == 3) {
						curvePts.remove(curvePts.size()-1);
						imagedisp2.reInit();
						for (int i=0; i< curvePts.size(); i++) {
							imagedisp2.drawPoint(curvePts.get(i)[0], curvePts.get(i)[1], 1);
						}
						imagedisp2.drawLine(center2, center1, 2);
						imagedisp2.drawLine(measurePts[0], measurePts[1], 1);
						imagedisp2.drawPoint(measurePts[0][0], measurePts[0][1], 1);
						imagedisp2.drawPoint(measurePts[1][0], measurePts[1][1], 1);
					}
						
				}

				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}
				
			});

			
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
	
	/*
	
	public void drawPoint(int x, int y, Display display, Image image, Label label) {
		GC gc = new GC(image); 
		gc.setLineWidth(4);
		gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
		System.out.println("Drawing circle from: " + (x-5) + " , " +(y-5) + " to " + (x+5) + " , " + (y+5));
		gc.drawOval(x-5, y-5, 10, 10); 
		gc.dispose();
		label.redraw();
	}
	
	public void drawLine(int[] pt1, int[] pt2, Display display, Image image, Label label) {
		GC gc = new GC(image); 
		gc.setLineWidth(4);
		gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
		gc.drawLine(pt1[0],pt1[1],pt2[0],pt2[1]);
		gc.dispose();
		label.redraw();
	}
	
	
	public static BufferedImage resize(BufferedImage input, double scalling) {
		
		BufferedImage output = new BufferedImage((int) (input.getWidth()*scalling),(int) (input.getHeight()*scalling) ,input.getType());
		Graphics2D g2d = output.createGraphics();
		g2d.drawImage(input,0,0,(int) (input.getWidth()*scalling),(int) (input.getHeight()*scalling),null);
		g2d.dispose();
		
		return output;
	}
	
	public static ImageData convertToSWT(BufferedImage bufferedImage) {
	    if (bufferedImage.getColorModel() instanceof DirectColorModel) {
	        DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
	        PaletteData palette = new PaletteData(
	            colorModel.getRedMask(),
	            colorModel.getGreenMask(),
	            colorModel.getBlueMask()
	        );
	        ImageData data = new ImageData(
	            bufferedImage.getWidth(),
	            bufferedImage.getHeight(), colorModel.getPixelSize(),
	            palette
	        );
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[3];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                int pixel = palette.getPixel(
	                    new RGB(pixelArray[0], pixelArray[1], pixelArray[2])
	                );
	                data.setPixel(x, y, pixel);
	            }
	        }
	        return data;
	    } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
	        IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
	        int size = colorModel.getMapSize();
	        byte[] reds = new byte[size];
	        byte[] greens = new byte[size];
	        byte[] blues = new byte[size];
	        colorModel.getReds(reds);
	        colorModel.getGreens(greens);
	        colorModel.getBlues(blues);
	        RGB[] rgbs = new RGB[size];
	        for (int i = 0; i < rgbs.length; i++) {
	            rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
	        }
	        PaletteData palette = new PaletteData(rgbs);
	        ImageData data = new ImageData(
	            bufferedImage.getWidth(),
	            bufferedImage.getHeight(),
	            colorModel.getPixelSize(),
	            palette
	        );
	        data.transparentPixel = colorModel.getTransparentPixel();
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[1];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                data.setPixel(x, y, pixelArray[0]);
	            }
	        }
	        return data;
	    }
	    return null;
	}
	*/
	
	public static void main(String[] args) {
		int[][] coords = new int[][]{new int[]{100,100}, new int[]{800,800}};
		ProjectClass proj = new ProjectClass(coords);
		proj.initImages();
		proj.initImages();
		ImageFrame2 imgFrama = new ImageFrame2(proj);
	}
	
}
