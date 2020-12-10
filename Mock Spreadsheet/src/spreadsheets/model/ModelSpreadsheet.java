package spreadsheets.model;

/**
 * Represents our model Interface.
 */
public interface ModelSpreadsheet {

  /**
   * get the cell at a specific coord.
   *
   * @param coord coord of the cell to be gotten
   * @return the cell at the given coord.
   */
  ICell getCellAt(Coord coord);

  /**
   * Creates a cell given its coordinates and formula.
   *
   * @param coord the coord of the cell to be created
   * @param f     the formula of the new cell.
   * @return an ICell.
   */
  ICell createCellModel(Coord coord, Formula f);

  /**
   * Evaluates the cell at a coordinate.
   *
   * @param coord the coord of the cell to be evaluated.
   * @param model the spreadsheet the cell is going to be evaluated on.
   * @return Value  the evaluated cell.
   */
  Value evaluate(Coord coord, ModelSpreadsheet model);

  /**
   * deletes the cell at the given coord.
   *
   * @param coord coord of the cell to be deleted.
   */
  void delete(Coord coord);

  /**
   * modify the content of the given cell with the given contents.
   *
   * @param coord    coord of cell to be modified.
   * @param contents contents of cell to be changed.
   */
  void modify(Coord coord, String contents);

  /**
   * Visits all the grids entries.
   *
   * @return true if all the entries have been visited.
   */
  boolean visitAll();
}