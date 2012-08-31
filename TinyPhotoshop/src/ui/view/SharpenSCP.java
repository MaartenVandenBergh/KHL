package ui.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SharpenSCP extends JPanel{
	
	private JLabel alphaLabel;
	private JLabel sigmaLabel;
	private JTextField sigma;
	private JTextField alpha;
	
	public SharpenSCP(){
		this.initiate();
	}
	
	
	private void initiate() {
		this.alphaLabel = new JLabel("Gaussal parameter:");
		this.add(alphaLabel);
		
		this.sigmaLabel = new JLabel("Sharpness parameter:");
		this.add(sigmaLabel);
		
		this.sigma = new JTextField();
		this.add(sigma);
		
		this.alpha = new JTextField();
		this.add(alpha);
		
		this.arrange();
	}


	private void arrange() {
		this.setLayout(null);
		
		this.sigmaLabel.setBounds(20, 25, 140, 20);
		this.sigma.setBounds(160,27,30,20);
		this.sigma.setHorizontalAlignment(JTextField.CENTER);
		
		this.alphaLabel.setBounds(20,50, 140, 20);
		this.alpha.setBounds(160,52,30,20 );
		this.alpha.setHorizontalAlignment(JTextField.CENTER);
		
	}


	public double getSigma()throws NumberFormatException {
		return Double.parseDouble(this.sigma.getText());
	}
	public void setSigma(double sigma){
		this.sigma.setText(Double.toString(sigma));
	}

	public double getAlpa() throws NumberFormatException{
		return Double.parseDouble(this.alpha.getText());
	}
	public void setAlpha(double alpha){
		this.alpha.setText(Double.toString(alpha));
	}

}
