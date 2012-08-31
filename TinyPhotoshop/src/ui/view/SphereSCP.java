package ui.view;

import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SphereSCP extends JPanel{
	
	private JLabel centerLabel;
	private JTextField centerX;
	private JLabel centerLabelcenter;
	private JTextField centerY;
	private JLabel centerLabelend;
	
	private JLabel radiusLabel;
	private JTextField radius;
	
	private JLabel indexLabel;
	private JTextField index;
	
	public SphereSCP(){
		this.initiate();
	}
	
	private void initiate() {
		this.centerLabel = new JLabel("Center point: (");
		this.add(centerLabel);
		this.centerX = new JTextField();
		this.add(centerX);
		this.centerLabelcenter = new JLabel(",");
		this.add(centerLabelcenter);
		this.centerY = new JTextField();
		this.add(centerY);
		this.centerLabelend = new JLabel(")");
		this.add(centerLabelend);
		
		this.radiusLabel = new JLabel("Radius:");
		this.add(radiusLabel);
		this.radius = new JTextField();
		this.add(radius);
		
		this.indexLabel = new JLabel("Index:");
		this.add(indexLabel);
		this.index = new JTextField();
		this.add(index);
		
		this.arrange();
	}


	private void arrange() {
		this.setLayout(null);
		
		this.centerLabel.setBounds(20, 13, 80, 20);
		this.centerX.setBounds(100,15,30,20);
		this.centerX.setHorizontalAlignment(JTextField.CENTER);
		this.centerLabelcenter.setBounds(130, 13, 4, 20);
		this.centerY.setBounds(134,15,30,20);
		this.centerY.setHorizontalAlignment(JTextField.CENTER);
		this.centerLabelend.setBounds(163, 13, 4, 20);
		
		this.radiusLabel.setBounds(20,38, 80,20);
		this.radius.setBounds(100, 40, 40, 20);
		this.radius.setHorizontalAlignment(JTextField.RIGHT);
		
		this.indexLabel.setBounds(20,63, 80,20);
		this.index.setBounds(100, 65, 30, 20);
		this.index.setHorizontalAlignment(JTextField.CENTER);
		
	}

	public Point getCenter() throws NumberFormatException{
		int x = Integer.parseInt(this.centerX.getText());
		int y = Integer.parseInt(this.centerY.getText());
		
		return new Point(x,y);
	}
	public void setCenter(Point p){
		this.centerX.setText(Integer.toString(p.x));
		this.centerY.setText(Integer.toString(p.y));
	}

	public double getRadius() throws NumberFormatException{
		return Double.parseDouble(this.radius.getText());
	}
	public void setRadius(double r){
		this.radius.setText(Double.toString(r));
	}

	public double getIndex() throws NumberFormatException{
		return Double.parseDouble(this.index.getText());
	}

	public void setIndex(double i) {
		this.index.setText(Double.toString(i));
		
	}

}
