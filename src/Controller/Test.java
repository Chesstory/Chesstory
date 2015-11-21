package Controller;
import java.io.*;

import javax.swing.JFileChooser;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  // The name of the file to open.
        String fileName = "./Saves/temp.txt";
        String strToWrite="hello i'm Franky";
        String line = null;
        
        
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("./Saves/"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
        	try(FileReader fileReader = new FileReader(chooser.getSelectedFile())) {
        		BufferedReader bufferedReader = new BufferedReader(fileReader);
        		while((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                } 
            }catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(
                        "Unable to open file '" + 
                        fileName + "'"); 
            }
        }
       
        /*
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        
        
        
        
        
        
        
        
        
        
        
        
        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write("Hello there,");
            bufferedWriter.write(" here is some text.");
            bufferedWriter.newLine();
            bufferedWriter.write("We are writing");
            bufferedWriter.write(" the text to the file.");

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }*/
        
        
        
        
        
        
        
        
        
        
     // This will reference one line at a time
        /* line=null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(chooser.getSelectedFile());

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }*/
        /*JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("./Saves/"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
        	try(FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt")) {
                fw.write(strToWrite.toString());
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }*/
        
        

        /*try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write("PENIS");
            bufferedWriter.newLine();
            bufferedWriter.write("We are writing");
            bufferedWriter.write("PENISES");

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        } */
	}

}
