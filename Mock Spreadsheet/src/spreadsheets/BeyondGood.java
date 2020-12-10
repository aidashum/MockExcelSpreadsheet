package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ModelSpreadsheet;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   *
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /*
      TODO: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */

    if (args.length == 4 && (args[0].equals("-in") && (args[2].equals("-eval")))) {
      try {
        FileReader file = new FileReader(new File(args[1]));
        WorksheetReader.WorksheetBuilder<ModelSpreadsheet> ws = new Spreadsheet.Builder();

        ModelSpreadsheet ws1 = WorksheetReader.read(ws, file);
        String coord = args[3];
        String rowString = coord.substring(coord.length() - 1);
        int row = Integer.parseInt(rowString);
        int col = Coord.colNameToIndex(coord.substring(0, 1));

        System.out.print(ws1.evaluate(new Coord(col, row), ws1).toString());
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Cannot find given file!");
      } catch (IllegalArgumentException e1) {
        System.out.print(e1.getMessage());
      }

    }

  }


}
