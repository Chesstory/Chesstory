package Controller;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 * The Main of this project, it starts the program !
 * 
 * @author Acevedo Roman and Guillemot Baptiste.
 *
 */
public class MainExe {
	static MainMenu mainMenu;
	static ChesstoryGame chesstoryGame;
	static RulesEditor rulesEditor;
	static JFrame frame;
	public static void main(String[] args) {
		
		frame = new JFrame();
		frame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width,
				GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.setForeground(Color.BLUE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		mainMenu = new MainMenu(frame, false);
		frame.repaint();
		frame.revalidate();
		
		/*File file= new File("data/defaultSaves/default_SHATRANJ.txt");
		if(new File().ex){
			
		}*/
	}

	static void switchToChesstoryGame(int gameType) {
		clearMainMenu();
		chesstoryGame = new ChesstoryGame("Classical game", gameType, frame);
	}

	static void switchToChesstoryGame(int gameType, String fileName) {
		rulesEditor = null;
		chesstoryGame = new ChesstoryGame(fileName, gameType, frame);
	}

	static void switchToEditor() {
		clearMainMenu();
		rulesEditor = RulesEditor.getInstance(frame);
		frame.repaint();
		frame.revalidate();
	}

	static void switchToMainMenuFromChesstoryGame() {
		clearChesstoryGame();
		mainMenu = new MainMenu(frame, true);
	}

	static void switchToMainMenu() {
		clearEditor();
		rulesEditor.removeInstance();
		mainMenu = new MainMenu(frame, true);
	}

	static void clearMainMenu() {
		mainMenu = null;
	}

	static void clearChesstoryGame() {
		chesstoryGame = null;
	}

	static void clearEditor() {
		rulesEditor = null;
	}

	static void refreshFrame() {

	}
}
