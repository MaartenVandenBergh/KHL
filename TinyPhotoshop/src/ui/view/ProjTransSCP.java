package ui.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import util.Kernel;
import util.KernelListCellRenderer;

public class ProjTransSCP extends JPanel{
	
	private JLabel kernelLabel;
	private JTextField[][] fields;
	
	private JList<Kernel> list;
	private JScrollPane scroll;
	private JButton add;
	private JButton del;
	
	public ProjTransSCP(ActionListener al,ListSelectionListener lsl){
		this.initialise(al,lsl);
	}
	
	private void initialise(ActionListener al,ListSelectionListener lsl){
		this.kernelLabel = new JLabel("Kernel:"); 
		this.add(kernelLabel);
		
		this.fillFields();
		this.addFieldsToContainer();
		
		this.list = new JList<Kernel>();
		this.list.addListSelectionListener(lsl);
		this.scroll = new JScrollPane(list);
		this.add(scroll);
		this.add = new JButton("+Add");
		this.add(add);
		this.add.addActionListener(al);
		this.del = new JButton("-Del");
		this.add(del);
		this.del.addActionListener(al);
		
		this.arrange();
		
	}
	private void arrange(){
		this.setLayout(null);
		
		this.kernelLabel.setBounds(20, 35, 60, 20);
		
		for(int x = 0; x<fields[0].length;x++){
			for(int y = 0;y<fields.length;y++){
				fields[y][x].setBounds(80+(x*40), 16+(y*20), 40, 20);
				fields[y][x].setHorizontalAlignment(JTextField.LEFT);
			}
		}
		this.list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.list.setLayoutOrientation(JList.VERTICAL);
		this.list.setVisibleRowCount(-1);
		this.list.setCellRenderer(new KernelListCellRenderer());
		
		this.scroll.setBounds(225, 10, 150, 60);
		this.add.setBounds(225, 70, 80, 20);
		this.del.setBounds(307, 70, 67, 20);
		
	}
	private void fillFields(){
		this.fields = new JTextField[3][3];
		for(int x = 0; x<fields[0].length;x++){
			for(int y = 0;y<fields.length;y++){
				fields[y][x] = new JTextField();
			}
		}
	}
	private void addFieldsToContainer(){
		for(int x = 0; x<fields[0].length;x++){
			for(int y = 0;y<fields.length;y++){
				this.add(fields[y][x]);
			}
		}
	}

	public Kernel getKernel() throws NumberFormatException{
		double[][] e = new double[3][3];
		for(int x = 0; x<fields[0].length;x++){
			for(int y = 0;y<fields.length;y++){
				e[y][x] = Double.parseDouble(fields[y][x].getText());
			}
		}
		
		return new Kernel(e);
	}
	public void setKernel(Kernel k) throws NumberFormatException{
		for(int x = 0; x<fields[0].length;x++){
			for(int y = 0;y<fields.length;y++){
				this.fields[y][x].setText(Double.toString(k.get(x, y)));
			}
		}
	}
	public void setList(Kernel[] klist){
		this.list.setListData(klist);
	}

	public boolean isEmpty() {
		return this.list.getModel().getSize() == 0;
	}
	public void emptyList(){
		Kernel[] klist = new Kernel[0];
		this.list.setListData(klist);
	}
	public int getIndexOfSelected(){
		return this.list.getSelectedIndex();
	}
	public JButton getAddButton(){
		return this.add;
	}
	public JButton getDelButton(){
		return this.del;
	}
}
