package model;

import ij.process.ImageProcessor;

public class MirroredBorderStrategy extends BorderStrategy{

	@Override
	public int getPixel(ImageProcessor ip, int x, int y) {
		int nx = x;
		int ny = y;
		
		if(x< 0){
			nx = -x;
		}
		if(x>ip.getWidth()){
			nx = ip.getWidth()-(x-ip.getWidth());
		}
		if(y<0){
			ny = -y;
		}
		if(y>ip.getHeight()){
			ny = ip.getHeight()-(y-ip.getHeight());
		}
		return ip.getPixel(nx, ny);
	}

}
