package backend;

public class LongAxisCalc {
	
	public static int[] find_center(int[] pos1, int[] pos2) {
		int[] center = new int[2];
		center[0] = ((pos1[0]+pos2[0])/2);
		center[1] = ((pos1[1]+pos2[1])/2);
		return center;
	}
	
	public static double[] find_center_in_double(int[] pos1, int[] pos2) {
		double[] center = new double[2];
		center[0] = ((pos1[0]+pos2[0])/2);
		center[1] = ((pos1[1]+pos2[1])/2);
		return center;
	}
	/*
	public static int[][] adj_pts(int[][] pts) {
		for(int i=0; i<pts.length;i++) {
			pts[i][1] = -pts[i][1];
		}
		return pts;
	}
	*/
	
	public static void get_matrix(int[][] pts, ProjectClass project) {
		
		double[][] matrix = new double[2][2];
		double[] firstcenter = find_center_in_double(pts[0], pts[1]);
		double[] secondcenter = find_center_in_double(pts[2], pts[3]);
		double angle;
		System.out.println(firstcenter[0] + "," + firstcenter[1]);
		System.out.println(secondcenter[0] + "," + secondcenter[1]);
		if ((secondcenter[0] - firstcenter[0]) != 0) {
			double slope = (secondcenter[1] - firstcenter[1])/(secondcenter[0] - firstcenter[0]);
			System.out.println("slope1: " + slope);
			slope = -1/slope;
			System.out.println("slope2: " + slope);
			angle = -Math.atan(slope);
			System.out.println("angle:" + angle);
		}	
		else {
			angle = 0;
		}
		
		matrix[0][0] = Math.cos(angle);
		matrix[0][1] = -Math.sin(angle);
		matrix[1][0] = Math.sin(angle);
		matrix[1][1] = Math.cos(angle);
		
		double det = matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
		
		double[][] inverse = new double[2][2];
		
		inverse[0][0] = det*matrix[1][1];
		inverse[0][1] = -det*matrix[0][1];
		inverse[1][0] = -det*matrix[1][0];
		inverse[1][1] =  det*matrix[0][0];
		
		System.out.println("matrix:");
		System.out.println("00: " + inverse[0][0] + " , 10: " + inverse[1][0]);
		System.out.println("01: " + inverse[0][1] + " , 11: " + inverse[1][1]);
		
		project.set_matrix(matrix);
		project.set_inv_matrix(inverse);
	}

}
