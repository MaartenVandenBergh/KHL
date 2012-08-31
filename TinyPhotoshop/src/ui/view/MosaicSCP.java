package ui.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MosaicSCP extends JPanel{
	
	private JLabel amountLabel;
	private JTextField amount;
	
	public MosaicSCP(){
		this.initialise();
	}
	private void initialise(){
		this.amountLabel = new JLabel("Amount:");
		this.add(amountLabel);
		this.amount = new JTextField();
		this.add(amount);
		
		this.arrange();
		
	}
	private void arrange(){
		this.setLayout(null);
		
		this.amountLabel.setBounds(20, 35, 60, 20);
		this.amount.setBounds(80, 35, 40, 20);
		this.amount.setHorizontalAlignment(JTextField.RIGHT);
	}

	public int getAmount()throws NumberFormatException{
		return Integer.parseInt(this.amount.getText());
	}
	public void setAmount(int a){
		this.amount.setText(Integer.toString(a));
	}

}
