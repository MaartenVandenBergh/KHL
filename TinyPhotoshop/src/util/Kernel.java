package util;

public class Kernel {
	
	private double[][] elements;
	private String name = "---";
	
//	Constructor
	public Kernel(double[][] elements){
		this.setElements(elements);
	}
	
//	Getters&Setters
	public void setElements(double[][] ds) {
		this.elements = ds;
	}
	public double[][] getElements(){
		return this.elements;
	}
	public void setName(String n){
		this.name = n;
	}
	public String getName(){
		return this.name;
	}
//	Utilities
	public double getElement(int x, int y){
		return elements[y][x];
	}
	public int getNbRows(){
		return this.elements.length;
	}
	public int getNbColums(){
		return this.elements[0].length;
	}
	public double get(int x, int y){
		return this.elements[y][x];
	}
	public void set(int x, int y, double input){
		this.elements[y][x] = input;
	}
	public Kernel multiplyBy(Kernel m){
		Kernel newM = new Kernel(new double[this.getNbRows()][m.getNbColums()]);
		for(int x = 0; x<newM.getNbColums();x++){
			for(int y = 0;y<newM.getNbRows();y++){
				double sum = 0;
				for(int j = 0;j<m.getNbRows();j++){
					sum += this.get(j, y)*m.get(x, j);
				}
				newM.set(x, y,sum);
			}
		}
		return newM;
	}
	public double sumOfRow(int row){
		double sum = 0;
		for(int i = 0; i< this.getNbColums();i++){
			sum += this.get(row, i);
		}
		return sum;
	}
	public double sumOfColumn(int column){
		double sum = 0;
		for(int i = 0; i< this.getNbRows();i++){
			sum += this.get(i, column);
		}
		return sum;
	}
	
	@Override
	public boolean equals(Object o){
		boolean output = true;
		if(o instanceof Kernel){
			Kernel kernel = (Kernel)o;
			int x = 0;
			int y = 0;
			
			while(x<elements[0].length && output){
				while(y<elements.length && output){
					if(this.getElement(x,y) != kernel.getElement(x,y)){
						output = false;
					}
					y++;
				}
				x++;
			}
		}else{
			output = false;
		}
		
		return output;
	}
}
