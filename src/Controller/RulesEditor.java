package Controller;
								
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

public class RulesEditor extends JFrame implements ItemListener, MouseInputListener{
	private JFrame f;
	private JPanel panel;
	private ItemSliderChecked iV;
	private ItemSliderChecked iH;
	private ItemSliderChecked iD;
	
	private static RulesEditor INSTANCE=null;
	public static RulesEditor getInstance(JFrame f){
		if(INSTANCE==null){
			INSTANCE=new RulesEditor(f);
		}else{
			System.out.println("Critical error this should never happend 2 instances of RulesEditor");
		} 
		return INSTANCE;
	}
	private RulesEditor(JFrame f){
		this.f=f;
		panel=new JPanel();
		panel.setBackground(new Color(0x234F6E));
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
		private JSlider sliderMin;
		private JSlider sliderMax;
		private JTextField textFieldMin;
		private JTextField textFieldMax;
		private boolean isChecked;
		public ItemSliderChecked(String s){
			isChecked=false;
			p=new JPanel();
			p.setBackground(new Color(0x8EACC1));
			panel.add(p);
			label=new JLabel(s);
			p.add(label);
			
			checkBox=new JCheckBox();
			checkBox.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("HELLOW IS THIS WORKING ?");			
				}
			});
			p.add(checkBox);
			
			sliderMin=new JSlider(JSlider.HORIZONTAL, 0, 8, 1);
			sliderMin.setMinorTickSpacing(1);
			sliderMin.setMajorTickSpacing(1);
			sliderMin.setPaintTicks(true);
			sliderMin.setPaintLabels(true);
			sliderMin.setLabelTable(sliderMin.createStandardLabels(1));
			sliderMin.addChangeListener(new ChangeListener() {		
				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
				}
			});
			
			p.add(sliderMin);
			
			sliderMax=new JSlider(JSlider.HORIZONTAL, 0, 8, 1);
			sliderMax.setMinorTickSpacing(1);
			sliderMax.setMajorTickSpacing(1);
			sliderMax.setPaintTicks(true);
			sliderMax.setPaintLabels(true);
			sliderMax.setLabelTable(sliderMax.createStandardLabels(1));
			sliderMax.addChangeListener(new ChangeListener() {		
				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
				}
			});
			p.add(sliderMax);
			
			f.repaint();
			f.revalidate();
		}
		public void refreshIsChecked(){
			if(!isChecked){
				if(checkBox.isSelected()){
					isChecked=true;
					
					//TODO show
					
				}
			}else{
				if(!checkBox.isSelected()){
					isChecked=false;
					//TODO hide
			
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
