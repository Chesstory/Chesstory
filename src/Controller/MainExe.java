package Controller;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class MainExe {
	static MainMenu mainMenu;
	static ChesstoryGame chesstoryGame;
	static JFrame frame;
	public static void main(String[] args) {
		frame=new JFrame();
		frame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width,
				GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		frame.setForeground(Color.BLUE);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		mainMenu=new MainMenu(frame);
		frame.revalidate();
	}
	static void switchToChesstoryGame(int gameType){
		clearMainMenu();
		chesstoryGame=new ChesstoryGame("Classical game", gameType, frame);
		//TODO fix the left border, it seems that the panel is still here
	}
	static void switchToMainMenu(){
		clearChesstoryGame();
	}
	static void clearMainMenu(){
		mainMenu=null;
	}
	static void clearChesstoryGame(){
		chesstoryGame=null;
	}
	static void clearEitor(){
		
	}
	static void refreshFrame(){
		
	}

}
