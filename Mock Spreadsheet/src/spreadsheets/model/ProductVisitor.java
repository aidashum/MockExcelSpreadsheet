package spreadsheets.model;

import java.util.List;

/**
 * Visitor class that helps with coming up with the product of a formula.
 */
public class ProductVisitor implements FormulaVisitor<Double> {

  Spreadsheet model;

  public ProductVisitor(Spreadsheet model) {

    this.model = model;
  }

  /**
   * Process the Boolean class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public Double booleanVisitor(boolean b) {
    return 0.0;
  }

  /**
   * Process a Double class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public Double doubleVisitor(double b) {
    return b;
  }

  /**
   * Process the Strign class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public Double valueStringVisitor(String b) {
    return 0.0;
  }

  /**
   * Process a reference.
   *
   * @param reference the value
   * @return the desired result
   */
  @Override
  public Double referenceVisitor(List<Coord> reference) {

    double sum = 1.0;
    for (Coord c : reference) {
      if (model.getCellAt(c).getFormula() == null) {
        sum *= 1;
      } else {
        Cell cell = (Cell) model.getCellAt(c);
        sum *= cell.getFormula().evaluate().acceptArgs(new GetDouble());
      }

    }
    return sum;
  }


  /**
   * Process a function.
   *
   * @param f the value
   * @return the desired result
   */
  @Override
  public Double functionVisitor(Function f) {
    double product = 1.0;
    for (Formula formula : f.getArgs()) {
      product *= formula.evaluate().acceptArgs(new GetDouble());
    }
    return product;
  }
}
