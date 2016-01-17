package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static Controller.ChesstoryGame.GAMETYPE_CLASSICAL;
public class MainMenu {
	private JFrame f;
	private JPanel panel;
	private JButton bPlayGame;
	private JButton bParameters;
	private JButton bExit;
	private JButton bChooseBack;
	private JButton bChooseClassical;
	private JButton bChooseCustom;
	public MainMenu(JFrame f, boolean startToGameChooser){
		this.f=f;
		panel=new JPanel();
		f.getContentPane().add(panel);
		if(startToGameChooser){
			gameChooser();
		}else{
			bPlayGame=new JButton("Play Game");
			panel.add(bPlayGame);
			bParameters=new JButton("Parameters");
			panel.add(bParameters);
			bExit=new JButton("Exit");
			panel.add(bExit);
			bPlayGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
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
		panel.setBackground(Color.gray);
		f.getContentPane().add(panel);
		bChooseClassical=new JButton("Cassical");
		panel.add(bChooseClassical);
		bChooseCustom=new JButton("Custom");
		panel.add(bChooseCustom);
		bChooseBack=new JButton("Back");
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
