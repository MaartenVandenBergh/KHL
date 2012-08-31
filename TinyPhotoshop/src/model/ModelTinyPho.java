package model;

import ij.ImagePlus;
import ij.gui.NewImage;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import db.Db;
import db.DbKernel;
import db.DistinctionType;
import exceptions.DbException;
import exceptions.ModelException;

import ui.view.View;
import ui.view.ViewType;
import util.Kernel;

public class ModelTinyPho implements Model{
	
	private ViewType viewType;
	private DbKernel db;
	private ArrayList<View> viewList;
	
	private ImagePlus originalImage;
	private ImagePlus newImage;
	
	private Sharpen sharpen;
	private Mosaic mosaic;
	private Sphere sphere;
	private ProjectiveTransformation projTrans;
	
	
	public ModelTinyPho(Db db){
		this.initialise();
	}

//	Getters&Setters
	public void setViewType(ViewType type){
		this.viewType = type;
		this.notifyAllViews();
	}
	public ViewType getViewType(){
		return this.viewType;
	}
	public void setDb(DbKernel db){
		this.db = db;
	}
	public Db getDb(){
		return this.db;
	}
	public void setOriginalImage(ImagePlus image){
		this.originalImage = image;
		this.notifyAllViews();
	}
	public ImagePlus getOriginalImage(){
		return this.originalImage;
	}
	public void setNewImage(ImagePlus image){
		this.newImage = image;
		this.notifyAllViews();
	}
	public ImagePlus getNewImage(){
		return this.newImage;
	}
	public void setSharpen(double sigma, double alpha){
		this.sharpen = new Sharpen(sigma, alpha);
	}
	public Sharpen getSharpen(){
		return this.sharpen;
	}
	public void setMosaic(int amount){
		this.mosaic = new Mosaic(amount);
	}
	public Mosaic getMosaic(){
		return this.mosaic;
	}
	public void setSphere(Point point, double radius, double index){
		this.sphere = new Sphere(point, radius, index);
	}
	public Sphere getSphere(){
		return this.sphere;
	}
	public void setProjectiveTransformation(Kernel kernel){
		this.projTrans = new ProjectiveTransformation(kernel);
	}
	public ProjectiveTransformation getProjectiveTransformation(){
		return this.projTrans;
	}
//	Initialisation
	private void initialise() {
		this.viewList = new ArrayList<View>();
		this.setViewType(ViewType.CLEAR);
		this.setDb(new DbKernel());
		this.db.load("kernels.xml");
		this.setOriginalImage(NewImage.createRGBImage("empty", 500, 500, 1, NewImage.FILL_WHITE));
		this.sharpen = new Sharpen(0.75,3);
		this.mosaic = new Mosaic(100);
		this.sphere = new Sphere(new Point(0,0), 100, 1.5);
		this.projTrans = new ProjectiveTransformation(new Kernel(new double[][]{{0,0,0},{0,0,0},{0,0,0}}));
		
	}
//	Model Methods
	@Override
	public void registerView(View v) {
		if(v != null){
			if(!viewList.contains(v)){
				this.viewList.add(v);
			}
			else{
				throw new IllegalArgumentException("MainGame : View already registered");
			}
		}
		else{
			throw new IllegalArgumentException("MainGame : Can't register Invalid View");
		}
	}

	@Override
	public void removeView(View v) {
		if(v != null){
			if(viewList.contains(v)){
				this.viewList.remove(v);
			}
			else{
				throw new IllegalArgumentException("View not registered");
			}
		}
		else{
			throw new IllegalArgumentException("Can't remove Invalid View");
		}
	}

	@Override
	public void notifyAllViews() {
		@SuppressWarnings("unchecked")
		ArrayList<View> clone = (ArrayList<View>)this.viewList.clone();
		for(View view :clone){
			view.update();
		}	
	}

	@Override
	public void notifyView(int index) {
		viewList.get(index).update();
	}
//	Util
	public static ImagePlus loadImage(String name) {
		ImagePlus image = null;
		try{
			File file = new File(name);
			BufferedImage inMem = ImageIO.read(file);
			image = new ImagePlus(file.getName(), inMem);
		}
		catch(IOException e){
			System.out.println("File not found");
			System.exit(0);
		}
		return image;
	}
	
	public static ImagePlus loadURL(String name) {
		ImagePlus image = null;
		try{
			URL url = new URL(name);
			BufferedImage inMem = ImageIO.read(url);
			image = new ImagePlus("linked", inMem);
		}
		catch(IOException e){
			System.out.println("URL not found");
			System.exit(0);
		}
		return image;
	}

//Main Methods
	public void setOriginalImage(File file)throws ModelException{
		try{
			BufferedImage inMem = ImageIO.read(file);
			if(inMem != null){
				this.setOriginalImage(new ImagePlus(file.getName(), inMem));
			}
			else{
				throw new ModelException("Please select an Image file");
			}
		}
		catch(IOException e){
			throw new ModelException("Didn't find file");
		}
	}
	public void apply(){
		ImagePlus source = null;
		if(this.newImage == null){
			source = this.getOriginalImage().duplicate();
		}
		else{
			source = this.getNewImage();
		}
		
		switch(this.getViewType()){
		case CLEAR:
			source = null;
			break;
		case SHARPEN:
			sharpen.applyTo(source.getProcessor());
			break;
		case MOSAIC:
			mosaic.applyTo(source.getProcessor());
			break;
		case SPHERE:
			sphere.applyTo(source.getProcessor());
			break;
		case PROJECTIVE_TRANSFORMATION:
			projTrans.applyTo(source.getProcessor());
			break;
	}
		
		
		this.setNewImage(source);
	}
	public void reset(){
		this.setNewImage(null);
	}
	public void save(String type, String location)throws IllegalArgumentException{
		ImagePlus source = null;
		if(this.newImage == null){
			source = this.getOriginalImage().duplicate();
		}
		else{
			source = this.getNewImage();
		}
		try{
			ImageIO.write(source.getBufferedImage(), type, new File(location));
		}
		catch(IOException e){
			throw new IllegalArgumentException("Invalid image type");
		}
	}

	public Kernel[] getKernelList() {
		Object[] olist = this.db.getAllKernels().toArray();
		Kernel[] klist = new Kernel[olist.length];
		for(int i = 0;i<olist.length;i++){
			klist[i] = (Kernel)olist[i];
		}
		return klist;
	}

	public void setKernelWithIndex(int index) {
		Kernel k = this.getKernelList()[index];
		this.projTrans.setKernel(k);
		this.notifyAllViews();	
	}

	public void addCustomKernel(Kernel kernel) throws DbException {
		this.db.addKernel(kernel, DistinctionType.CUSTOM);
		this.notifyAllViews();
	}

	public void removeKernelWithIndex(int index)throws DbException {
		this.db.removeKernel(this.getKernelList()[index]);
		this.notifyAllViews();
	}

	public void saveDb() {
		this.db.save("kernels.xml");
		
	}
}
