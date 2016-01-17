package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RulesEditor extends JPanel implements ItemListener{
	private JFrame f;
	private JPanel panel;
	private ItemSliderChecked iV;
	private ItemSliderChecked iH;
	private ItemSliderChecked iD;
	public RulesEditor(JFrame f){
		this.f=f;
		panel=new JPanel();
		panel.setBackground(Color.red);
		f.getContentPane().add(panel);
		iV=new ItemSliderChecked("Vertical");
		iH=new ItemSliderChecked("Horizontal");
		iD=new ItemSliderChecked("Diagonal");
	}

	/*@Override
	public void itemStateChanged(ItemEvent arg0) {
		 iV.refreshIsChecked();
		 iH.refreshIsChecked();
		 iD.refreshIsChecked();
	}*/
	class ItemSliderChecked{
		private JPanel p;
		private JLabel label;
		private JCheckBox checkBox;
		private JLabel test;
		private boolean isChecked;
		public ItemSliderChecked(String s){
			isChecked=false;
			p=new JPanel();
			p.setBackground(Color.yellow);
			panel.add(p);
			label=new JLabel(s);
			p.add(label);
			checkBox=new JCheckBox();
			checkBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("PENI");
					
				}
			});
			p.add(checkBox);
			test=new JLabel("test");
			test.setEnabled(false);
			p.add(test);
			JButton b=new JButton("BOUTON");
			p.add(b);
		}
		public void refreshIsChecked(){
			if(!isChecked){
				if(checkBox.isSelected()){
					isChecked=true;
					
					//TODO show
					test.setEnabled(true);
				}
			}else{
				if(!checkBox.isSelected()){
					isChecked=false;
					//TODO hide
					test.setEnabled(false);
				}
			}
		}
		public boolean getIsChecked(){
			return isChecked;	
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
}
