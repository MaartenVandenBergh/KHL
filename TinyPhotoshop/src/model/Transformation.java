package model;

import ij.process.ImageProcessor;

import java.awt.Point;
import java.io.IOException;

public abstract class Transformation {
	
	public abstract Point pointFunction(ImageProcessor ip,Point point);
	
	public void applyTo(ImageProcessor ip){
		ImageProcessor copy = ip.duplicate();
		
		for(int x = 0; x<ip.getWidth();x++){
			for(int y = 0;y<ip.getHeight();y++){
				Point p = pointFunction(ip, new Point(x,y));
				int color = copy.getPixel((int) Math.round(p.x), (int)Math.round(p.y));
				ip.putPixel(x, y, color);
			}
		}
	}

}
