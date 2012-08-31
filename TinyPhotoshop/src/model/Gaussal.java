package model;

public class Gaussal extends Convolution{

	public Gaussal(double sigma) {
		super(Gaussal.createGaussalKernel(sigma));
	}

	public static double[][] createGaussalKernel(double sigma) {
		int range = (int) (2.5*sigma);
		
		double[][] kernel = new double[range*2+1][range*2+1];
		for(int x = 0; x < kernel[0].length;x++){
			for(int y = 0; y < kernel.length;y++){
				kernel[y][x] = Gaussal.gauss2D(-(range)+x, -(range)+y, sigma);
			}
		}
		return kernel;
	}
	public static double gauss1D(double x, double sigma){
		//return (1./(Math.sqrt(2*Math.PI*sigma)))*(Math.pow(Math.E,-(Math.pow(x, 2)/(2*Math.pow(sigma, 2)))));
		return (1./(sigma*Math.sqrt(2*Math.PI)))*(Math.pow(Math.E,-(Math.pow(x, 2)/(2*Math.pow(sigma, 2)))));
	}
	public static double gauss2D(double x,double y, double sigma){
		return Gaussal.gauss1D(x, sigma)*Gaussal.gauss1D(y, sigma);
		//return (1./(2*Math.PI*Math.pow(sigma, 2)))*(Math.pow(Math.E, -((Math.pow(x, 2)+Math.pow(y, 2))/(2*Math.pow(sigma, 2)))));
	}
	

}
