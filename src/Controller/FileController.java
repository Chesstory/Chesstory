package Controller;

import echecs.Deplacement;
import echecs.Position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

abstract class FileController {
	static boolean saveInFile(ArrayList<Deplacement> a) {

		return true;
	}

	static GameSave loadFile() {
		ArrayList<Deplacement> a = new ArrayList<Deplacement>();
		String fileName = "./Saves/save1.txt";//not used anymore
		String directory = "./Saves/";
		String line[];
		line=new String[100];
		String splitted[];
		splitted=new String[6];
		
		//var used for establishing if the line read is a Deplacement or something else, like the number of move expected or, later, the name, the type and date of the game
		int nbLine=0;
		boolean isFileCorrupted=true;
		int game_id=-1;
		int game_type=-1;
		int expectedNbOfLine;
		Object returnArray[]=new Object[104];
		
		//file chooser
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(directory));
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle("Load your chess game");
		chooser.setFileFilter(new FileNameExtensionFilter("txt fking file", "txt"));
		//chooser.setAcceptAllFileFilterUsed(false); // remove the accept-all (.*) file filter
		int retrival =chooser.showDialog(ChesstoryGame.f, "LOAD...");
		//int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try (FileReader fileReader = new FileReader(chooser.getSelectedFile())) {
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line[nbLine]=bufferedReader.readLine() ) != null && isFileCorrupted) {//Reading of the lines 
					splitted=line[nbLine].split(" " , -1);
					switch(nbLine){
					case 0:
						if(splitted.length==1){
							System.out.println("Ini :"+splitted[0]);
								if(!splitted[0].equals(">>>>||----Chesstory_SaveFile_Header--|")){
									isFileCorrupted=true;
								}
						}else{
							isFileCorrupted=true;
						};
					break;
					case 1://id of the game
						if(splitted.length==1){
							if(Character.isDigit(splitted[0].charAt(0))){//test if integer
								game_id=Integer.parseInt(splitted[0]);
							}else{
								isFileCorrupted=true;
							}
						}else{
							isFileCorrupted=true;
						};
					break;
					case 2://type of the game
						if(splitted.length==1){
							if(Character.isDigit(splitted[0].charAt(0))){//test if integer
								game_type=Integer.parseInt(splitted[0]);
							}else{
								isFileCorrupted=true;
							}
						}else{
							isFileCorrupted=true;
						};
					break;
					case 3://Number of line expected
						if(splitted.length==1){
							if(Integer.toString(Integer.parseInt(splitted[0]))==splitted[0]){//test if integer
								expectedNbOfLine=Integer.parseInt(splitted[0]);
							}else{
								isFileCorrupted=true;
							}
						}else{
							isFileCorrupted=true;
						}
					break;
					
					default://the list of moves
						if(!splitted[0].equals("|--Chesstory_SaveFile_Footer----||<<<<")){//test if it's not the footer
							//TODO test if the first and all the others are in fact the color, piece, pos1, pos2
							a.add(new Deplacement(new Position(Integer.parseInt(splitted[2]),Integer.parseInt(splitted[3])),    new Position(Integer.parseInt(splitted[4]),Integer.parseInt(splitted[5])),    splitted[0].toCharArray()[0],    splitted[1].toCharArray()[0]));
							
						}else{//TODO test footer
							System.out.println("Ini :"+splitted[0]);		
						}
					break;
					}					
					nbLine++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Unable to open file '" + fileName + "'");
			}
		}
		//here we check if the number on line is correct
		return new GameSave(isFileCorrupted, game_id, game_type, a);
	}
}
