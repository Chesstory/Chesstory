package Controller;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

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
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					
					UIManager.setLookAndFeel(info.getClassName());
					//UIManager.put("nimbusBlueGrey", new Color(0xff009a));
					//TODO here we can modifiy everything we want https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html#primary
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		frame = new JFrame();
		frame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds().width, GraphicsEnvironment
				.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.setForeground(Color.BLUE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		

		new File("ChesstoryData/defaultSaves").mkdirs();
		new File("ChesstoryData/playerSaves").mkdir();
		try {
			InputStream localInputStream = ClassLoader.getSystemClassLoader()
					.getResourceAsStream(
							"ChesstoryData/defaultSaves/default_SHATRANJ.txt");
			byte[] buffer = new byte[localInputStream.available()];
			//new File("ChesstoryData/defaultSaves").mkdirs();
			localInputStream.read(buffer);
			
			File targetFile = new File(
					"ChesstoryData/defaultSaves/default_SHATRANJ.txt");
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			outStream.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					localInputStream));
		} catch (Exception e) {
			String str = new String(" errorPN1:");
			for (int i = 0; i < e.getStackTrace().length; i++) {
				str += e.getStackTrace()[i];
			}
			JOptionPane.showMessageDialog(null, str);

		}
		try {
			InputStream localInputStream = ClassLoader.getSystemClassLoader()
					.getResourceAsStream(
							"ChesstoryData/defaultSaves/default_CLASSICAL.txt");
			byte[] buffer = new byte[localInputStream.available()];
			localInputStream.read(buffer);
			new File("ChesstoryData/defaultSaves").mkdirs();
			File targetFile = new File(
					"ChesstoryData/defaultSaves/default_CLASSICAL.txt");
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			outStream.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					localInputStream));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error:" + e);
		}

		try {
			InputStream localInputStream = ClassLoader
					.getSystemClassLoader()
					.getResourceAsStream(
							"ChesstoryData/defaultSaves/default_CHATURANGA.txt");
			byte[] buffer = new byte[localInputStream.available()];
			localInputStream.read(buffer);
			new File("ChesstoryData/defaultSaves").mkdirs();
			File targetFile = new File(
					"ChesstoryData/defaultSaves/default_CHATURANGA.txt");
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			outStream.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					localInputStream));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error:" + e);
		}		
		
		mainMenu = new MainMenu(frame, false);
		frame.repaint();
		frame.revalidate();
	}

	static void switchToChesstoryGame(int gameType) {
		System.out.println("*************************************");
		System.out.println("****////---->>>>switchToChesstoryGame");
		System.out.println("*************************************");
		/*clearMainMenu();rulesEditor.closeInstance();useless ?
		rulesEditor = null;*/
		
		chesstoryGame = new ChesstoryGame("Classical game", gameType, frame);
	}

	static void switchToChesstoryGameFromEditor(int gameType, String fileName) {
		System.out.println("*************************************");
		System.out.println("****////---->>>>switchToChesstoryGameFromEditor");
		System.out.println("*************************************");
		rulesEditor.closeInstance();
		rulesEditor = null;
		
		chesstoryGame = new ChesstoryGame(fileName, gameType, frame);
	}

	static void switchToEditor() {
		System.out.println("*************************************");
		System.out.println("****////---->>>>switchToEditor");
		System.out.println("*************************************");
		clearMainMenu();
		rulesEditor = RulesEditor.getInstance(frame);
		frame.repaint();
		frame.revalidate();
	}

	static void switchToMainMenuFromChesstoryGame() {
		System.out.println("*************************************");
		System.out.println("****////---->>>>switchToMainMenuFromChesstoryGame");
		System.out.println("*************************************");
		clearChesstoryGame();
		mainMenu = new MainMenu(frame, true);
	}

	static void switchToMainMenu() {
		System.out.println("****////---->>>>switchToMainMenu");
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
