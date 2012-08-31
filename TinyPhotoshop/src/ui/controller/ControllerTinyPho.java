package ui.controller;

import ij.ImagePlus;

import java.awt.Point;
import java.io.File;

import exceptions.DbException;
import exceptions.ModelException;

import ui.view.SharpenSCP;
import ui.view.ViewType;
import util.Kernel;
import model.Model;
import model.ModelTinyPho;
import model.Mosaic;
import model.ProjectiveTransformation;
import model.Sharpen;
import model.Sphere;

public class ControllerTinyPho implements Controller{
	
	private ModelTinyPho model;
	
//	Constructor
	public ControllerTinyPho(Model model){
		this.setModel(model);
	}
	
//	Main methods
	
//	Controller methods
	@Override
	public Model getModel() {
		return this.model;
	}

	@Override
	public void setModel(Model model){
		if(model instanceof ModelTinyPho){
			this.model = (ModelTinyPho)model;
		}
		else{
			throw new IllegalArgumentException("Invalid Model");
		}
	}

	public ViewType getViewType() {
		return this.model.getViewType();
	}
	public void setViewType(ViewType type){
		this.model.setViewType(type);
	}

	public void setOriginalImage(File f) throws ModelException{
		this.model.setOriginalImage(f);
		
	}

	public ImagePlus getOriginalImage() {
		return this.model.getOriginalImage();
	}
	public void apply(){
		this.model.apply();
	}
	public void reset(){
		this.model.reset();
	}
	public void save(String type, String location)throws IllegalArgumentException{
		this.model.save(type, location);
	}
	public void setSharpen(double sigma, double alpha){
		this.model.setSharpen(sigma, alpha);
	}
	public void setMosaic(int amount){
		this.model.setMosaic(amount);
	}
	public void setSphere(Point point, double radius, double index){
		this.model.setSphere(point, radius, index);
	}
	public void setProjectiveTransformation(Kernel kernel){
		this.model.setProjectiveTransformation(kernel);
	}

	public ImagePlus getNewImage() {
		return this.model.getNewImage();
	}

	public Sharpen getSharpen() {
		return this.model.getSharpen();
	}
	public Mosaic getMosaic(){
		return this.model.getMosaic();
	}
	public Sphere getSphere(){
		return this.model.getSphere();
	}
	public ProjectiveTransformation getProjectiveTransformation(){
		return this.model.getProjectiveTransformation();
	}

	public Kernel[] getKernelList() {
		return this.model.getKernelList();
	}

	public void setKernelWithIndex(int index) {
		this.model.setKernelWithIndex(index);
		
	}

	public void addCustomKernel(Kernel kernel) throws DbException {
		this.model.addCustomKernel(kernel);
		
	}

	public void removeKernelWithIndex(int index)throws DbException {
		this.model.removeKernelWithIndex(index); 
		
	}

	public void saveDb() {
		this.model.saveDb();
		
	}

}
