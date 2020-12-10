package spreadsheets.model;

/**
 * Gets the primitive type in our implemented Value Classes.
 */
public class GetDouble implements ValueToArg<Double> {

  /**
   * Visitor for a boolean value.
   *
   * @param b boolean value to be visited.
   * @return 0.0 becuase a boolean is not a double
   */
  @Override
  public Double booleanVisitor(boolean b) {
    return 0.0;
  }

  /**
   * visitor for a string value.
   *
   * @param str string value to be visited.
   * @return 0.0 because a string is not a double.
   */
  @Override
  public Double stringVisitor(String str) {
    return 0.0;
  }


  /**
   * visitor for a double.
   *
   * @param d the double to be visited.
   * @return the double d.
   */
  @Override
  public Double doubleVisitor(double d) {
    return d;
  }
}
