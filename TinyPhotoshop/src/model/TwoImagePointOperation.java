package model;

import ij.process.ImageProcessor;

public abstract class TwoImagePointOperation {

	public abstract int f(int color1, int color2);
	
	public void applyTo(ImageProcessor ip1, ImageProcessor ip2){
		for(int x = 0;x<ip1.getWidth();x++){
			for(int y = 0;y<ip1.getHeight();y++){
				int color1 = ip1.getPixel(x, y);
				int color2 = ip2.getPixel(x,y);
				int c = f(color1, color2);
				ip1.putPixel(x, y, c);
			}
		}
	}
}
