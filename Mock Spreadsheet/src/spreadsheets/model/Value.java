package spreadsheets.model;

/**
 * The value type of a Formual.
 */
public interface Value extends Formula {

  /**
   * Overrides the Object class equals method that compares the 2 values. The first value would be
   * the value that was initially at the cell. The second would be the one from the formula.
   *
   * @param o the Object to be compared in the Value subclass.
   * @return true if the 2 are the same/equals.
   */
  @Override
  boolean equals(Object o);

  /**
   * Overrides the hashCode method in the Object class.
   *
   * @return the hashCode of the Value subclass.
   */
  @Override
  int hashCode();

  /**
   * accepter that accepts a valueToArg type to be visited.
   *
   * @param visitor the ValueToArg visitor
   * @param <K>     Coord.
   * @return The Coord.
   */
  <K> K acceptArgs(ValueToArg<K> visitor);

  /**
   * Checks to see if this value is a double or not.
   * @return true if it is of type Double
   */
  boolean isDouble();
}