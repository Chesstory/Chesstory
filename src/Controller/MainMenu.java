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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;
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
	private JPanel panelParam;
	private Dimension dimensionButtons;
	private Dimension dimensionFillBetweenButtons;
	private JLabel labelBanner;
	private JLabel labelParam;
	private JButton bPlayGame;
	private JButton bParameters;
	private JButton bExit;
	private JButton bChooseBack;
	private JButton bChooseClassical;
	private JButton bChooseShatranj;
	private JButton bChooseChaturanga;
	private JButton bChooseCustom;

	private JButton bParamBack, bSaveChange;
	private int[] arrayParam;
	private JCheckBox checkboxDisplayHelp, checkboxTimer;
	private boolean changesNotSaved = false;
	private JTextField tfTimer;

	/**
	 * Create the menu and displays it.
	 * 
	 * @param f
	 *            The frame
	 * @param startToGameChooser
	 *            Whenever it launch to the game chooser or not.
	 */
	public MainMenu(JFrame f, boolean startToGameChooser) {
		dimensionButtons = new Dimension(
				java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 8,
				java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 12);
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
				f.remove(panel);
				displayParameter();
			}
		});
		bExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave ?",
						"Warning you are about to leave", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

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
		panel.removeAll();
		panel.setBackground(new Color(0x0F3855));
		f.getContentPane().add(panel);
		panelChoose = new JPanel();
		panelChoose.setBackground(new Color(0x234F6E));
		panelChoose.setPreferredSize(new Dimension(
				(int) (java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width
						* (0.90)),
				(int) (java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height
						* (0.90))));
		GridLayout gridLayout = new GridLayout(2, 3);
		gridLayout.setHgap(20);
		gridLayout.setVgap(20);
		panelChoose.setLayout(gridLayout);
		panel.add(panelChoose);

		cl = this.getClass().getClassLoader();
		image = new ImageIcon(cl.getResource("icons/classical.jpg"));
		bChooseClassical = new JButton("Classical", image);
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

	/**
	 * Display the parameter board
	 */
	private void displayParameter() {
		arrayParam = FileController.loadParameters("mainParameters");

		panel.removeAll();
		f.getContentPane().add(panel);
		panelParam = new JPanel();
		panelParam.setBackground(new Color(0x234F6E));
		panelParam.setPreferredSize(new Dimension(
				(int) (java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width
						* (0.90)),
				(int) (java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height
						* (0.90))));
		panelParam.setLayout(new BoxLayout(panelParam, BoxLayout.Y_AXIS));
		panel.add(panelParam);

		panelParam.add(Box.createRigidArea(new Dimension(0, 100)));
		labelParam = new JLabel("Parameters");
		labelParam.setAlignmentY(Component.CENTER_ALIGNMENT);
		labelParam.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelParam.setFont(new Font("Papyrus", Font.PLAIN, 32));
		panelParam.add(labelParam);
		panelParam.add(Box.createRigidArea(dimensionFillBetweenButtons));
		checkboxDisplayHelp = new JCheckBox("Do you want to be helped by graphic hints ? ");
		checkboxDisplayHelp.setSelected(arrayParam[0] == 1);
		checkboxDisplayHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changesNotSaved = true;
				bSaveChange.setEnabled(true);
			}
		});
		panelParam.add(checkboxDisplayHelp);
		panelParam.add(Box.createRigidArea(dimensionFillBetweenButtons));
		checkboxTimer = new JCheckBox("Allow timer ? ");
		checkboxTimer.setSelected(arrayParam[1] == 1);
		checkboxTimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changesNotSaved = true;
				bSaveChange.setEnabled(true);
				if (!tfTimer.isEnabled())
					tfTimer.setEnabled(true);
				else
					tfTimer.setEnabled(false);
			}
		});
		panelParam.add(checkboxTimer);
		tfTimer = new JTextField("xhxxmxxs", 8);
		tfTimer.setMaximumSize(new DimensionUIResource(100, 40));
		panelParam.add(tfTimer);
		tfTimer.setEnabled(checkboxTimer.isSelected());

		bSaveChange = new JButton("Confirm changes");
		bSaveChange.setMaximumSize(dimensionButtons);
		bSaveChange.setAlignmentY(Component.CENTER_ALIGNMENT);
		bSaveChange.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelParam.add(bSaveChange);
		panelParam.add(Box.createRigidArea(dimensionFillBetweenButtons));
		bSaveChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveParameters();
				bSaveChange.setEnabled(false);
				changesNotSaved = false;
			}
		});
		bSaveChange.setEnabled(false);
		bParamBack = new JButton("Back");
		bParamBack.setMaximumSize(dimensionButtons);
		bParamBack.setAlignmentY(Component.CENTER_ALIGNMENT);
		bParamBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelParam.add(bParamBack);
		panelParam.add(Box.createRigidArea(dimensionFillBetweenButtons));
		bParamBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (changesNotSaved) {
					int response = JOptionPane.showConfirmDialog(null, "Do you want to save changes ?",
							"Warning you are about to go back to main menu", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						if (!saveParameters())
							return;
					} else if (response == JOptionPane.CANCEL_OPTION)
						return;
				}
				displayMainMenu();
			}
		});

		f.repaint();
		f.revalidate();
	}

	private boolean saveParameters() {
		arrayParam[0] = checkboxDisplayHelp.isSelected() ? 1 : 0;
		if (checkboxTimer.isSelected()) {
			try {
				String[] inter1 = tfTimer.getText().split("h");
				String[] inter2 = inter1[1].split("m");
				String[] inter3 = inter2[1].split("s");
				arrayParam[1] = Integer.parseInt(inter1[0]) * 3600 + Integer.parseInt(inter2[0]) * 60
						+ Integer.parseInt(inter3[0]);//TODO operation is wrong
			} catch (Exception e) {
				JOptionPane.showMessageDialog(f, "Timer format must be : XhXXmXXs");
				return false;
			}
		} else
			arrayParam[1] = 0;

		FileController.saveParameters("mainParameters", arrayParam);
		FileController.loadParametersToChesstoryConstants();
		return true;
	}
}
