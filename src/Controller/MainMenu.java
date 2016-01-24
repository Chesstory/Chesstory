package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static Controller.ChesstoryConstants.*;
public class MainMenu {
	private JFrame f;
	private JPanel panel;
	private Dimension dimensionButtons;
	private Dimension dimensionFillBetweenButtons;
	private JLabel labelBanner;
	private JButton bPlayGame;
	private JButton bParameters;
	private JButton bExit;
	private JButton bChooseBack;
	private JButton bChooseClassical;
	private JButton bChooseCustom;
	public MainMenu(JFrame f, boolean startToGameChooser){
		dimensionButtons=new Dimension(java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/8,
				java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/12);
		dimensionFillBetweenButtons=new Dimension(0,50);
		this.f=f;
		panel=new JPanel();
		panel.setMaximumSize(f.getSize());
		panel.setBackground(new Color(0x234F6E));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		f.getContentPane().add(panel);
		if(startToGameChooser){
			gameChooser();
		}else{
			panel.add(Box.createRigidArea(new Dimension(0,100)));
			labelBanner=new JLabel("Chesstorygame");
			labelBanner.setAlignmentY(Component.CENTER_ALIGNMENT);
			labelBanner.setAlignmentX(Component.CENTER_ALIGNMENT);
			labelBanner.setFont(new Font("Papyrus",Font.PLAIN,32));
			panel.add(labelBanner);
			panel.add(Box.createRigidArea(dimensionFillBetweenButtons));
			bPlayGame=new JButton("Play Game");
			bPlayGame.setMaximumSize(dimensionButtons);
			bPlayGame.setAlignmentY(Component.CENTER_ALIGNMENT);
			bPlayGame.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(bPlayGame);
			panel.add(Box.createRigidArea(dimensionFillBetweenButtons));
			bParameters=new JButton("Parameters");
			bParameters.setMaximumSize(dimensionButtons);
			bParameters.setAlignmentY(Component.CENTER_ALIGNMENT);
			bParameters.setAlignmentX(Component.CENTER_ALIGNMENT);
			bParameters.setEnabled(false);//TODO remove
			panel.add(bParameters);
			panel.add(Box.createRigidArea(dimensionFillBetweenButtons));
			bExit=new JButton("Exit");
			bExit.setMaximumSize(dimensionButtons);
			bExit.setAlignmentY(Component.CENTER_ALIGNMENT);
			bExit.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(bExit);
			bPlayGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					f.remove(panel);
					gameChooser();
				}
			});																																																
			bParameters.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO parameters
				}
			});
			bExit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO confirm dialogbox
					System.exit(0);
				}
			});
		}
	}
	private void gameChooser(){
		panel.removeAll();
		panel=new JPanel();
		panel.setBackground(new Color(0x0F3855));
		f.getContentPane().add(panel);
		bChooseClassical=new JButton("Cassical");
		panel.add(bChooseClassical);
		bChooseCustom=new JButton("Custom");
		panel.add(bChooseCustom);
		bChooseBack=new JButton("Back");
		bChooseBack.setEnabled(false);//TODO remove
		panel.add(bChooseBack);
		bChooseClassical.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				panel.removeAll();
				panel.setVisible(false);
				f.remove(panel);
				panel=null;
				MainExe.switchToChesstoryGame(GAMETYPE_CLASSICAL);	
			}
		});
		bChooseCustom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.setVisible(false);
				f.remove(panel);
				panel=null;
				MainExe.switchToEditor();	
			}
		});
		bChooseBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		f.revalidate();
		
	}
	

}
