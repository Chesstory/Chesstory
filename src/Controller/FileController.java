package Controller;

import echecs.Deplacement;
import echecs.Position;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

abstract class FileController {
	static boolean saveFile(GameSave g) {
		String directory = "./Saves/";
		String suffix = "txt";

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(directory));
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("text file", suffix));
		suffix = ".txt";
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			String sufTemp[] = chooser.getSelectedFile().getName().split("\\.", -1);
			if (sufTemp.length > 1) {
				if (sufTemp[1].equals("txt")) {
					suffix = "";
				} else {
					suffix = ".txt";
					System.out.println("xxxxxx");
				}
			}
			try (FileWriter fileWriter = new FileWriter(chooser.getSelectedFile() + suffix)) {
				System.out.println("---->Saving file");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(">>>>||----Chesstory_SaveFile_Header--|");
				bufferedWriter.newLine();
				
				bufferedWriter.write(Integer.toString(g.getGameId()));
				System.out.println("    >id : " + g.getGameId());
				bufferedWriter.newLine();
				
				bufferedWriter.write(Integer.toString(g.getGameType()));
				System.out.println("    >type : " + g.getGameType());
				bufferedWriter.newLine();
				
				ArrayList<Deplacement> temp = g.getMoveList();
				bufferedWriter.write(Integer.toString(temp.size()));//Size of the arraylist expected
				bufferedWriter.newLine();
				
				bufferedWriter.write(g.getFEN());
				System.out.println("    >departureFEN : "+g.getFEN());
				bufferedWriter.newLine();
				
				for(int i=0;i<6;i++){
					bufferedWriter.write(g.getArrayRulePiece()[i]);
					System.out.println("    >rule "+i+" : "+g.getArrayRulePiece());
				}
				
				for (int i = 0; i < temp.size(); i++) {
					bufferedWriter.write(
							temp.get(i).getColor() + " " + temp.get(i).getPiececode() + " " + temp.get(i).getX1() + " "
									+ temp.get(i).getY1() + " " + temp.get(i).getX2() + " " + temp.get(i).getY2());
					;
					bufferedWriter.newLine();
				}
				bufferedWriter.write("|--Chesstory_SaveFile_Footer----||<<<<");
				System.out.println("---->File saved !");

				/*
				 * if(!chooser.getSelectedFile().getAbsolutePath().endsWith(
				 * suffix)){//useless I think file = new File(bufferedWriter +
				 * suffix); }
				 */

				// Always close files.
				bufferedWriter.close();

			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Unable to save file");
				/*
				 * JOptionPane.showMessageDialog(this,
				 * "The Text could not be Saved!", "Error!",
				 * JOptionPane.INFORMATION_MESSAGE);
				 */
			}
		}
		return true;
	}

	static GameSave loadFile() {
		ArrayList<Deplacement> a = new ArrayList<Deplacement>();
		String fileName = "./Saves/save1.txt";// not used anymore
		String directory = "./Saves/";
		String line[];
		line = new String[100];
		String splitted[];
		splitted = new String[6];

		// var used for establishing if the line read is a Deplacement or
		// something else, like the number of move expected or, later, the name,
		// the type and date of the game
		int nbParaLine = 10;// test var for the parameters : game_id,
							// game_type...
		boolean isFileCorrupted = false;
		boolean foundFooter = false;

		int nbLine = 0;
		int game_id = -1;
		int game_type = -1;
		int expectedNbOfMoves = -1;
		String FEN="-1";
		String[] arrayRulePiece=new String[6];
		
		// TODO the case where there is a " " at the end of a line
		
		// file chooser
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(directory));
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle("Load your chess game");
		chooser.setFileFilter(new FileNameExtensionFilter("text file", "txt"));
		// chooser.setAcceptAllFileFilterUsed(false); // remove the accept-all
		// (.*) file filter
		int retrival = chooser.showDialog(ChesstoryGame.f, "LOAD...");
		// int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try (FileReader fileReader = new FileReader(chooser.getSelectedFile())) {
				@SuppressWarnings("resource")
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line[nbLine] = bufferedReader.readLine()) != null && !isFileCorrupted) {// Reading
																								// of
																								// the
																								// lines
					splitted = line[nbLine].split(" ", -1);
					switch (nbLine) {
					case 0:
						if (splitted.length == 1) {
							// System.out.println("Ini :"+splitted[0]);
							if (!splitted[0].equals(">>>>||----Chesstory_SaveFile_Header--|")) {
								isFileCorrupted = true;
								System.out.println("ErrorFileLoad: Header not found at the begining of the file");
							}
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: First line should be an unique component -> header");
						}
						;
						break;
					case 1:// id of the game
						if (splitted.length == 1) {
							if (Character.isDigit(splitted[0].charAt(0))) {// test
																			// if
																			// integer
								game_id = Integer.parseInt(splitted[0]);
							} else {
								isFileCorrupted = true;
								System.out.println("ErrorFileLoad: Second line should be a integer");
							}
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Second line should be a unique component -> game_id");
						}
						;
						break;
					case 2:// type of the game
						if (splitted.length == 1) {
							if (Character.isDigit(splitted[0].charAt(0))) {// test
																			// if
																			// integer
								game_type = Integer.parseInt(splitted[0]);
							} else {
								isFileCorrupted = true;
								System.out.println("ErrorFileLoad: Third line should be an integer");
							}
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Third line should be a unique component -> game_type");
						}
						;
						break;
					case 3:// Number of line expected
						if (splitted.length == 1) {
							if (splitted[0].matches("^-?\\d+$")) {// test if
																	// integer
								expectedNbOfMoves = Integer.parseInt(splitted[0]);
							} else {
								isFileCorrupted = true;
								System.out.println("ErrorFileLoad: Fourth line should be an integer");
							}
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Fourth line should be a unique component -> number of lines");
						}
						break;
					case 4://FEN
						if(splitted.length==1){
							FEN=splitted[0];
						}else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Fifth line should be a unique component -> FEN");
						}
						break;
					case 5://1 pawn
						if(splitted.length==1){
							arrayRulePiece[0]=splitted[0];
						}else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 1 pawn");
						}
						break;
					case 6://2 rook
						if(splitted.length==1){
							arrayRulePiece[1]=splitted[0];
						}else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 2 rook");
						}
						break;
					case 7://3 queen
						if(splitted.length==1){
							arrayRulePiece[2]=splitted[0];
						}else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 3 queen");
						}
						break;
					case 8://4 king
						if(splitted.length==1){
							arrayRulePiece[3]=splitted[0];
						}else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 4 king");
						}
						break;
					case 9://5 bishop
						if(splitted.length==1){
							arrayRulePiece[4]=splitted[0];
						}else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 5 bishop");
						}
						break;
					case 10://6 knight
						if(splitted.length==1){
							arrayRulePiece[5]=splitted[0];
						}else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 6 pion");
						}
						break;
					default:// the list of moves
						if (!splitted[0].equals("|--Chesstory_SaveFile_Footer----||<<<<")) {// test
																							// if
																							// it's
																							// not
																							// the
																							// footer
							if (splitted.length == 6) {// there is 6 parameter,
														// no more, no less
								// TODO test if the first and all the others are
								// in fact the color, piece, pos1, pos2
								a.add(new Deplacement(
										new Position(Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3])),
										new Position(Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5])),
										splitted[1].toCharArray()[0], splitted[0].toCharArray()[0]));
							} else {
								isFileCorrupted = true;
								System.out
										.println("ErrorFileLoad: Move parameters incorrect, at line: " + (nbLine + 1));
							}
						} else {
							foundFooter = true;
							// System.out.println("Eni :"+splitted[0]);
						}
						break;
					}
					nbLine++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("ErrorFileLoad: Unable to open file '" + fileName + "'");
			}
			// here we check if the number on line is correct
			if (expectedNbOfMoves == (nbLine - nbParaLine - 1)) {// 2 is the header
																// and footer
				isFileCorrupted = true;
				System.out.println("ErrorFileLoad: expected nb of line: " + expectedNbOfMoves + ", received :"
						+ (nbLine - nbParaLine - 1));
			}
			
			if(expectedNbOfMoves>0){
				// here we check if the game is correct : w,b,w,...
				char last = a.get(0).getColor();
				for (int i = 1; i < a.size(); i++) {
					if (last == a.get(i).getColor()) {
						isFileCorrupted = true;
						System.out.print("ErrorFileLoad: The game is not correct at line: " + (i + 1 + nbParaLine)
								+ ", because there is two consecutive turn of :" + last + "\n");
					}
					last = a.get(i).getColor();
				}
			}else{
				if(expectedNbOfMoves<0){
					System.out.println("ErrorFileLoad: Something went wrong with the number or moves expected :"+expectedNbOfMoves);
				}
			}
			

			// here we check is the footer was found
			if (!foundFooter) {
				isFileCorrupted = true;
				System.out.println("ErrorFileLoad: Footer not found at the begining of the file");
			}
		}
		

		return new GameSave(isFileCorrupted, game_id, game_type, a, FEN, arrayRulePiece);
	}
}
