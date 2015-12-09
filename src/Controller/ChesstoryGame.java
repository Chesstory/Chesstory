package Controller;
import java.awt.event.ActionEvent;
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
import java.awt.TextArea;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.border.EtchedBorder;

import java.util.ArrayList;











import gui.YetAnotherChessGame;
import echecs.Echiquier;
import echecs.Deplacement;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

public class ChesstoryGame extends JFrame implements MouseListener{
	//static YetAnotherChessGame YACG;
	
	private YetAnotherChessGame YACG;
	//>Interface
	public static JFrame f;
	private JPanel panelLeft;
	//	private float panelLeftRatio=(2/3);
	private JPanel panelRight;
	private JPanel panelLeftChessboard;
	private JPanel panelLeftMenu;
	private JButton bLoad;
	private JButton bSave;
	private JButton bParameters;
	private JButton bExit;
	private JButton arrowLeft;
	private JButton arrowMiddle; 
	private JButton arrowRight; 
	
	
	
	private JTextArea rulesText;//RULES
	private JScrollPane rulesTextScroll;
	
	private static JTextArea logsText;//LOGS
	private JScrollPane logsTextScroll;
	//<Interface
	
	private static ArrayList<Deplacement> moveList;//List of all the moves of the current game
	private int gameId;
	private int gameType;
	private String fenDeDepart="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
	
	
	
	public ChesstoryGame(String title, int gameType){
		this.gameType=gameType;
		
		/*heightRightPanel=(int)(height*(0.66));
		widthRightPanel=(int)(width*(0.66));*/
		
		f=new JFrame();
		//f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		f.setForeground(Color.BLUE);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		//Chessboard
		YACG=new YetAnotherChessGame(fenDeDepart);
		
		
		panelLeft = new JPanel();
		panelLeft.setBorder(new LineBorder(Color.GREEN));
		//panelLeft.setSize((int)(f.getSize().getWidth()*panelLeftRatio)-200, (int)f.getSize().getHeight()+500);
		panelLeft.setBackground(Color.gray);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[]{1000, 0};
		gbl_panelLeft.rowHeights = new int[]{10, 600, 0};
		gbl_panelLeft.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelLeft.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelLeft.setLayout(gbl_panelLeft);
		
		
		panelLeftMenu = new JPanel();
		GridBagConstraints gbc_panelLeftMenu = new GridBagConstraints();
		gbc_panelLeftMenu.fill = GridBagConstraints.BOTH;
		gbc_panelLeftMenu.insets = new Insets(0, 0, 5, 0);
		gbc_panelLeftMenu.gridx = 0;
		gbc_panelLeftMenu.gridy = 0;
		panelLeft.add(panelLeftMenu, gbc_panelLeftMenu);
		panelLeftMenu.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panelLeftMenuMain = new JPanel();
		panelLeftMenu.add(panelLeftMenuMain);
		
		bLoad = new JButton("Load");
		panelLeftMenuMain.add(bLoad);
		
		bSave = new JButton("Save");
		panelLeftMenuMain.add(bSave);
		
		bParameters = new JButton("Parameters");
		panelLeftMenuMain.add(bParameters);
		
		bExit = new JButton("Exit");
		panelLeftMenuMain.add(bExit);
		
		JPanel panelLeftMenuBrowse = new JPanel();
		panelLeftMenu.add(panelLeftMenuBrowse);
		
		/*arrowLeft.setBorder(BorderFactory.createEmptyBorder());
		arrowLeft.setContentAreaFilled(false);*/
		//Icon icon =new ImageIcon(new ImageIcon(cl.getResource("arrow_left.png")).getImage().getScaledInstance(20, 60, Image.SCALE_DEFAULT));
		
		Icon icon =new ImageIcon(new ImageIcon("C:\\Users\\Roman\\git\\Chesstory\\bin\\ui_icons\\arrow_left.png").getImage().getScaledInstance(60, 40, Image.SCALE_DEFAULT));		
		arrowLeft = new JButton(icon);
		panelLeftMenuBrowse.add(arrowLeft);
		
		icon =new ImageIcon(new ImageIcon("C:\\Users\\Roman\\git\\Chesstory\\bin\\ui_icons\\arrow_play.png").getImage().getScaledInstance(60, 40, Image.SCALE_DEFAULT));
		arrowMiddle = new JButton(icon);
		panelLeftMenuBrowse.add(arrowMiddle);
		
		icon =new ImageIcon(new ImageIcon("C:\\Users\\Roman\\git\\Chesstory\\bin\\ui_icons\\arrow_right.png").getImage().getScaledInstance(60, 40, Image.SCALE_DEFAULT));
		arrowRight = new JButton(icon);
		panelLeftMenuBrowse.add(arrowRight);
		
		
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
				//TODO forward
			}
		});
		arrowMiddle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO play
			}
		});
		arrowRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO back
			}
		});
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.X_AXIS));
		panelLeftChessboard = new JPanel();
		panelLeftChessboard.setSize(new Dimension(600, 600));
		panelLeftChessboard.setMinimumSize(new Dimension(600, 600));
		FlowLayout flowLayout = (FlowLayout) panelLeftChessboard.getLayout();
		GridBagConstraints gbc_panelLeftChessboard = new GridBagConstraints();
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
		
		
		
		
		//RULES
		rulesText = new JTextArea();
		rulesText.setWrapStyleWord(true);
		rulesText.setLineWrap(true);
		rulesText.setEditable(false);
		rulesText.setColumns(30);
		rulesText.setTabSize(1);
		rulesText.setRows(15);
		rulesText.setText( "This is an editable JrulesText. " +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font." +
			    "A text area is a \"plain\" text component, " +
			    "which means that although it can display text " +
			    "in any font, all of the text is in the same font.");
		
		
		rulesTextScroll = new JScrollPane(rulesText);
		rulesTextScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelRight.add(rulesTextScroll);
		//< RULES
		
		//LOGS
		logsText = new JTextArea();
		logsText.setWrapStyleWord(true);
		logsText.setLineWrap(true);
		logsText.setEditable(false);
		logsText.setColumns(30);
		logsText.setTabSize(1);
		logsText.setRows(32);
		
		logsTextScroll = new JScrollPane(logsText);
		logsTextScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelRight.add(logsTextScroll);
		f.getContentPane().add(panelRight);
		
		//<Chessboard
		
		
	
		f.validate();
		
		moveList=new ArrayList<Deplacement>();
		}
	public static void addLogsText(String s){
		logsText.append(s+"\n\r");	
	}
	public static void addLogsMove(echecs.Deplacement d, String piece, char joueur){
		String color;
		if(joueur=='b'){
			color="noir";
		}else if(joueur=='w'){
			color="blanc";
		}else{
			color="ERREUR_COULEUR";
		}
		
		
	//	String s=color+" : Déplacement "+piece+", de "+x1+y1+" à "+x2+y2;
		String s=color+" : Déplacement "+piece+", de "+d;
		addLogsText(s);
	}
	public static void addMove(Deplacement d){
		moveList.add(d);
	}
	public void loadGame(){		
		//moveList=new ArrayList<Deplacement>(FileController.loadFile());
		System.out.println("---->Loading file");
		GameSave gameSave=FileController.loadFile();
		if(gameSave.getIsGameCorrupted()){
			System.out.println("---->FILE CORRUPTED\n---->Loading failed");
		}else{
			System.out.println("---->Loading successed");
		}
		moveList=gameSave.getMoveList();
		gameId=gameSave.getGameId();
		gameType=gameSave.getGameType();
		System.out.println("Load game, id: "+gameId+", type: "+gameType);
		for(int i=0;i<moveList.size();i++){
			System.out.println("c ="+moveList.get(i).getColor()+", p ="+moveList.get(i).getPiececode()+", ("+moveList.get(i).getX1()+", "+moveList.get(i).getY1()+") -> ("+moveList.get(i).getX2()+", "+moveList.get(i).getY2()+")");
			
		}	
	}
	public void saveGame(){
		//TODO game id generator
		gameId=1;
		GameSave g=new GameSave(true, gameId, gameType, moveList);
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
