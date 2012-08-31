package model;

import ij.process.ImageProcessor;

public abstract class BorderStrategy {
	public abstract int getPixel(ImageProcessor ip, int x, int y);

}
