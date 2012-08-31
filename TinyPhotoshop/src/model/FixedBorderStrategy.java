package model;

import ij.process.ImageProcessor;

public class FixedBorderStrategy extends BorderStrategy {

	@Override
	public int getPixel(ImageProcessor ip, int x, int y) {
		int color;
		
		if(x <0 || x >= ip.getWidth() || y <0 || y >= ip.getHeight()){
			color = 0;
		}
		else{
			color = ip.getPixel(x, y);
		}
		return color;
	}

}
