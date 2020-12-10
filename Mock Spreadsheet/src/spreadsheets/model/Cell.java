package spreadsheets.model;

/**
 * Represents a cell with data in the excel spreadsheet.
 */
public class Cell implements ICell {

  private final Formula formula;
  private final Coord coord;

  /**
   * constructs a cell.
   * @param formula   The formula of this given Cell.
   * @param coord    The coord of this Cell.
   */
  public Cell(Formula formula, Coord coord) {

    this.formula = formula;

    //Java Style saying we should delete this field
    //we use this field when creating the cells.
    //is it possible to get the 2 points back?
    this.coord = coord;
  }


  /**
   * Gets the formula of an ICell.
   *
   * @returns a Formula
   */
  public Formula getFormula() {
    return this.formula;
  }


  /**
   * Checks to see if an ICell is Blank or An actual cell.
   *
   * @return a boolean
   */
  @Override
  public boolean isCell() {
    return true;
  }

}