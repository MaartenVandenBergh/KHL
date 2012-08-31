package model;

import model.Sharpening;
import ij.process.ImageProcessor;

public class Sharpen {
	
	private double sigma;
	private double alpha;
	
	
	public Sharpen(double sigma, double alpha){
		this.setSigma(sigma);
		this.setAlpha(alpha);
	}

	public void setAlpha(double alpha2) {
		this.alpha = alpha2;
	}
	public double getAlpha(){
		return this.alpha;
	}
	public void setSigma(double sigma2) {
		this.sigma = sigma2;	
	}
	public double getSigma(){
		return this.sigma;
	}
	public void applyTo(ImageProcessor ip){
		ImageProcessor copy = ip.duplicate();
		
		GaussalFast gaussal = new GaussalFast(this.sigma, new MirroredBorderStrategy());
		gaussal.applyTo(copy);
		
		Sharpening sharpen = new Sharpening(this.alpha);
		sharpen.applyTo(ip, copy);
	}
	

}
