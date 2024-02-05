package src.Logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Arena class represents a game arena with a specified width and height.
 * It contains methods to set the dimensions of the arena and load the arena from a file.
 */
public class Arena {
    private int width, height;
    private boolean[][] matrix;

    public Arena(String filePath){
        setDimensions(filePath);
        loadArena(filePath);
    }

    public void print(){
        for(int j=0; j< height; j++){
            for(int i=0; i< width; i++) if (!matrix[j][i]) System.out.print("## "); else System.out.print("   ");
            System.out.println();
        }
    }
        //set width and height to the file dimensions
        private void setDimensions(String filePath) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String line;
                int lineCount = 0;
                while ((line = br.readLine()) != null) {
                    if (lineCount == 0) width = line.length()/2 + 1;
                    lineCount++;
                }
                height = lineCount;
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        //fill arena with values from file
        public void loadArena(String filePath) {
            setDimensions(filePath);
            matrix = new boolean[height][width];
            try {
                FileReader fileReader = new FileReader(filePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                int row = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    for (int col = 0; col < line.length(); col += 2) { // increment by 2 to skip spaces
                        matrix[row][col / 2] = Character.getNumericValue(line.charAt(col)) == 0 ? false : true; // divide by 2 to adjust for spaces
                    }
                    row++;
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
