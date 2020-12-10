package spreadsheets.model;

import java.util.Stack;

/**
 * <p>Represents a Formula.  A Formula is one of</p>
 *
 * <ul>
 * <li>A Value</li>
 * <li>A Reference</li>
 * <li>A Function</li>
 * </ul>
 */
public interface Formula {


  /**
   * accept method that accepts a list of formulaVisitors
   *
   * @param visitor list of formulaVisitors
   * @param <T>     A Coord
   * @return A Coord
   */
  <T> T accept(FormulaVisitor<T> visitor);


  /**
   * Evaluates the formula.
   *
   * @return the evaluated formula.
   */
  Value evaluate();

  /**
   * helps with cycles in a formula.
   *
   * @param seen list of seen coords
   * @return true if there is a loop
   */
  boolean depHelp(Stack<Coord> seen);

}