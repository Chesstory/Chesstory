package Controller;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class MainExe {
	static MainMenu mainMenu;
	static ChesstoryGame chesstoryGame;
	static RulesEditor rulesEditor;
	static JFrame frame;
	public static void main(String[] args) {
		frame=new JFrame();
		frame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width,
				GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		frame.setForeground(Color.BLUE);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		mainMenu=new MainMenu(frame,false);
		frame.revalidate();
	}
	static void switchToChesstoryGame(int gameType){
		clearMainMenu();
		chesstoryGame=new ChesstoryGame("Classical game", gameType, frame);
		//TODO fix the left border, it seems that the panel is still here
	}
	static void switchToEditor(){
		clearMainMenu();
		rulesEditor=RulesEditor.getInstance(frame);
	}
	static void switchToMainMenu(){
		clearChesstoryGame();
		clearEditor();
	}
	static void clearMainMenu(){
		mainMenu=null;
	}
	static void clearChesstoryGame(){
		chesstoryGame=null;
	}
	static void clearEditor(){
		
	}
	static void refreshFrame(){
		
	}

}
