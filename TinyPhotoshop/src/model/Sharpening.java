package model;

import util.Convertor;

public class Sharpening extends TwoImagePointOperation{
	
	private double alpha;

	public Sharpening(double alpha){
		this.setAlpha(alpha);
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
		
	}

	@Override
	public int f(int color1, int color2) {
		int[]comp1 = Convertor.getRGBComponentsFromColor(color1);
		int[]comp2 = Convertor.getRGBComponentsFromColor(color2);
		int[]nComp = new int[3];
		
		nComp[0] = (int)(((1+alpha)*comp1[0])-(alpha*comp2[0]));
		nComp[1] = (int)(((1+alpha)*comp1[1])-(alpha*comp2[1]));
		nComp[2] = (int)(((1+alpha)*comp1[2])-(alpha*comp2[2]));
		
		for(int i = 0; i<nComp.length;i++){
			nComp[i] = this.maintainColorBorders(nComp[i]);
		}
		
		return Convertor.getColorFromRGBComponents(nComp);
		
	}

	private int maintainColorBorders(int color) {
		if(color<0){
			color = 0;
		}
		if(color>255){
			color = 255;
		}
		return color;
		
	}

}
