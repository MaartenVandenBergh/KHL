package ui;

import ij.ImagePlus;
import ij.gui.ImageCanvas;

import javax.swing.JFrame;

import ui.controller.ControllerTinyPho;
import ui.view.ViewMain;

import model.ModelTinyPho;
import db.DbKernel;

public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbKernel db = new DbKernel();
		ModelTinyPho model = new ModelTinyPho(db);
		ControllerTinyPho cont = new ControllerTinyPho(model);
		ViewMain view = new ViewMain(cont);
		
		model.registerView(view);
	}

}
