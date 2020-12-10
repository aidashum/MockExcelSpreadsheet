package spreadsheets.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

/**
 * Helps create the excel Spreadsheet.
 */
public class Spreadsheet implements ModelSpreadsheet {

  private HashMap<Coord, ICell> grid;

  public Spreadsheet() {
    this.grid = new HashMap<>();

  }

  public Spreadsheet(HashMap<Coord, ICell> grid) {
    this.grid = grid;
  }


  /**
   * getter method that gets the cell at the given Coord.
   *
   * @param coord the Coord to get the cell at.
   * @return The Cell at that coord.
   */
  @Override
  public ICell getCellAt(Coord coord) {

    if (grid.get(coord) == null) {
      throw new IllegalArgumentException("the cell is null");
    } else {
      return grid.get(coord);
    }
  }

  /**
   * Visits all the grids entries.
   *
   * @return true if all the entries have been visited.
   */
  @Override
  public boolean visitAll() {

    Stack<Coord> visitedList = new Stack<>();
    for (Map.Entry<Coord, ICell> entry : grid.entrySet()) {

      ICell cell = this.grid.get(entry.getKey());
      // visitedList.push(entry.getKey());
      cell.getFormula().depHelp(visitedList);

    }

    return false;
  }


  /**
   * Creates a cell given its coordinates and formula.
   *
   * @param coord the coord of the cell to be created
   * @param f     the formula of the new cell.
   * @return an ICell.
   */
  @Override
  public ICell createCellModel(Coord coord, Formula f) {

    return new Cell(f, coord);
  }

  /**
   * Evaluates the cell at a coordinate.
   *
   * @param coord the coord of the cell to be evaluated.
   * @param model the spreadsheet the cell is going to be evaluated on.
   * @return Value  the evaluated cell.
   */
  @Override
  public Value evaluate(Coord coord, ModelSpreadsheet model) {
    if (this.visitAll()) {
      throw new IllegalArgumentException("there is a cycle");
    } else {
      return grid.get(coord).getFormula().evaluate();
    }

  }

  /**
   * deletes the cell at the given coord.
   *
   * @param coord coord of the cell to be deleted.
   */
  @Override
  public void delete(Coord coord) {
    this.grid.replace(coord, new Cell(null, coord));
  }

  /**
   * modify the content of the given cell with the given contents.
   *
   * @param coord    coord of cell to be modified.
   * @param contents contents of cell to be changed.
   */
  @Override
  public void modify(Coord coord, String contents) {
    Sexp p = Parser.parse(contents);
    Formula formula = p.accept(new TransformToFormula(this));
    this.grid.replace(coord, new Cell(formula, coord));
  }


  /**
   * Builder innerclass that helps build the Excel Spreadsheet.
   */
  public static class Builder implements WorksheetBuilder<ModelSpreadsheet> {
    private Spreadsheet model;


    public Builder() {
      model = new Spreadsheet();
    }

    @Override
    public WorksheetBuilder<ModelSpreadsheet> createCell(int col, int row, String contents) {
      String withoutEqual = contents.replace("=", "");
      Sexp p = Parser.parse(withoutEqual);
      Coord c = new Coord(col, row);
      Formula f = p.accept(new TransformToFormula(this.model));
      ICell cell = model.createCellModel(c, f);
      model.grid.put(c, cell);

      return this;
    }

    @Override
    public ModelSpreadsheet createWorksheet() {
      return model;
    }
  }

}