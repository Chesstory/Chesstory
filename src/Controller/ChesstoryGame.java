package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
















import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class ChesstoryGame extends JFrame implements MouseListener{
	//static YetAnotherChessGame YACG;
	
	private YetAnotherChessGame YACG;
	//>Interface
	public static JFrame f;
	private JPanel panelLeft;
	private JPanel panelRight;
	private JPanel panelLeftChessboard;
	
	private static final int height=850;
	private static final int width=1600;
	private int heightRightPanel;
	private int widthRightPanel;
	
	
	private JTextArea rulesText;//RULES
	private JScrollPane rulesTextScroll;
	
	private static JTextArea logsText;//LOGS
	private JScrollPane logsTextScroll;
	//<Interface
	
	private static ArrayList<Deplacement> moveList;//List of all the moves of the current game
	private int gameId;
	private int gameType;
	private String fenDeDepart="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
	private JPanel panelLeftMenu;
	private JButton bLoad;
	private JButton bSave;
	private JButton bParameters;
	private JButton bExit;
	
	public ChesstoryGame(String title, int gameType){
		this.gameType=gameType;
		
		heightRightPanel=(int)(height*(0.66));
		widthRightPanel=(int)(width*(0.66));
		
		f=new JFrame();
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setForeground(Color.BLUE);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.getContentPane().setLayout(null);
		
		
		panelLeft = new JPanel();
		panelLeft.setBounds(10, 27, 1163, 772);
		panelLeft.setBackground(Color.gray);
		f.getContentPane().add(panelLeft);
		
		
		
		
		
		panelRight = new JPanel();
		panelRight.setBounds(1183, 27, 403, 772);
		panelRight.setBackground(Color.darkGray);
		f.getContentPane().add(panelRight);
		
		
		
		
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
		//Chessboard
		YACG=new YetAnotherChessGame(fenDeDepart);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[]{1163, 0};
		gbl_panelLeft.rowHeights = new int[]{154, 154, 0, 0};
		gbl_panelLeft.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelLeft.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		panelLeft.setLayout(gbl_panelLeft);
		
		panelLeftMenu = new JPanel();
		GridBagConstraints gbc_panelLeftMenu = new GridBagConstraints();
		gbc_panelLeftMenu.insets = new Insets(0, 0, 5, 0);
		gbc_panelLeftMenu.fill = GridBagConstraints.BOTH;
		gbc_panelLeftMenu.gridx = 0;
		gbc_panelLeftMenu.gridy = 0;
		panelLeft.add(panelLeftMenu, gbc_panelLeftMenu);
		
		bLoad = new JButton("Load");
		bLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGame();
			}
		});
		panelLeftMenu.add(bLoad);
		
		bSave = new JButton("Save");
		bSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveGame();
			}
		});
		panelLeftMenu.add(bSave);
		
		bParameters = new JButton("Parameters");
		panelLeftMenu.add(bParameters);
		
		bExit = new JButton("Exit");
		panelLeftMenu.add(bExit);
		
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
