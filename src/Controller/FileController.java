package Controller;

import echecs.Deplacement;
import echecs.Echiquier;
import echecs.Position;
import gui.YetAnotherChessGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static Controller.ChesstoryConstants.*;

/**
 * Check the structure of a text/file in order to save/load it.
 * 
 * @author Acevedo Roman and Guillemot Baptiste, based on Delepoulle Samuel's
 *         code.
 *
 */
abstract class FileController {
	static boolean saveFile(GameSave g) {
		return saveFile(g, "CHOOSER..FILE");
	}

	static boolean saveFile(GameSave g, String fileNameToSave) {
		File fileToSave;
		int retrival;
		String directory = "./ChesstoryData/playerSaves/";
		String suffix = ".txt";
		if (fileNameToSave != "CHOOSER..FILE") {
			retrival = JFileChooser.APPROVE_OPTION;
			fileToSave = new File("ChesstoryData/defaultSaves/" + fileNameToSave);
		} else {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(directory));
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			suffix = "txt";
			chooser.setFileFilter(new FileNameExtensionFilter("text file", suffix));
			retrival = chooser.showSaveDialog(null);
			fileToSave = chooser.getSelectedFile();
			try {
				String sufTemp[] = fileToSave.getName().split("\\.", -1);
				if (sufTemp.length > 1) {
					if (sufTemp[1].equals("txt")) {
						suffix = "";
					} else {
						suffix = ".txt";
						System.out.println("xxxxxx");
					}
				} else {
					suffix = ".txt";
				}
				fileNameToSave = fileToSave.getName();
			} catch (Exception e) {

			}

		}
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try (FileWriter fileWriter = new FileWriter(fileToSave + suffix)) {
				System.out.println("---->Saving file");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(">>>>||----Chesstory_SaveFile_Header--|");
				bufferedWriter.newLine();
				System.out.println("Name file chooser : " + fileToSave.getAbsolutePath());
				bufferedWriter.write(Integer.toString(g.getGameId()));
				System.out.println("    >id : " + g.getGameId());
				bufferedWriter.newLine();

				bufferedWriter.write(Integer.toString(g.getGameType()));
				System.out.println("    >type : " + g.getGameType());
				bufferedWriter.newLine();

				ArrayList<Deplacement> temp = g.getMoveList();
				bufferedWriter.write(Integer.toString(temp.size()));// Size of
																	// the
																	// arraylist
																	// expected
				bufferedWriter.newLine();

				bufferedWriter.write(g.getFEN());
				System.out.println("    >departureFEN : " + g.getFEN());
				bufferedWriter.newLine();

				for (int i = 0; i < 6; i++) {
					bufferedWriter.write(g.getArrayRulePiece()[i]);
					System.out.println("    >rule " + i + " : " + g.getArrayRulePiece()[i]);
					bufferedWriter.newLine();
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
				// Always close files.
				bufferedWriter.close();

			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Unable to save file");
				JOptionPane.showMessageDialog(null, "The Text could not be Saved!", "Error!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		return true;
	}

	static GameSave loadFile(String fileToLoad) {
		ArrayList<Deplacement> a = new ArrayList<Deplacement>();
		String directory = "./ChesstoryData/playerSaves/";
		String line[];
		line = new String[100];
		String splitted[];
		splitted = new String[6];

		// var used to establish if the line read is a Deplacement or
		// something else, like the number of move expected or, later, the name,
		// the type and date of the game
		int nbParaLine = 10;// test var for the parameters : game_id,
							// game_type...
		boolean isFileCorrupted = false;
		boolean wasTheWindowClosedBeforeSelection = false;
		boolean foundFooter = false;

		int nbLine = 0;
		int game_id = -1;
		int game_type = -1;
		int expectedNbOfMoves = -1;
		String FEN = "-1";
		String[] arrayRulePiece = new String[6];

		// TODO the case where there is a " " at the end of a line

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		int retrival;
		File file;
		if (fileToLoad == "choosingAFile") {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(directory));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setDialogTitle("Load your chess game");
			chooser.setFileFilter(new FileNameExtensionFilter("text file", "txt"));
			// chooser.setAcceptAllFileFilterUsed(false); // remove the
			// accept-all
			// (.*) file filter
			retrival = chooser.showDialog(null, "LOAD...");
			file = chooser.getSelectedFile();
		} else {
			retrival = JFileChooser.APPROVE_OPTION;
			file = null;
			try {
				file = new File("ChesstoryData/defaultSaves/" + fileToLoad + ".txt");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error : file was not found", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (retrival == JFileChooser.APPROVE_OPTION) {
			System.out.println("FILE :::::::: " + file.getName());
			try (FileReader fileReader = new FileReader(file)) {
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line[nbLine] = bufferedReader.readLine()) != null && !isFileCorrupted) {// Reading
																								// of
																								// the
																								// lines
					splitted = line[nbLine].split(" ", -1);
					switch (nbLine) {
					case 0:
						if (splitted.length == 1) {
							if (!splitted[0].equals(">>>>||----Chesstory_SaveFile_Header--|")) {
								isFileCorrupted = true;
								System.out.println("ErrorFileLoad: Header not found at the begining of the file");
							}
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: First line should be an unique component -> header");
						}
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
						break;
					case 3:// Number of moves lines expeted
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
							System.out.println(
									"ErrorFileLoad: Fourth line should be a unique component -> number of lines");
						}
						break;
					case 4:// FEN
						if (splitted.length == 1) {
							FEN = splitted[0];
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Fifth line should be a unique component -> FEN");
						}
						break;
					case 5:// 1 pawn
						if (splitted.length == 1) {
							arrayRulePiece[0] = splitted[0];
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 1 pawn");
						}
						break;
					case 6:// 2 rook
						if (splitted.length == 1) {
							arrayRulePiece[1] = splitted[0];
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 2 rook");
						}
						break;
					case 7:// 3 queen
						if (splitted.length == 1) {
							arrayRulePiece[2] = splitted[0];
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 3 queen");
						}
						break;
					case 8:// 4 king
						if (splitted.length == 1) {
							arrayRulePiece[3] = splitted[0];
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 4 king");
						}
						break;
					case 9:// 5 bishop
						if (splitted.length == 1) {
							arrayRulePiece[4] = splitted[0];
						} else {
							isFileCorrupted = true;
							System.out.println("ErrorFileLoad: Sixth line should be a unique component -> 5 bishop");
						}
						break;
					case 10:// 6 knight
						if (splitted.length == 1) {
							arrayRulePiece[5] = splitted[0];
						} else {
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
							System.out.println("Footer :" + splitted[0]);
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
						}
						break;
					}
					nbLine++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("ErrorFileLoad: Unable to open file '" + file + "'");
			}
			// here we check if the number on line is correct
			if (expectedNbOfMoves != (nbLine - nbParaLine - 2)) {// 2 is the
																	// header
																	// and
																	// footer
				isFileCorrupted = true;
				System.out.println("ErrorFileLoad: expected nb of moves " + expectedNbOfMoves + ", received : "
						+ (nbLine - nbParaLine - 1) + " = nbline " + nbLine + " + nbparaline " + nbParaLine);
			}

			if (expectedNbOfMoves > 0) {
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
			} else {
				if (expectedNbOfMoves < 0) {
					System.out.println("ErrorFileLoad: Something went wrong with the number or moves expected :"
							+ expectedNbOfMoves);
				}
			}

			// here we check is the footer was found
			if (!foundFooter) {
				isFileCorrupted = true;
				System.out.println("ErrorFileLoad: Footer not found at the end of the file");
			}
		} else {
			wasTheWindowClosedBeforeSelection = true;
		}
		if (wasTheWindowClosedBeforeSelection) {
			return null;
		}
		return new GameSave(isFileCorrupted, game_id, game_type, a, FEN, arrayRulePiece);
	}

	/**
	 * Save a file in the directory /ChesstoryData/parameters/ of parameters.
	 * 
	 * @param nameOfTheFileToLoad
	 *            The name of the file to load, has to be a parameters file
	 * @return - Array of int (parameters)<br/>
	 *         - (-1) the file was corrupted, if was replaced by the default one
	 *         <br/>
	 *         - (-2) Fatal error in the cloning, thus cloning should not
	 *         happend anyway
	 */
	public static int[] loadParameters(String nameOfTheFileToLoad) {
		String splitted[];
		int res[];
		File fileToLoad;
		try {// we try to load the file
			fileToLoad = new File("ChesstoryData/parameters/" + nameOfTheFileToLoad + ".txt");
		} catch (Exception e) {// if the file was not found
			try {// we create the file from the jar
				JOptionPane.showMessageDialog(null, "File '" + nameOfTheFileToLoad + "' not found", "Error",
						JOptionPane.ERROR_MESSAGE);
				InputStream localInputStream = ClassLoader.getSystemClassLoader()
						.getResourceAsStream("ChesstoryData/parameters/" + nameOfTheFileToLoad + ".txt");
				byte[] buffer = new byte[localInputStream.available()];
				localInputStream.read(buffer);
				File targetFile = new File("ChesstoryData/parameters/" + nameOfTheFileToLoad + ".txt");
				OutputStream outStream = new FileOutputStream(targetFile);
				outStream.write(buffer);
				outStream.close();
				BufferedReader br = new BufferedReader(new InputStreamReader(localInputStream));
				br.close();
				fileToLoad = new File("ChesstoryData/parameters/" + nameOfTheFileToLoad + "txt");
			} catch (Exception ex) {// if the cloning has failed
				JOptionPane.showMessageDialog(null,
						"The operation 'cloning the file " + nameOfTheFileToLoad + " from jar' failed", "Fatal Error",
						JOptionPane.ERROR_MESSAGE);
				res = new int[1];
				res[0] = -2;
				return res;
			}
		}

		try {// we process the file, turning string into a array of int
			FileReader fileReader = new FileReader(fileToLoad);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			splitted = bufferedReader.readLine().split(",", -1);
			res = new int[splitted.length];
			for (int i = 0; i < splitted.length; i++) {
				res[i] = Integer.parseInt(splitted[i]);
			}
			bufferedReader.close();
		} catch (Exception e) {// if the file was corrupted
			JOptionPane.showMessageDialog(null,
					"The file " + nameOfTheFileToLoad + " is corrupted, it was replaced by the the default one",
					"Error", JOptionPane.ERROR_MESSAGE);
			try {// we replace the file from the jar
				JOptionPane.showMessageDialog(null, "File '" + nameOfTheFileToLoad + "' not found", "Error",
						JOptionPane.ERROR_MESSAGE);
				InputStream localInputStream = ClassLoader.getSystemClassLoader()
						.getResourceAsStream("ChesstoryData/parameters/" + nameOfTheFileToLoad + ".txt");
				byte[] buffer = new byte[localInputStream.available()];
				localInputStream.read(buffer);
				File targetFile = new File("ChesstoryData/parameters/" + nameOfTheFileToLoad + ".txt");
				OutputStream outStream = new FileOutputStream(targetFile);
				outStream.write(buffer);
				outStream.close();
				BufferedReader br = new BufferedReader(new InputStreamReader(localInputStream));
				br.close();
				fileToLoad = new File("ChesstoryData/parameters/" + nameOfTheFileToLoad + "txt");// we
																									// reset
																									// the
																									// fileToLoad
																									// with
																									// the
																									// new
																									// one
																									// we've
																									// just
																									// created
			} catch (Exception ex) {// if the cloning has failed
				JOptionPane.showMessageDialog(null,
						"The operation 'cloning the file " + nameOfTheFileToLoad + " from jar' failed", "Fatal Error",
						JOptionPane.ERROR_MESSAGE);
				res = new int[1];
				res[0] = -2;
				return res;
			}
			res = new int[1];
			res[0] = -1;
			return res;
		}
		return res;

	}

	public static void saveParameters(String nameOfTheFileToSave, int parameters[]) {
		File fileToSave;
		try {
			fileToSave = new File("ChesstoryData/parameters/" + nameOfTheFileToSave + ".txt");
			FileWriter fileWriter = new FileWriter(fileToSave);
			System.out.println("---->Saving parameters file");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			String tempStr = "";
			for (int i = 0; i < parameters.length; i++) {
				if (i != 0) {
					tempStr += ",";
				}
				tempStr += parameters[i];
			}
			bufferedWriter.write(tempStr);
			bufferedWriter.close();
			System.out.println("---->Save successful");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error : File was not saved", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void loadParametersToChesstoryConstants() {
		// TODO setBoolelolo etc
		int[] paramToLoad = loadParameters("mainParameters");
		YACG_DISPLAYHELP = true;// (paramToLoad[0] == 1);
		CHGA_TIMER = 0;// paramToLoad[1];
		// TODO stop the errors
	}
}
