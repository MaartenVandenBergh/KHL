package util;

import java.util.Arrays;

public class Convertor {
	
	public static int[] getRGBComponentsFromColor(int color){
		int[] components = new int[3];
		
		components[0] = (color & 0x00ff0000) >> 16;//red
		components[1] = (color & 0x0000ff00) >> 8;//green
		components[2] = (color & 0x000000ff) >> 0;//blue
		
		return components;
	}
	public static int getColorFromRGBComponents(int[] components){	
		
		int red = components[0] << 16;
		int green = components[1] << 8;
		int blue = components[2] << 0;
		
		return red + green + blue;
	}
	public static int[] getHSVfromRGB(int[] components){
		float[] comps = new float[components.length];
		float[] compsS = new float[components.length];
		for(int i = 0; i<components.length;i++){
			comps[i] = ((float)components[i]/255);
			compsS[i] = comps[i];
			
			//DEBUG RGB INPUT
			//System.out.println(components[i]);
		}
		Arrays.sort(compsS);
		if(compsS[2] != 0){
			if(components[0] == components[1] && components[1] == components[2]){
				components[0] = 0;
				components[1] = 0;
			}
			else{
				float h = 0;
				float s = 0;
				float v = 0;
				if(compsS[2] == comps[0]){
					h = ( 0+(comps[1]-comps[2])/(compsS[2]-compsS[0]) )*60;
				}
				else{
					if(compsS[2] == comps[1]){
						h = ( 2+(comps[2]-comps[0])/(compsS[2]-compsS[0]) )*60;
					}
					else{
						if(compsS[2] == comps[2]){
							h = ( 4+(comps[0]-comps[1])/(compsS[2]-compsS[0]) )*60;
						}
					}
				}
				s=(compsS[2]-compsS[0])/compsS[2];
				v=compsS[2];
				components[0] = (int) (h);
				components[1] = (int) (s*100);
				components[2] = (int) (v*100);
				
				/*
				//DEBUG HSV OUTPUT
				for(int i = 0; i<components.length;i++){
					System.out.println(components[i]);
				}*/
 			}
		}
		return components;
	}
	
}
