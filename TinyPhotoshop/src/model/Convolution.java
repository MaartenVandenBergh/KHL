package model;

import util.Convertor;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class Convolution extends NeighbourhoodOperation{
	
	protected double[][]kernel;
	protected BorderStrategy borderStrategy;
	
	public Convolution(double[][] kernel){
		this(kernel,null);
	}
	public Convolution(double[][] kernel, BorderStrategy bs){
		this.setKernel(kernel);
		this.setBorderStrategy(bs);
		
	}

	private void setBorderStrategy(BorderStrategy bs) {
		if(bs != null){
			this.borderStrategy = bs;
		}
		else{
			this.borderStrategy = new FixedBorderStrategy();
		}
		
	}
	public void setKernel(double[][] kernel) {
		if(kernel.length%2 == 1 && kernel[0].length%2 == 1){
			this.kernel = this.normalize(kernel);
		}
		else{
			throw new IllegalArgumentException("Invalid kernel");
		}
	}

	@Override
	public int f(ImageProcessor ip, int x, int y) {
		double total = 0.0;
		if(ip instanceof ByteProcessor){
			int xRange = kernel[0].length/2;
			int yRange = kernel.length/2;

			for(int i = -xRange;i<=xRange;i++){
				for(int j = -yRange;j<=yRange;j++){
					total += borderStrategy.getPixel(ip,x+i, y+j)*(kernel[yRange+j][xRange+i]);
				}
			}
		}
		else if(ip instanceof ColorProcessor){
			double redTotal = 0.0;
			double greenTotal = 0.0;
			double blueTotal = 0.0;
			
			int xRange = kernel[0].length/2;
			int yRange = kernel.length/2;

			for(int i = -xRange;i<=xRange;i++){
				for(int j = -yRange;j<=yRange;j++){
					
					int[] comps = Convertor.getRGBComponentsFromColor(borderStrategy.getPixel(ip,x+i, y+j));
					redTotal += comps[0]*(kernel[yRange+j][xRange+i]);
					greenTotal += comps[1]*(kernel[yRange+j][xRange+i]);
					blueTotal += comps[2]*(kernel[yRange+j][xRange+i]);
					
				}
			}
			int[]comps = {(int) redTotal, (int) greenTotal, (int) blueTotal};
			total = Convertor.getColorFromRGBComponents(comps);
			
		}
		else{
			throw new UnsupportedOperationException();
		}
		return (int) total;
	}
	public double[][] normalize(double[][] kernel){
		double sum = 0;
		for(int x = 0; x < kernel[0].length;x++){
			for(int y = 0; y< kernel.length;y++){
				sum += kernel[y][x];
			}
		}
		for(int x = 0; x < kernel[0].length;x++){
			for(int y = 0; y< kernel.length;y++){
				kernel[y][x] = kernel[y][x]/sum;
			}
		}
		return kernel;
	}

}
