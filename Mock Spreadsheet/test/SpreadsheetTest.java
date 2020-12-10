import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.ModelSpreadsheet;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import edu.cs3500.spreadsheets.model.ValueDouble;
import edu.cs3500.spreadsheets.model.ValueString;
import edu.cs3500.spreadsheets.model.WorksheetReader;

import static org.junit.Assert.assertEquals;

/**
 * SpreadSheet Tester class.
 * tests the different funcionality for the builder and SpreadSheet classes.
 */
public class SpreadsheetTest {

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

  WorksheetReader.WorksheetBuilder<ModelSpreadsheet> builder = new Spreadsheet.Builder();
  FileReader file2;

  {
    try {
      file2 = new FileReader("test/SelfRefData.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("no dile ");
    }
  }

  ModelSpreadsheet model2 = WorksheetReader.read(builder, file2);


  WorksheetReader.WorksheetBuilder<ModelSpreadsheet> builder2 = new Spreadsheet.Builder();
  FileReader file3;

  {
    try {
      file3 = new FileReader("test/DirectRefData.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("no dile ");
    }
  }

  ModelSpreadsheet model3 = WorksheetReader.read(builder, file3);

  @Test
  public void TestSum() {

    assertEquals("75.000000", ws1.evaluate(new Coord(1, 2), ws1).toString());
  }

  @Test
  public void TestBooleanDouble() {

    assertEquals("2.000000", ws1.evaluate(new Coord(2, 2), ws1).toString());
  }

  @Test
  public void TestRegionRefs() {

    assertEquals("6.000000", ws1.evaluate(new Coord(4, 3), ws1).toString());
  }

  @Test
  public void TestRefs() {

    assertEquals("8.000000", ws1.evaluate(new Coord(2, 3), ws1).toString());
  }

  @Test
  public void TestProduct() {

    assertEquals("24.000000", ws1.evaluate(new Coord(2, 4), ws1).toString());
  }

  @Test
  public void TestAppend() {

    assertEquals("HELLO", ws1.evaluate(new Coord(2, 5), ws1).toString());
  }

  @Test
  public void TestLessThan() {

    assertEquals("false", ws1.evaluate(new Coord(1, 6), ws1).toString());
  }

  @Test
  public void TestLessThanTrue() {

    assertEquals("true", ws1.evaluate(new Coord(1, 7), ws1).toString());
  }

  @Test
  public void TestRefSameCell() {

    assertEquals("6.000000", ws1.evaluate(new Coord(3, 4), ws1).toString());
  }

  @Test
  public void TestCreateCell() {

    assertEquals("2.000000",
            ws1.createCellModel(new Coord(5, 5), new ValueDouble(2)).getFormula().toString());
  }

  @Test
  public void TestCellAddedToTheGrid() { /// isnt tested properly

    assertEquals("2.000000", ws1.getCellAt(new Coord(3, 4)).toString());
  }

  /**
   * tests indirect self referential data.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInderectSelfRef() {

    assertEquals("there is a cycle", model2.evaluate(new Coord(4, 6), model2));
  }

  /**
   * tests direct self referential data.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestDirectSelfRef() {

    assertEquals("there is a cycle", model3.evaluate(new Coord(1, 5), model3));
  }

  @Test
  public void testAddToSpreadSheet() {
    HashMap<Coord, ICell> ss = new HashMap<>();
    Coord c1 = new Coord(1,1);
    ss.put(c1, new Cell(new ValueString("Cell 1"), c1));
    Coord c2 = new Coord(1,2);
    ss.put(c1, new Cell(new ValueString("Cell 2"), c2));

    Spreadsheet spreadSht = new Spreadsheet();
    spreadSht.createCellModel(c1, new ValueString("Cell 1"));
    spreadSht.createCellModel(c2, new ValueString("Cell 2"));

    for (Map.Entry<Coord, ICell> entry : ss.entrySet()) {
      assertEquals(entry, spreadSht.getCellAt(entry.getKey()));
    }
  }

  @Test
  public void testEmpty() {
    HashMap<Coord, ICell> ss = new HashMap<>();

    Spreadsheet spreadSht = new Spreadsheet();


    for (Map.Entry<Coord, ICell> entry : ss.entrySet()) {
      assertEquals(entry, spreadSht.getCellAt(entry.getKey()));
    }
  }
}
