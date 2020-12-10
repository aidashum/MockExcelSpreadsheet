import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ModelSpreadsheet;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;

import static org.junit.Assert.assertEquals;

/**
 * tests the blank and ocupied cells.
 */
public class CellTest {

  WorksheetReader.WorksheetBuilder<ModelSpreadsheet> ws = new Spreadsheet.Builder();
  FileReader file;

  {
    try {
      file = new FileReader("test/testfile.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("no dile ");
    }
  }

  ModelSpreadsheet ws1 = WorksheetReader.read(ws, file);


  @Test
  public void TestValDouble() {

    assertEquals("3.000000",
            ws1.getCellAt(new Coord(1, 1)).getFormula().evaluate().toString());
  }


  @Test
  public void TestValString() {

    assertEquals("HI",
            ws1.getCellAt(new Coord(3, 5)).getFormula().evaluate().toString());
  }

  @Test
  public void TestValBool() {

    assertEquals("false",
            ws1.getCellAt(new Coord(3, 6)).getFormula().evaluate().toString());
  }

  @Test
  public void TestCellFormula() {

    assertEquals("10.000000",
            ws1.getCellAt(new Coord(4, 6)).getFormula().evaluate().toString());
  }

  /**
   * a cell is blank when the formula is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestBlankCell() {

    assertEquals(null,
            ws1.getCellAt(new Coord(9, 9)).getFormula().evaluate().toString());
  }
}
