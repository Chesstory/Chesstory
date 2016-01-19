package Controller;
	
import static Controller.ChesstoryConstants.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

public class RulesEditor extends JFrame implements ItemListener, MouseInputListener{
	private JFrame f;
	private JPanel panel;
	private ItemSliderChecked iV;
	private ItemSliderChecked iH;
	private ItemSliderChecked iD;
	private JComboBox<String> comboBox;
	private String[] nameOfPieces;
	private GameSave loadedRules;
	private String[][] pieceRules;
	
	
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
		panel.setSize(new Dimension(java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2,
				java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2));
		panel.setBackground(new Color(0x234F6E));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		f.getContentPane().add(panel);
		loadedRules=FileController.loadFile(FILE_DEFAULT_CLASSICAL);
		pieceRules=new String[NB_PIECE][NB_RULE_PER_PIECE];
		nameOfPieces=new String[]{ "Piece 1 'pawn'","Piece 2 'rook'","Piece 3 'queen'","Piece 4 'king'","Piece 5 'bishop'","Piece 6 'knight'"};//TODO we may need to change the names
		comboBox=new JComboBox<String>(nameOfPieces);
		comboBox.setMaximumSize(new Dimension(10,20));
		comboBox.setSelectedItem(1);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ActionListener : action sur " + ((JComboBox<String>) e.getSource()).getSelectedItem());
			}
		});
		panel.add(comboBox);
		for(int p=0;p<NB_PIECE;p++){
			//System.out.println("p : "+p+", raw result : "+loadedRules.toString());
			//System.out.println("RESULT :"+loadedRules.getArrayRulePiece()[p]);
			String temp[]=loadedRules.getArrayRulePiece()[p].split(",",-1);
			for(int r=0;r<NB_RULE_PER_PIECE;r++){
				
				pieceRules[p][r]=temp[r];
				//TODO PRESQUE
				
				
				
			//	System.out.println("p : "+p+", r : "+r+", result : "+temp[r]);
			}
		}
		
		iH=new ItemSliderChecked("Horizontal", Integer.parseInt(pieceRules[1][2]), Integer.parseInt(pieceRules[1][3]));
		iV=new ItemSliderChecked("Vertical", Integer.parseInt(pieceRules[1][4]), Integer.parseInt(pieceRules[1][5]));
		iD=new ItemSliderChecked("Diagonal", Integer.parseInt(pieceRules[1][6]), Integer.parseInt(pieceRules[1][7]));
		
	}
	//TODO constructor without initialization
	class ItemSliderChecked{
		private JPanel p;
		private JLabel label;
		private JCheckBox checkBox;
		private JSlider sliderMin;
		private JSlider sliderMax;
		private JTextField textFieldMin;
		private JTextField textFieldMax;
		private boolean isChecked;
		public ItemSliderChecked(String s, int minCursor, int maxCursor){
			isChecked=false;
			p=new JPanel();
			p.setBackground(new Color(0x8EACC1));
			panel.add(p);
			p.setBorder(new LineBorder(Color.gray));
			label=new JLabel(s);
			p.add(label);
			
			checkBox=new JCheckBox();
			checkBox.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					refreshIsChecked();
				}
			});
			p.add(checkBox);
			
			sliderMin=new JSlider(JSlider.HORIZONTAL, 0, 8, 1);
			sliderMin.setMinorTickSpacing(1);
			sliderMin.setMajorTickSpacing(1);
			sliderMin.setPaintTicks(true);
			sliderMin.setPaintLabels(true);
			sliderMin.setLabelTable(sliderMin.createStandardLabels(1));
			textFieldMin=new JTextField(sliderMin.getValue());
			textFieldMin.setEditable(false);
			sliderMin.setEnabled(false);
			p.add(sliderMin);
			p.add(textFieldMin);
			
			sliderMax=new JSlider(JSlider.HORIZONTAL, 0, 8, 1);
			sliderMax.setMinorTickSpacing(1);
			sliderMax.setMajorTickSpacing(1);
			sliderMax.setPaintTicks(true);
			sliderMax.setPaintLabels(true);
			sliderMax.setLabelTable(sliderMax.createStandardLabels(1));
			textFieldMax=new JTextField(sliderMax.getValue());
			textFieldMax.setEditable(false);
			sliderMax.setEnabled(false);
			p.add(sliderMax);
			p.add(textFieldMax);
			
			sliderMin.setValue(minCursor);
			sliderMax.setValue(maxCursor);
			textFieldMin.setText(""+sliderMin.getValue());
			textFieldMax.setText(""+sliderMax.getValue());
			sliderMin.addChangeListener(new ChangeListener() {		
				@Override
				public void stateChanged(ChangeEvent e) {
					textFieldMin.setText(""+sliderMin.getValue());
					if(sliderMin.getValue()>sliderMax.getValue())
						sliderMax.setValue(sliderMin.getValue());
				}
			});
			sliderMax.addChangeListener(new ChangeListener() {		
				@Override
				public void stateChanged(ChangeEvent e) {
					textFieldMax.setText(""+sliderMax.getValue());
					if(sliderMax.getValue()<sliderMin.getValue())
						sliderMin.setValue(sliderMax.getValue());
				}
			});
			f.repaint();
			f.revalidate();
		}
		public void refreshIsChecked(){
			if(!isChecked){
				if(checkBox.isSelected()){
					isChecked=true;
					sliderMin.setEnabled(true);
					sliderMax.setEnabled(true);
				}
			}else{
				if(!checkBox.isSelected()){
					isChecked=false;
					sliderMin.setEnabled(false);
					sliderMax.setEnabled(false);
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
