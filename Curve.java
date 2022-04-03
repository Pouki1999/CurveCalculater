package backend;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import java.lang.Math.*;

public class Curve {
	
	public static double[][] pts_transf(int[][] list, double[][] matrix, ProjectClass project) {
		
		double[][] new_list = new double[list.length][2];
		
		System.out.println("After matrix multiplication:");
		
		
		
		for(int i=0; i<list.length; i++) {
			new_list[i][0] = list[i][0]*matrix[0][0] + list[i][1]*matrix[0][1];
			new_list[i][1] = list[i][0]*matrix[1][0] + list[i][1]*matrix[1][1];
			System.out.println(new_list[i][0] + "," + new_list[i][1]);
		}
		
		double origin_x = new_list[list.length-1][0];
		double origin_y = new_list[list.length-1][1];
		
		System.out.println("After pts translation:");
		
		if (origin_x >= new_list[0][0]) {
			project.set_direction(-1);
			for(int i=0; i<new_list.length; i++) {
				
				new_list[i][0] = new_list[i][0]*(-1) + origin_x + 0.001;
				new_list[i][1] = new_list[i][1]*(-1) + origin_y + 0.001;
				System.out.println(new_list[i][0] + "," + new_list[i][1]);
			}	
		}
		else if (origin_x < new_list[0][0]) {
			project.set_direction(1);
			for(int i=0; i<new_list.length; i++) {
				
				new_list[i][0] = new_list[i][0] - origin_x + 0.001;
				new_list[i][1] = new_list[i][1]*(-1) + origin_y + 0.001;
				System.out.println(new_list[i][0] + "," + new_list[i][1]);
	
			}	
		}
		
		System.out.println("After conversion from pixels to centimeters:");
		
		double ratio = project.get_ratio();
		
		for(int i=0; i<new_list.length; i++) {
			new_list[i][0] = new_list[i][0]/ratio;
			new_list[i][1] = new_list[i][1]/ratio;
			System.out.println(new_list[i][0] + "," + new_list[i][1]);
		}
		
		return new_list;
	}
	
	public static void setCurve(ProjectClass project) {
		
		double[][] pts = project.get_curve_pts();
		
		double[] y = new double[pts.length];
		double[] x = new double[pts.length];
		
		System.out.println("Linearized points:");
		
		for (int i=0; i < (pts.length-1); i++) {
			y[i] = Math.log(pts[i][1]);
			x[i] = Math.log(pts[i][0]);
			System.out.println(x[i] + "," + y[i]);
			
		}
		
		SimpleRegression model = new SimpleRegression();
		
		for (int i=0; i<pts.length;i++) {
			model.addData(x[i], y[i]);
		}
		
		System.out.println("n:" + model.getSlope());
		System.out.println("a:" + model.getIntercept());
		
		project.set_n(model.getSlope());
		project.set_a(Math.exp(model.getIntercept()));
		//project.set_accuracy(model.getRSquare());	
		
		/*
		double[] y = new double[pts.length];
		double[][] x = new double[pts.length][2];
		
		for (int i=0; i<pts.length;i++) {
			y[i] = Math.pow(Math.log(pts[i][0]),2)*(Math.log(pts[i][1]));
			x[i][0] = Math.pow(Math.log(pts[i][0]),2)*(Math.log(pts[i][0]));
			x[i][1] = Math.pow(Math.log(pts[i][0]),2);
		}
		
		OLSMultipleLinearRegression model = new OLSMultipleLinearRegression();
		model.setNoIntercept(true);
		model.newSampleData(y, x);
		
		double[] regParam = model.estimateRegressionParameters();
		
		project.set_n(regParam[0]);
		project.set_a(Math.exp(regParam[1]));
		//project.set_accuracy(model.getRSquare());
		*/
		
	}
	
	public static void main(String[] args) {
		double[][] pts = new double[][]{new double[]{1,1}, new double[]{5,4}, new double[]{40,11}, new double[]{75,14}, new double[]{100,15}};
		int[][] coords = new int[][]{new int[]{100,100}, new int[]{800,800}};
		ProjectClass project = new ProjectClass(coords);
		project.set_curve(pts);	
		setCurve(project);
		System.out.println(project.get_a() + "x^" + project.get_n() + ", accuracy:" + project.get_accuracy());
		}

}
