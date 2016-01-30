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
		frame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds().width, GraphicsEnvironment
				.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.setForeground(Color.BLUE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		mainMenu = new MainMenu(frame, false);
		frame.repaint();
		frame.revalidate();

		
		new File("ChesstoryData").mkdirs();
		try {
			InputStream localInputStream = ClassLoader.getSystemClassLoader()
					.getResourceAsStream(
							"ChesstoryData/defaultSaves/default_SHATRANJ.txt");
			byte[] buffer = new byte[localInputStream.available()];
			
			localInputStream.read(buffer);
			new File("ChesstoryData/defaultSaves").mkdirs();
			File targetFile = new File(
					"ChesstoryData/defaultSaves/default_SHATRANJ.txt");
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			outStream.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					localInputStream));
			JOptionPane.showMessageDialog(null, "Creation done1:" + targetFile);
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
			JOptionPane.showMessageDialog(null, "Creation done:" + targetFile);
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
			JOptionPane.showMessageDialog(null, "Creation done:" + targetFile);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error:" + e);
		}
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
