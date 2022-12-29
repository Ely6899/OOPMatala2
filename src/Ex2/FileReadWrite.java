package Ex2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


/**
 * This class holds all file reading and writing functions which will be used by the Ex2_1 class.
 * Each function works on a single file.
 */
public class FileReadWrite {


    /**
     * Writes to the given file (lineCount) times a given string to the file, save it in the dist, and returns
     * the new file name, formatted by .txt
     * @param fileName Name of a file.
     * @param lineCount Amount of lines for the file.
     * @return New formatted file name(Add .txt format to the name).
     */
    public static String writeFile(String fileName, int lineCount) {
        try {
            String txtFormat = String.format("%s%s",fileName,".txt");
            FileOutputStream fileOutputStream = new FileOutputStream(txtFormat, false); //Override changes

            //Write a string (lineCount) times
            for (int i = 0; i < lineCount; i++) {
                fileOutputStream.write("albam\n".getBytes());
            }

            fileOutputStream.close(); //Close file stream.
            return txtFormat;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Counts the amount of lines the given file has, not including the last empty line generated.
     * @param fileName Name of a file.
     * @return line count of a file
     */
    public static int readFileAndCountRows(String fileName){
        //Create new file object
        File file = new File(fileName);

        try {
            //Create Scanner instance
            //Scans given file
            Scanner fileScanner = new Scanner(file);

            int rowCount = 0;

            //Read each line in the file associated to the scanner.
            while(fileScanner.hasNextLine()){
                fileScanner.nextLine();
                rowCount++;
            }
            //Close scanner when done iterating through file.
            fileScanner.close();

            return rowCount;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
