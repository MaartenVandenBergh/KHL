package model;

import ij.process.ImageProcessor;

public abstract class NeighbourhoodOperation {
	public abstract int f(ImageProcessor ip, int x, int y);
	
	public void applyTo(ImageProcessor ip){
		ImageProcessor copy = ip.duplicate();
		
		for(int x = 0; x < ip.getWidth(); x++){
			for(int y = 0; y < ip.getHeight();y++){
				int c = f(copy, x, y);
				ip.putPixel(x, y, c);
			}
		}
	}
}
