package util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class KernelListCellRenderer extends JLabel implements ListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object o,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		Kernel k = (Kernel)o;
		
		 if (isSelected) {
            this.setBackground(new Color(99,184,255));
            this.setForeground(Color.WHITE);
	     } 
		 else {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
	     }
		this.setText(k.getName());
		this.setOpaque(true);
		this.setBorder(new EmptyBorder(2,3,2,3));
		this.setEnabled(list.isEnabled());
		this.setFont(list.getFont());
		return this;
	}

}
