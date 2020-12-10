package spreadsheets.model;

import java.util.Stack;

/**
 * Represents the String value of a Cell.
 */
public class ValueString implements Value {
  private final String str;


  public ValueString(String str) {
    this.str = str;
  }

  /**
   * accept method that accepts a list of formulaVisitors.
   *
   * @param visitor list of formulaVisitors
   * @param <T>     A Coord
   * @return A Coord
   */
  @Override
  public <T> T accept(FormulaVisitor<T> visitor) {
    return visitor.valueStringVisitor(this.str);
  }


  /**
   * Evaluates the formula.
   *
   * @return the evaluated formula.
   */
  @Override
  public Value evaluate() {
    return this;
  }

  /**
   * helps with cycles in a formula.
   *
   * @param seen list of seen coords
   * @return true if there is a loop
   */
  @Override
  public boolean depHelp(Stack<Coord> seen) {
    return false;
  }


  /**
   * Overrides the toString method.
   *
   * @return the given value cell.
   */
  @Override
  public String toString() {
    return "" + this.str;
  }


  /**
   * Overrides the Object class equals method that compares the 2 values. The first value would be
   * the value that was initially at the cell. The second would be the one from the formula.
   *
   * @param o the Object to be compared in the Value subclass.
   * @return true if the 2 are the same/equals.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof ValueString) {
      ValueString c = (ValueString) o;
      return this.str.equals(c.str);
    }
    return false;
  }

  /**
   * Overrides the hashCode method in the Object class.
   *
   * @return the hashCode of the Value subclass.
   */
  @Override
  public int hashCode() {
    return str.hashCode();
  }

  /**
   * accepter that accepts a valueToArg type to be visited.
   *
   * @param visitor the ValueToArg visitor
   * @param <K>     Coord.
   * @return The Coord.
   */
  @Override
  public <K> K acceptArgs(ValueToArg<K> visitor) {
    return visitor.stringVisitor(this.str);
  }

  /**
   * Checks to see if this value is a double or not.
   * @return true if it is of type Double
   */
  @Override
  public boolean isDouble() {
    return false;
  }
}