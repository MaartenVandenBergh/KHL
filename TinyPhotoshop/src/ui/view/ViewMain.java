package ui.view;

import ij.ImagePlus;
import ij.gui.ImageCanvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exceptions.DbException;
import exceptions.ModelException;

import ui.controller.Controller;
import ui.controller.ControllerTinyPho;
import util.FileExtensionUtils;
import util.Kernel;

public class ViewMain extends JFrame implements View, ActionListener,WindowListener,ListSelectionListener{
	
//	JFrame Parameters
	private static final long serialVersionUID = 1L;
	
//	View Parameters
	private ControllerTinyPho controller;
	
//	Component Parameters
	private JMenuBar menuBar;
	private JButton imageMenu;
	private JMenu effectMenu;
	private JMenuItem sharpen;
	private JMenuItem mosaic;
	private JMenuItem sphere;
	private JMenuItem projTransf;
	
	private JPanel optionsPanel;
	private ImageCanvas imageCanvas;
	private JPanel controlPanel;
	private JButton applyButton;
	private JButton resetButton;
	private JButton saveButton;
	
	private JPanel specificControlPanel;
	
//	Consistency Parameters
	private int menuBarHeight;
	private int optionsPanelHeight;

	
//	Constructor
	public ViewMain(ControllerTinyPho cont){
		this.setController(cont);
		this.initialise();
	}

//	View Methods
	@Override
	public void setController(Controller cont) {
		if(cont instanceof ControllerTinyPho){
			this.controller = (ControllerTinyPho)cont;
		}
		else{
			throw new IllegalArgumentException("Invalid Controller");
		}
		
	}
	@Override
	public Controller getController(){
		return this.controller;
	}

	@Override
	public void update() {
		switch(this.controller.getViewType()){
			case CLEAR:
				this.emptyOptionsPanel();
				break;
			case SHARPEN:
				if(!(this.specificControlPanel instanceof SharpenSCP)){
					this.emptyOptionsPanel();
					this.specificControlPanel = new SharpenSCP();
					this.optionsPanel.add(this.specificControlPanel);
				}
				((SharpenSCP)this.specificControlPanel).setSigma(this.controller.getSharpen().getSigma());
				((SharpenSCP)this.specificControlPanel).setAlpha(this.controller.getSharpen().getAlpha());
				
				break;
			case MOSAIC:
				if(!(this.specificControlPanel instanceof MosaicSCP)){
					this.emptyOptionsPanel();
					this.specificControlPanel = new MosaicSCP();
					this.optionsPanel.add(this.specificControlPanel);
				}
				((MosaicSCP)this.specificControlPanel).setAmount(this.controller.getMosaic().getCount());
				break;
			case SPHERE:
				if(!(this.specificControlPanel instanceof SphereSCP)){
					this.emptyOptionsPanel();
					this.specificControlPanel = new SphereSCP();
					this.optionsPanel.add(this.specificControlPanel);
				}
				((SphereSCP)this.specificControlPanel).setCenter(this.controller.getSphere().getCenter());
				((SphereSCP)this.specificControlPanel).setRadius(this.controller.getSphere().getRadius());
				((SphereSCP)this.specificControlPanel).setIndex(this.controller.getSphere().getIndex());
				break;
			case PROJECTIVE_TRANSFORMATION:
				if(!(this.specificControlPanel instanceof ProjTransSCP)){
					this.emptyOptionsPanel();
					this.specificControlPanel = new ProjTransSCP(this,this);
					this.optionsPanel.add(this.specificControlPanel);
				}
				if(((ProjTransSCP)this.specificControlPanel).isEmpty()){
					((ProjTransSCP)this.specificControlPanel).setList(this.controller.getKernelList());
				}
				((ProjTransSCP)this.specificControlPanel).setKernel(this.controller.getProjectiveTransformation().getKernel());
				break;
		}
		if(this.controller.getNewImage() == null && !this.imageCanvas.getImage().equals(this.controller.getOriginalImage())){
			this.changeImage(this.controller.getOriginalImage());
		}
		if(this.controller.getNewImage() != null){
			this.changeImage(this.controller.getNewImage());
		}
		this.arrange();
	}
	//	Main Methods
	private void initialise() {
		this.setLayout(null);
		this.addWindowListener(this);
		
		this.menuBarHeight = 50;
		this.optionsPanelHeight = 100;
		
		this.initialiseMenuBar();
		
		this.imageCanvas = new ImageCanvas(this.controller.getOriginalImage());
		this.add(imageCanvas);
		
		initialiseOptionsPanel();
		
		this.changeImage(this.controller.getOriginalImage());
		
		this.update();
		this.setTitle("TinyPho");
		this.setResizable(false);
		
		this.pack();
		this.setVisible(true);
		
	}

	public void initialiseOptionsPanel() {
		this.optionsPanel = new JPanel();
		this.add(optionsPanel);
		this.optionsPanel.setLayout(null);
		this.optionsPanel.setBackground(null);
		
		this.controlPanel = new JPanel();
		this.optionsPanel.add(this.controlPanel);
		this.controlPanel.setLayout(null);
		
		this.applyButton = new JButton("Apply");
		this.controlPanel.add(applyButton);
		this.applyButton.addActionListener(this);
		
		this.resetButton = new JButton("Reset");
		this.controlPanel.add(resetButton);
		this.resetButton.addActionListener(this);
		
		this.saveButton = new JButton("Save");
		this.controlPanel.add(saveButton);
		this.saveButton.addActionListener(this);
		
	}
	private void initialiseMenuBar() {
		this.menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		this.menuBar.setBounds(0,0,this.getWidth(),this.menuBarHeight);
		this.menuBar.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.imageMenu = new JButton("(select imagefile)");
		this.imageMenu.addActionListener(this);
		
		Border bevel = BorderFactory.createLoweredBevelBorder();
		Border margin = new EmptyBorder(3, 5, 3, 5);
		Border compound = new CompoundBorder(bevel, margin);
		this.imageMenu.setBorder(compound);
		
		this.imageMenu.setHorizontalAlignment(SwingConstants.LEFT);
		this.imageMenu.setBackground(Color.WHITE);
		this.menuBar.add(imageMenu);
		
		this.menuBar.add(Box.createHorizontalGlue());
		
		this.effectMenu = new JMenu("(choose effect)");
		this.menuBar.add(effectMenu);
		
		this.sharpen = new JMenuItem("Sharpen");
		this.effectMenu.add(sharpen);
		this.sharpen.addActionListener(this);
		
		mosaic = new JMenuItem("Mosaïc");
		this.effectMenu.add(mosaic);
		this.mosaic.addActionListener(this);
		
		sphere = new JMenuItem("Sphere");
		this.effectMenu.add(sphere);
		this.sphere.addActionListener(this);
		
		projTransf = new JMenuItem("Projective Transformation");
		this.effectMenu.add(projTransf);
		this.projTransf.addActionListener(this);
	}
	
//	Util
	private void emptyOptionsPanel() {
		if(this.optionsPanel.getComponents().length > 1){
			this.optionsPanel.remove(1);
		}
		
	}
	private void arrange(){
		this.setSize(this.imageCanvas.getWidth(), this.imageCanvas.getHeight()+this.menuBarHeight+this.optionsPanelHeight);
		this.setMinimumSize(new Dimension(500,this.menuBarHeight+this.optionsPanelHeight+15));
		
		this.imageMenu.setMaximumSize(new Dimension(this.menuBar.getWidth()-100,this.menuBar.getHeight()));
		this.optionsPanel.setBounds(0, 0,this.getWidth(), this.optionsPanelHeight);
		this.imageCanvas.setLocation(0,this.optionsPanelHeight);
		
		this.controlPanel.setBounds(this.optionsPanel.getWidth()-100,0,100, this.optionsPanelHeight);
		this.controlPanel.setBackground(null);
		this.applyButton.setBounds(5, 5, 85, 40);
		this.applyButton.setBackground(new Color(99,184,255));
		this.resetButton.setBounds(5, 50, 85, 20);
		this.saveButton.setBounds(5, 75, 85, 20);
		
		if(this.specificControlPanel != null){
			this.specificControlPanel.setBounds(0, 0, this.optionsPanel.getWidth()-100, this.optionsPanelHeight);
		}
	}
	public void changeImage(ImagePlus image) {
		this.remove(imageCanvas);
		this.imageCanvas = new ImageCanvas(image);
		this.add(imageCanvas);
	}
	private void trySavingFile(File file) {
		try{
			  this.controller.save(FileExtensionUtils.getExtensionFromFile(file), file.getPath());
		  }
		  catch(IllegalArgumentException e){
			  JOptionPane.showMessageDialog(null, e.getMessage());
		  }
	}
	
//	ActionListener Methods
	@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getSource().equals(this.imageMenu)){
			JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            if(f != null){
            	try{
            		this.controller.reset();
            		this.controller.setOriginalImage(f);
            		this.imageMenu.setText(f.getPath());
            	}
            	catch(ModelException me){
            		JOptionPane.showMessageDialog(null,me.getMessage());
            	}
            }
		}
		if(a.getSource().equals(this.sharpen)){
			this.controller.setViewType(ViewType.SHARPEN);
			this.effectMenu.setText("Sharpen");
		}
		if(a.getSource().equals(this.mosaic)){
			this.controller.setViewType(ViewType.MOSAIC);
			this.effectMenu.setText("Mosaïc");
		}
		if(a.getSource().equals(this.sphere)){
			this.controller.setViewType(ViewType.SPHERE);
			this.effectMenu.setText("Sphere");
		}
		if(a.getSource().equals(this.projTransf)){
			this.controller.setViewType(ViewType.PROJECTIVE_TRANSFORMATION);
			this.effectMenu.setText("Projective Trans.");
		}
		if(a.getSource().equals(this.applyButton)){
			try{
				switch(this.controller.getViewType()){
				case CLEAR:
					
					break;
				case SHARPEN:
					if(this.specificControlPanel instanceof SharpenSCP){
						SharpenSCP sSCP = (SharpenSCP)this.specificControlPanel;
						this.controller.setSharpen(sSCP.getSigma(),sSCP.getAlpa());
						
					}
					break;
				case MOSAIC:
					if(this.specificControlPanel instanceof MosaicSCP){
						MosaicSCP mSCP = (MosaicSCP)this.specificControlPanel;
						this.controller.setMosaic(mSCP.getAmount());
						
					}
					break;
				case SPHERE:
					if(this.specificControlPanel instanceof SphereSCP){
						SphereSCP sSCP = (SphereSCP)this.specificControlPanel;
						this.controller.setSphere(sSCP.getCenter(),sSCP.getRadius(),sSCP.getIndex());
						
					}
					break;
				case PROJECTIVE_TRANSFORMATION:
					if(this.specificControlPanel instanceof ProjTransSCP){
						ProjTransSCP pSCP = (ProjTransSCP)this.specificControlPanel;
						this.controller.setProjectiveTransformation(pSCP.getKernel());
						
					}
					break;
				}
				this.controller.apply();
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Wrong input");
			}
		}
		if(a.getSource().equals(this.resetButton)){
			this.controller.reset();
		}
		if(a.getSource().equals(this.saveButton)){
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			  File file = fileChooser.getSelectedFile();
			  if(!file.exists()){
				  trySavingFile(file);
			  }
			  else{
				  int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to replace this file?","WARNING!",0);
				  if(option == 0){
				 	trySavingFile(file);
				  }
			  }
			}
		}
		if(this.specificControlPanel instanceof ProjTransSCP){
			if(a.getSource().equals(((ProjTransSCP)this.specificControlPanel).getAddButton())){
				Kernel k = ((ProjTransSCP)this.specificControlPanel).getKernel();
				k.setName(JOptionPane.showInputDialog("Save as:"));
				try{
					((ProjTransSCP)this.specificControlPanel).emptyList();
					this.controller.addCustomKernel(k);
				}
				catch(DbException e){
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			if(a.getSource().equals(((ProjTransSCP)this.specificControlPanel).getDelButton())){
				try{
					int i = ((ProjTransSCP)this.specificControlPanel).getIndexOfSelected();
					this.controller.removeKernelWithIndex(i);
					((ProjTransSCP)this.specificControlPanel).emptyList();
					this.update();
				}
				catch(DbException e){
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
		
	}

//	WindowListener Methods
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {	
		this.controller.saveDb();
		System.exit(0);	
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
//	ListSelectionListener Methods
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

	        if (((JList)e.getSource()).getSelectedIndex() == -1){
	        //No selection
	        } 
	        else {
	        //Selection
	        	if(this.specificControlPanel instanceof ProjTransSCP){
					ProjTransSCP pSCP = (ProjTransSCP)this.specificControlPanel;
					this.controller.setKernelWithIndex(pSCP.getIndexOfSelected());
					pSCP.setKernel(this.controller.getProjectiveTransformation().getKernel());
				}
	        }
	    }
		
	}
	

}
