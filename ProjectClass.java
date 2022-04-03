package backend;

import java.awt.image.BufferedImage;
import java.lang.Math;

public class ProjectClass {
	
	private int[][] image_coords = null;
	private double[][] curve_pts = null;
	private double[][] inv_matrix = null;
	private double[][] matrix = null;
	private double n;
	private double a;
	private BufferedImage image1 = null;
	private BufferedImage image2 = null;
	private double accuracy = 0;
	private double ratio = 0;
	private int direction;
	
	public ProjectClass(int[][] coords) {
		this.image_coords = coords;
	}
	
	public void initImages() {
		if (this.image1 == null) {
			this.image1 = ImageCapture.getScreenShot(image_coords[0], image_coords[1]);
		}
		else if (this.image2 == null) {
			this.image2 = ImageCapture.getScreenShot(this.image_coords[0], this.image_coords[1]);
		}
	}
	public void erase_data1() {
		this.matrix = null;
	}
	public void erase_data2() {
		this.ratio = 0;
	}
	public void erase_data3() {
		this.curve_pts = null;
	}
	public BufferedImage get_image1() {
		return this.image1;
	}
	public BufferedImage get_image2() {
		return this.image2;
	}
	public double[][] get_curve_pts() {
		return this.curve_pts;
	}
	public double[][] get_inv_matrix(){
		return this.inv_matrix;
	}
	public double[][] get_matrix() {
		return this.matrix;
	}
	public double get_n() {
		return this.n;
	}
	public double get_a() {
		return this.a;
	}
	public double get_accuracy() {
		return this.accuracy;
	}
	public int get_direction() {
		return this.direction;
	}
	public double get_ratio() {
		return this.ratio;
	}
	/*
	public void set_curve_pts(int[][] pts) {
		this.curve_pts = Curve.pts_transf(pts, this.matrix);
	}
	*/
	public void set_n(double n) {
		this.n = n;
	}
	public void set_a(double a) {
		this.a = a;
	}
	public void set_accuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public void set_inv_matrix(double[][] inv_matrix) {
		this.inv_matrix = inv_matrix;
	}
	public void set_matrix(double[][] matrix) {
		this.matrix = matrix;
	}
	public void set_curve(double[][] curve) {
		this.curve_pts = curve;
	}
	public void set_direction(int dir) {
		this.direction = dir;
	}
	public void compute_and_set_ratio(int[] pt1, int[] pt2) {
		this.ratio = Math.sqrt(Math.pow(pt1[0] - pt2[0], 2) + Math.pow(pt1[1] - pt2[1], 2));
	}
	
}
