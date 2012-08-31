package model;

import ij.process.ImageProcessor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mosaic extends Transformation{

	private int count;
	private List<Point> mosaicField;
	
	public Mosaic(int count){
		this.setCount(count);
	}
	
	public void setCount(int count) {
		this.count = count;
		
	}
	public int getCount(){
		return this.count;
	}

	private void setMosaicField(List<Point> mosaicField) {
		this.mosaicField = mosaicField;
		
	}
	@Override
	public Point pointFunction(ImageProcessor ip, Point point) {
		Point closest = null;
		double test = 999999999;
		
		for(Point p:mosaicField){
			double distance = Math.pow((p.x-point.x), 2)+Math.pow((p.y-point.y), 2);//Math.sqrt weggelaten omdat de relatieve grootte tegenover de anderen niet veranderd.
			if(distance < test){
				closest = p;
				test = distance;
			}
		}
		return closest;
	}
	@Override
	public void applyTo(ImageProcessor ip){
		this.setMosaicField(this.generateMosaicField(this.count,ip.getWidth(), ip.getHeight()));
		super.applyTo(ip);
	}
	private List<Point> generateMosaicField(int count, int width, int height) {
		List<Point> field = new ArrayList<Point>();
		Random r = new Random();
		
		int i = 0;
		while(field.size()<count){
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			
			Point p = new Point(x,y);
			if(!field.contains(p)){
				field.add(i, p);
				i++;
			}
		}
		return field;
	}

}
