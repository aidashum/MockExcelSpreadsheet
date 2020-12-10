
package spreadsheets.model;

/**
 * Represents a Blank cell in the excel spreadsheet. the value/info can either be deleted or not
 * added.
 */
public class Blank implements ICell {

  private final Formula f;


  public Blank() {
    f = null;
  }


  /**
   * Gets the formula of an ICell.
   *
   * @returns a Formula
   */
  public Formula getFormula() {
    return this.f;
  }

  /**
   * Checks to see if an ICell is Blank or An actual cell.
   *
   * @return a boolean
   */
  @Override
  public boolean isCell() {
    return false;
  }
}