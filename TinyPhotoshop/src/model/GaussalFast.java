package model;

import ij.process.ImageProcessor;
import util.Orientation;

public class GaussalFast{
	
	private double sigma;
	private Convolution convolution;

	public GaussalFast(double sigma, BorderStrategy bs){
		this.setSigma(sigma);
		this.setConvolution(new Convolution(this.createFastGaussalKernel(sigma, Orientation.HORIZONTAL), bs));
	}
	public void setConvolution(Convolution convolution) {
		this.convolution = convolution;
		
	}
	private void setSigma(double sigma) {
		this.sigma = sigma;
		
	}
	public double[][] createFastGaussalKernel(double sigma, Orientation or){
		int range = (int) (2.5*sigma);
		double[][]kernel;
		switch(or){
			case HORIZONTAL:
				kernel = new double[1][range*2+1];
				break;
			case VERTICAL:
				kernel = new double[range*2+1][1];
				break;
			default:
				throw new IllegalArgumentException("wrong orientation");
		}
		for(int x = 0; x < kernel[0].length;x++){
			for(int y = 0; y < kernel.length;y++){
				kernel[y][x] = Gaussal.gauss2D(-(range)+x, -(range)+y, sigma);
			}
		}
		return kernel;
	}
	public void applyTo(ImageProcessor ip){
		convolution.applyTo(ip);
		
		convolution.setKernel(this.createFastGaussalKernel(sigma, Orientation.VERTICAL));
		convolution.applyTo(ip);
	}

}
