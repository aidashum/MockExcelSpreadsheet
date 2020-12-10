package spreadsheets.model;

import java.util.List;
import java.util.Stack;

/**
 * Represents a Function which is a type of Formula. Evaluates the function.
 */
public class Function implements Formula {
  private Spreadsheet model;
  private List<Formula> args;
  private String operator;

  /**
   * constructs a function type.
   * @param args  The list of arguments of the function.
   * @param operator  The operator of the function call.
   * @param model     The Model spreadsheet.
   */
  public Function(List<Formula> args, String operator, Spreadsheet model) {
    this.args = args;
    this.operator = operator;
    this.model = model;
  }


  /**
   * accept method that accepts a list of formulaVisitors.
   *
   * @param visitor list of formulaVisitors
   * @param <T>     A Cell
   * @return A Cell
   */
  @Override
  public <T> T accept(FormulaVisitor<T> visitor) {

    return visitor.functionVisitor(this);
  }


  /**
   * getter method.
   *
   * @return the list of arguments in the function.
   */
  public List<Formula> getArgs() {
    return args;
  }

  /**
   * Evaluates the formula.
   *
   * @return the evaluated formula.
   */
  @Override
  public Value evaluate() {

    if (operator.equals("SUM")) {

      return new ValueDouble(this.accept(new SumVisitor(this.model)));

    } else if (operator.equals("PRODUCT")) {
      return new ValueDouble(this.accept(new ProductVisitor(this.model)));
    } else if (operator.equals("<")) {
      return new ValueBoolean(this.accept(new LessThanVisitor(this.model)));
    } else if (operator.equals("APPEND")) {
      return new ValueString(this.accept(new AppendVisitor(this.model)));
    } else {
      throw new IllegalArgumentException("the function doesn't have an operator");
    }
  }


  /**
   * helps with cycles in a formula.
   *
   * @param seen list of seen coords
   * @return true if there is a loop
   */
  @Override
  public boolean depHelp(Stack<Coord> seen) {
    boolean b;
    for (Formula arg : this.args) {
      b = arg.depHelp(seen);
      if (b) {
        throw new IllegalArgumentException("there is a cycle2");
      }
    }
    return false;
  }


}