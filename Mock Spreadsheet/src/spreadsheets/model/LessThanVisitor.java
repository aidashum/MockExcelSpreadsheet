package spreadsheets.model;

import java.util.List;

/**
 * represents a class that implements the less than function based on which type of Formula its
 * given.
 */
public class LessThanVisitor implements FormulaVisitor<Boolean> {
  Spreadsheet model;

  public LessThanVisitor(Spreadsheet model) {
    this.model = model;
  }

  /**
   * Process the Boolean class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public Boolean booleanVisitor(boolean b) {
    return b;
  }

  /**
   * Process a Double class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public Boolean doubleVisitor(double b) {
    return false;
  }

  /**
   * Process the Strign class.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public Boolean valueStringVisitor(String b) {
    return false;
  }

  /**
   * Process a reference.
   *
   * @param reference the value
   * @return the desired result
   */
  @Override
  public Boolean referenceVisitor(List<Coord> reference) {
    for (Coord ref : reference) {
      Value val = this.model.getCellAt(ref).getFormula().evaluate();
      if (!val.isDouble()) {
        throw new IllegalArgumentException("the value is not a number");
      } else {
        val.acceptArgs(new GetDouble());
      }

    }
    return null;
  }

  /**
   * Process a function.
   *
   * @param f the value
   * @return the desired result
   */
  @Override
  public Boolean functionVisitor(Function f) {
    if (f.getArgs().size() != 2) {
      throw new IllegalArgumentException("This function can only take in 2 arguments!!");
    }
    for (int i = 0; i < f.getArgs().size() - 1; i++) {
      if (f.getArgs().get(i).evaluate().acceptArgs(new GetDouble())
              > f.getArgs().get(i + 1).evaluate().acceptArgs(new GetDouble())) {
        return false;
      }
    }
    return true;

  }
}