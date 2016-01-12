package Controller;

import java.awt.event.ActionEvent;

import javax.swing.UIManager.*;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

import gui.YetAnotherChessGame;
import echecs.Deplacement;

import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import java.awt.SystemColor;

public class ChesstoryGame extends JFrame implements MouseListener {
	// static YetAnotherChessGame YACG;

	private YetAnotherChessGame YACG;
	// >Interface
	public static JFrame f;
	private JPanel panelLeft;
	private JPanel panelRight;
	private JPanel panelLeftChessboard;
	private JPanel panelLeftMenu;
	private JPanel panelLeftMenuBrowse;
	private JPanel panelLeftMenuMain;
	
	private JButton bLoad;
	private JButton bSave;
	private JButton bParameters;
	private JButton bExit;
	private JButton arrowLeft;
	private JButton arrowMiddle;
	private JButton arrowRight;

	private JTextArea rulesText;// RULES
	private JScrollPane rulesTextScroll;

	private JTextArea logsText;// LOGS
	private JScrollPane logsTextScroll;
	// <Interface

	private ArrayList<Deplacement> moveList;// List of all the moves of the current game
	private int moveListCursor;
	
	private boolean isBrowserView=false;
	private ArrayList<String> FENList;//one turn = one FEN
	
	private int gameId;
	private int gameType;
	private String fenDeDepart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
	private JLabel labelSizeOfMoveList;
	private JLabel labelMoveListCursor;
	
	//TODO remove test
	private JFrame testFrame;
	private JTextArea testFrameTextArea;
	
	
	public ChesstoryGame(String title, int gameType) {
		this.gameType = gameType;

		/*
		 * heightRightPanel=(int)(height*(0.66));
		 * widthRightPanel=(int)(width*(0.66));
		 */

		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		f = new JFrame();
		
		// f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width,
				GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		f.setForeground(Color.BLUE);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		// Chessboard
		YACG = new YetAnotherChessGame(fenDeDepart, this);
		
		f.setVisible(true);
		panelLeft = new JPanel();
		panelLeft.setBorder(new LineBorder(Color.GREEN));
		// panelLeft.setSize((int)(f.getSize().getWidth()*panelLeftRatio)-200,
		// (int)f.getSize().getHeight()+500);
		panelLeft.setBackground(Color.gray);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[] { 1000, 0 };
		gbl_panelLeft.rowHeights = new int[] { 10, 600, 0, 0, 0 };
		gbl_panelLeft.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelLeft.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		panelLeft.setLayout(gbl_panelLeft);

		panelLeftMenu = new JPanel();
		GridBagConstraints gbc_panelLeftMenu = new GridBagConstraints();
		gbc_panelLeftMenu.fill = GridBagConstraints.BOTH;
		gbc_panelLeftMenu.insets = new Insets(0, 0, 5, 0);
		gbc_panelLeftMenu.gridx = 0;
		gbc_panelLeftMenu.gridy = 0;
		panelLeft.add(panelLeftMenu, gbc_panelLeftMenu);
		panelLeftMenu.setLayout(new GridLayout(2, 1, 0, 0));

		panelLeftMenuMain = new JPanel();
		panelLeftMenu.add(panelLeftMenuMain);

		bLoad = new JButton("Load");
		panelLeftMenuMain.add(bLoad);

		bSave = new JButton("Save");
		panelLeftMenuMain.add(bSave);

		bParameters = new JButton("Parameters");
		panelLeftMenuMain.add(bParameters);

		bExit = new JButton("Exit");
		panelLeftMenuMain.add(bExit);

		panelLeftMenuBrowse = new JPanel();
		panelLeftMenu.add(panelLeftMenuBrowse);

		/*
		 * arrowLeft.setBorder(BorderFactory.createEmptyBorder());
		 * arrowLeft.setContentAreaFilled(false);
		 */
		// Icon icon =new ImageIcon(new
		// ImageIcon(cl.getResource("arrow_left.png")).getImage().getScaledInstance(20,
		// 60, Image.SCALE_DEFAULT));

		Icon icon = new ImageIcon(
				new ImageIcon("./bin/icons/arrow_left.png").getImage().getScaledInstance(60, 40, Image.SCALE_DEFAULT));
		arrowLeft = new JButton(icon);
		panelLeftMenuBrowse.add(arrowLeft);

		icon = new ImageIcon(
				new ImageIcon("./bin/icons/arrow_play.png").getImage().getScaledInstance(60, 40, Image.SCALE_DEFAULT));
		arrowMiddle = new JButton(icon);
		panelLeftMenuBrowse.add(arrowMiddle);

		icon = new ImageIcon(
				new ImageIcon("./bin/icons/arrow_right.png").getImage().getScaledInstance(60, 40, Image.SCALE_DEFAULT));
		arrowRight = new JButton(icon);
		panelLeftMenuBrowse.add(arrowRight);
		
		labelSizeOfMoveList = new JLabel("Size : x");
		panelLeftMenuBrowse.add(labelSizeOfMoveList);
		
		labelMoveListCursor = new JLabel("Current : x");
		panelLeftMenuBrowse.add(labelMoveListCursor);

		bSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveGame();
			}
		});
		bLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGame();
			}
		});
		bExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		arrowLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browserViewBack();
			}
		});
		arrowMiddle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browserViewPlay();
			}
		});
		arrowRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browserViewNext();
			}
		});
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.X_AXIS));
		panelLeftChessboard = new JPanel();
		panelLeftChessboard.setSize(new Dimension(600, 600));
		panelLeftChessboard.setMinimumSize(new Dimension(600, 600));
		FlowLayout flowLayout = (FlowLayout) panelLeftChessboard.getLayout();
		GridBagConstraints gbc_panelLeftChessboard = new GridBagConstraints();
		gbc_panelLeftChessboard.insets = new Insets(0, 0, 5, 0);
		gbc_panelLeftChessboard.fill = GridBagConstraints.BOTH;
		gbc_panelLeftChessboard.gridx = 0;
		gbc_panelLeftChessboard.gridy = 1;
		panelLeft.add(panelLeftChessboard, gbc_panelLeftChessboard);
		panelLeftChessboard.add(YACG.CreationChessboard());
		f.getContentPane().add(panelLeft);

		panelRight = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelRight.getLayout();
		panelRight.setBorder(new LineBorder(Color.RED));
		panelRight.setBackground(Color.darkGray);

		// RULES
		rulesText = new JTextArea();
		rulesText.setWrapStyleWord(true);
		rulesText.setLineWrap(true);
		rulesText.setEditable(false);
		rulesText.setColumns(30);
		rulesText.setTabSize(1);
		rulesText.setRows(15);
		rulesText.setText("This is an editable JrulesText. " + "A text area is a \"plain\" text component, "
				+ "which means that although it can display text " + "in any font, all of the text is in the same font."
				+ "A text area is a \"plain\" text component, " + "which means that although it can display text "
				+ "in any font, all of the text is in the same font." + "A text area is a \"plain\" text component, "
				+ "which means that although it can display text " + "in any font, all of the text is in the same font."
				+ "A text area is a \"plain\" text component, " + "which means that although it can display text "
				+ "in any font, all of the text is in the same font." + "A text area is a \"plain\" text component, "
				+ "which means that although it can display text " + "in any font, all of the text is in the same font."
				+ "A text area is a \"plain\" text component, " + "which means that although it can display text "
				+ "in any font, all of the text is in the same font." + "A text area is a \"plain\" text component, "
				+ "which means that although it can display text " + "in any font, all of the text is in the same font."
				+ "A text area is a \"plain\" text component, " + "which means that although it can display text "
				+ "in any font, all of the text is in the same font." + "A text area is a \"plain\" text component, "
				+ "which means that although it can display text " + "in any font, all of the text is in the same font."
				+ "A text area is a \"plain\" text component, " + "which means that although it can display text "
				+ "in any font, all of the text is in the same font.");

		rulesTextScroll = new JScrollPane(rulesText);
		rulesTextScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelRight.add(rulesTextScroll);
		// < RULES

		// LOGS
		logsText = new JTextArea();
		logsText.setWrapStyleWord(true);
		logsText.setLineWrap(true);
		logsText.setEditable(false);
		logsText.setColumns(30);
		logsText.setTabSize(1);
		logsText.setRows(32);
		DefaultCaret caret= (DefaultCaret)logsText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);//auto scrolling

		logsTextScroll = new JScrollPane(logsText);
		logsTextScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelRight.add(logsTextScroll);
		f.getContentPane().add(panelRight);

		// <Chessboard
		
		disableBrowserView();//We are in a game
		
		f.validate();

		moveList = new ArrayList<Deplacement>();
		
		
		
		FENList=new ArrayList<String>();
		//TODO remove test doute
		testFrame=new JFrame();
		testFrame.setTitle("Affichage dynamique de la moveList");
		testFrame.setSize(300,800);
		testFrameTextArea=new JTextArea();
		testFrameTextArea.setText("ARRAYLIST THIS IS NOT SUPPOSED TO BE DISPLAYED");
		testFrame.getContentPane().add(testFrameTextArea);
		testFrame.setAlwaysOnTop(true);
		testFrame.setVisible(true);
		testFrame.validate();
	}

	public void addLogsText(String s) {
		logsText.append(s + "\n\r");
	}

	public void addLogsMove(Deplacement d, char piece, char joueur) {
		String color;
		//TODO addMove, WHY ?
		if (joueur == 'b') {
			color = "noir";
		} else if (joueur == 'w') {
			color = "blanc";
		} else {
			color = "ERREUR_COULEUR";
		}

		// String s=color+" : Déplacement "+piece+", de "+x1+y1+" à "+x2+y2;
		String s = color + " : Déplacement " + d;//TODO improve display
		addLogsText(s);
	}

	public void addMove(Deplacement d) {
		moveList.add(d);
		moveListCursor=moveList.size()-1;
	}
	public void addMoveMadeByPlayer(Deplacement d){
		addMove(d);
		addLogsMove(d, d.getPiececode(), d.getColor());
		moveListCursor++;
		// one turn = one move, this is for the browserView (back function)
		FENList.add(YACG.getFEN());
		refreshLabelsGame();
		
		
		
	}
	private void enableBrowserView(){
		YACG.switchClickable(false);
		isBrowserView=true;
		switchBrowserViewBorder(true);
		YACG.switchBorder(false);
		addLogsText("    > STATE ----> Browser view !");
		//TODO get a table of FEN, already done, I think...
	}
	private void disableBrowserView(){
		YACG.switchClickable(true);
		isBrowserView=false;
		switchBrowserViewBorder(false);
		YACG.switchBorder(true);
		addLogsText("    > STATE ----> Game view!");
	}
	private void browserViewPlay(){
		if(!isBrowserView){
			enableBrowserView();
		}else{
			int i=moveListCursor;//the user has choosen a point in the game to return to
			while(i<moveList.size()){//we delete all moves after this point/turn
				moveList.remove(i);
				FENList.remove(i);
			}
			disableBrowserView();
			refreshLabelsGame();
		}
	}
	private void browserViewNext(){
		if(!isBrowserView)
			enableBrowserView();
		if(moveListCursor<moveList.size()){
			
			moveListCursor++;
			//YACG.makeDeplacement(moveList.get(moveListCursor-1));
			addLogsText("Next, moveList : "+moveList.size()+", cursor : "+moveListCursor);
			YACG.makeDrawFen(FENList.get(moveListCursor-1));
		}
		refreshLabelsGame();
	}
	private void browserViewBack(){
		if(!isBrowserView)
			enableBrowserView();
		if(moveListCursor>=0){
			
			moveListCursor--;
			//YACG.forceMakeDeplacement(new Deplacement(moveList.get(moveListCursor).getArrive(),moveList.get(moveListCursor).getDepart()));
			//addLogsText("Back, moveList : "+moveList.size()+", cursor : "+moveListCursor); Useless ?
			if(moveListCursor==0){//begining of the game so classic disposition
				YACG.makeDrawFen(fenDeDepart);
			}else{
				
				YACG.makeDrawFen(FENList.get(moveListCursor-1));
			}
			addLogsText("Back, moveList : "+moveList.size()+", cursor : "+moveListCursor);
		}else{//put the arrow in grey
			
		}
		refreshLabelsGame();
	}
	private void refreshArrows(){
		if(moveListCursor==moveList.size()){
			if(arrowRight.isEnabled())		arrowRight.setEnabled(false);
		}else{
			if(!arrowRight.isEnabled())		arrowRight.setEnabled(true);
		}
		if(moveListCursor==0){
			if(arrowLeft.isEnabled())		arrowLeft.setEnabled(false);
		}else{
			if(!arrowLeft.isEnabled())		arrowLeft.setEnabled(true);
		}
	}
	private void refreshLabelsGame(){
		labelMoveListCursor.setText("Cursor : "+moveListCursor);
		labelSizeOfMoveList.setText("Size : "+moveList.size());
		refreshArrows();
		refreshTestFrame();
	}
	//TODO remove test
	private void refreshTestFrame(){
		testFrameTextArea.setText("");
		if(FENList.size()>0){
			for (int i = 0; i < moveList.size(); i++) {
				 //TODO improve display
				
				testFrameTextArea.append((i+1)+" >"+moveList.get(i).getColor() + " : Déplacement " + moveList.get(i) + " ||FEN: "+FENList.get(i) +"\n\r");
			}
		}else{
			testFrameTextArea.append("FENList size is : "+FENList.size());
		}
	}
	private void switchBrowserViewBorder(boolean b){
		if(b){
			panelLeftMenuBrowse.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 255, 255), new Color(0, 255, 255), new Color(0, 255, 255), new Color(0, 255, 255)));
		}else{
			panelLeftMenuBrowse.setBorder(new EmptyBorder(0, 0, 0, 0));
		}
	}
	public void loadGame() {//TODO multiple moveLists I don't know why
		disableBrowserView();//We are in a game
		// moveList=new ArrayList<Deplacement>(FileController.loadFile());
		System.out.println("---->Loading file");
		GameSave gameSave = FileController.loadFile();
		if (gameSave.getIsGameCorrupted()) {
			System.out.println("---->FILE CORRUPTED\n---->Loading failed");
		} else {
			System.out.println("---->Loading successful");
		}
		//moveList = gameSave.getMoveList();
		gameId = gameSave.getGameId();
		gameType = gameSave.getGameType();
		moveListCursor=0;
		moveList.clear();
		System.out.println("Load game, id: " + gameId + ", type: " + gameType);
		YACG.makeDrawFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
		
		for (int i = 0; i < gameSave.getMoveList().size(); i++) {
			
			//moveList.add(gameSave.getMoveList().get(i));
			YACG.makeDeplacement(gameSave.getMoveList().get(i));
			System.out.println("c =" + moveList.get(i).getColor() + ", p =" + moveList.get(i).getPiececode() + ", ("
					+ moveList.get(i).getX1() + ", " + moveList.get(i).getY1() + ") -> (" + moveList.get(i).getX2()
					+ ", " + moveList.get(i).getY2() + ")");

		}
		//moveListCursor = moveList.size()-1;
	

	}

	public void saveGame() {
		// TODO game id generator
		gameId = 1;
		GameSave g = new GameSave(true, gameId, gameType, moveList);
		FileController.saveFile(g);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
