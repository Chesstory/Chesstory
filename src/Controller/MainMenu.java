package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import static Controller.ChesstoryConstants.*;

/**
 * The first menu of the game ; It permits the user to choose the game he wants
 * to play, etc. Which means it contains graphic elements and a set of methods
 * to instance other classes.
 * 
 * @author Acevedo Roman and Guillemot Baptiste.
 *
 */
public class MainMenu {
	private JFrame f;
	private JPanel panel;
	private JPanel panelChoose;
	private Dimension dimensionButtons;
	private Dimension dimensionFillBetweenButtons;
	private Dimension dimensionChoose;
	private JLabel labelBanner;
	private JButton bPlayGame;
	private JButton bParameters;
	private JButton bExit;
	private JButton bChooseBack;
	private JButton bChooseClassical;
	private JButton bChooseShatranj;
	private JButton bChooseChaturanga;
	private JButton bChooseCustom;

	/**
	 * Create the menu and displays it.
	 * 
	 * @param f
	 *            The frame
	 * @param startToGameChooser
	 *            Whether it launch to the game chooser or not.
	 */
	public MainMenu(JFrame f, boolean startToGameChooser) {
		dimensionButtons = new Dimension(
				java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getMaximumWindowBounds().width / 8,
				java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getMaximumWindowBounds().height / 12);
		dimensionFillBetweenButtons = new Dimension(0, 50);
		this.f = f;
		panel = new JPanel();
		panel.setMaximumSize(f.getSize());
		panel.setBackground(new Color(0x234F6E));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		f.getContentPane().add(panel);
		if (startToGameChooser) {
			displayGameChooser();
		} else {
			displayMainMenu();
		}
	}

	/**
	 * Display the menu, with all the labels, panels, etc.
	 */
	private void displayMainMenu() {
		panel.removeAll();

		panel.add(Box.createRigidArea(new Dimension(0, 100)));
		labelBanner = new JLabel("Chesstorygame");
		labelBanner.setAlignmentY(Component.CENTER_ALIGNMENT);
		labelBanner.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelBanner.setFont(new Font("Papyrus", Font.PLAIN, 32));
		panel.add(labelBanner);
		panel.add(Box.createRigidArea(dimensionFillBetweenButtons));
		bPlayGame = new JButton("Play Game");
		bPlayGame.setMaximumSize(dimensionButtons);
		bPlayGame.setAlignmentY(Component.CENTER_ALIGNMENT);
		bPlayGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(bPlayGame);
		panel.add(Box.createRigidArea(dimensionFillBetweenButtons));
		bParameters = new JButton("Parameters");
		bParameters.setMaximumSize(dimensionButtons);
		bParameters.setAlignmentY(Component.CENTER_ALIGNMENT);
		bParameters.setAlignmentX(Component.CENTER_ALIGNMENT);
		bParameters.setEnabled(false);// TODO remove
		panel.add(bParameters);
		panel.add(Box.createRigidArea(dimensionFillBetweenButtons));
		bExit = new JButton("Exit");
		bExit.setMaximumSize(dimensionButtons);
		bExit.setAlignmentY(Component.CENTER_ALIGNMENT);
		bExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(bExit);
		bPlayGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.remove(panel);
				displayGameChooser();
			}
		});
		bParameters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO parameters
			}
		});
		bExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to leave ?",
						"Warning you are about to leave",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		f.repaint();
		f.revalidate();
	}

	/**
	 * Display the game chooser menu, with the buttons to choose between
	 * chaturanga, shatran, custon, etc. And add the action listeners to those
	 * buttons.
	 */
	private void displayGameChooser() {
		ClassLoader cl;
		ImageIcon image;
		dimensionChoose = new Dimension(
				(int) (java.awt.GraphicsEnvironment
						.getLocalGraphicsEnvironment().getMaximumWindowBounds().width * (0.2)),
				(int) (java.awt.GraphicsEnvironment
						.getLocalGraphicsEnvironment().getMaximumWindowBounds().height * (0.1)));
		panel.removeAll();

		panel.setBackground(new Color(0x0F3855));
		f.getContentPane().add(panel);
		panelChoose = new JPanel();
		panelChoose.setBackground(new Color(0x234F6E));
		panelChoose
				.setPreferredSize(new Dimension(
						(int) (java.awt.GraphicsEnvironment
								.getLocalGraphicsEnvironment()
								.getMaximumWindowBounds().width * (0.90)),
						(int) (java.awt.GraphicsEnvironment
								.getLocalGraphicsEnvironment()
								.getMaximumWindowBounds().height * (0.90))));
		GridLayout gridLayout = new GridLayout(2, 3);
		gridLayout.setHgap(20);
		gridLayout.setVgap(20);
		panelChoose.setLayout(gridLayout);
		panel.add(panelChoose);
		
		cl = this.getClass().getClassLoader();
		image = new ImageIcon(cl.getResource("icons/classical.jpg"));
		bChooseClassical = new JButton("Classical", image);
		// bChooseClassical.setPreferredSize(dimensionChoose);
		panelChoose.add(bChooseClassical);

		image = new ImageIcon(cl.getResource("icons/shatranj.jpg"));
		bChooseShatranj = new JButton("Shatranj", image);
		panelChoose.add(bChooseShatranj);
		image = new ImageIcon(cl.getResource("icons/chaturanga.jpg"));
		bChooseChaturanga = new JButton("Chaturanga", image);
		panelChoose.add(bChooseChaturanga);
		image = new ImageIcon(cl.getResource("icons/custom.png"));
		bChooseCustom = new JButton("Custom", image);
		panelChoose.add(bChooseCustom);
		bChooseBack = new JButton("Back");
		panelChoose.add(bChooseBack);
		bChooseClassical.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelChoose.removeAll();
				panelChoose.setVisible(false);
				f.remove(panelChoose);
				panelChoose = null;
				panel.removeAll();
				panel.setVisible(false);
				f.remove(panel);
				panel = null;
				MainExe.switchToChesstoryGame(GAMETYPE_CLASSICAL);
			}
		});
		bChooseCustom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelChoose.removeAll();
				panelChoose.setVisible(false);
				f.remove(panelChoose);
				panelChoose = null;
				panel.removeAll();
				panel.setVisible(false);
				f.remove(panel);
				panel = null;
				MainExe.switchToEditor();
			}
		});
		bChooseShatranj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelChoose.removeAll();
				panelChoose.setVisible(false);
				f.remove(panelChoose);
				panelChoose = null;
				panel.removeAll();
				panel.setVisible(false);
				f.remove(panel);
				panel = null;
				MainExe.switchToChesstoryGame(GAMETYPE_SHATRANJ);
			}
		});
		bChooseChaturanga.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelChoose.removeAll();
				panelChoose.setVisible(false);
				f.remove(panelChoose);
				panelChoose = null;
				panel.removeAll();
				panel.setVisible(false);
				f.remove(panel);
				panel = null;
				f.getContentPane().removeAll();
				MainExe.switchToChesstoryGame(GAMETYPE_CHATURANGA);
			}
		});
		bChooseBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayMainMenu();
			}
		});
		f.repaint();
		f.revalidate();
	}
}
