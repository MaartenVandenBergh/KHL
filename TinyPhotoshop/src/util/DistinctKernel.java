package util;

import db.DistinctionByType;
import db.DistinctionType;

public class DistinctKernel implements DistinctionByType{
	
	private DistinctionType type;
	private Kernel kernel;

	public DistinctKernel(Kernel kernel, DistinctionType type) {
		this.setKernel(kernel);
		this.setDistinctionType(type);
	}
	
	public void setKernel(Kernel kernel){
		this.kernel = kernel;
	}
	public Kernel getKernel(){
		return this.kernel;
	}

	@Override
	public void setDistinctionType(DistinctionType type) {
		this.type = type;
	}

	@Override
	public DistinctionType getDistinctionType() {
		return this.type;
	}
	@Override
	public boolean equals(Object o){
		boolean output = true;
		if(o instanceof DistinctKernel){
			DistinctKernel dk = (DistinctKernel)o;
			if(!this.getKernel().equals(dk.getKernel())){
				output = false;
			}
		}
		else{
			output = false;
		}
		return output;
	}
}
