package model;

import ij.process.ImageProcessor;

import java.awt.Point;

import util.Kernel;

public class ProjectiveTransformation extends Transformation{
	
	private Kernel matrix;
	
	public ProjectiveTransformation(Kernel matrix){
		this.setKernel(matrix);
	}
	public void setKernel(Kernel matrix){
		this.matrix = matrix;
	}
	public Kernel getKernel(){
		return this.matrix;
	}
	@Override
	public Point pointFunction(ImageProcessor ip, Point point) {	
		Kernel m = ProjectiveTransformation.convertFromPointToHomogeneousCoordinate(point);
		m = this.matrix.multiplyBy(m);
		point = ProjectiveTransformation.convertFromHomogeneousCoordinateToPoint(m);
		
		return point;
	}

	private static Kernel convertFromPointToHomogeneousCoordinate(Point point) {
		double[][] elements = new double[][]{{point.x},{point.y},{1}};
		return new Kernel(elements);
	}
	private static Point convertFromHomogeneousCoordinateToPoint(Kernel m){
		int x = (int) (m.get(0, 0)/m.get(0, 2));
		int y = (int) (m.get(0, 1)/m.get(0, 2));
		
		return new Point(x,y);
	}

}
