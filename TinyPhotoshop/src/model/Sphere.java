package model;

import ij.process.ImageProcessor;

import java.awt.Point;
import java.io.IOException;

public class Sphere extends Transformation{
	
	private Point center;
	private double radius;
	private double index;

	public Sphere(Point center, double radius, double index) {
		this.setCenter(center);
		this.setRadius(radius);
		this.setIndex(index);
	}

	private void setIndex(double index) {
		this.index = index;
		
	}
	public double getIndex(){
		return this.index;
	}

	public void setRadius(double radius) {
		this.radius = radius;
		
	}
	public double getRadius(){
		return this.radius;
	}

	public void setCenter(Point center) {
		if(center.x < 0 || center.y <0){
			throw new IllegalArgumentException("Center can't have negative values");
		}
		this.center = center;
		
		
	}
	public Point getCenter(){
		return this.center;
	}

	@Override
	public Point pointFunction(ImageProcessor ip, Point p){
		if(!this.checkCenter(ip)){
			throw new IllegalArgumentException("Center is not in on image.");
		}

		int x = 0;
		int y = 0;
		int dx = p.x-center.x;
		int dy = p.y-center.y;
		double distance = Math.sqrt(Math.pow((p.x-center.x), 2)+Math.pow((p.y-center.y), 2));
		double z = Math.sqrt(Math.pow(this.radius, 2)-Math.pow(distance, 2));
		double nIndex = this.smoothLens(this.index, distance, this.radius);
		
		if(distance <= this.radius){
			x = this.sphereFormula(p.x, dx, distance, z, nIndex);
			y = this.sphereFormula(p.y, dy, distance, z, nIndex);
		}
		else{
			x = p.x;
			y = p.y;
		}
		
		
		return new Point(x,y);
	}

	private double smoothLens(double index, double distance, double radius) {
		return 1+((index-1)*(radius-distance))/radius;
	}

	private int sphereFormula(int x, int dx , double distance, double z, double index) {
		int output = (int)(x-(z*Math.tan((1-1/index)*Math.asin(dx/(Math.sqrt(Math.pow(distance, 2)+Math.pow(z, 2)))))));
		return output;
		
	}

	private boolean checkCenter(ImageProcessor ip){
		if(center.x > ip.getWidth() || center.y >ip.getHeight()){
			return false;
		}
		else{
			return true;
		}
	}
	

}
