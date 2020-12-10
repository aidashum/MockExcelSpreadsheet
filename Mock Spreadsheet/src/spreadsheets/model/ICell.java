package spreadsheets.model;

/**
 * Represents a cell in an excel spreadsheet. can either be Blank or Cell.
 */
public interface ICell {
  /**
   * Checks to see if an ICell is Blank or An actual cell.
   *
   * @return a boolean
   */
  boolean isCell();

  /**
   * Gets the formula of an ICell.
   *
   * @returns a Formula
   */
  Formula getFormula();

}