package spreadsheets.model;

import java.util.List;


/**
 * Implements the append function which append two strings together.
 */
public class AppendVisitor implements FormulaVisitor<String> {

  Spreadsheet model;

  public AppendVisitor(Spreadsheet model) {

    this.model = model;
  }

  /**
   * Process the Boolean class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public String booleanVisitor(boolean b) {
    return Boolean.toString(b);
  }

  /**
   * Process a Double class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public String doubleVisitor(double b) {
    return Double.toString(b); // takes in two arguments
  }

  /**
   * Process the Strign class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public String valueStringVisitor(String b) {
    return b;
  }

  /**
   * Process a reference.
   *
   * @param reference the value
   * @return the desired result
   */
  @Override
  public String referenceVisitor(List<Coord> reference) {
    String appending = "";
    for (Coord c : reference) {
      if (model.getCellAt(c).getFormula() == null) {
        appending = "";
      } else {
        Cell cell = (Cell) model.getCellAt(c);
        appending = appending + cell.getFormula().evaluate().acceptArgs(new GetString());
      }

    }
    return appending;
  }

  /**
   * Process a function.
   *
   * @param f the value
   * @return the desired result
   */
  @Override
  public String functionVisitor(Function f) {
    String appending = "";
    for (Formula s : f.getArgs()) {
      appending = appending + s.evaluate().acceptArgs(new GetString());
    }
    return appending;
  }
}
